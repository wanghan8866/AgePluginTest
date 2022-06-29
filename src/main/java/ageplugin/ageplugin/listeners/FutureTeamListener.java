package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FutureTeamListener implements Listener {
    private AgePlugin agePlugin;
    private static final HashSet<Material> disallowedFood= Stream.of(
            Material.APPLE, Material.BAKED_POTATO, Material.BEETROOT, Material.BEETROOT_SOUP,
            Material.BREAD, Material.CARROT, Material.CHORUS_FRUIT, Material.COOKED_CHICKEN,
            Material.COOKED_COD, Material.COOKED_MUTTON, Material.COOKED_PORKCHOP, Material.COOKED_RABBIT,
            Material.COOKED_SALMON,  Material.DRIED_KELP, Material.GLOW_BERRIES, Material.MELON_SLICE,
            Material.POISONOUS_POTATO, Material.POTATO, Material.PUFFERFISH, Material.BEEF, Material.CHICKEN,
            Material.COD, Material.MUTTON, Material.PORKCHOP, Material.RABBIT, Material.SALMON, Material.ROTTEN_FLESH,
            Material.SPIDER_EYE, Material.COOKED_BEEF, Material.SWEET_BERRIES, Material.TROPICAL_FISH

    ).collect(Collectors.toCollection(HashSet::new));

    public FutureTeamListener(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(typeCheck(e.getPlayer().getUniqueId())){
            if(e.getBlock().getType().equals(Material.REDSTONE_ORE)||e.getBlock().getType().equals(Material.DEEPSLATE_REDSTONE_ORE)){
                e.setDropItems(false);
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                        setDropItem(e.getPlayer(),Material.REDSTONE,5, 6, 7 ,8));
            }
            else if(e.getBlock().getType().equals(Material.LAPIS_ORE)||e.getBlock().getType().equals(Material.DEEPSLATE_LAPIS_ORE)){
                e.setDropItems(false);
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                        setDropItem(e.getPlayer(),Material.LAPIS_LAZULI,8, 11, 14 ,17));
            }
            else if(e.getBlock().getType().equals(Material.COAL_ORE)||e.getBlock().getType().equals(Material.DEEPSLATE_COAL_ORE)){
                e.setDropItems(false);
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                        setDropItem(e.getPlayer(),Material.COAL,2, 3, 4 ,5));
            }
        }



    }

    private ItemStack setDropItem(Player player, Material item, int a, int b, int c, int d){
        int amount=a;
        if(player.getInventory().getItemInMainHand()!=null){
            if(player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)){
                switch (player.getPlayer().getInventory().getItemInMainHand().getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS)){
                    case 1:amount=b;break;
                    case 2:amount=c;break;
                    case 3:amount=d;break;
                }
            }
        }

        return new ItemStack(item,amount);
    }


    @EventHandler
    public void onMobDeath(EntityDeathEvent e){
        if(e.getEntity().getKiller()!=null&&typeCheck(e.getEntity().getKiller().getUniqueId())){
            if(e.getEntity().getType().equals(EntityType.CREEPER)){
                e.getDrops().add(new ItemStack(Material.GUNPOWDER,1));
            }
            else if(e.getEntity().getType().equals(EntityType.BLAZE)){
                e.getDrops().add(new ItemStack(Material.BLAZE_ROD,1));
            }
            else if(e.getEntity().getType().equals(EntityType.SLIME)){
                if(((Slime)e.getEntity()).getSize()==1){
                    e.getDrops().add(new ItemStack(Material.SLIME_BALL,1));
                }

            }
        }


    }



    @EventHandler
    public void onEnchanting(EnchantItemEvent e){

        Player player=e.getEnchanter();
        if(typeCheck(player.getUniqueId())){
            player.setLevel(player.getLevel()+e.whichButton());
        }

    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e){
        Player player=e.getPlayer();
        if(typeCheck(player.getUniqueId())){
            Material food=e.getItem().getType();


//        System.out.println(e.getItem());
            if(food.equals(Material.MILK_BUCKET)){
//                player.sendMessage("Drinking milk");
                e.setCancelled(true);
                player.getInventory().setItemInMainHand(new ItemStack(Material.BUCKET,1));
            }else if(disallowedFood.contains(food)){
                e.setCancelled(true);
                player.sendMessage("So disgusting!!!");
                ItemStack food_stack=player.getInventory().getItemInMainHand();
                if(food_stack.getAmount()==1){
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                }else{
                    player.getInventory().setItemInMainHand(new ItemStack(food_stack.getType(),food_stack.getAmount()-1));
                }
                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,100,0));
            }
            else{
                e.setCancelled(false);
            }
        }



    }

    private boolean typeCheck(UUID id){
        return this.agePlugin.getTeamManager().checkType(id, TeamType.FUTURE);
    }


}