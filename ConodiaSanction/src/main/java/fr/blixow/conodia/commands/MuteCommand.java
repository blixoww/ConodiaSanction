package fr.blixow.conodia.commands;

import fr.blixow.conodia.ConodiaSanction;
import fr.blixow.conodia.PermissionList;
import fr.blixow.conodia.SanctionType;
import fr.blixow.conodia.utils.CheckUtils;
import fr.blixow.conodia.utils.DateUtils;
import fr.blixow.conodia.utils.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import java.util.List;

public class MuteCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(PermissionList.MUTE_PERM)) {
            if (args.length >= 3) {
                if (CheckUtils.isPlayerExist(args[0])) {
                    if(!DateUtils.isValid(args[1])) {
                        sender.sendMessage(StringUtils.prefix + "§cdate pas valide");
                        return true;
                    }
                    StringBuilder reasonString = new StringBuilder();
                    for(int i = 2; i < args.length; i++) { reasonString.append(args[i]).append(" "); }
                    String reason = reasonString.toString();
                    reason = reason.substring(0, reason.length() - 1);
                    long unixTime = DateUtils.unixEndFromFormattedDate(args[1]);
                    ConodiaSanction.getConodiaSanction().getSanctionManager().addSanction(args[0], sender.getName(), unixTime, SanctionType.MUTE, reason);
                    return true;
                }
            } else {
                sender.sendMessage(StringUtils.prefix + "§c/mute <Joueur> <Temps> [Raison]");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
