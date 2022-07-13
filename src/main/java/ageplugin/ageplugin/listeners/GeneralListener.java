package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.instances.MyConsole;
import ageplugin.ageplugin.team.AbstractTeam;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.util.UUID;

public class GeneralListener implements Listener {
    private AgePlugin agePlugin;
    public GeneralListener(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        // ban the player after the death
        agePlugin.getTeamManager().removeTeam(e.getEntity().getUniqueId());
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"ban "+e.getEntity().getName());
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e){
        System.out.println("[event]   loading the world ");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        // When player joins the server, try to find their team and try to start the abilities.
        Player player = e.getPlayer();
        AbstractTeam team = this.agePlugin.getTeamManager().getTeam(player);
        if (team != null) {

            team.onStart(player);

//            player.discoverRecipe(null);
        }
    }


    @EventHandler
    public void onServerLoad(ServerLoadEvent e){
        // use the default team command to set up the team and change the colour.
        System.out.println("[event]   loading the server ");
        for(TeamType type:TeamType.values()){
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"team remove "+type.getText());
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"team add "+type.getText());

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team modify "+type.getText()+" color "+type.getColorText());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team modify "+type.getText()+" prefix "+"{\"text\":\"["+type.getText()+"] \",\"color\":\""+type.getColorText()+"\"}");


        }

        System.out.println(this.agePlugin.getTeamsFileManager().getPlayers());
        for(UUID uuid:this.agePlugin.getTeamsFileManager().getPlayers().keySet()){
            System.out.println(uuid);

            this.agePlugin.getTeamManager().setTeam(uuid,this.agePlugin.getTeamsFileManager().getPlayers().get(uuid));
        }
    }



}
