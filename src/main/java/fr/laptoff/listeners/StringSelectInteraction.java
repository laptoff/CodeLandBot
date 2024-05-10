package fr.laptoff.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.sql.SQLException;

import org.jetbrains.annotations.NotNull;

import fr.laptoff.Bot;
import fr.laptoff.BotUser;

public class StringSelectInteraction extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event){

        //GitHub Menu - CodeLand
        if (event.getComponentId().equals("menu-selector")){
            if (event.getValues().getFirst().equals("github")){

                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("GitHub - CodeLand")
                        .setDescription("Menu GitHub de CodeLand.\n Ici, vous pouvez gérer votre compte GitHub ainsi que vos repositories !")
                        .setFooter(event.getUser().getName())
                        .build();

                event.replyEmbeds(embed).addActionRow(
                        StringSelectMenu.create("github-selector")
                                .addOptions(SelectOption.of("Connexion", "connexion")
                                        .withDescription("Connectez votre compte GitHub à CodeLand.")
                                        .withEmoji(Emoji.fromUnicode("U+1F517")))
                                .addOptions(SelectOption.of("Déconnexion", "deconnexion")
                                        .withDescription("Déconnectez votre compte GitHub de CodeLand.")
                                        .withEmoji(Emoji.fromUnicode("U+1F516")))
                                .build()
                ).queue();

            }
        }

        //Connexion GitHub.
        if (event.getComponentId().equals("github-selector")){

            if (event.getValues().getFirst().equals("connexion")){
                TextInput input = TextInput.create("github-token", "Token", TextInputStyle.PARAGRAPH)
                        .setPlaceholder("GitHub Token...")
                        .setRequiredRange(5, 1000)
                        .build();

                Modal modal = Modal.create("github-connexion", "Connexion GitHub").addActionRow(input).build();

                event.replyModal(modal).queue();
            } else if (event.getValues().getFirst().equals("deconnexion")) {
                try {
                    if (BotUser.isExist(event.getUser().getId())) {
                        BotUser user = BotUser.getBotUser(event.getUser().getId());
                        user.setGithub(null);
                        event.replyEmbeds(Bot.getSuccessEmbed("Vous avez bien été déconnecté de votre compte GitHub !"));
                    } else {
                        event.replyEmbeds(Bot.getErrorEmbed("Vous n'êtes pas connecté à GitHub."));
                    }
                } catch (SQLException e) {
                    event.replyEmbeds(Bot.getErrorEmbed("Pardon mais nous avons eu un problème lors de l'interaction avec notre base de donnée... Veuillez contacter un développeur !"));
                }
            }
        }
    }

}
