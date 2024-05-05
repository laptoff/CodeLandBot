package fr.laptoff.listeners;

import fr.laptoff.Bot;
import fr.laptoff.managers.Database;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModalInteraction extends ListenerAdapter {

    private static final Database database = Bot.getDatabase();

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event){
        if (event.getModalId().equals("github-connexion")){
            String token = event.getValue("github-token").getAsString();

            try {
                GitHub github = new GitHubBuilder().withOAuthToken(token).build();
                github.checkApiUrlValidity();
            } catch (IOException e) {
                event.replyEmbeds(Bot.getErrorEmbed("**Il y a eu une erreur suite à la verification de votre token... Si vous êtes sûr de vous, veuillez contacter un développeur.**")).setEphemeral(true).queue();
                e.printStackTrace();
            }

            try(PreparedStatement stmt = database.getConnection().prepareStatement("INSERT INTO github (user_id, token) VALUES (?, ?);")){
                stmt.setString(1, event.getUser().getId());
                stmt.setString(2, token);
                stmt.execute();
            } catch (SQLException e){
                event.replyEmbeds(Bot.getErrorEmbed("Une erreur est survenus lors de l'interaction avec notre base de donnée... Veuillez contacter un développeur !")).setEphemeral(true).queue();
            }

            event.replyEmbeds(Bot.getSuccessEmbed("La connexion de votre compte GitHub avec CodeLandBot est un succès !")).queue();

        }
    }

}
