package fr.blixow.conodia.events;

import fr.blixow.conodia.ConodiaSanction;
import fr.blixow.conodia.utils.DateUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BanEvent implements Listener {

    @EventHandler
    public void onConnection(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
        String reason = fileConfiguration.getString(player.getName() + ".ban.reason");
        if (fileConfiguration.contains(player.getName() + ".ban.duration")) {
            long duration = fileConfiguration.getLong(player.getName() + ".ban.duration");
            long current = System.currentTimeMillis() / 1000;
            if (duration > current) {
                String timeLeft = DateUtils.unixEndToDate(duration);
                player.kickPlayer("" + ConodiaSanction.getConodiaSanction().getSanctionManager().getBanMessage(player.getName(), reason, timeLeft));
            }
        }
    }
}
