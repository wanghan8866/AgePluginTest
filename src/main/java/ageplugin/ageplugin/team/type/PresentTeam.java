package ageplugin.ageplugin.team.type;

import ageplugin.ageplugin.AgePlugin;
import ageplugin.ageplugin.team.AbstractTeam;
import ageplugin.ageplugin.team.TeamType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class PresentTeam extends AbstractTeam {
    public PresentTeam(AgePlugin agePlugin, UUID uuid){
        super(agePlugin,TeamType.PRESENT,uuid);
    }

    @Override
    public void onStart(Player player) {
        if(this.agePlugin.getTeamManager().getIsStarted()){
    //        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,Integer.MAX_VALUE,2));
            player.sendMessage(type.getDisplay()+": "+"chosen");

            startingTitle();
        }

    }
}