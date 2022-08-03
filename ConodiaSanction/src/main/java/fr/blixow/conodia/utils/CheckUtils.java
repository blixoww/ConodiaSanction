package fr.blixow.conodia.utils;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class CheckUtils {

    /* Classe qui va permettre de vérifier beaucoup de chose :
     * - Si un joueur est connectés
     * - Si un joueur est déconnectés
     * - Si un joueur existe sur le serveur
     * - Si un string est un entier, long, double...
     * */

    public static boolean isPlayerExist(String name) {
        return isPlayerOnline(name) || isPlayerOffline(name);
    }

    /**
     * @param name : Le nom du joueur dont vous souhaitez vérifier s'il est connecté
     * @return : Un booléen
     */
    public static boolean isPlayerOnline(String name) {
        try {
            Player player = Bukkit.getPlayerExact(name);
            return true;
        } catch(Exception ignored) {}
        return false;
    }

    public static boolean isPlayerOffline(String name) {
        try {
            OfflinePlayer player = Bukkit.getOfflinePlayer(name);
            return true;
        } catch(Exception ignored) {}
        return false;
    }

    public static String getPlayerName(String name) {
        if (isPlayerExist(name)) {
            if (isPlayerOnline(name)) {
                Bukkit.getPlayer(name);
            } else if (isPlayerOffline(name)) {
                Bukkit.getOfflinePlayer(name);
            }
        }
        return Bukkit.getOfflinePlayer(name).getName();
    }

    public static boolean isInteger(String str){
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception ignored){}
        return false;
    }

    public static boolean isDouble(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception ignored){}
        return false;
    }

    public static boolean isLong(String str){
        try {
            long l = Long.parseLong(str);
            return true;
        } catch (Exception ignored){}
        return false;
    }

}
