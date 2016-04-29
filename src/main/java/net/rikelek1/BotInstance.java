package net.rikelek1;

import net.rikelek1.GUI.MainWindow;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.EventSubscriber;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.impl.events.*;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotInstance {
    private MainWindow window;

    private volatile IDiscordClient client;
    private IPrivateChannel privateChannel;
    private IMessage message;
    private IChannel messageChannel, channelGeneral, channelAFK, channelDev;
    private Optional<IVoiceChannel> voiceChannel;
    private String email, password, token, messageContent;
    private final AtomicBoolean reconnect = new AtomicBoolean(true);

    private boolean newMessage, isRunning = false;

    private ArrayList<String> messageList = new ArrayList<>();

//    public BotInstance(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }

    public BotInstance(String token, MainWindow guiWindow) {
        this.token = token;
        this.window = guiWindow;
    }

    public void login() throws DiscordException {
//        if(token == null) {
//            client = new ClientBuilder().withLogin(email, password).login();
//        } else {
//            client = new ClientBuilder().withToken(token).login();
//        }
        client = new ClientBuilder().withToken(token).login();
        client.getDispatcher().registerListener(this);
    }

    @EventSubscriber
    public void onReady(ReadyEvent event) {
        channelGeneral = client.getChannelByID("140212795592409088");
        channelAFK = client.getChannelByID("140214754277982208");
        channelDev = client.getChannelByID("171800511245320193");
        MessageList generalMessages = channelGeneral.getMessages();
        for (IMessage message:generalMessages) {
            System.out.println(message.getContent());
        }
        System.out.println("Botelek1 is ready");
        messageList.add("Botelek1 is ready");
        updateMessages();
        isRunning = true;
    }

    @EventSubscriber
    public void onDisconnect(DiscordDisconnectedEvent event) {
        if(reconnect.get()) {
            System.out.println("Reconnecting...");
            messageList.add("Reconnecting...");
            updateMessages();
            try {
                login();
            } catch (DiscordException e) {
                System.out.println("Failed to reconnect: " + e);
                isRunning = false;
            }
        } else {
            isRunning = false;
        }
    }

    @EventSubscriber
    public void onUserJoin(UserJoinEvent event) {
        sendMessage("Welcome " + event.getUser().getName() + "!");
    }

    @EventSubscriber
    public void onInvite(InviteReceivedEvent event) {
        try {
            event.getInvite().delete();
        } catch (HTTP429Exception | DiscordException e) {
            e.printStackTrace();
        }
    }

    @EventSubscriber
    public void onMessageReceived(MessageReceivedEvent event) {
        message = event.getMessage();
        messageChannel = message.getChannel();
        messageContent = message.getContent();
        String[] messageContentSplit = messageContent.split(" ");

        addToMessageList();
        updateMessages();

//        System.out.println(messageList);
//        System.out.println("Message channel: " + messageChannel);
//        for(int i = 0; i < client.getGuilds().size(); i++) {
//            System.out.println("Guild " + i + ": " + client.getGuilds().get(i).getName());
//        }

        if(messageContent.startsWith("!")) {
            switch(messageContent) {
                case "!info":
                    sendMessage("Botelek1 v1.0\nCoded by Rikki \"Rikelek1\" Lanouette using Discord4J v2.4.4\nUse !help to get a list of available commands.");
                    break;

                case "!help":
                    sendMessage("List of available commands:");
                    break;

                case "!test":
                    sendMessage("This is a test command.");
                    try {
                        privateChannel = client.getOrCreatePMChannel(client.getUserByID(message.getAuthor().getID()));
                        privateChannel.sendMessage("This is a test command");
                    } catch (DiscordException | HTTP429Exception | MissingPermissionsException e) {
                        e.printStackTrace();
                    }
                    break;

                case "!join":
                    requestVoice();

                   break;

                case "!stop":
                    terminate();
                    break;

                default:
                    break;
            }
        }
    }

    private void addToMessageList() {
        if(messageList.size() <= 10) {
            messageList.add("\n" + message.getAuthor().getName() + ": " + messageContent);
        } else {
            messageList.remove(0);
            messageList.add("\n" + message.getAuthor().getName() + ": " + messageContent);
        }
    }

    private void addToMessageList(String message) {
        if(messageList.size() <= 10) {
            messageList.add(message);
        } else {
            messageList.remove(0);
            messageList.add(message);
        }
    }

    public void updateMessages() {
        window.setMessages(messageList);
        window.updateMessages();
    }

    public void sendMessage(String message, IChannel channel) {
        try {
            new MessageBuilder(client).withChannel(channel).withContent(message).build();
        } catch (HTTP429Exception | DiscordException | MissingPermissionsException e) {
            e.printStackTrace();
        }

        addToMessageList("\nBotelek1:" + message);
        updateMessages();
    }

    public void sendMessage(String message) {
        try {
            new MessageBuilder(client).withChannel(messageChannel).withContent(message).build();
        } catch (HTTP429Exception | DiscordException | MissingPermissionsException e) {
            e.printStackTrace();
        }

        addToMessageList("\nBotelek1: " + message);
        updateMessages();
    }

    public void requestVoice() {

    }

    public void terminate() {
        try {
            sendMessage("Stopping...");
        } catch (RuntimeException e) {
            System.out.println("Not connected to a text channel");
        }
        reconnect.set(false);
        try {
            client.logout();
        } catch (HTTP429Exception | DiscordException e) {
            System.out.println("Logout failed: " + e);
        }

        isRunning = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNewMessage() {
        return newMessage;
    }

    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
