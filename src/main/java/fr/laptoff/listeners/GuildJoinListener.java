package fr.laptoff.listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent e){
        final Guild guild = e.getGuild();
        if (!guild.getId().equals("1196523803451330771") && !guild.getId().equals("1097155362014117932")){
            Objects.requireNonNull(guild.getDefaultChannel()).asTextChannel().sendMessage("CodeLandBot ne peut pas rejoindre votre serveur, celui-ci n'est pas publique ! Désolé :(").queue();
            guild.leave().queue();
        }
    }

}
