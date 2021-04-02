package Model.Entitys.Monsters;

import Model.Entitys.BasicPlayer;
import Model.Entitys.Entity;
import Model.Map.Etage;
import Model.Utils.*;

import java.util.ArrayList;

public abstract class AbstractMonster extends Entity {
    private int Alert=0;
    private final int Agro;
    private final int pathtype;
    private final int update_rate_ms;

    protected AbstractMonster(Etage m, Position pos, String nom, int pv, int force, int vision_radius, int agro, int update_rate_ms, int path_type) {
        super(m, pos, vision_radius, nom, pv, force);
        this.update_rate_ms=update_rate_ms;
        this.Agro=agro;
        this.pathtype=path_type;
    }

    public void updateMonster(Etage etage, BasicPlayer mainPlayer){
        int vision_radius = Alert>0 ? Agro : getVision_radius();
        if(mainPlayer.getPosition().Distance(getPosition())<=vision_radius){
            if(Alert==0){
                TourManager.AddMessage(getNom()+" vous a reperé!!!");
            }
            Alert=Agro;
            ArrayList<Position> pathToPlayer = Tools.Astar(etage, getPosition(), mainPlayer.getPosition(), pathtype);
            if(pathToPlayer.size()!=0) move(pathToPlayer.get(pathToPlayer.size() - 2));
            Alert--;
        }
        else{
            Alert=0;
        }
    }

    public int getUpdate_rate_ms() {
        return update_rate_ms;
    }

    @Override
    public String toString() {
        return Affichage.RED+'N';
    }
}
