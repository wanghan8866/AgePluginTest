package ageplugin.ageplugin;

import ageplugin.ageplugin.Enchantments.CustomEnchants;
import ageplugin.ageplugin.commands.SimulationCommand;
import ageplugin.ageplugin.commands.SimulationTab;
import ageplugin.ageplugin.commands.TestCommand;
import ageplugin.ageplugin.listeners.*;
import ageplugin.ageplugin.managers.*;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public final class AgePlugin extends JavaPlugin  {
    private TeamManager teamManager;
    private TeamFileManager teamsFileManager;
    private BlockFileManager blockFileManager;
    private SpecialItemManager specialItemManager;
    private CSVTeamManager csvTeamManager;
    private HashMap<TeamType, ArrayList<NamespacedKey>> recipeKeysMap;


    @Override
    public void onEnable() {
        // Plugin startup
        recipeKeysMap=new HashMap<>();
        recipeKeysMap.put(TeamType.FUTURE,new ArrayList<>());
        recipeKeysMap.put(TeamType.PAST,new ArrayList<>());
        recipeKeysMap.put(TeamType.PRESENT,new ArrayList<>());
        recipeKeysMap.put(TeamType.ADMIN,new ArrayList<>());


        ConfigManager.setupConfig(this);
        csvTeamManager=new CSVTeamManager(this,"teamTestSample.csv");
        teamManager=new TeamManager(this);
        teamsFileManager=new TeamFileManager(this,"teams.yml");
        blockFileManager=new BlockFileManager(this,"blocks.yml");
        specialItemManager=new SpecialItemManager(this);

        //Custom data
        CustomEnchants.register();


        //EVENTS
        Bukkit.getPluginManager().registerEvents(new GeneralListener(this),this);
        Bukkit.getPluginManager().registerEvents(new UIListener(this),this);
        Bukkit.getPluginManager().registerEvents(new TeamListener(this),this);
        Bukkit.getPluginManager().registerEvents(new FutureTeamListener(this),this);
        Bukkit.getPluginManager().registerEvents(new PastTeamListener(this),this);
        Bukkit.getPluginManager().registerEvents(new AdminTeamListener(this),this);
        Bukkit.getPluginManager().registerEvents(new PresentTeamListener(this),this);
        Bukkit.getPluginManager().registerEvents(new SpecialItemListener(this),this);
        Bukkit.getPluginManager().registerEvents(new CustomEnchantmentListener(this),this);
        //commands:
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("simulation").setExecutor(new SimulationCommand(this));
        getCommand("simulation").setTabCompleter(new SimulationTab(this));






    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        teamsFileManager.writeTeams(teamManager.getTeams());
        blockFileManager.writeBlocks(specialItemManager.getDecoratedItems());
//        ConfigManager.setIsStarted(teamManager.getIsStarted());
//        ConfigManager.setMaxNumberPerTeam(teamManager.getMaxTeamCount());

        this.getTeamManager().removeAll();
//        this.saveConfig();



    }

    public TeamManager getTeamManager(){
        return teamManager;
    }
    public TeamFileManager getTeamsFileManager(){
        return teamsFileManager;
    }
    public BlockFileManager getBlockFileManager(){
        return blockFileManager;
    }
    public SpecialItemManager getSpecialItemManager(){
        return specialItemManager;
    }
    public HashMap<TeamType,ArrayList<NamespacedKey>> getRecipeKeysMap(){
        return recipeKeysMap;
    }
    public CSVTeamManager getCsvTeamManager(){return csvTeamManager;}




}
