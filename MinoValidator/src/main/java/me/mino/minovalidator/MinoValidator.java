package me.mino.minovalidator;

import me.mino.minovalidator.bukkit.listener.PlayerLoginListener;
import me.mino.minovalidator.discord.listener.PrivateMessageListener;
import me.mino.minovalidator.sql.MySQL;
import me.mino.minovalidator.sql.SQLGetter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.util.HashMap;

public final class MinoValidator extends JavaPlugin {
    public static MinoValidator plugin;
    public JDA jda;
    public MySQL SQL;
    public SQLGetter data;

    public static HashMap<String, String> connectKeys = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        this.SQL = new MySQL();
        this.data = new SQLGetter();

        try {
            SQL.connect();
        } catch (SQLException e) {
            Bukkit.getLogger().info("[MinoValidator] Database not connected");
        }

        if (SQL.isConnected()){
            Bukkit.getLogger().info("[MinoValidator] Database is connected!");
            data.createTable();
        }

        try {
            jda = JDABuilder.createDefault(getConfig().getString("bot-token")).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }

        registerEvents();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }


    public static MinoValidator getPlugin() {
        return plugin;
    }

    private void registerEvents(){
        jda.addEventListener(new PrivateMessageListener());

        getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            SQL.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
