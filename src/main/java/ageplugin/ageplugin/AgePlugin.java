package ageplugin.ageplugin;

import ageplugin.ageplugin.commands.SimulationCommand;
import ageplugin.ageplugin.commands.SimulationTab;
import ageplugin.ageplugin.commands.TestCommand;
import ageplugin.ageplugin.listeners.GeneralListener;
import ageplugin.ageplugin.listeners.UIListener;
import ageplugin.ageplugin.managers.FileManager;
import ageplugin.ageplugin.managers.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AgePlugin extends JavaPlugin  {
    private TeamManager teamManager;
    private FileManager teamsFileManager;

    @Override
    public void onEnable() {
        // Plugin startup
        Bukkit.getPluginManager().registerEvents(new GeneralListener(this),this);
        Bukkit.getPluginManager().registerEvents(new UIListener(this),this);

        teamManager=new TeamManager(this);
        //commands:
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("simulation").setExecutor(new SimulationCommand(this));
        getCommand("simulation").setTabCompleter(new SimulationTab(this));

        teamsFileManager=new FileManager(this,"teams.yml");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        teamsFileManager.writeTeams(teamManager.getTeams());
        this.getTeamManager().removeAll();


    }

    public TeamManager getTeamManager(){
        return teamManager;
    }
    public FileManager getTeamsFileManager(){
        return teamsFileManager;
    }




}
