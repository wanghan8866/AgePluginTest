package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.Enchantments.CustomEnchants;
import ageplugin.ageplugin.team.TeamType;
import ageplugin.ageplugin.team.type.AdminTeam;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class AdminTeamListener implements Listener {
    private AgePlugin agePlugin;
    public AdminTeamListener(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @EventHandler
    public void onAnvilPlace(BlockPlaceEvent e){

        // only allow placing DECORATION anvil by admin players
        Player player=e.getPlayer();

        Block anvil=e.getBlockPlaced();
        ItemStack itemStack=e.getItemInHand();
        if(anvil.getType().equals(Material.ANVIL) && itemStack.getType().equals(Material.ANVIL)){

            if(itemStack.getEnchantments().containsKey(CustomEnchants.DECORATION)){
                if(typeCheck(player.getUniqueId())&&player.getGameMode().equals(GameMode.CREATIVE)){

                    this.agePlugin.getSpecialItemManager().getDecoratedItems().add(anvil);


                }else{
                    e.setCancelled(true);
                }
            }
        }



    }


    private boolean typeCheck(UUID id){
        return this.agePlugin.getTeamManager().checkType(id, TeamType.ADMIN);
    }
}
