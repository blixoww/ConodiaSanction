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

import java.util.List;

public class KickCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionList.WARN_PERM)) {
            if (args.length >= 2) {
                if (CheckUtils.isPlayerExist(args[0])) {
                    StringBuilder reasonString = new StringBuilder();
                    for(int i = 1; i < args.length; i++) { reasonString.append(args[i]).append(" "); }
                    String reason = reasonString.toString();
                    reason = reason.substring(0, reason.length() - 1);
                    ConodiaSanction.getConodiaSanction().getSanctionManager().addSanction(CheckUtils.getPlayerName(args[0]), sender.getName(), SanctionType.KICK, reason);
                    Bukkit.getPlayer(args[0]).kickPlayer("§cVous avez été kick par " + sender.getName());
                    return true;
                }
            } else {
                sender.sendMessage(StringUtils.prefix + "§c/kick <Joueur> [Raison]");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
