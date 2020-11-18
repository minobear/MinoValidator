package me.mino.minovalidator.data;

import java.util.HashMap;
import java.util.UUID;

public class UserData {
    public static HashMap<UUID, UserData> users = new HashMap<>();

    private String name;
    private UUID uuid;
    private String discordID;
    private String validateTime;
    private String ip;

    public UserData(String name, UUID uuid, String discordID, String validateTime, String ip) {
        this.name = name;
        this.uuid = uuid;
        this.discordID = discordID;
        this.validateTime = validateTime;
        this.ip = ip;
        users.put(uuid, this);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getDiscordID() {
        return discordID;
    }

    public String getValidateTime() {
        return validateTime;
    }

    public String getIp() {
        return ip;
    }
}
