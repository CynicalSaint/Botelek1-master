//import sx.blah.discord.api.EventSubscriber;
//import sx.blah.discord.handle.impl.events.*;
//import sx.blah.discord.handle.obj.IChannel;
//import sx.blah.discord.handle.obj.IMessage;
//import sx.blah.discord.util.DiscordException;
//import sx.blah.discord.util.HTTP429Exception;
//import sx.blah.discord.util.MessageBuilder;
//import sx.blah.discord.util.MissingPermissionsException;
//
//public class AnnotationListener {
//    IMessage message;
//    IChannel messageChannel;
//    String messageContent;
//
//    @EventSubscriber
//    public void onReady(ReadyEvent event) {
//        sendMessage("Botelek1 has successfully started and is now ready!");
//    }
//
//    @EventSubscriber
//    public void onUserJoin(UserJoinEvent event) {
//        sendMessage("Welcome to " + event.getUser().getName() + "!");
//    }
//
//    @EventSubscriber
//    public void onInvite(InviteReceivedEvent event) {
//        try {
//            event.getInvite().delete();
//        } catch (HTTP429Exception | DiscordException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @EventSubscriber
//    public void onMessageReceived(MessageReceivedEvent event) {
//        message = event.getMessage();
//        messageChannel = message.getChannel();
//        messageContent = message.getContent();
//
//        if(messageContent.startsWith("!")) {
//            switch(messageContent) {
//                case "!info":
//                    sendMessage("Botelek1 v1.0\nCoded by Rikki \"Rikelek1\" Lanouette using Discord4J v2.4.4\nUse !help to get a list of available commands.");
//                    break;
//
//                case "!help":
//                    sendMessage("List of available commands:");
//                    break;
//
//                case "!test":
//                    sendMessage("This is a test command.");
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    public void sendMessage(String message, IChannel channel) {
//        try {
//            new MessageBuilder(Botelek1.client).withChannel(channel).withContent(message).build();
//        } catch (HTTP429Exception | DiscordException | MissingPermissionsException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void sendMessage(String message) {
//        try {
//            new MessageBuilder(Botelek1.client).withChannel(messageChannel).withContent(message).build();
//        } catch (HTTP429Exception | DiscordException | MissingPermissionsException e) {
//            e.printStackTrace();
//        }
//    }
//}
