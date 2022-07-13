package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PresentTeamListener implements Listener {

    private AgePlugin agePlugin;


    public PresentTeamListener(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e){

        Player player= (Player) e.getViewers().get(0);
        if(typeCheck(player.getUniqueId())){
            Recipe result = e.getRecipe();
            if(result!=null){
                if(result.getResult().getType().equals(Material.ENCHANTED_GOLDEN_APPLE)){
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                }
            }

        }



    }
    @EventHandler
    public void onCrossBowShoot(EntityShootBowEvent e){
        if(e.getEntity() instanceof Player){
            Player player=(Player)e.getEntity();
            if(typeCheck(player.getUniqueId())){
                if(e.getBow()!=null){
                    if(e.getBow().getType().equals(Material.CROSSBOW) && e.getEntity() instanceof Player){
                        if(e.getBow().getEnchantments().containsKey(Enchantment.QUICK_CHARGE) ){
//                            System.out.println();
//                            System.out.println();
                            if(e.getBow().getEnchantments().get(Enchantment.QUICK_CHARGE)>3){

                                e.setCancelled(true);
//                                e.setProjectile(e.getProjectile());



                            }
                        }
                        if(e.getBow().getEnchantments().containsKey(Enchantment.PIERCING) ){
//                            System.out.println();
//                            System.out.println();
                            if(e.getBow().getEnchantments().get(Enchantment.PIERCING)>4){

                                e.setCancelled(true);
//                                e.setProjectile(e.getProjectile());



                            }
                        }
                    }
                }

            }

        }

    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e){
        Player player=e.getPlayer();
        if(typeCheck(player.getUniqueId())){
            Material food=e.getItem().getType();
            if(food.equals(Material.MILK_BUCKET)){
//                player.sendMessage("Drinking milk");
                e.setCancelled(true);
                player.getInventory().setItemInMainHand(new ItemStack(Material.BUCKET,1));
            }
        }



    }

    private boolean typeCheck(UUID id){
        return this.agePlugin.getTeamManager().checkType(id, TeamType.PRESENT);
    }
}
