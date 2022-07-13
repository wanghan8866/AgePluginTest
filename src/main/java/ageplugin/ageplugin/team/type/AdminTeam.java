package ageplugin.ageplugin.team.type;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.AbstractTeam;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.UUID;

public class AdminTeam extends AbstractTeam {
    public AdminTeam(AgePlugin agePlugin, UUID uuid) {
        super(agePlugin, TeamType.ADMIN, uuid);
    }
    private boolean once=false;

    @Override
    public void onStart(Player player) {
        if(this.agePlugin.getTeamManager().getIsStarted()){
//            System.out.println(player.getDisplayName());
            player.sendMessage(type.getDisplay()+": "+"chosen");
            if(!once){
                createSideBar(player);
                once=true;
            }else{
                updateSideBar(player);
            }



            startingTitle();
            setChatColor();
        }

    }

    public void updateSideBar(Player player){
        // update the team number for all team
        for(TeamType teamType:TeamType.values()){
           updateSideBar(player,teamType);
        }

    }
    public void updateSideBar(Player player, TeamType type){
        // update the team number for a given team
        if(player.getScoreboard().getTeam(type.getDisplay())!=null){
            player.getScoreboard().getTeam(type.getDisplay()).setSuffix(this.agePlugin.getTeamManager().getTeamCount(type)+"/"+this.agePlugin.getTeamManager().getMaxTeamCount());
        }



    }

    private void createSideBar(Player player){
        // create the team scoreboard
        Scoreboard scoreboard= player.getScoreboard();
        if(scoreboard==null){
            scoreboard= Bukkit.getScoreboardManager().getNewScoreboard();
        }
        Objective obj=scoreboard.getObjective("teams-board");
        if(obj==null){
            obj=scoreboard.registerNewObjective("teams-board","dummy",
                    ChatColor.GREEN.toString()+ChatColor.BOLD+"Teams Board"
            );
        }

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);



        int i=0;
        Score space1=obj.getScore("   ");
        space1.setScore(0);
        for(TeamType teamType:TeamType.values()){
            Team teamLine=scoreboard.getTeam(teamType.getDisplay());
            if(teamLine==null){
                teamLine=scoreboard.registerNewTeam(teamType.getDisplay());
            }

            teamLine.addEntry(teamType.getColor());
            teamLine.setPrefix(teamType.getDisplay()+": ");
            teamLine.setSuffix(this.agePlugin.getTeamManager().getTeamCount(teamType)+"/"+this.agePlugin.getTeamManager().getMaxTeamCount());
            obj.getScore(teamType.getColor()).setScore(1+i*2);
            Score space=obj.getScore(teamType.getColor()+"  ");
            space.setScore(2+i*2);

            i++;
        }


        player.setScoreboard(scoreboard);

    }
}
