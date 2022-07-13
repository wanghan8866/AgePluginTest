package ageplugin.ageplugin.Enchantments;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomEnchants {

    public static final Enchantment CROWN=new EnchantmentWrapper("crown","Crown",1, EnchantmentTarget.ARMOR_HEAD);
    public static final Enchantment DECORATION=new EnchantmentWrapper("decoration","Decoration",1,null);
    public static final Enchantment MAGNETISM=new EnchantmentWrapper("magnetism","Magnetism",1,EnchantmentTarget.WEAPON);


    public static void register(){
        // register new Enchantments
        boolean registered= Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(CROWN);
        if(!registered){
            registerEnchantment(CROWN);
        }

        registered= Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(DECORATION);
        if(!registered){
            registerEnchantment(DECORATION);
        }

        registered= Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(MAGNETISM);
        if(!registered){
            registerEnchantment(MAGNETISM);
        }

    }

    public static void registerEnchantment(Enchantment enchantment){
        boolean registered=true;
        try{
            Field f=Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null,true);
            Enchantment.registerEnchantment(enchantment);

        }catch (Exception e){
            registered=false;
            e.printStackTrace();
        }

        if(registered){
            System.out.println("["+enchantment+"]"+" has been registered!");
        }
    }

    public static void setLore(ItemMeta meta,String lore){
        List<String> lores=new ArrayList<String>();
        lores.add(ChatColor.GRAY+lore);
        if(meta.hasLore()){
            lores.addAll(meta.getLore());
        }
        meta.setLore(lores);
    }
}
