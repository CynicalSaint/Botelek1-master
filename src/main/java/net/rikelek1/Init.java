//package net.rikelek1;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import sx.blah.discord.util.DiscordException;
//
//public class Init {
//    private static final Logger log = LoggerFactory.getLogger(Init.class);
//
//    public static void main(String[] args) {
//        BotInstance bot;
//
//        if(args.length == 0) {
//            throw new IllegalArgumentException("Please enter an email and password or a token.");
//        } else if(args.length == 1) {
//            bot = new BotInstance(args[0]);
//        } else {
//            bot = new BotInstance(args[0], args[1]);
//        }
//
//        try {
//            bot.login();
//        } catch (DiscordException e) {
//            log.warn("Bot could not start", e);
//        }
//    }
//}
