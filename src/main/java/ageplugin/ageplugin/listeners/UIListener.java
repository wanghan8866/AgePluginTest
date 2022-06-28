package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.UI.ConfirmationUI;
import ageplugin.ageplugin.UI.TeamUI;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UIListener implements Listener {
    private AgePlugin agePlugin;
    public UIListener(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getInventory()!=null && e.getCurrentItem()!=null && e.getView().getTitle().contains("Team Selection")){
            TeamType team=TeamType.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());

            Player player=(Player) e.getWhoClicked();
            player.closeInventory();
            e.setCancelled(true);

            if(this.agePlugin.getTeamManager().getTeam(player)!=null&&team.equals(this.agePlugin.getTeamManager().getTeam(player).getType())){
                player.sendMessage(ChatColor.RED+"You are already on this team!");
            }
            else if(this.agePlugin.getTeamManager().getTeam(player)!=null){
                player.sendMessage(ChatColor.RED+"You have already chosen a team!");
            }
            else{

                if(this.agePlugin.getTeamManager().getTeamCount(team)>=this.agePlugin.getTeamManager().getMaxTeamCount()){
                    player.sendMessage(ChatColor.RED+"Too many people in this team, please choose another team!");
                }else{


                    new ConfirmationUI(player,team.name());

                }

            }


        }else if(e.getInventory()!=null && e.getCurrentItem()!=null && e.getView().getTitle().contains("Confirmation for Team")){
            String result=e.getCurrentItem().getItemMeta().getDisplayName();

            Player player=(Player) e.getWhoClicked();
            player.closeInventory();
            e.setCancelled(true);
            if(result.equalsIgnoreCase("yes")){
                TeamType team=TeamType.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());
                this.agePlugin.getTeamManager().setTeam(player.getUniqueId(),team);
                player.sendMessage(ChatColor.AQUA+"You are now on "+team.getDisplay()+ChatColor.AQUA+" team!");


            }else if(result.equalsIgnoreCase("cancel")){
                new TeamUI(this.agePlugin,player);
            }
        }

    }
}

