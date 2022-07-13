package ageplugin.ageplugin.managers;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVTeamManager {
    private AgePlugin agePlugin;
    private File file;

    public CSVTeamManager(AgePlugin agePlugin, String fileName){
        this.agePlugin=agePlugin;
        this.file=new File(this.agePlugin.getDataFolder(),fileName);
    }

    public void readTeams(){
        String line="";
        String splitBy=",";
        try{
            BufferedReader br=new BufferedReader(new FileReader(file));
            br.readLine();
            this.agePlugin.getTeamManager().removeAll();
            while ((line=br.readLine())!=null){
                String[] team_info=line.split(splitBy);


                Player player=Bukkit.getPlayer(team_info[1]);
                TeamType teamType= TeamType.getTeam(team_info[2]);
                if(teamType !=null){
                    if(player!=null){
                        System.out.println("ign name: "+team_info[1]+" role: "+teamType+ " with id: "+player.getUniqueId());
                        this.agePlugin.getTeamManager().setTeam(player.getUniqueId(),teamType);
                    }else{
                        System.out.println("ign name: "+team_info[1]+" role: "+teamType +" not online");
                    }
                }else{
                    System.out.println("ign name: "+team_info[1]+" wrong team!");
                }


            }

        }catch (IOException | IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
