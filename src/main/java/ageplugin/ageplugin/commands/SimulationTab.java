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
        // set up tab completion for the simulation commands
        List<String> results=new ArrayList<>();
        if(sender instanceof Player){
            Player player=(Player) sender;
            // \simulation team join <>
            //  \simulation team remove
            if(args.length==1){
                results.add("team");
            }else if(args.length==2 && args[0].equals("team")){
                results.add("join");
                if(player.isOp()){

                    results.add("remove");
                    results.add("teleport");
                    results.add("give");
                    results.add("hunger");
                }
            }else if(args.length==3 && args[0].equals("team") && args[1].equals("remove")){
                if(player.isOp()){
                    for(Player p:Bukkit.getOnlinePlayers()){
                        results.add(p.getName());
                    }
                }

            }
            else if(args.length==3 && args[0].equals("team") && args[1].equals("hunger")){
                if(player.isOp()){
                    results.add("start");
                    results.add("end");
                }

            }
            else if(args.length==3 && args[0].equals("team") && args[1].equals("give")){
                if(player.isOp()){
                    for(Player p:Bukkit.getOnlinePlayers()){
                        results.add(p.getName());
                    }
                }

            }
            else if(args.length==4 && args[0].equals("team") && args[1].equals("give")){
                if(player.isOp()){
                    results.addAll(this.agePlugin.getSpecialItemManager().getSpecialItems().keySet());
                }

            }
            else if(args.length==3 && args[0].equals("team") && (args[1].equals("join")|args[1].equals("teleport"))){
                if(player.isOp()){
                    for(TeamType type:TeamType.values()){
                        results.add(type.getText());
                    }
                }

            }
            else if(args.length==4 && args[0].equals("team") && args[1].equals("join") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                if(player.isOp()){
                    for(Player p:Bukkit.getOnlinePlayers()){
                        results.add(p.getName());
                    }
                }

            }else if(args.length==4 && args[0].equals("team") && args[1].equals("teleport") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                if(player.isOp()){
                    results.add("x");
                }

            }
            else if(args.length==5 && args[0].equals("team") && args[1].equals("teleport") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                if(player.isOp()){
                    results.add("y");
                }

            }
            else if(args.length==6 && args[0].equals("team") && args[1].equals("teleport") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                if(player.isOp()){
                    results.add("z");
                }

            }

        }


        return results;
    }
}
