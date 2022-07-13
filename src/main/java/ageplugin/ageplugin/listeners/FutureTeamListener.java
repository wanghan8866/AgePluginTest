package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FutureTeamListener implements Listener {
    private AgePlugin agePlugin;
    private static final HashSet<Material> disallowedFood= Stream.of(
            Material.APPLE, Material.BAKED_POTATO, Material.BEETROOT, Material.BEETROOT_SOUP,
             Material.CARROT, Material.CHORUS_FRUIT, Material.COOKED_CHICKEN,
            Material.COOKED_COD, Material.COOKED_MUTTON, Material.COOKED_PORKCHOP, Material.COOKED_RABBIT,
            Material.COOKED_SALMON,  Material.DRIED_KELP, Material.GLOW_BERRIES, Material.MELON_SLICE,
            Material.POISONOUS_POTATO, Material.POTATO, Material.PUFFERFISH, Material.BEEF, Material.CHICKEN,
            Material.COD, Material.MUTTON, Material.PORKCHOP, Material.RABBIT, Material.SALMON, Material.ROTTEN_FLESH,
            Material.SPIDER_EYE, Material.COOKED_BEEF, Material.SWEET_BERRIES, Material.TROPICAL_FISH

    ).collect(Collectors.toCollection(HashSet::new));

    private static final HashSet<Enchantment> crossBowAllowed=Stream.of(
            Enchantment.QUICK_CHARGE,
            Enchantment.MULTISHOT,
            Enchantment.PIERCING,
            Enchantment.DURABILITY,
            Enchantment.MENDING,
            Enchantment.VANISHING_CURSE
    ).collect(Collectors.toCollection(HashSet::new));

    public FutureTeamListener(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
//        NamespacedKey e_golden_apple_key=new NamespacedKey(this.agePlugin,"custom_e_golden_apple");
//        ShapedRecipe e_golden_apple=new ShapedRecipe(e_golden_apple_key,new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
//        e_golden_apple.shape(
//                "GGG",
//                "GAG",
//                "GGG");
//        e_golden_apple.setIngredient('G', Material.GOLD_BLOCK);
//        e_golden_apple.setIngredient('A', Material.APPLE);
//        Bukkit.addRecipe(e_golden_apple);
//        this.agePlugin.getRecipeKeysMap().get(TeamType.FUTURE).add(e_golden_apple_key);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        // plus one fortune
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
        // set the number of drops based on the looting fortune level
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
        // plus one looting for mob drops
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

        // return the used lapis when using enchanting table.
        Player player=e.getEnchanter();
        if(typeCheck(player.getUniqueId())){
            player.setLevel(player.getLevel()+e.whichButton());
            player.getInventory().addItem(new ItemStack(Material.LAPIS_LAZULI,e.whichButton()+1));
        }


    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e){
        // restrict diet to certain food
        // disable drinking milk

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

    @EventHandler
    public void onEnchantingCrossBow(PrepareAnvilEvent e){
        // allow more op enchants for crossbow
        if(typeCheck(e.getViewers().get(0).getUniqueId())){
            AnvilInventory anvilInventory=e.getInventory();
            anvilInventory.setRepairCost(Math.max(1,anvilInventory.getRepairCost()/2));

            ItemStack item1=e.getView().getTopInventory().getItem(0);
            ItemStack item2=e.getView().getTopInventory().getItem(1);

            if(item1!=null&&item2!=null  && item1.getType().equals(Material.ENCHANTED_BOOK)&&item1.getType().equals(item2.getType()) &&e.getResult()!=null ){
//                System.out.println(item1);
//                System.out.println(item2);
                if(item1.getItemMeta() instanceof EnchantmentStorageMeta && item2.getItemMeta() instanceof EnchantmentStorageMeta &&e.getResult().getItemMeta() instanceof  EnchantmentStorageMeta){
                    EnchantmentStorageMeta meta1= (EnchantmentStorageMeta) item1.getItemMeta();
                    EnchantmentStorageMeta meta2= (EnchantmentStorageMeta) item2.getItemMeta();


                        ItemStack book=new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta enchantmentStorageMeta= (EnchantmentStorageMeta) e.getResult().getItemMeta();
                        if(enchantmentStorageMeta!=null){
                            Map<Enchantment,Integer>maps=combineEnchantments(meta1.getStoredEnchants(),meta2.getStoredEnchants());
                            for(Enchantment enchantment:maps.keySet()){
                                enchantmentStorageMeta.addStoredEnchant(enchantment,maps.get(enchantment),true);
                            }

                            book.setItemMeta(enchantmentStorageMeta);
                            e.setResult(book);
                        }







                }


            }else if(item1!=null && item2!=null && item1.getType().equals(Material.CROSSBOW) && item2.getType().equals(Material.ENCHANTED_BOOK)){

//                System.out.println(item1);
                if(item2.getItemMeta() instanceof EnchantmentStorageMeta){
                    EnchantmentStorageMeta meta2= (EnchantmentStorageMeta) item2.getItemMeta();
//                    System.out.println(item1.getEnchantments());
//                    System.out.println(meta2.getStoredEnchants());

                    HashMap<Enchantment,Integer>maps=combineEnchantments(item1.getEnchantments(),meta2.getStoredEnchants());
//                    System.out.println(maps);

                    ItemStack crossbow=new ItemStack(Material.CROSSBOW);
                    crossbow.addUnsafeEnchantments(maps);
                    e.setResult(crossbow);




                }
            }else if(item1!=null&&item2!=null&&item1.getType().equals(Material.CROSSBOW)&&item1.getType().equals(item2.getType())){
                HashMap<Enchantment,Integer>maps=combineEnchantments(item1.getEnchantments(),item2.getEnchantments());

                ItemStack crossbow=new ItemStack(Material.CROSSBOW);
                crossbow.addUnsafeEnchantments(maps);
                e.setResult(crossbow);
            }


                }

            }




    private boolean typeCheck(UUID id){
        return this.agePlugin.getTeamManager().checkType(id, TeamType.FUTURE);
    }

    private HashMap<Enchantment,Integer> combineEnchantments(Map<Enchantment,Integer> a1,Map<Enchantment,Integer> b1){
        // combine Enchantments from two items
        HashMap<Enchantment,Integer> maps=new HashMap<>();
        loopEnchantments(b1, a1, maps);
        loopEnchantments(a1, b1, maps);

        return maps;
    }

    private void loopEnchantments(Map<Enchantment, Integer> a1, Map<Enchantment, Integer> b1, Map<Enchantment, Integer> maps) {
        // if an enchants appear on both maps, try to increment the level of it.
        for(Enchantment key:b1.keySet()){
            if(!crossBowAllowed.contains(key)){
                continue;
            }
            Integer level=b1.get(key);
            if(a1.get(key)!=null&&level.equals(a1.get(key))){
                if(key.equals(Enchantment.QUICK_CHARGE)) {
                    level = Math.min(4, level + 1);
                }else if(key.equals(Enchantment.PIERCING)){
                    level = Math.min(5, level + 1);
                }
                else if(key.equals(Enchantment.DURABILITY)){
                    level=Math.min(3,level+1);
                }

            }else if(a1.get(key)!=null){
                level=Math.max(a1.get(key),level);
            }
            maps.put(key,level);
        }
    }

    @EventHandler
    public void onCrossBowShoot(EntityShootBowEvent e){
        // disabled shooting fireworks if the quick charge is higher than 3
        if(e.getEntity() instanceof Player){
            Player player=(Player)e.getEntity();
            if(typeCheck(player.getUniqueId())){
                if(e.getBow()!=null){
                    if(e.getBow().getType().equals(Material.CROSSBOW) && e.getEntity() instanceof Player){
                        if(e.getBow().getEnchantments().containsKey(Enchantment.QUICK_CHARGE)){
//                            System.out.println();
//                            System.out.println();
                            if(e.getBow().getEnchantments().get(Enchantment.QUICK_CHARGE)>3 && e.getConsumable().getType().equals(Material.FIREWORK_ROCKET)){

                                e.setCancelled(true);
//                                e.setProjectile(e.getProjectile());



                            }
                        }
                    }
                }

            }

        }

    }



}
