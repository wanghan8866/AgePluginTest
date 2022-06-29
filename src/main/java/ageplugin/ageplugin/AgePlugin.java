package ageplugin.ageplugin;

import ageplugin.ageplugin.commands.SimulationCommand;
import ageplugin.ageplugin.commands.SimulationTab;
import ageplugin.ageplugin.commands.TestCommand;
import ageplugin.ageplugin.listeners.*;
import ageplugin.ageplugin.managers.ConfigManager;
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

        ConfigManager.setupConfig(this);
        teamManager=new TeamManager(this);
        teamsFileManager=new FileManager(this,"teams.yml");


        //EVENTS
        Bukkit.getPluginManager().registerEvents(new GeneralListener(this),this);
        Bukkit.getPluginManager().registerEvents(new UIListener(this),this);
        Bukkit.getPluginManager().registerEvents(new TeamListener(this),this);
        Bukkit.getPluginManager().registerEvents(new FutureTeamListener(this),this);
        Bukkit.getPluginManager().registerEvents(new PastTeamListener(this),this);
        //commands:
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("simulation").setExecutor(new SimulationCommand(this));
        getCommand("simulation").setTabCompleter(new SimulationTab(this));






    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        teamsFileManager.writeTeams(teamManager.getTeams());
        ConfigManager.setIsStarted(teamManager.getIsStarted());

        this.getTeamManager().removeAll();
        this.saveConfig();


    }

    public TeamManager getTeamManager(){
        return teamManager;
    }
    public FileManager getTeamsFileManager(){
        return teamsFileManager;
    }




}
