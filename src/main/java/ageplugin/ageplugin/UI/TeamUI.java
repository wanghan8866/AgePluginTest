package ageplugin.ageplugin.UI;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.ChatPaginator;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class TeamUI {
    public TeamUI(AgePlugin agePlugin, Player player){
        Inventory gui= Bukkit.createInventory(null,54, ChatColor.BLUE+"Team Selection");

        int i=0;
        // set up button for each team excluding admin tean
        for(TeamType teamType: TeamType.values()){
            if(teamType.getHidden()){
                continue;
            }
            ItemStack itemStack=new ItemStack(teamType.getMaterial());
            ItemMeta itemMeta=itemStack.getItemMeta();
            itemMeta.setDisplayName(teamType.getDisplay()+" "+
                    ChatColor.GRAY+ "("+agePlugin.getTeamManager().getTeamCount(teamType)+"/"+
                    agePlugin.getTeamManager().getMaxTeamCount()
                    +" players)");
//            System.out.println(Arrays.asList(ChatPaginator.wordWrap(teamType.getDescription(), 30)));

            itemMeta.setLore(Arrays.asList(ChatPaginator.wordWrap(teamType.getDescription(), 30)));
            itemMeta.setLocalizedName(teamType.name());
            itemStack.setItemMeta(itemMeta);
            gui.setItem(20+2*i,itemStack);
            i++;
        }
        player.openInventory(gui);



    }
}
