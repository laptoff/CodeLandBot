package fr.laptoff.listeners;

import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public class SlashCommandListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){

        //Command: /menu
        if (event.getName().equalsIgnoreCase("menu")){
            MessageEmbed embed = new MessageEmbed(
                    null,
                    "Menu CodeLandBot",
                    "Menu de CodeLandBot qui vous permet d'accéder à toutes les fonctionnalités de CodeLandBot ! \n \n *Pour un quelconque problème, veuillez contacter l'équipe de développement de CodeLandBot !*",
                    null,
                    OffsetDateTime.now(),
                    0,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            event.replyEmbeds(embed).addActionRow(
                            StringSelectMenu.create("menu-selector")
                                    .addOptions(SelectOption.of("Github", "github")
                                            .withDescription("Gérez la connexion avec votre compte GitHub.")
                                            .withEmoji(Emoji.fromUnicode("U+1F517")))
                                    .build())
                    .queue();
        }
    }

}