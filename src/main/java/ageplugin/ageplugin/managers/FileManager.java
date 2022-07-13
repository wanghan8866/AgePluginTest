package ageplugin.ageplugin.managers;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.AbstractTeam;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class FileManager {

    protected AgePlugin agePlugin;
    protected File file;
    protected YamlConfiguration yamlConfiguration;



    public FileManager(AgePlugin agePlugin,String fileName ) {
        this.agePlugin=agePlugin;

        this.file=new File(this.agePlugin.getDataFolder(),fileName);
        try {
            if(!file.exists()){
                file.createNewFile();


            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        this.yamlConfiguration=YamlConfiguration.loadConfiguration(file);


    }

    protected void beforeWrite(){
        try{
            if(file.exists()){
                file.delete();

            }
            file.createNewFile();
            this.yamlConfiguration= YamlConfiguration.loadConfiguration(file);

        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("writing the file");
    }

    protected void save(){
        try {
            yamlConfiguration.save(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }







}
