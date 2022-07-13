package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.Enchantments.CustomEnchants;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpecialItemListener implements Listener {
    private AgePlugin agePlugin;

    public  SpecialItemListener(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e){
        ItemStack item=e.getItemDrop().getItemStack();
        if(item!=null &&( item.getType().equals(Material.DIAMOND_SWORD) && item.containsEnchantment(Enchantment.BINDING_CURSE) )||
                (item.getType().equals(Material.TURTLE_HELMET) && item.containsEnchantment(Enchantment.BINDING_CURSE))){
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerThrowEnderPearl(PlayerInteractEvent e){

        Player player=e.getPlayer();
        if(e.getHand().equals(EquipmentSlot.HAND)){
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR)|e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                ItemStack pearl=player.getInventory().getItemInMainHand();
                if(player!=null&&pearl.containsEnchantment(Enchantment.ARROW_INFINITE)){

                    if(!player.hasCooldown(Material.ENDER_PEARL)){
                        this.agePlugin.getSpecialItemManager().spawnItemToPlayer(player,"infinity_ender_pearl");
                    }

                }
            }
        }



    }

//    @EventHandler
//    public void OnWearHelmet(InventoryClickEvent e){
//        Player player= (Player) e.getWhoClicked();
//        if(player!=null){
//           if(e.getSlotType().equals(InventoryType.SlotType.ARMOR)){
//               ItemStack itemStack=player.getItemOnCursor();
//
//               System.out.println(e.getCurrentItem());
//               System.out.println(itemStack);
//               List<ItemStack> armors= Arrays.stream(player.getInventory().getArmorContents()).collect(Collectors.toList());
//               System.out.println(armors);
////               System.out.println(e.);
//           }
//
////           if(e.isShiftClick()){
////               if(e.getCurrentItem()!=null&&
////                       e.getCurrentItem().getType().equals(Material.TURTLE_HELMET)&&
////                        e.getCurrentItem().getItemMeta().getDisplayName().equals("The Crown")
////               ){
////
////
////                   System.out.println(armors.contains(e.getCurrentItem()));
////
////               }
////           }
//        }
//    }


}
