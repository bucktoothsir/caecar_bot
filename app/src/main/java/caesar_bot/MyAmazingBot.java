package caesar_bot;

//import my_caesar_bot.Config;
//import my_caesar_bot.commands;


import java.util.Map;

import caesar_bot.commands.CommandFactory;
import java.io.IOException;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class MyAmazingBot extends TelegramLongPollingBot {
    private Config config;
    private CommandFactory commandFactory;
    private final String COMMAND_PREFIX = "/";
    private ApiClient apiClient;
    private SqlClient sqlClient = new SqlClient();

    public MyAmazingBot(){
        try{
            config = new Config("config.properties");
            commandFactory = new CommandFactory();
            apiClient = new ApiClient();
            sqlClient.read();
            sqlClient.write();
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    /**
     * Return if the message is a command
     *
     * @param message_text to process
     * @@return True if message_text is a valid commad
     */
    private boolean isCommand(String message_text) {
        if (message_text.startsWith(COMMAND_PREFIX) && commandFactory.hasCommand(message_text.substring(1))){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText().trim();
            if (isCommand(message_text)){
                commandFactory.getCommand(message_text.substring(1)).execute(update);
            }
            else{
                encryptMessage(update);
            }
        }
    }

    private void encryptMessage(Update update) {
        String message_text = update.getMessage().getText().trim();
        long chat_id = update.getMessage().getChatId();
        int index = message_text.lastIndexOf(' ');
        String key_text = message_text.substring(index).trim();
        try {
            int key = NumberUtils.createInteger(key_text);
            String plainText = message_text.substring(0, index).trim();
            ImmutableMap<String, Object> values = ImmutableMap.<String, Object>builder().
                put("plaintext", plainText).
                put("key", key).build();
            Map<String, String> resultMap = apiClient.post(values);
            String cipherText = resultMap.get("ciphertext");
            // Create a message object object
            SendMessage message = new SendMessage();
            message.setChatId(Long.toString(chat_id));
            message.setText(cipherText);
            try {
               execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            System.out.println("key should be a valid number");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); 
        }

    }

    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
        return config.username;
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return config.token;
    }

    public void read() {
        sqlClient.read();
    }

    public void write() {
        sqlClient.write();
    }
}
