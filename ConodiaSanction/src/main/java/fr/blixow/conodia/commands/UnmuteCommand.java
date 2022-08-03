package fr.blixow.conodia.commands;

import fr.blixow.conodia.ConodiaSanction;
import fr.blixow.conodia.PermissionList;
import fr.blixow.conodia.SanctionType;
import fr.blixow.conodia.utils.CheckUtils;
import fr.blixow.conodia.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class UnmuteCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(PermissionList.MUTE_PERM)) {
            sender.sendMessage("§cVous n'avez pas accès à cette commande.");
            return false;
        }
        if(args.length == 1) {
            if(CheckUtils.isPlayerExist(args[0])){
                FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
                String targetName = CheckUtils.getPlayerName(args[0]);
                if (!ConodiaSanction.getConodiaSanction().getSanctionManager().isStillSanctioned(targetName, SanctionType.MUTE)) {
                    sender.sendMessage(StringUtils.prefix + "§cCe joueur n'est pas mute.");
                } else {
                    Bukkit.broadcastMessage(StringUtils.prefix + "§c" + CheckUtils.getPlayerName(args[0]) + "§c a été unmute par " + sender.getName());
                    ConodiaSanction.getConodiaSanction().getSanctionManager().removeSanction(targetName, SanctionType.MUTE);
                }
            } else {
                sender.sendMessage("§cLe joueur §f" + args[0] + " §cest introuvable.");
            }
            return true;
        }
        sender.sendMessage(StringUtils.prefix + "§c/unmute <Joueur>");
        return false;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
