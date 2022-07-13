package ageplugin.ageplugin.Enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class EnchantmentWrapper extends Enchantment {
    private final String name;
    private final int maxLevel;
    private final EnchantmentTarget enchantmentTarget;
    public EnchantmentWrapper(String namespace, String name, int maxLevel, EnchantmentTarget enchantmentTarget) {
        super(NamespacedKey.minecraft(namespace));
        this.name = name;
        this.maxLevel = maxLevel;
        this.enchantmentTarget = enchantmentTarget;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return enchantmentTarget;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return false;
    }
}
