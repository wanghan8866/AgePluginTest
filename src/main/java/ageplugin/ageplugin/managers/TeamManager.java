package ageplugin.ageplugin.managers;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.AbstractTeam;
import ageplugin.ageplugin.team.TeamType;
import ageplugin.ageplugin.team.type.AdminTeam;
import ageplugin.ageplugin.team.type.FutureTeam;
import ageplugin.ageplugin.team.type.PastTeam;
import ageplugin.ageplugin.team.type.PresentTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class TeamManager {
    private HashMap<UUID, AbstractTeam> teams=new HashMap<>();
    private final AgePlugin agePlugin;
    private int MAX_TEAM_COUNT;
    private boolean isStarted;
    private boolean isHungered;

    public TeamManager(AgePlugin agePlugin){

        this.agePlugin=agePlugin;
        isStarted=ConfigManager.getIsStarted();
        MAX_TEAM_COUNT=ConfigManager.getMaxNumberPerTeam();
        isHungered=false;


    }

    public void setTeam(UUID id, TeamType type){
        removeTeam(id);
        System.out.println("setting");
        Player player=Bukkit.getPlayer(id);

        if(player!=null){
            System.out.println("setting the team for online player");
//            player.performCommand("team leave "+player.getName());

            player.sendMessage(ChatColor.GREEN+"Team is set"+ " type: "+type.getDisplay());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team leave "+player.getName());

            typeSelection(type, id);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team join "+type.getText()+" "+player.getName());
//            player.performCommand("team join "+type.getText());
            updateAllAdmin(type);
            player.discoverRecipes(this.agePlugin.getRecipeKeysMap().get(type));
//            System.out.println("setting the recipes "+this.agePlugin.getRecipeKeysMap().get(type));
        }
        else{
            OfflinePlayer offlinePlayer=Bukkit.getOfflinePlayer(id);
            setTeam(offlinePlayer,id,type);
        }

    }

    public void setTeam(OfflinePlayer player, UUID id,TeamType type){

        System.out.println(player==null);
        System.out.println(player);
        if(player!=null){
            System.out.println("setting the team for offline player");


            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team leave "+player.getName());
            typeSelection(type, id);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team join "+type.getText()+" "+player.getName());
//            System.out.println("update for admin before");
            updateAllAdmin(type);
//            System.out.println("update for admin after");
        }else{
            System.out.println("not existing");
        }

    }

    private void typeSelection(TeamType type, UUID id) {
        if(type==TeamType.FUTURE){
            teams.put(id,new FutureTeam(agePlugin,id));
        }else if(type==TeamType.PRESENT){
            teams.put(id,new PresentTeam(agePlugin,id));
        }else if(type==TeamType.PAST){
            teams.put(id,new PastTeam(agePlugin,id));
        }else if(type==TeamType.ADMIN){
            teams.put(id,new AdminTeam(agePlugin,id));
        }
        Player player=Bukkit.getPlayer(id);
        if(player!=null){
            teams.get(id).onStart(player);
        }

    }


    public void removeTeam(UUID id){
        if(teams.containsKey(id)){
            removePlayer(id,true);
            TeamType teamType=teams.get(id).getType();
            teams.remove(id);
            updateAllAdmin(teamType);

        }



    }

    public void removeFromEvent(UUID id){
        if(teams.containsKey(id)){
            teams.get(id).remove();
        }
    }

    public HashMap<UUID,AbstractTeam> getTeams(){
        return teams;
    }

    public int getTeamCount(TeamType team){
        int amount=0;
        for(AbstractTeam t: teams.values()){
            if(t.getType()==team){
                amount++;
            }
        }
        return amount;
    }

    public boolean inTeam(Player player){
        return teams.containsKey(player.getUniqueId());

    }

    public AbstractTeam getTeam(Player player){
        if(inTeam(player)){
            return teams.get(player.getUniqueId());
        }
        return null;
    }


    public int getMaxTeamCount() {
        return MAX_TEAM_COUNT;
    }

    public void removeAll() {

        for (UUID id:teams.keySet()){
            removePlayer(id,true);
        }
        teams.clear();
        System.out.println("REMOVE ALL");
        updateAllAdmin();
    }

    public void updateAllAdmin(){
        for(UUID id: teams.keySet()){
            AbstractTeam team=teams.get(id);
            if(team.getType().equals(TeamType.ADMIN)){
                Player player=Bukkit.getPlayer(id);
                if(player!=null){
                    ((AdminTeam)team).updateSideBar(player);
                }

            }
        }
    }
    public void updateAllAdmin(TeamType type){
        for(UUID id: teams.keySet()){
            AbstractTeam team=teams.get(id);
            if(team.getType().equals(TeamType.ADMIN)){
                Player player=Bukkit.getPlayer(id);
                if(player!=null){
                    ((AdminTeam)team).updateSideBar(player,type);
                }

            }
        }
    }

    public void teleportTeam(TeamType type, Location location){
        for(UUID id:teams.keySet()){
            AbstractTeam team=teams.get(id);
            if(team.getType().equals(type)){
                Player player=Bukkit.getPlayer(id);
                if(player!=null){
                    player.teleport(location);

                }
            }
        }
    }

    private void removePlayer(UUID id, boolean fromTeam) {
        Player player= Bukkit.getPlayer(id);
        if(player!=null){
            for (PotionEffect effect:player.getActivePotionEffects()
            ) {
                player.removePotionEffect(effect.getType());
            }
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20.0);

            player.sendMessage(ChatColor.RED+"Team is removed");

            if(teams.get(id).getType().equals(TeamType.ADMIN)){
                //player.getScoreboard().getTeam(teams.get(id).getType().getDisplay()+" ").removeEntry(teams.get(id).getType().getColor()+player.getName());
//                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);

            }
            if(fromTeam){
//                player.performCommand("team leave "+player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team leave "+player.getName());
            }



        }
        teams.get(id).remove();

    }
    public boolean getIsStarted(){
        return isStarted;
    }
    public void setIsStarted(boolean isStarted){
        this.isStarted=isStarted;
    }

    public boolean checkType(UUID id, TeamType type){
        if(teams.containsKey(id)){
            if(teams.get(id).getType().equals(type)){
                return true;
            }
        }
        return false;
    }
    public boolean getIsHungered(){
        return isHungered;
    }
    public void setHungered(boolean h){
        isHungered=h;
    }

    public void giveHungerToAll(){
        setHungered(true);
        for(UUID id:teams.keySet()){
            AbstractTeam team=teams.get(id);
            if(!team.getType().equals(TeamType.ADMIN)){
                Player player=Bukkit.getPlayer(id);
                if(player!=null){
                    team.startHunger(player);

                }
            }
        }
    }

    public void removeHungerToAll(){
        setHungered(false);
        for(UUID id:teams.keySet()){
            AbstractTeam team=teams.get(id);
            if(!team.getType().equals(TeamType.ADMIN)){
                Player player=Bukkit.getPlayer(id);
                if(player!=null){
                    team.endHunger(player);

                }
            }
        }
    }





}
