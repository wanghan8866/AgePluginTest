package ageplugin.ageplugin.team;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum TeamType {
    PAST(ChatColor.GREEN.toString(),"green","Past", Material.STONE,
            "The Past being naturally stronger but less technologically advanced, stone age", false),
    PRESENT(ChatColor.GRAY.toString(),"gray","Present",Material.IRON_INGOT,
            "The Present being like modern day civilization, normal minecraft",false),
    FUTURE(ChatColor.RED.toString(),"red","Future",Material.REDSTONE,
            "The future being physically weaker but with more technology",false),
    ADMIN(ChatColor.GOLD.toString(),"gold","Admin",Material.REDSTONE,
            "The administers of this event",true);

    private String display;
    private String text;
    private String color;
    private String description;
    private Material material;
    private Boolean hidden;
    private String colorText;

    TeamType(String color, String colorText,String text, Material material, String description, boolean hidden){
        this.display=color+text;
        this.color=color;
        this.text=text;
        this.material=material;
        this.description=description;
        this.hidden=hidden;
        this.colorText=colorText;
    }

    public String getDisplay(){
        return display;
    }

    public String getDescription(){
        return description;
    }
    public Material getMaterial(){
        return material;
    }
    public String getText(){return text;}
    public String getColor(){return color;}
    public String getColorText(){return colorText;}
    public boolean getHidden(){return hidden;}

}
