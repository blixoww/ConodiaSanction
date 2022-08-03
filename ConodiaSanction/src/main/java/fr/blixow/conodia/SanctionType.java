package fr.blixow.conodia;

public enum SanctionType {
    MUTE("mute"),
    BAN("ban"),
    WARN("avertie"),
    KICK("expulsé");

    private final String str;
    SanctionType(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
