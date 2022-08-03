package fr.blixow.conodia;

import org.bukkit.configuration.file.FileConfiguration;

public class Sanction {
    private final String playerName;

    private final String author;

    private long left;
    private final SanctionType type;
    private String reason;

    public Sanction(String playerName, String author, SanctionType type, String reason) {
        this.playerName = playerName;
        this.author = author;
        this.type = type;
        this.reason = reason;
        this.left = -1;
    }


    public Sanction(String playerName, String author, long left, SanctionType type, String reason) {
        this.playerName = playerName;
        this.author = author;
        this.type = type;
        this.reason = reason;
        this.left = left;
    }

    public void setLeft(long left) {
        this.left = left;
    }

    public void setReason(String reason) { this.reason = reason; }

    public String getPlayerName() { return playerName; }

    public long getLeft() { return left; }

    public SanctionType getType() {
        return type;
    }

    public String getReason() {
        return reason;
    }

    public void save() {
        FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
        fileConfiguration.set(this.playerName + "." + this.type.name().toLowerCase() + ".author", author);
        fileConfiguration.set(this.playerName + "." + this.type.name().toLowerCase() + ".duration", left);
        fileConfiguration.set(this.playerName + "." + this.type.name().toLowerCase() + ".reason", reason);
        ConodiaSanction.getConodiaSanction().saveConfig();
    }

}
