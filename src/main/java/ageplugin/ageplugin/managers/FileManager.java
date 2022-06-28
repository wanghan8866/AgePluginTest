package ageplugin.ageplugin.managers;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.AbstractTeam;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileManager {

    private AgePlugin agePlugin;
    private File file;
    private YamlConfiguration yamlConfiguration;
    private HashMap<UUID, TeamType> players;


    public FileManager(AgePlugin agePlugin,String fileName ) {
        this.agePlugin=agePlugin;
        players=new HashMap<>();
        this.file=new File(this.agePlugin.getDataFolder(),fileName);
        try {
            if(!file.exists()){
                file.createNewFile();


            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        this.yamlConfiguration=YamlConfiguration.loadConfiguration(file);
        readTeams(this.agePlugin.getTeamManager().getTeams());

    }


    public void writeTeams(HashMap<UUID, AbstractTeam> teams){
        try{
            if(file.exists()){
                file.delete();

            }
            file.createNewFile();
            this.yamlConfiguration=YamlConfiguration.loadConfiguration(file);

        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("writing the file");

        for(UUID uuid:teams.keySet()){
            System.out.println(uuid+": "+teams.get(uuid).getType().name());

            yamlConfiguration.set("teams."+uuid,teams.get(uuid).getType().name());

        }
        try {
            yamlConfiguration.save(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readTeams(HashMap<UUID, AbstractTeam> teams){
        if(yamlConfiguration.getConfigurationSection("teams.")!=null){
            for(String uuid:yamlConfiguration.getConfigurationSection("teams.").getKeys(false)){
                System.out.println(UUID.fromString(uuid));
                String type=yamlConfiguration.getString("teams."+uuid);
                try{
                    System.out.println(yamlConfiguration.get("teams."+uuid));

                   players.put(UUID.fromString(uuid),TeamType.valueOf(type));


                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }


            }
        }

    }

    public HashMap<UUID, TeamType> getPlayers(){
        return players;
    }


}
