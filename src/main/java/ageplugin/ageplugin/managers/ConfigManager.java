package ageplugin.ageplugin.managers;

import ageplugin.ageplugin.AgePlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private static FileConfiguration config;


    public static void setupConfig(AgePlugin agePlugin){
        ConfigManager.config=agePlugin.getConfig();
        agePlugin.saveDefaultConfig();
    }

    public static boolean getIsStarted(){
        return config.getBoolean("simulation.isStarted");
    }
    public static int getMaxNumberPerTeam(){
        return config.getInt("simulation.maxTeamMemberCap");
    }

    public static void setIsStarted(Boolean isStarted){
        config.set("simulation.isStarted",isStarted);
    }
    public static void setMaxNumberPerTeam(int cap){
        config.set("simulation.maxTeamMemberCap",cap);
    }

}
