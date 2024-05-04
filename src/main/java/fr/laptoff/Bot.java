package fr.laptoff;

import fr.laptoff.listeners.GuildJoinListener;
import fr.laptoff.listeners.MessagesListener;
import fr.laptoff.listeners.SlashCommandListener;
import fr.laptoff.managers.Database;
import fr.laptoff.managers.FileManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Bot {

    private static Database database;

    public static void main(String[] args) throws Exception{

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

        final JDA bot = JDABuilder.createDefault(prop.getProperty("BOT_TOKEN")).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        database.connection();

        bot.addEventListener(new MessagesListener());
        bot.addEventListener(new GuildJoinListener());
        bot.addEventListener(new SlashCommandListener());

        bot.updateCommands().addCommands(
                Commands.slash("menu", "Ouvre le menu de CodeLandBot.")
        ).queue();
    }

    public static Database getDatabase(){
        return database;
    }
}