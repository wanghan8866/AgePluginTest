package ageplugin.ageplugin.managers;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.AbstractTeam;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class TeamFileManager extends FileManager{

    private HashMap<UUID, TeamType> players;

    public TeamFileManager(AgePlugin agePlugin, String fileName) {
        super(agePlugin, fileName);
        players=new HashMap<>();
        readTeams(this.agePlugin.getTeamManager().getTeams());
    }

    public void writeTeams(HashMap<UUID, AbstractTeam> teams){
       beforeWrite();

        for(UUID uuid:teams.keySet()){
            System.out.println(uuid+": "+teams.get(uuid).getType().name());

            yamlConfiguration.set("teams."+uuid,teams.get(uuid).getType().name());

        }
        save();
    }

    public void readTeams(HashMap<UUID, AbstractTeam> teams){
        if(yamlConfiguration.getConfigurationSection("teams.")!=null){
            for(String uuid:yamlConfiguration.getConfigurationSection("teams.").getKeys(false)){
                System.out.println(UUID.fromString(uuid));
                String type=yamlConfiguration.getString("teams."+uuid);
                try{
                    System.out.println(yamlConfiguration.get("teams."+uuid));

                    players.put(UUID.fromString(uuid), TeamType.valueOf(type));


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
