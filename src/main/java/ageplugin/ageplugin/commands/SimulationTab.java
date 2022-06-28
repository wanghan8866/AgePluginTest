package ageplugin.ageplugin.commands;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationTab implements TabCompleter {

    private AgePlugin agePlugin;

    public SimulationTab(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        List<String> results=new ArrayList<>();
        if(sender instanceof Player){
            Player player=(Player) sender;
            // \simulation team join <>
            //  \simulation team remove
            if(args.length==1){
                results.add("team");
            }else if(args.length==2 && args[0].equals("team")){
                results.add("join");
                results.add("remove");
                results.add("teleport");
            }else if(args.length==3 && args[0].equals("team") && args[1].equals("remove")){
                for(Player p:Bukkit.getOnlinePlayers()){
                    results.add(p.getName());
                }
            }
            else if(args.length==3 && args[0].equals("team") && (args[1].equals("join")|args[1].equals("teleport"))){
                for(TeamType type:TeamType.values()){
                    results.add(type.getText());
                }
            }
            else if(args.length==4 && args[0].equals("team") && args[1].equals("join") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                for(Player p:Bukkit.getOnlinePlayers()){
                    results.add(p.getName());
                }
            }else if(args.length==4 && args[0].equals("team") && args[1].equals("teleport") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                results.add("x");
            }
            else if(args.length==5 && args[0].equals("team") && args[1].equals("teleport") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                results.add("y");
            }
            else if(args.length==6 && args[0].equals("team") && args[1].equals("teleport") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                results.add("z");
            }

        }


        return results;
    }
}