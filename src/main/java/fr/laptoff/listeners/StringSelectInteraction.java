package fr.laptoff.listeners;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

public class StringSelectInteraction extends ListenerAdapter {

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event){
        if (event.getComponentId().equals("menu-selector")){

            if (event.getValues().getFirst().equals("github")){
                TextInput input = TextInput.create("github-token", "Token", TextInputStyle.PARAGRAPH)
                        .setPlaceholder("GitHub Token...")
                        .setRequiredRange(5, 1000)
                        .build();

                Modal modal = Modal.create("github-connexion", "Connexion GitHub").addActionRow(input).build();

                event.replyModal(modal).queue();
            }
        }
    }

}
