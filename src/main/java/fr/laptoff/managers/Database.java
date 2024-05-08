package fr.laptoff.managers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection co;
    private final String User;
    private final String Password;
    private String Path;

    public Database(String motor, String host, String port, String databaseName, String user, String password) throws ClassNotFoundException {
        this.User = user;
        this.Password = password;

        switch (motor){
            case "mysql" :
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.Path = "jdbc:com.mysql.cj://" + host + ":" + port + "/" + databaseName;
                break;
            case "sqlite" :
                this.Path = "jdbc:sqlite:src/main/resources/Data/bot.db";
                break;
            case "mariadb" :
                Class.forName("org.mariadb.jdbc.Driver");
                this.Path = "jdbc:org.mariadb://" + host + ":" + port + "/" + databaseName;
                break;
        }
    }

    public Connection getConnection() {
        return this.co;
    }

    public void connection() throws SQLException {
        this.co = DriverManager.getConnection(this.Path, this.User, this.Password);
    }

    public void closeConnection() throws SQLException {
        if (this.co != null && !this.co.isClosed())
            this.co.close();
    }

    public void setup() throws SQLException {
        //Table pour les tokens github.
        try(Statement stmt = this.co.createStatement()){
            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS bot_user (
                    user_id INT PRIMARY KEY,
                    token TEXT NOT NULL,
                    xp INT DEFAULT 0);
                    """);
        }
    }

}
