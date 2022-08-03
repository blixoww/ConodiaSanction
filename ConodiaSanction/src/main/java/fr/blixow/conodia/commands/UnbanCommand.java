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

public class UnbanCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(PermissionList.BAN_PERM)) {
            sender.sendMessage("§cVous n'avez pas accès à cette commande.");
            return false;
        }
        if(args.length == 1) {
            if(CheckUtils.isPlayerExist(args[0])){
                FileConfiguration fileConfiguration = ConodiaSanction.getConodiaSanction().getConfiguration();
                String targetName = CheckUtils.getPlayerName(args[0]);
                if (!ConodiaSanction.getConodiaSanction().getSanctionManager().isStillSanctioned(targetName, SanctionType.BAN)) {
                    sender.sendMessage(StringUtils.prefix + "§cCe joueur n'est pas banni.");
                } else {
                    Bukkit.broadcastMessage(StringUtils.prefix + "§c" + CheckUtils.getPlayerName(args[0]) + "§c a été débanni par " + sender.getName());
                    ConodiaSanction.getConodiaSanction().getSanctionManager().removeSanction(targetName, SanctionType.BAN);
                }
            } else {
                sender.sendMessage("§cLe joueur §f" + args[0] + " §cest introuvable.");
            }

            return true;
        }
        sender.sendMessage(StringUtils.prefix + "§c/deban <Joueur>");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}