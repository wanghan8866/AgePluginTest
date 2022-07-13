package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.Enchantments.CustomEnchants;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.HashMap;
import java.util.List;

public class CustomEnchantmentListener implements Listener {
    private AgePlugin agePlugin;
    public CustomEnchantmentListener(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @EventHandler
    public void onInteractWithTables(PlayerInteractEvent e){
        // disable interaction with DECORATION anvil
        Player player=e.getPlayer();

        Block block=e.getClickedBlock();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block!=null&&
                (block.getType().equals(Material.ANVIL)||
                        block.getType().equals(Material.CHIPPED_ANVIL)||
                        block.getType().equals(Material.DAMAGED_ANVIL)
                        ))
        {
            this.agePlugin.getSpecialItemManager().updateAnvil();
            if(this.agePlugin.getSpecialItemManager().getDecoratedItems().contains(block)){
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED+"It is just a decoration!");
            }


        }
    }

    @EventHandler
    public void onAnvilBreak(BlockBreakEvent e){
        // disable drops from DECORATION anvil
        Block block=e.getBlock();

        if(block!=null&&
                (block.getType().equals(Material.ANVIL)||
                        block.getType().equals(Material.CHIPPED_ANVIL)||
                        block.getType().equals(Material.DAMAGED_ANVIL)
                )){
            this.agePlugin.getSpecialItemManager().updateAnvil();
            if(this.agePlugin.getSpecialItemManager().getDecoratedItems().contains(block)){
                e.setDropItems(false);
                this.agePlugin.getSpecialItemManager().getDecoratedItems().remove(block);
            }
        }
    }

    @EventHandler
    public void onAnvilDrop(EntityDropItemEvent e){
        // disable drops from DECORATION anvil
        ItemStack block=e.getItemDrop().getItemStack();

        if(block!=null&&
                (block.getType().equals(Material.ANVIL)||
                        block.getType().equals(Material.CHIPPED_ANVIL)||
                        block.getType().equals(Material.DAMAGED_ANVIL)
                )){
           e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        // allow magnetic sword to take all items from a player into their killer's inventory.
        Player killer=e.getEntity().getKiller();
        Player player =e.getEntity();
        if(killer!=null &&  player!=null){
            if(killer.getInventory().getItemInMainHand()==null) return;
            if(!killer.getInventory().getItemInMainHand().hasItemMeta()) return;
            if(!killer.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.MAGNETISM)) return;;
            if(killer.getGameMode()== GameMode.CREATIVE||killer.getGameMode()==GameMode.SPECTATOR) return;
//            if(killer.getInventory().firstEmpty()==-1) return;
            List<ItemStack> drops=e.getDrops();
            System.out.println("player death: "+e.getDrops());
            System.out.println("player death: "+(e.getEntity()));
            handlerDrops(killer, drops, e.getEntity());


        }
    }

    private void handlerDrops(Player player, List<ItemStack> drops, LivingEntity entity) {
        // try to put new items into player's inventory. If full/fail, will drop near the player.
        if(!drops.isEmpty()){
//                killer.sendMessage("get items testing");
            for (int i = 0; i <drops.size() ; i++) {

                HashMap<Integer, ItemStack> result=player.getInventory().addItem(drops.get(i));
//                    System.out.println(result);
                if(!result.isEmpty()){
                    for (Integer index:result.keySet()
                    ) {
                        player.getWorld().dropItemNaturally(entity.getLocation(),result.get(index));
                    }

                }
            }
            drops.clear();
        }
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        // allow magnetic sword to take all drops from a mob into their killer's inventory.
        if(e.getEntity().getKiller() instanceof Player){
            Player player=e.getEntity().getKiller();
            if(player!=null){
                if(player.getInventory().getItemInMainHand()==null) return;
                if(!player.getInventory().getItemInMainHand().hasItemMeta()) return;
                if(!player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(CustomEnchants.MAGNETISM)) return;;
                if(player.getGameMode()== GameMode.CREATIVE||player.getGameMode()==GameMode.SPECTATOR) return;
//                if(player.getInventory().firstEmpty()==-1) return;

                List<ItemStack> drops=e.getDrops();

                System.out.println("entity death: "+e.getDrops());
                System.out.println("entity death: "+(e.getEntity()));
                handlerDrops(player, drops, e.getEntity());



//                System.out.println("dropping to you!");
            }


        }


    }

}
