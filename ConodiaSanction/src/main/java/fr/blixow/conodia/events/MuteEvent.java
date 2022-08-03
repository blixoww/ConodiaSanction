package fr.blixow.conodia.events;

import fr.blixow.conodia.ConodiaSanction;
import fr.blixow.conodia.SanctionType;
import fr.blixow.conodia.utils.DateUtils;
import fr.blixow.conodia.utils.SanctionManager;
import fr.blixow.conodia.utils.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.util.Arrays;
import java.util.List;

public class MuteEvent implements Listener {

    private final List<String> commandList = Arrays.asList("tell", "msg", "m", "emsg", "etell", "ereply", "w", "er", "r");

    @EventHandler
    public void muteEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
        if (fileConfiguration.contains(player.getName() + ".mute.duration")) {
            long duration = fileConfiguration.getLong(player.getName() + ".mute.duration");
            long current = System.currentTimeMillis() / 1000;
            if (duration > current) {
                String timeLeft = DateUtils.unixEndToDate(duration);
                event.setCancelled(true);
                player.sendMessage(StringUtils.prefix + "§7Tu as été mute, tu dois encore attendre : §c"  + timeLeft);
            } else {
                ConodiaSanction.getConodiaSanction().getSanctionManager().removeSanction(player.getName(), SanctionType.MUTE);
            }
        }
    }

    @EventHandler
    public void commandChat(PlayerCommandPreprocessEvent event) {
        SanctionManager sanctionManager = ConodiaSanction.getConodiaSanction().getSanctionManager();
        if (sanctionManager.isStillSanctioned(event.getPlayer().getName(), SanctionType.MUTE)) {
            String cmd = event.getMessage().toLowerCase();
            if (cmd.startsWith("/")) {
                cmd = cmd.substring(1);
            }
            if (cmd.split(" ").length >= 1) {
                cmd = cmd.split(" ")[0];
            }
            if (commandList.contains(cmd)) {
                event.getPlayer().sendMessage(StringUtils.prefix + "§7Tu n'as accès à cette commande quand tu es mute");
                event.setCancelled(true);
            }
        }
    }

}
