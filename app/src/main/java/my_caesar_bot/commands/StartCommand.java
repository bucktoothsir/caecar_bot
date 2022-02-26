package my_caesar_bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand extends Command{

    final static private String cmdName = "start";
    final static private String cmdDesc = "Return the basic info of this bot.";

    /**
     * Get the name of this command
     *
     * @@return the cmdName
     */
    public String getCmdName() {
        return cmdName;
    }
    
    /**
     * Get the description of this command
     *
     * @@return the description
     */
    public String getCmdDesc() {
        return cmdDesc;
    }

    public void execute(Update update){
        System.out.println("Hello World");
    }

}
