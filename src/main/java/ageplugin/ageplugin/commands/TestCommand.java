package ageplugin.ageplugin.commands;

import ageplugin.ageplugin.AgePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    private AgePlugin agePlugin;

    public TestCommand(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(sender instanceof Player){
            Player player= (Player) sender;

            player.sendMessage(ChatColor.YELLOW+ChatColor.BOLD.toString()+"Testing!");
        }


        return false;
    }
}
