package me.mino.minovalidator.bukkit.listener;

import me.mino.minovalidator.MinoValidator;
import me.mino.minovalidator.data.UserData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Objects;

public class PlayerLoginListener implements Listener {
    private final MinoValidator plugin = MinoValidator.getPlugin();

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerLogin(PlayerLoginEvent e){
        Player player = e.getPlayer();
        String playerIP = e.getAddress().getHostAddress();
        plugin.data.createPlayer(player);
        if (plugin.data.getPlayerNameByIP(playerIP) == null){
            plugin.data.setPlayerIP(player.getName(), playerIP);
        }else if (!plugin.data.getPlayerNameByIP(playerIP).equals(player.getName())){
            if (!player.hasPermission("ignoreIPLogin")) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("ip-has-logged-another-account")).replace("<name>", plugin.data.getPlayerNameByIP(playerIP))));
                plugin.data.setPlayerIP(player.getName(), "(Same player with: "+plugin.data.getPlayerNameByIP(playerIP)+")");
                return;
            }else {
                plugin.data.setPlayerIP(player.getName(), playerIP);
            }
        }

        if(plugin.data.getDiscordID(player) == null){
            if (!MinoValidator.connectKeys.containsKey(player.getName())){
                int ranNumber = (int) (Math.random()*100000);
                while (MinoValidator.connectKeys.containsValue(String.valueOf(ranNumber)) || String.valueOf(ranNumber).length() != 5){
                    ranNumber = (int) (Math.random()*100000);
                }
                MinoValidator.connectKeys.put(player.getName(), String.valueOf(ranNumber));
            }

            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("join-discord-server")+"\n\n" +
                            plugin.getConfig().getString("find-the-bot")+"\n\n"+
                            Objects.requireNonNull(plugin.getConfig().getString("private-message-key")).replace("<key-number>", MinoValidator.connectKeys.get(player.getName()))+"\n\n"+
                            plugin.getConfig().getString("finally-complete")
            ));
            return;
        }

        new UserData(player.getName(), player.getUniqueId(), plugin.data.getDiscordID(player), plugin.data.getValidateTime(player.getUniqueId()), plugin.data.getIP(player.getUniqueId()));
    }
}
