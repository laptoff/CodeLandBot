package fr.laptoff;

import fr.laptoff.listeners.GuildJoinListener;
import fr.laptoff.listeners.MessagesListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.FileInputStream;
import java.util.Properties;

public class Bot {

    public static void main(String[] args) throws Exception{

        final Properties prop = new Properties();
        prop.load(new FileInputStream("src/main/resources/config.properties"));

        final JDA bot = JDABuilder.createDefault(prop.getProperty("BOT_TOKEN")).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        bot.addEventListener(new MessagesListener());
        bot.addEventListener(new GuildJoinListener());
    }
}