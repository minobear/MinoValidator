package me.mino.minovalidator.sql;

import me.mino.minovalidator.MinoValidator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {
    MinoValidator plugin = MinoValidator.getPlugin();

    public void createTable() {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS users "
                    + "(name VARCHAR(100), " +
                    "uuid VARCHAR(100), " +
                    "discord_id VARCHAR(100), " +
                    "ip VARCHAR(100), "+
                    "validate_time VARCHAR(100), "+
                    "PRIMARY KEY (name))");
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        String name = player.getName();
        String uuid = player.getUniqueId().toString();
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT name FROM users WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()){
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT INTO users (name, uuid) VALUES (?,?)");
                ps2.setString(1, name);
                ps2.setString(2, uuid);
                ps2.executeUpdate();
            }else if (!rs.getString("name").equals(name)) {
                PreparedStatement ps3 = plugin.SQL.getConnection().prepareStatement("UPDATE users SET name=? WHERE uuid=?");
                ps3.setString(1, name);
                ps3.setString(2, uuid);
                ps3.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getDiscordID(Player player){
        String name = player.getName();
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT discord_id FROM users WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("discord_id");
            }else{
                createPlayer(player);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public String getDiscordID(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        String name = player.getName();
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT discord_id FROM users WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("discord_id");
            }else{
                createPlayer(player);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void setDiscordID(String name, String id){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE users SET discord_id=? WHERE name=?");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isDiscordUsed(String id){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT name FROM users WHERE discord_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public String getNameFromDiscordID(String id){
        try{
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT name FROM users WHERE discord_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("name");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void setPlayerIP(String name, String ip){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE users SET ip=? WHERE name=?");
            ps.setString(1, ip);
            ps.setString(2, name);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getPlayerNameByIP(String ip){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT name FROM users WHERE ip=?");
            ps.setString(1, ip);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("name");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void setValidateTime(String name){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE users SET validate_time=? WHERE name=?");
            ps.setString(1, String.valueOf(System.currentTimeMillis()));
            ps.setString(2, name);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getValidateTime(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT validate_time FROM users WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("validate_time");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public String getIP(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT ip FROM users WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("ip");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
