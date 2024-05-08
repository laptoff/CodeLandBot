package fr.laptoff;

import fr.laptoff.listeners.*;
import fr.laptoff.managers.Database;
import fr.laptoff.managers.FileManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.time.OffsetDateTime;
import java.util.Properties;

public class Bot {

    private static Database database;

    public static void main(String[] args) throws Exception {

        FileManager.createFile(new File("src/main/resources/config.properties"));

        final Properties prop = new Properties();
        prop.load(new FileInputStream("src/main/resources/config.properties"));

        database = new Database(
                prop.getProperty("DATABASE_MOTOR"),
                prop.getProperty("DATABASE_HOST"),
                prop.getProperty("DATABASE_PORT"),
                prop.getProperty("DATABASE_DATABASENAME"),
                prop.getProperty("DATABASE_USER"),
                prop.getProperty("DATABASE_PASSWORD")
        );

        final JDA bot = JDABuilder
                .createDefault(prop.getProperty("BOT_TOKEN"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setActivity(Activity.playing("CodeLand"))
                .build();


        database.connection();
        database.setup();

        bot.addEventListener(new MessagesListener());
        bot.addEventListener(new GuildJoinListener());
        bot.addEventListener(new SlashCommandListener());
        bot.addEventListener(new StringSelectInteraction());
        bot.addEventListener(new ModalInteraction());

        bot.updateCommands().addCommands(
                Commands.slash("menu", "Ouvre le menu de CodeLandBot.")
        ).queue();
    }

    public static Database getDatabase(){
        return database;
    }

    public static MessageEmbed getErrorEmbed(String desc){
//        return new EmbedBuilder().setColor(Color.RED).setDescription(desc).setImage("https://i.ibb.co/xLkt4gT/klipartz-com.png").setFooter(OffsetDateTime.now().toString()).build();
        return new MessageEmbed(
                null,
                desc,
                null,
                null,
                OffsetDateTime.now(),
                Color.RED.getRGB(),
                new MessageEmbed.Thumbnail("https://i.ibb.co/xLkt4gT/klipartz-com.png", null, 20, 20),
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static MessageEmbed getSuccessEmbed(String desc){
        return new MessageEmbed(
                null,
                desc,
                null,
                null,
                OffsetDateTime.now(),
                Color.GREEN.getRGB(),
                new MessageEmbed.Thumbnail("https://i.ibb.co/mhC2d8W/pngwing-com.png", null, 20, 20),
                null,
                null,
                null,
                null,
                null,
                null
                );
    }
}