package me.mino.minovalidator.api;

import me.mino.minovalidator.data.UserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class UsersAPI {
    public static String getDiscordID(UUID uuid){
        return UserData.users.get(uuid).getDiscordID();
    }

    public static String getDiscordID(Player player){
        return UserData.users.get(player.getUniqueId()).getDiscordID();
    }

    public static String getDiscordID(String name){
        Player player = Bukkit.getPlayer(name);
        if (player != null) return UserData.users.get(player.getUniqueId()).getDiscordID();
        return null;
    }

    public static String getDiscordIDbyIP(String ip){
        UserData user = UserData.users.values().stream().filter(userData -> userData.getIp().equals(ip)).findFirst().orElse(null);

        if (user != null){
            return user.getDiscordID();
        }

        return null;
    }

    public static String getIP(UUID uuid){
        return UserData.users.get(uuid).getIp();
    }

    public static String getIP(Player player){
        return UserData.users.get(player.getUniqueId()).getIp();
    }

    public static String getIP(String name){
        Player player = Bukkit.getPlayer(name);
        if (player != null) return UserData.users.get(player.getUniqueId()).getIp();
        return null;
    }

    public static String getValidateTime(UUID uuid){
        return UserData.users.get(uuid).getValidateTime();
    }

    public static String getValidateTime(Player player){
        return UserData.users.get(player.getUniqueId()).getValidateTime();
    }

    public static String getValidateTime(String name){
        Player player = Bukkit.getPlayer(name);
        if (player != null) return UserData.users.get(player.getUniqueId()).getValidateTime();
        return null;
    }

    public static boolean isLoaded(UUID uuid){
        return UserData.users.containsKey(uuid);
    }

    public static boolean isLoaded(Player player){
        return UserData.users.containsKey(player.getUniqueId());
    }
}
