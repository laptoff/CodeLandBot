package fr.laptoff.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessagesListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e){

        if (e.getAuthor().isBot())
            return;

        final Message message = e.getMessage();
        final String messageContent = message.getContentRaw();

        if (messageContent.equalsIgnoreCase("!Ping")){
            MessageChannel channel = e.getChannel();
            channel.sendMessage("Pong !").queue();
        }
    }

}
