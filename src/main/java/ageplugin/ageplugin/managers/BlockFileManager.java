package ageplugin.ageplugin.managers;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.TeamType;
import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockFileManager{
    private File file;
//    private Gson gson;
    public BlockFileManager(AgePlugin agePlugin, String fileName) {

        try {
            this.file =new File(agePlugin.getDataFolder(),"blocks.json");
            if(!file.exists()){
                file.createNewFile();
            }


        }
        catch (IOException e){e.printStackTrace();}

    }

    public void writeBlocks(HashSet<Block> blocks){
        try {
            // write all location of decoration anvil into a file
            // since anvil can fail, loop through possible y value to find it and update the location
            List<Map<String,Object>> data= new ArrayList<>();

            for (Block block:blocks
                 ) {
                if(block.getType().equals(Material.ANVIL)||block.getType().equals(Material.DAMAGED_ANVIL)||block.getType().equals(Material.CHIPPED_ANVIL)){
                    data.add(block.getLocation().serialize());
                }else if(block.getType().equals(Material.AIR)){
                    Location location=block.getLocation();
                    for(int i=block.getLocation().getBlockY();i>-64;i--){
                        location.setY(i);
                        Block newBlock=location.getBlock();
                        if(!newBlock.getType().equals(Material.AIR)){
                            if(newBlock.getType().equals(Material.ANVIL)||
                                    newBlock.getType().equals(Material.DAMAGED_ANVIL)||newBlock.getType().equals(Material.CHIPPED_ANVIL)){
                                data.add(newBlock.getLocation().serialize());
                            }else{
                                break;
                            }
                        }
                    }
                }
            }
//                    blocks.stream().map((block -> block.getLocation().serialize())).collect(Collectors.toList());
            Gson gson=new Gson();
            Writer writer=new FileWriter(file,false);
            gson.toJson(data,writer);
            writer.flush();
            writer.close();


        }
        catch (IOException e){e.printStackTrace();}


    }

    public HashSet<Block> readBlocks(){
        // read the file to get location of each decoration anvil
        try {
            Gson gson=new Gson();
            Reader reader=new FileReader(file);
            List<Map<String,Object>> data=gson.fromJson(reader,List.class);
            System.out.println("reading blocks");
//            System.out.println(data);
            if(data!=null){
                return data.stream().map(location->Location.deserialize(location).getBlock()).collect(Collectors.toCollection(HashSet::new));
            }





        }
        catch (
                IOException e){e.printStackTrace();
        }
        return null;
    }
}
