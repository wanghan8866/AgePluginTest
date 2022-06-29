package ageplugin.ageplugin.commands;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.AbstractTeam;
import ageplugin.ageplugin.team.TeamType;
import ageplugin.ageplugin.UI.TeamUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationCommand implements CommandExecutor {
    private AgePlugin agePlugin;

    public SimulationCommand(AgePlugin agePlugin){
        this.agePlugin=agePlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player=(Player) sender;
            // \simulation team join <team> <player>

            if(args.length==1 && args[0].equalsIgnoreCase("team")){
                AbstractTeam team=this.agePlugin.getTeamManager().getTeam(player);
//                System.out.println(team);
                if(team!=null){
                    player.sendMessage("Current team: "+team.getType().getDisplay());
                }else{
                    player.sendMessage(ChatColor.RED+"You are not in any team now!");
                }

            }
            else if(args.length==2 && args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("join")){

                new TeamUI(this.agePlugin,player);
//                if(!agePlugin.getTeamManager().inTeam(player)){
//                    new TeamUI(this.agePlugin,player);
//                }else{
//                    player.sendMessage(ChatColor.RED+"You have chosen the team: "+agePlugin.getTeamManager().getTeam(player).getType().getDisplay());
//                }
            }
            else if(args.length==3 && args[0].equalsIgnoreCase("team") &&args[1].equalsIgnoreCase("join")){
                String typeName=args[2];
                joinTeam(player, typeName);
            }
            else if(args.length==4 && args[0].equalsIgnoreCase("team") &&args[1].equalsIgnoreCase("join") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){

                String typeName=args[2];
                String playerName=args[3];
                player=Bukkit.getPlayer(playerName);
                if(player!=null){
                    joinTeam(player, typeName);
                }

            } // \simulation team

            // \simulation team remove
            else if(args.length==2 &&  args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("remove")){
                agePlugin.getTeamManager().removeTeam(player.getUniqueId());
            }
            else if(args.length==3 &&  args[0].equalsIgnoreCase("team") && args[1].equalsIgnoreCase("remove")){
                String playerName=args[2];
                player=Bukkit.getPlayer(playerName);
                if(player!=null){
                    agePlugin.getTeamManager().removeTeam(player.getUniqueId());
                }

            }
            else if(args.length==6  && args[0].equalsIgnoreCase("team") &&args[1].equalsIgnoreCase("teleport") &&
                    Stream.of(TeamType.values()).map(e->e.getText().toLowerCase()).collect(Collectors.toList()).contains(args[2].toLowerCase())){
                try {
                    double x=Integer.parseInt(args[3]);
                    double y=Integer.parseInt(args[4]);
                    double z=Integer.parseInt(args[5]);
                    String typeName=args[2];
                    TeamType type=null;
                    if(typeName.equalsIgnoreCase(TeamType.FUTURE.name())){
                    type=TeamType.FUTURE;

                    }else if(typeName.equalsIgnoreCase(TeamType.PAST.name())){
                        type=TeamType.PAST;
                    }else if(typeName.equalsIgnoreCase(TeamType.PRESENT.name())){
                        type=TeamType.PRESENT;

                    }else if(typeName.equalsIgnoreCase(TeamType.ADMIN.name())){
                        type=TeamType.ADMIN;
                    }
                    if(type!=null){
                        this.agePlugin.getTeamManager().teleportTeam(type,new Location(player.getWorld(),x,y,z));
                    }
                    else{
                        player.sendMessage(ChatColor.RED+"Invalid Team Type!");
                    }


                }catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED+"Wrong format for coordinates");

                }

            }
            else{
                player.sendMessage(ChatColor.RED+"Wrong command for \\simulation");
                player.sendMessage(ChatColor.RED+"Please try: ");
                player.sendMessage(ChatColor.RED+"  join <Team> <Name | Optional>");
                player.sendMessage(ChatColor.RED+"  remove <Name | Optional>");
            }

        }else if(sender instanceof ConsoleCommandSender){
            if(args.length==1 && args[0].equalsIgnoreCase("start")){
                if(!this.agePlugin.getTeamManager().getIsStarted()){
                    this.agePlugin.getTeamManager().setIsStarted(true);
                    for(UUID id: agePlugin.getTeamManager().getTeams().keySet()){
                        System.out.println(agePlugin.getTeamManager().getTeams().get(id));
                        Player player=Bukkit.getPlayer(id);
                        if(player!=null){
                            agePlugin.getTeamManager().getTeams().get(id).onStart(player);
                            player.closeInventory();
                        }

                    }
                    System.out.println("starting!");
                }else{
                    System.out.println("The event has already started!");
                }


            }else if(args.length==1 && args[0].equalsIgnoreCase("list")){
                for(UUID id: agePlugin.getTeamManager().getTeams().keySet()){
                    System.out.println(id+": "+agePlugin.getTeamManager().getTeams().get(id));


                }
            }else if(args.length==1 && args[0].equalsIgnoreCase("end")){
                agePlugin.getTeamManager().setIsStarted(false);
            }
            else{
                System.out.println("no such command!");
            }
        }

        return false;
    }

    private void joinTeam(Player player, String typeName) {
        test();

        if(typeName.equalsIgnoreCase(TeamType.FUTURE.name())){
//                    for (int i = 0; i < 100; i++) {
//                        agePlugin.getTeamManager().setTeam(player.getUniqueId(),TeamType.FUTURE);
//                    }
            agePlugin.getTeamManager().setTeam(player.getUniqueId(),TeamType.FUTURE);

        }else if(typeName.equalsIgnoreCase(TeamType.PAST.name())){
            agePlugin.getTeamManager().setTeam(player.getUniqueId(),TeamType.PAST);
        }else if(typeName.equalsIgnoreCase(TeamType.PRESENT.name())){
            agePlugin.getTeamManager().setTeam(player.getUniqueId(),TeamType.PRESENT);

        }else if(typeName.equalsIgnoreCase(TeamType.ADMIN.name())){
            agePlugin.getTeamManager().setTeam(player.getUniqueId(),TeamType.ADMIN);
        }
        else{
            player.sendMessage(ChatColor.RED+"Invalid Team Type!");
        }
    }

    private void test(){
        agePlugin.getTeamManager().setTeam(UUID.fromString("1190f227-e21e-4113-8e63-4d7c8cd95182"),TeamType.PAST);
        agePlugin.getTeamManager().setTeam(UUID.fromString("1190f227-e21e-4113-8e63-4d7c8cd95183"),TeamType.FUTURE);
        agePlugin.getTeamManager().setTeam(UUID.fromString("1190f227-e21e-4113-8e63-4d7c8cd95184"),TeamType.PRESENT);
        agePlugin.getTeamManager().setTeam(UUID.fromString("1190f227-e21e-4113-8e63-4d7c8cd95185"),TeamType.ADMIN);
    }
}
