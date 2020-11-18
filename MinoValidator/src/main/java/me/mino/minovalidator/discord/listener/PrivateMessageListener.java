package me.mino.minovalidator.discord.listener;

import me.mino.minovalidator.MinoValidator;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class PrivateMessageListener extends ListenerAdapter {
    MinoValidator plugin = MinoValidator.getPlugin();

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) throws NoClassDefFoundError {
        if (event.getAuthor().isBot()) return;

        String message = event.getMessage().getContentRaw();
        String userID = event.getAuthor().getId();

        if (message.length() == 5){
            if (plugin.data.isDiscordUsed(userID)){
                sendPrivateMessage(event.getAuthor(), Objects.requireNonNull(plugin.getConfig().getString("has-already-verified-by-mcid")).replace("<name>", plugin.data.getNameFromDiscordID(userID)));
            }else{
                if (MinoValidator.connectKeys.containsValue(message)){
                    for (String name : MinoValidator.connectKeys.keySet()){
                        if (MinoValidator.connectKeys.get(name).equals(message)){
                            plugin.data.setDiscordID(name, userID);
                            plugin.data.setValidateTime(name);
                            event.getMessage().addReaction("✅").queue();
                            sendPrivateMessage(event.getAuthor(), Objects.requireNonNull(plugin.getConfig().getString("verified-success")).replace("<name>", name));
                            MinoValidator.connectKeys.remove(name);
                            break;
                        }
                    }
                }else{
                    event.getMessage().addReaction("❌").queue();
                    sendPrivateMessage(event.getAuthor(), plugin.getConfig().getString("verified-failed"));
                }
            }
        }
    }

    public void sendPrivateMessage(User user, String content) {
        user.openPrivateChannel().queue((channel) -> channel.sendMessage(content).queue());
    }
}
