package ageplugin.ageplugin.managers;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.Enchantments.CustomEnchants;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.text.AttributeSet;
import java.util.*;

public class SpecialItemManager {
    private final HashMap<String, ItemStack> specialItemsMap=new HashMap<>();
    private AgePlugin agePlugin;
    private final HashSet<Block> decoratedItems=new HashSet<>();

    public SpecialItemManager(AgePlugin agePlugin){
        this.agePlugin=agePlugin;


        ItemStack sword=new ItemStack(Material.DIAMOND_SWORD,1);
        sword.addUnsafeEnchantment(Enchantment.BINDING_CURSE,1);
        sword.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE,3);
        sword.addUnsafeEnchantment(Enchantment.DURABILITY,3);
        sword.addUnsafeEnchantment(Enchantment.MENDING,1);

        specialItemsMap.put("binding_sword",sword);


        ItemStack pearl=new ItemStack(Material.ENDER_PEARL,1);
        pearl.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1);
        specialItemsMap.put("infinity_ender_pearl",pearl);


        ItemStack crown=new ItemStack(Material.TURTLE_HELMET,1);
        crown.addUnsafeEnchantment(CustomEnchants.CROWN,1);
        crown.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,5);
        crown.addUnsafeEnchantment(Enchantment.DURABILITY,5);
        crown.addUnsafeEnchantment(Enchantment.MENDING,1);
        crown.addUnsafeEnchantment(Enchantment.BINDING_CURSE,1);
        crown.addUnsafeEnchantment(Enchantment.VANISHING_CURSE,1);


        ItemMeta meta=crown.getItemMeta();
        if(meta!=null){

            CustomEnchants.setLore(meta,ChatColor.GOLD+"The Crown");


            meta.setUnbreakable(true);
            AttributeModifier modifier =new AttributeModifier(UUID.randomUUID(),
                   "generic.armor",6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD
                    );
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR,modifier);
            AttributeModifier modifier1 =new AttributeModifier(UUID.randomUUID(),
                    "generic.armorThoroughness",4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD
            );
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,modifier1);
            AttributeModifier modifier2 =new AttributeModifier(UUID.randomUUID(),
                    "generic.knockBack_Resistance",0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD
            );
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,modifier2);

            meta.setDisplayName(ChatColor.GOLD+"The Crown");
            meta.setLocalizedName("The Crown");

            crown.setItemMeta(meta);
        }
        crown.getItemMeta().setUnbreakable(true);


        specialItemsMap.put("crown",crown);


        ItemStack anvil=new ItemStack(Material.ANVIL);
        anvil.addUnsafeEnchantment(CustomEnchants.DECORATION,1);
        ItemMeta anvil_meta=anvil.getItemMeta();
        if(anvil_meta!=null) {
            CustomEnchants.setLore(anvil_meta,"Decoration");
            anvil_meta.setDisplayName("Rail Anvil");


            anvil.setItemMeta(anvil_meta);
        }


            specialItemsMap.put("decoration_anvil",anvil);


        HashSet<Block> data=this.agePlugin.getBlockFileManager().readBlocks();
        if(data!=null){
            decoratedItems.addAll(data);
        }

        System.out.println(decoratedItems);


        ItemStack magnetic_sword=new ItemStack(Material.GOLDEN_SWORD);
        magnetic_sword.addUnsafeEnchantment(Enchantment.DURABILITY,3);
        magnetic_sword.addUnsafeEnchantment(Enchantment.MENDING,1);
        magnetic_sword.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS,3);
        magnetic_sword.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE,3);
        magnetic_sword.addUnsafeEnchantment(CustomEnchants.MAGNETISM,1);
        magnetic_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL,3);
        ItemMeta sword_meta=magnetic_sword.getItemMeta();
        if(sword_meta!=null){
            CustomEnchants.setLore(sword_meta,"Magnetism");
            magnetic_sword.setItemMeta(sword_meta);

        }
        specialItemsMap.put("magnetic_sword",magnetic_sword);

        ItemStack op_crossbox=new ItemStack(Material.CROSSBOW);
        op_crossbox.addUnsafeEnchantment(Enchantment.QUICK_CHARGE,5);
        op_crossbox.addUnsafeEnchantment(Enchantment.DURABILITY,3);
        op_crossbox.addUnsafeEnchantment(Enchantment.MULTISHOT,1);
        op_crossbox.addUnsafeEnchantment(Enchantment.MENDING,1);
        specialItemsMap.put("machine_gun",op_crossbox);



    }

    public boolean spawnItemToPlayer(Player player,String itemName){

        if(specialItemsMap.containsKey(itemName)){

            player.getInventory().addItem(specialItemsMap.get(itemName));

            return true;
        }
        return false;

    }

    public ItemStack getItem(String itName){
        if(specialItemsMap.containsKey(itName)){
            return specialItemsMap.get(itName);
        }
        return null;
    }

    public HashMap<String, ItemStack> getSpecialItems(){
        return specialItemsMap;
    }

    public HashSet<Block>getDecoratedItems(){
        return decoratedItems;
    }
    public void updateAnvil(){
        HashSet<Block> blocks=new HashSet<>();
        for (Block block:decoratedItems
        ) {
            if(block.getType().equals(Material.ANVIL)||block.getType().equals(Material.DAMAGED_ANVIL)||block.getType().equals(Material.CHIPPED_ANVIL)){
                blocks.add(block);
            }else if(block.getType().equals(Material.AIR)){
                Location location=block.getLocation();
                for(int i=block.getLocation().getBlockY();i>-64;i--){
                    location.setY(i);
                    Block newBlock=location.getBlock();
                    if(!newBlock.getType().equals(Material.AIR)){
                        if(newBlock.getType().equals(Material.ANVIL)||
                                newBlock.getType().equals(Material.DAMAGED_ANVIL)||newBlock.getType().equals(Material.CHIPPED_ANVIL)){
                            blocks.add(newBlock);
                        }else{
                            break;
                        }
                    }
                }
            }
        }

        decoratedItems.clear();
        decoratedItems.addAll(blocks);
    }

}
