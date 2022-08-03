package fr.blixow.conodia.runnable;

import fr.blixow.conodia.ConodiaSanction;
import fr.blixow.conodia.SanctionType;
import fr.blixow.conodia.utils.SanctionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UnmuteCheckRunnable extends BukkitRunnable {

    @Override
    public void run() {
        SanctionManager sanctionManager = ConodiaSanction.getConodiaSanction().getSanctionManager();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(sanctionManager.isSanctionEvenIfOutdated(p.getName(), SanctionType.MUTE)){
                if(!sanctionManager.isStillSanctioned(p.getName(), SanctionType.MUTE)){
                    sanctionManager.removeSanction(p.getName(), SanctionType.MUTE);
                    p.sendMessage("§7Vous pouvez désormais parler dans le chat, votre mute a expiré.");
                }
            }
        }
    }
}
