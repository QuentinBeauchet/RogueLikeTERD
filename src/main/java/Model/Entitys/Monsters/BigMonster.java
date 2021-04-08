package Model.Entitys.Monsters;

import Model.Entitys.Entity;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Utils.Affichage;
import Model.Utils.Position;
import Model.Utils.Procedure;

public class BigMonster  extends AbstractMonster{
    private HandOfMonster leftHand;
    private HandOfMonster rightHand;
    protected BigMonster(Etage m, Position pos, String nom, int pv, int force, int vision_radius, int agro, int update_rate_ms, int path_type) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type);
        leftHand = new HandOfMonster(m, new Position(pos.getX()-1,pos.getY()-1), nom, pv/2, force, vision_radius, agro, update_rate_ms, path_type);
        rightHand = new HandOfMonster(m, new Position(pos.getX()+1,pos.getY()-1), nom, pv/2, force, vision_radius, agro, update_rate_ms, path_type);
        while(!leftHand.getEtage().get(pos.getX()-1,pos.getY()-1).isAccesible() && !rightHand.getEtage().get(pos.getX()+1,pos.getY()-1).isAccesible()){
            this.move(Procedure.getAccesibleRandomPosition(true,this.getEtage()));
        }
    }

    @Override
    public boolean updatePV(int pv) {
        boolean isAlive = super.updatePV(pv);
        if (!isAlive){
            leftHand.updatePV(-leftHand.getPv());
            rightHand.updatePV(-rightHand.getPv());
        }
        return isAlive;
    }

    @Override
    public void move(Position pos) {
        if(leftHand.getEtage().get(new Position(pos.getX()-1,pos.getY()-1)).isAccesible()
                && rightHand.getEtage().get(new Position(pos.getX()+1,pos.getY()-1)).isAccesible()
                && this.getEtage().get(pos).isAccesible() ){
            leftHand.move(new Position(pos.getX()-1,pos.getY()-1));
            rightHand.move(new Position(pos.getX()+1,pos.getY()-1));
            super.move(pos);
        }
    }

    @Override
    public String toString() {
        return Affichage.BLUE_BACKGROUND+Affichage.BOLD+"o";
    }
}
