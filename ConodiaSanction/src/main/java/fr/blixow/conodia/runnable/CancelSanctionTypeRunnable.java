package fr.blixow.conodia.runnable;

import fr.blixow.conodia.ConodiaSanction;
import fr.blixow.conodia.SanctionType;
import fr.blixow.conodia.utils.CheckUtils;
import fr.blixow.conodia.utils.SanctionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;



public class CancelSanctionTypeRunnable extends BukkitRunnable {

    private final String targetName;
    private final SanctionType sanctionType;

    public CancelSanctionTypeRunnable(String targetName, SanctionType sanctionType){
        this.targetName = targetName;
        this.sanctionType = sanctionType;
    }

    @Override
    public void run() {
            SanctionManager sanctionManager = ConodiaSanction.getConodiaSanction().getSanctionManager();
            Player player = Bukkit.getPlayerExact(targetName);
            if (sanctionManager.isSanctionEvenIfOutdated(targetName, SanctionType.MUTE)) {
                if (!sanctionManager.isStillSanctioned(targetName, SanctionType.MUTE)) {
                    sanctionManager.removeSanction(targetName, SanctionType.MUTE);
                    if(CheckUtils.isPlayerOnline(targetName)){
                        Player target = Bukkit.getPlayerExact(targetName);
                        if ("mute".equals(sanctionType.name().toLowerCase())) {
                            target.sendMessage("§7Vous pouvez désormais parler dans le chat, le mute a expiré.");
                        }
                    }
                }
            }
        }

}
