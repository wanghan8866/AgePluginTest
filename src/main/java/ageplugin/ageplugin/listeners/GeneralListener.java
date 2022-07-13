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

        agePlugin.getTeamManager().removeTeam(e.getEntity().getUniqueId());
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"ban "+e.getEntity().getName());
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e){
        System.out.println("[event]   loading the world ");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player=e.getPlayer();
        AbstractTeam team=this.agePlugin.getTeamManager().getTeam(player);
        if(team!=null){

            team.onStart(player);

//            player.discoverRecipe(null);
        }

//        MyConsole myConsole=new MyConsole(this.agePlugin);
//        Bukkit.dispatchCommand(myConsole,"say not hi");
//        Bukkit.dispatchCommand(myConsole,"team list");
//        System.out.println(MyConsole.getLastOutput());
    }
//    @EventHandler
//    public void onLeave(PlayerQuitEvent e){
//        agePlugin.getTeamManager().removeFromEvent(e.getPlayer().getUniqueId());
//    }

    @EventHandler
    public void onServerLoad(ServerLoadEvent e){
        System.out.println("[event]   loading the server ");
        for(TeamType type:TeamType.values()){
//            System.out.println("team add "+type.getText());
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"team remove "+type.getText());
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"team add "+type.getText());
//            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"simulation start");
//            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"say hi");

//            System.out.println("team modify "+type.getText()+" color "+type.getColor());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team modify "+type.getText()+" color "+type.getColorText());
//            System.out.println("team modify "+type.getText()+" prefix "+"{\"text\":\"["+type.getDisplay()+"] \"");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"team modify "+type.getText()+" prefix "+"{\"text\":\"["+type.getText()+"] \",\"color\":\""+type.getColorText()+"\"}");


        }

        System.out.println(this.agePlugin.getTeamsFileManager().getPlayers());
        for(UUID uuid:this.agePlugin.getTeamsFileManager().getPlayers().keySet()){
            System.out.println(uuid);

            this.agePlugin.getTeamManager().setTeam(uuid,this.agePlugin.getTeamsFileManager().getPlayers().get(uuid));
        }
    }



}
