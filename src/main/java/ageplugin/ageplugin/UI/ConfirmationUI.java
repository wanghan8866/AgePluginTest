package ageplugin.ageplugin.UI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;

import java.util.Arrays;

public class ConfirmationUI {
    public ConfirmationUI(Player player, String what){
        Inventory gui= Bukkit.createInventory(null,54,"Confirmation for Team");

        ItemStack yes=new ItemStack(Material.EMERALD);
        ItemMeta yesMeta=yes.getItemMeta();
        yesMeta.setDisplayName("Yes");
        yesMeta.setLocalizedName(what);
        yesMeta.setLore(Arrays.asList(ChatPaginator.wordWrap(
                "You are now confirm with this choose and can not regard later!"
        , 30)));
        yes.setItemMeta(yesMeta);
        gui.setItem(20, yes);


        ItemStack cancel=new ItemStack(Material.BARRIER);
        ItemMeta cancelMeta=cancel.getItemMeta();
        cancelMeta.setDisplayName("Cancel");
        cancelMeta.setLocalizedName("cancel");
        cancelMeta.setLore(Arrays.asList(ChatPaginator.wordWrap(
                "You are now cancelling selecting!"
                , 30)));
        cancel.setItemMeta(cancelMeta);
        gui.setItem(24,cancel);

        player.openInventory(gui);
    }
}
