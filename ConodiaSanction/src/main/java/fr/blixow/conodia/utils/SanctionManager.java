package fr.blixow.conodia.utils;

import fr.blixow.conodia.ConodiaSanction;
import fr.blixow.conodia.Sanction;
import fr.blixow.conodia.SanctionType;
import fr.blixow.conodia.runnable.CancelSanctionTypeRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class SanctionManager {

    public void addSanction(String playerName, String author, long left, SanctionType type, String reason) {
        FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
        long duration = isStillSanctioned(playerName, type) ? fileConfiguration.getLong(playerName + "." + type.name().toLowerCase() + ".duration") : (System.currentTimeMillis() / 1000);

        String formatedLeftTime = DateUtils.unixEndToDate(left);
        if (duration < left) {
            long diff = left - duration;
            Sanction sanction = new Sanction(playerName, author, left, type, reason);
            Bukkit.broadcastMessage("§8§m------------------" +
                    "\n§c" + playerName + " §7a été §c" + type.getStr() + " §7par §a" + author +
                    "\n§7Durée§f: §c" + formatedLeftTime +
                    "\n§7Raison§f: §e" + reason +
                    "\n§8§m------------------"
            );
            if (diff < 3600) {
                new CancelSanctionTypeRunnable(playerName, type).runTaskLater(ConodiaSanction.getConodiaSanction(), diff * 20L);
            }
            sanction.save();
        } else {
            Bukkit.getPlayer(author).sendMessage(StringUtils.prefix + "§cCe joueur est sanctionnné");
        }
    }

    public void addSanction(String playerName, String author, SanctionType type, String reason) {
        Sanction sanction = new Sanction(playerName, author, type, reason);
        Bukkit.broadcastMessage("§8§m------------------\n§c" +
                playerName + " §7a été §c" + type.getStr() +
                "\n§7par §a" + author +
                "\n§7pour§e " + reason +
                "\n§8§m------------------");
        sanction.save();
    }

    public void removeSanction(String playerName, SanctionType type) {
        if (isSanctionEvenIfOutdated(playerName, type)) {
            FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
            fileConfiguration.set(playerName + "." + type.name().toLowerCase(), null);
            ConodiaSanction.getConodiaSanction().saveConfig();
        }
    }

    public boolean isStillSanctioned(String playerName, SanctionType type) {
        FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
        if (fileConfiguration.contains(playerName + "." + type.name().toLowerCase() + ".duration")) {
            long duration = fileConfiguration.getLong(playerName + "." + type.name().toLowerCase() + ".duration");
            return (System.currentTimeMillis() / 1000) < duration;
        }
        return false;
    }

    public boolean isSanctionEvenIfOutdated(String playerName, SanctionType type) {
        FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
        return fileConfiguration.contains(playerName + "." + type.name().toLowerCase() + ".duration");
    }

    public String getBanMessage(String player, String reason, String timeLeft) {
        return "§cVous avez été banni par " + player + "\n\n §cRaison :§7 " + reason + "\n\n§cErreur ? : §7conodia.fr/discord" + "\n\n§cDébannis dans : " + timeLeft;
    }

}
