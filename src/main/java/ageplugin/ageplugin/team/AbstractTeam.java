package ageplugin.ageplugin.team;

import ageplugin.ageplugin.AgePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.UUID;

public abstract class AbstractTeam implements Listener {
    protected TeamType type;
    protected UUID uuid;
    protected AgePlugin agePlugin;

    public AbstractTeam(AgePlugin agePlugin,TeamType type, UUID uuid){
        this.type=type;
        this.uuid=uuid;
        this.agePlugin=agePlugin;
        Bukkit.getPluginManager().registerEvents(this,agePlugin);
    }


    public UUID getUuid(){
        return uuid;
    }

    public TeamType getType(){
        return type;
    }

    public abstract void onStart(Player player);

    public void remove(){
        HandlerList.unregisterAll(this);

    }


    public void startingTitle(){
        Player player=Bukkit.getPlayer(this.uuid);
        if(player!=null){
            player.sendTitle(ChatColor.YELLOW.toString()+"The Event Starts!","!",10,70,20);
        }
    }
}
