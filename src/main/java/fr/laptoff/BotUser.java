package fr.laptoff;

import fr.laptoff.managers.Database;
import org.jetbrains.annotations.Nullable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BotUser {

    private final int Id;
    private String Github;
    private int Xp;
    private static final Database database = Bot.getDatabase();

    public BotUser(int id, @Nullable String github, int xp){
        this.Id = id;
        this.Github = github;
        this.Xp = xp;
    }

    public int getId(){
        return this.Id;
    }

    public String getGithub() throws SQLException {
        try (PreparedStatement stmt = database.getConnection().prepareStatement("SELECT token FROM bot_user WHERE id = ?;")) {
            stmt.setInt(1, this.Id);
            this.Github = stmt.executeQuery().getString("token");
            return this.Github;
        }
    }

    public int getXp() throws SQLException {
        try (PreparedStatement stmt = database.getConnection().prepareStatement("SELECT xp FROM bot_user WHERE id = ?;")) {
            stmt.setInt(1, this.Id);
            this.Xp = stmt.executeQuery().getInt("xp");
            return this.Xp;
        }
    }

    public void setGithub(String token) throws SQLException {
        try(PreparedStatement stmt = database.getConnection().prepareStatement("UPDATE bot_user SET token = ? WHERE id = ?;")){
            stmt.setString(1, token);
            stmt.setInt(2, this.Id);
            stmt.execute();
            this.Github = token;
        }
    }

    public void setXp(int xp) throws SQLException {
        try(PreparedStatement stmt = database.getConnection().prepareStatement("UPDATE bot_user SET xp = ? WHERE id = ?;")){
            stmt.setInt(1, xp);
            stmt.setInt(2, this.Id);
            stmt.execute();
            this.Xp = xp;
        }
    }

    public boolean isExist() throws SQLException {
        try(PreparedStatement stmt = database.getConnection().prepareStatement("SELECT token FROM bot_user WHERE user_id = ?;")){
            stmt.setInt(1, this.Id);

            return stmt.executeQuery().next();
        }
    }

    public void register() throws SQLException {
        if (!isExist()){
            try(PreparedStatement stmt = database.getConnection().prepareStatement("INSERT INTO bot_user (user-id, token, xp) VALUES (?, ?, ?);")){
                stmt.setInt(1, this.Id);
                stmt.setString(2, this.Github);
                stmt.setInt(3, this.Xp);
                stmt.execute();
            }
        }
    }

    public void delete() throws SQLException {
        if (isExist()){
            try(PreparedStatement stmt = database.getConnection().prepareStatement("DELETE FROM bot_user WHERE ID = ?;")){
                stmt.setInt(1, this.Id);

                stmt.execute();
            }
        }
    }

}