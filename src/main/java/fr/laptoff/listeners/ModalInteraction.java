package fr.laptoff.listeners;

import fr.laptoff.Bot;
import fr.laptoff.BotUser;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.sql.SQLException;

public class ModalInteraction extends ListenerAdapter {

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event){
        if (event.getModalId().equals("github-connexion")){
            String token = event.getValue("github-token").getAsString();

            try {
                GitHub github = new GitHubBuilder().withOAuthToken(token).build();
                github.checkApiUrlValidity();
            } catch (IOException e) {
                event.replyEmbeds(Bot.getErrorEmbed("**Il y a eu une erreur suite à la verification de votre token... Si vous êtes sûr de vous, veuillez contacter un développeur.**")).setEphemeral(true).queue();
                return;
            }

            BotUser user = new BotUser(event.getUser().getId(), token, 0);

            try{
                if(user.isExist()){
                    user.setGithub(token);
                } else {
                    user.register();
                }
            } catch(SQLException e){
                event.replyEmbeds(Bot.getErrorEmbed("Une erreur est survenus lors de l'interaction avec notre base de donnée... Veuillez contacter un développeur ! Code d'erreur: 1")).setEphemeral(true).queue();
                System.out.println(e);
                return;
            }
            
            event.replyEmbeds(Bot.getSuccessEmbed("La connexion de votre compte GitHub avec CodeLandBot est un succès !")).queue();
            return;

        }
    }

}
