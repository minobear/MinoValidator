package me.mino.minovalidator.sql;

import me.mino.minovalidator.MinoValidator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private final MinoValidator plugin = MinoValidator.getPlugin();
    private final String host = plugin.getConfig().getString("mysql.host");
    private final String port = plugin.getConfig().getString("mysql.port");
    private final String database = plugin.getConfig().getString("mysql.database");
    private final String username = plugin.getConfig().getString("mysql.username");
    private final String password = plugin.getConfig().getString("mysql.password");

    private Connection connection;

    public boolean isConnected(){
        return connection == null ? false : true;
    }

    public void connect() throws SQLException {
        if (!isConnected())
            connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database+"?useSSL=false", username, password);
    }

    public void disconnect() throws SQLException {
        if (isConnected())
            connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
