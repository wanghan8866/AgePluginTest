package ageplugin.ageplugin.listeners;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PastTeamListener implements Listener {
    private AgePlugin agePlugin;

    private static final HashSet<Material> allowedCraftingResults = Stream.of
            (
                    Material.ACACIA_PLANKS,Material.BIRCH_PLANKS,
                    Material.CRIMSON_PLANKS,Material.OAK_PLANKS,
                    Material.DARK_OAK_PLANKS,Material.JUNGLE_PLANKS,
                    Material.SPRUCE_PLANKS,Material.WARPED_PLANKS,
                    Material.STICK,Material.TORCH,Material.CHEST,
                    Material.WOODEN_AXE,Material.WOODEN_HOE,Material.WOODEN_PICKAXE,
                    Material.WOODEN_SHOVEL,Material.WOODEN_SWORD,
                    Material.STONE_AXE,Material.STONE_HOE,Material.STONE_PICKAXE,
                    Material.STONE_SHOVEL,Material.STONE_SWORD,Material.FISHING_ROD, Material.BOW,
                    Material.ARROW, Material.LEATHER, Material.LEATHER_BOOTS,Material.LEATHER_LEGGINGS, Material.LEATHER_HORSE_ARMOR,
                    Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,Material.HAY_BLOCK,Material.BIRCH_BOAT,
                    Material.ACACIA_BOAT,Material.OAK_BOAT,Material.DARK_OAK_BOAT, Material.JUNGLE_BOAT,Material.SPRUCE_BOAT,

                    Material.DARK_OAK_DOOR, Material.ACACIA_DOOR, Material.OAK_DOOR, Material.BIRCH_DOOR, Material.CRIMSON_DOOR,
                    Material.JUNGLE_DOOR, Material.SPRUCE_DOOR, Material.WARPED_DOOR,

                    Material.DARK_OAK_SLAB, Material.DARK_OAK_BUTTON, Material.DARK_OAK_FENCE, Material.DARK_OAK_FENCE_GATE,
                    Material.DARK_OAK_SIGN, Material.DARK_OAK_STAIRS,Material.DARK_OAK_TRAPDOOR,
                    Material.ACACIA_SLAB, Material.ACACIA_BUTTON, Material.ACACIA_FENCE, Material.ACACIA_FENCE_GATE,
                    Material.ACACIA_SIGN, Material.ACACIA_STAIRS,Material.ACACIA_TRAPDOOR,
                    Material.OAK_SLAB, Material.OAK_BUTTON, Material.OAK_FENCE, Material.OAK_FENCE_GATE,
                    Material.OAK_SIGN, Material.OAK_STAIRS,Material.OAK_TRAPDOOR,
                    Material.BIRCH_SLAB, Material.BIRCH_BUTTON, Material.BIRCH_FENCE, Material.BIRCH_FENCE_GATE,
                    Material.BIRCH_SIGN, Material.BIRCH_STAIRS,Material.BIRCH_TRAPDOOR,
                    Material.CRIMSON_SLAB, Material.CRIMSON_BUTTON, Material.CRIMSON_FENCE, Material.CRIMSON_FENCE_GATE,
                    Material.CRIMSON_SIGN, Material.CRIMSON_STAIRS,Material.CRIMSON_TRAPDOOR,
                    Material.JUNGLE_SLAB, Material.JUNGLE_BUTTON, Material.JUNGLE_FENCE, Material.JUNGLE_FENCE_GATE,
                    Material.JUNGLE_SIGN, Material.JUNGLE_STAIRS,Material.JUNGLE_TRAPDOOR,
                    Material.SPRUCE_SLAB, Material.SPRUCE_BUTTON, Material.SPRUCE_FENCE, Material.SPRUCE_FENCE_GATE,
                    Material.SPRUCE_SIGN, Material.SPRUCE_STAIRS,Material.SPRUCE_TRAPDOOR,
                    Material.WARPED_SLAB, Material.WARPED_BUTTON, Material.WARPED_FENCE, Material.WARPED_FENCE_GATE,
                    Material.WARPED_SIGN, Material.WARPED_STAIRS,Material.WARPED_TRAPDOOR,

                    Material.COBBLESTONE_SLAB, Material.COBBLESTONE_STAIRS, Material.COBBLESTONE_WALL,
                    Material.BREAD, Material.SUGAR, Material.LADDER, Material.ITEM_FRAME, Material.MELON_SEEDS,
                    Material.MELON_SLICE, Material.MELON, Material.PAINTING,

                    Material.RED_DYE, Material.RED_BED, Material.RED_WOOL, Material.RED_CARPET,
                    Material.BLACK_DYE, Material.BLACK_BED, Material.BLACK_WOOL, Material.BLACK_CARPET,
                    Material.BLUE_DYE, Material.BLUE_BED, Material.BLUE_WOOL, Material.BLUE_CARPET,
                    Material.BROWN_DYE, Material.BROWN_BED, Material.BROWN_WOOL, Material.BROWN_CARPET,
                    Material.CYAN_DYE, Material.CYAN_BED, Material.CYAN_WOOL, Material.CYAN_CARPET,
                    Material.GRAY_DYE, Material.GRAY_BED, Material.GRAY_WOOL, Material.GRAY_CARPET,
                    Material.GREEN_DYE, Material.GREEN_BED, Material.GREEN_WOOL, Material.GREEN_CARPET,
                    Material.LIGHT_BLUE_DYE, Material.LIGHT_BLUE_BED, Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_CARPET,
                    Material.LIGHT_GRAY_DYE, Material.LIGHT_GRAY_BED, Material.LIGHT_GRAY_WOOL, Material.LIGHT_GRAY_CARPET,
                    Material.LIME_DYE, Material.LIME_BED, Material.LIME_WOOL, Material.LIME_CARPET,
                    Material.MAGENTA_DYE, Material.MAGENTA_BED, Material.MAGENTA_WOOL, Material.MAGENTA_CARPET,
                    Material.ORANGE_DYE, Material.ORANGE_BED, Material.ORANGE_WOOL, Material.ORANGE_CARPET,
                    Material.PINK_DYE, Material.PINK_BED, Material.PINK_WOOL, Material.PINK_CARPET,
                    Material.PURPLE_DYE, Material.PURPLE_BED, Material.PURPLE_WOOL, Material.PURPLE_CARPET,
                    Material.WHITE_DYE, Material.WHITE_BED, Material.WHITE_WOOL, Material.WHITE_CARPET,
                    Material.YELLOW_DYE, Material.YELLOW_BED, Material.YELLOW_WOOL, Material.YELLOW_CARPET,
                    Material.CAMPFIRE, Material.COMPOSTER, Material.BARREL, Material.BOWL, Material.MUSHROOM_STEW, Material.BEETROOT_SOUP,
                    Material.CRAFTING_TABLE, Material.BONE_MEAL, Material.BONE,
                    Material.IRON_INGOT, Material.SHIELD, Material.BUCKET, Material.FLINT_AND_STEEL,
                    Material.SHEARS, Material.IRON_BARS, Material.IRON_BLOCK,
                    Material.IRON_AXE,Material.IRON_HOE,Material.IRON_PICKAXE,
                    Material.IRON_SHOVEL,Material.IRON_SWORD,
                    Material.IRON_BOOTS,Material.IRON_LEGGINGS, Material.IRON_HORSE_ARMOR,
                    Material.IRON_CHESTPLATE, Material.IRON_HELMET, Material.FURNACE,
                    Material.CHAINMAIL_BOOTS,Material.CHAINMAIL_LEGGINGS,
                    Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.IRON_NUGGET, Material.CHAIN

                    ).collect(Collectors.toCollection(HashSet::new));
    private static final HashSet<Material> disallowedTables=Stream.of(
            Material.FURNACE_MINECART, Material.BLAST_FURNACE, Material.SMOKER,
            Material.BREWING_STAND,Material.SMITHING_TABLE, Material.STONECUTTER, Material.GRINDSTONE,
            Material.ENCHANTING_TABLE,Material.FLETCHING_TABLE, Material.CARTOGRAPHY_TABLE, Material.LOOM,
            Material.DROPPER, Material.DISPENSER, Material.LECTERN, Material.ANVIL, Material.BEACON

    ).collect(Collectors.toCollection(HashSet::new));

    private static final HashSet<InventoryType> disallowedInventories=Stream.of(
            InventoryType.FURNACE,InventoryType.BLAST_FURNACE, InventoryType.SMOKER, InventoryType.MERCHANT,
            InventoryType.BREWING, InventoryType.SMITHING, InventoryType.STONECUTTER, InventoryType.GRINDSTONE,
            InventoryType.ENCHANTING, InventoryType.ANVIL, InventoryType.BEACON, InventoryType.CARTOGRAPHY,
            InventoryType.DISPENSER, InventoryType.DROPPER, InventoryType.LOOM, InventoryType.LECTERN

    ).collect(Collectors.toCollection(HashSet::new));


    public PastTeamListener(AgePlugin agePlugin){

        // create recipes for chain-mails
        this.agePlugin=agePlugin;

        NamespacedKey key1=new NamespacedKey(this.agePlugin,"custom_chained_boots_1");
        ShapedRecipe chain_boots=new ShapedRecipe(key1,new ItemStack(Material.CHAINMAIL_BOOTS));
        chain_boots.shape(
                "N N",
                "I I",
                "   ");
        chain_boots.setIngredient('N', Material.IRON_NUGGET);
        chain_boots.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chain_boots);

        ShapedRecipe chain_boots_1=new ShapedRecipe(new NamespacedKey(this.agePlugin,"custom_chained_boots_2"),new ItemStack(Material.CHAINMAIL_BOOTS));
        chain_boots_1.shape(
                "   ",
                "N N",
                "I I"
               );
        chain_boots_1.setIngredient('N', Material.IRON_NUGGET);
        chain_boots_1.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chain_boots_1);


        ShapedRecipe chain_helmet_1=new ShapedRecipe(new NamespacedKey(this.agePlugin,"custom_chained_helmet_1"),new ItemStack(Material.CHAINMAIL_HELMET));
        chain_helmet_1.shape(
                "   ",
                "NIN",
                "I I"
        );
        chain_helmet_1.setIngredient('N', Material.IRON_NUGGET);
        chain_helmet_1.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chain_helmet_1);


        NamespacedKey key2=new NamespacedKey(this.agePlugin,"custom_chained_helmet_2");
        ShapedRecipe chain_helmet_2=new ShapedRecipe(key2,new ItemStack(Material.CHAINMAIL_HELMET));
        chain_helmet_2.shape(
                "NIN",
                "I I",
                "   "
        );
        chain_helmet_2.setIngredient('N', Material.IRON_NUGGET);
        chain_helmet_2.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chain_helmet_2);


        NamespacedKey key3 = new NamespacedKey(this.agePlugin,"custom_chained_chestplate_1");
        ShapedRecipe chain_chestplate_1=new ShapedRecipe(key3,new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        chain_chestplate_1.shape(
                "N N",
                "III",
                "NIN"
        );
        chain_chestplate_1.setIngredient('N', Material.IRON_NUGGET);
        chain_chestplate_1.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chain_chestplate_1);


        NamespacedKey key4 = new NamespacedKey(this.agePlugin,"custom_chained_leggings_1");
        ShapedRecipe chain_leggings_1=new ShapedRecipe(key4,new ItemStack(Material.CHAINMAIL_LEGGINGS));
        chain_leggings_1.shape(
                "NIN",
                "I I",
                "N N"
        );
        chain_leggings_1.setIngredient('N', Material.IRON_NUGGET);
        chain_leggings_1.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(chain_leggings_1);



        this.agePlugin.getRecipeKeysMap().get(TeamType.PAST).add(key1);
        this.agePlugin.getRecipeKeysMap().get(TeamType.PAST).add(key2);
        this.agePlugin.getRecipeKeysMap().get(TeamType.PAST).add(key3);
        this.agePlugin.getRecipeKeysMap().get(TeamType.PAST).add(key4);

    }


    @EventHandler
    public void onInteractWithTables(PlayerInteractEvent e){
        // only allow interaction with certain inventory,
        Player player=e.getPlayer();
        if(typeCheck(player.getUniqueId())){
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock()!=null&& disallowedTables.contains(e.getClickedBlock().getType())
            ){
                e.setCancelled(true);

            }else{
                e.setCancelled(false);
            }
        }
    }




    @EventHandler
    public void onCrossBowShoot(EntityShootBowEvent e){
        // can not use crossbow
        if(e.getEntity() instanceof Player){
            Player player=(Player)e.getEntity();
            if(typeCheck(player.getUniqueId())){
                if(e.getBow()!=null){
                    if(e.getBow().getType().equals(Material.CROSSBOW) && e.getEntity() instanceof Player){
                        e.setCancelled(true);
                    }
                }

            }

        }

    }






    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e){
        // disable milk
        Player player = e.getPlayer();
        if (typeCheck(player.getUniqueId())) {
            Material food = e.getItem().getType();

//        System.out.println(e.getItem());
            if (food.equals(Material.MILK_BUCKET)) {
//                player.sendMessage("Drinking milk");
                e.setCancelled(true);
                player.getInventory().setItemInMainHand(new ItemStack(Material.BUCKET,1));
            }

        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEntityEvent e){
        // disable interaction with VILLAGER and WANDERING_TRADER
        if(typeCheck(e.getPlayer().getUniqueId())){
            if(e.getRightClicked().getType().equals(EntityType.VILLAGER)||e.getRightClicked().getType().equals(EntityType.WANDERING_TRADER)){
                e.setCancelled(true);
            }else{
                e.setCancelled(false);
            }
        }

    }

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e){

        // crating restriction
        // unlock iron armours with levels.
        Player player= (Player) e.getViewers().get(0);
        if(typeCheck(player.getUniqueId())){
            Recipe result = e.getRecipe();
            if(result!=null){
                if(!allowedCraftingResults.contains(result.getResult().getType())){
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                }else if(result.getResult().getType().equals(Material.IRON_HELMET)||result.getResult().getType().equals(Material.IRON_BOOTS)){

//                    System.out.println("PrepareItemCraftEvent: " +result.getResult());
                    if(player.getLevel()>=8){
//                        player.setLevel(player.getLevel()-15);
                    }else{
                        e.getInventory().setResult(new ItemStack(Material.AIR));
                        player.sendMessage(ChatColor.RED+"Not enough level to craft this!");
                    }
                }else if(result.getResult().getType().equals(Material.IRON_LEGGINGS)){
                    if(player.getLevel()>=10){
//                        player.setLevel(player.getLevel()-15);
                    }else{
                        e.getInventory().setResult(new ItemStack(Material.AIR));
                        player.sendMessage(ChatColor.RED+"Not enough level to craft this!");
                    }
                }
                else if(result.getResult().getType().equals(Material.IRON_CHESTPLATE)){

//                    System.out.println("PrepareItemCraftEvent: " +result.getResult());
                    if(player.getLevel()>=12){
//                        player.setLevel(player.getLevel()-15);
                    }else{
                        e.getInventory().setResult(new ItemStack(Material.AIR));
                        player.sendMessage(ChatColor.RED+"Not enough level to craft this!");
                    }
                }
            }

        }



    }

    @EventHandler
    public void OnCraftArmors(CraftItemEvent e){
        //cost iron armours wih levels
//        System.out.println("CraftItemEvent: " +e.getResult());
        Player player= (Player) e.getViewers().get(0);
        if(typeCheck(player.getUniqueId())){
            ItemStack result = e.getRecipe().getResult();

            if(result.getType().equals(Material.IRON_HELMET)||result.getType().equals(Material.IRON_BOOTS)){


                if(player.getLevel()>=8){
                    player.setLevel(player.getLevel()-8);
                }else{
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                    player.sendMessage(ChatColor.RED+"Not enough level to craft this!");
                }
            }else if(result.getType().equals(Material.IRON_LEGGINGS)){
                if(player.getLevel()>=10){
                    player.setLevel(player.getLevel()-10);
                }else{
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                    player.sendMessage(ChatColor.RED+"Not enough level to craft this!");
                }
            }
            else if(result.getType().equals(Material.IRON_CHESTPLATE)){

                if(player.getLevel()>=12){
                    player.setLevel(player.getLevel()-12);
                }else{
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                    player.sendMessage(ChatColor.RED+"Not enough level to craft this!");
                }
            }
        }

    }

    private boolean typeCheck(UUID id){
        return this.agePlugin.getTeamManager().checkType(id, TeamType.PAST);
    }
}
