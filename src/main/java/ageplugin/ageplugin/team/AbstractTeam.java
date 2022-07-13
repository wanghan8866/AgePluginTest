package ageplugin.ageplugin.team;

import ageplugin.ageplugin.AgePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public abstract class AbstractTeam implements Listener {
    protected TeamType type;
    protected UUID uuid;
    protected AgePlugin agePlugin;
    protected boolean isHungered;

    public AbstractTeam(AgePlugin agePlugin,TeamType type, UUID uuid){
        this.type=type;
        this.uuid=uuid;
        this.agePlugin=agePlugin;
        this.isHungered=false;
//        Bukkit.getPluginManager().registerEvents(this,agePlugin);
    }


    public UUID getUuid(){
        return uuid;
    }

    public TeamType getType(){
        return type;
    }

    public abstract void onStart(Player player);

    public void remove(){
        // reset the player's name
        Player player=Bukkit.getPlayer(uuid);
        if(player!=null){
            player.setDisplayName(player.getName());

        }
//        HandlerList.unregisterAll(this);

    }
    protected void setChatColor(){
        // set the colour for name in the chat
        Player player=Bukkit.getPlayer(uuid);
        if(player!=null){
            player.setDisplayName(type.getColor()+"["+type.getText()+"] "+player.getName()+ChatColor.RESET);

        }

    }

    public boolean getIsHungered(){
        return isHungered;
    }
    public void setHungered(boolean h){
        isHungered=h;
    }


    public void startingTitle(){
        startingTitle(ChatColor.YELLOW.toString()+"The Event Starts!");
    }
    public void startingTitle(String title){
        Player player=Bukkit.getPlayer(this.uuid);
        startingTitle(player,title);
    }
    public void startingTitle(Player player,String title){

        if(player!=null){
            player.sendTitle(title,"!",10,70,20);
        }
    }
    public void startHunger(Player player){

        if(player!=null){
            startingTitle(player,ChatColor.RED+"The greater hunger starts!");
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,Integer.MAX_VALUE,1));
            setHungered(true);

        }
    }
    public void endHunger(Player player){

        if(player!=null){
            startingTitle(player,ChatColor.GREEN+"The greater hunger ends!");
            setHungered(false);
            player.removePotionEffect(PotionEffectType.HUNGER);

        }
    }
}
