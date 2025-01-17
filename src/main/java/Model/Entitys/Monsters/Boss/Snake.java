package Model.Entitys.Monsters.Boss;

import Model.Entitys.Items.Foods.AbstractFood;
import Model.Entitys.Items.Foods.FoodFactory;
import Model.Entitys.Monsters.AbstractMonster;
import Model.Map.Etage;
import Model.Utils.*;

import java.util.ArrayList;

/**
 * AbsractBoss serpent qui est en plusieurs parties et ne meurt que quand elles sont toutes mortes.
 * @author Quentin
 */
public class Snake extends AbsractBoss {
    /**
     * Classe d'un bout de queue du serpent.
     * @author Quentin
     */
    private class Tail extends AbstractMonster {
        private Tail(Etage m, Position pos, String nom, int pv, Tools.PathType path_type, int lvl) {
            super(m, pos, nom, pv, 0,0,0,60000, path_type, lvl);
        }

        @Override
        public void updateMonster() {}

        @Override
        public boolean updatePV(int pv, boolean limited) {
            boolean Alive = super.updatePV(pv, limited);
            if(!Alive){
                snakeTail.remove(this);
            }
            AbstractFood food = switch (Procedure.getRandomInt(39,0)){
                case 0,1,2,3,4 -> FoodFactory.getNewFood(getEtage(), FoodFactory.FoodType.BURGER);
                case 5,6 -> FoodFactory.getNewFood(getEtage(), FoodFactory.FoodType.APPLE);
                default -> null;
            };
            if(food != null){
                getEtage().addItem(food);
            }
            return Alive;
        }

        @Override
        public void move(Position pos) {
            if(getEtage().get(pos).getEntity()==null){
                super.move(pos);
            }
        }

        @Override
        public int getExp() {
            return 1;
        }

        @Override
        public String toString() {
            return super.toString() + "O";
        }
    }

    protected final ArrayList<Tail> snakeTail = new ArrayList<>();
    private final int size_of_tail;

    /**
     * Crée un AbsractBoss serpent.
     * @param m Etage
     * @param pos Position
     * @param nom Nom
     * @param pv Points de vie
     * @param force Force
     * @param vision_radius Champs de vision
     * @param agro Agro
     * @param update_rate_ms Vitesse
     * @param path_type Type de chemin
     * @param lvl Niveau de base
     * @param size_of_tail Nombre que partie pour sa queue
     * @author Quentin
     */
    public Snake(Etage m, Position pos, String nom, int pv, int force, double vision_radius, int agro, int update_rate_ms, Tools.PathType path_type, int lvl, int size_of_tail) {
        super(m, pos, nom, pv, force, vision_radius, agro, update_rate_ms, path_type, lvl);
        this.size_of_tail=size_of_tail;
        setTail(m,pos,pv,path_type);
    }

    /**
     * Crée les queues du serpent.
     * @param m Etage
     * @param pos Position
     * @param pv PV des queues
     * @param path_type Type de deplacement des queues
     * @author Quentin
     */
    private void setTail(Etage m, Position pos,int pv, Tools.PathType path_type){
        Position random_pos = Procedure.getAccesibleRandomPosition(true, m);
        while (pos.Distance(random_pos)<size_of_tail+1){
            random_pos = Procedure.getAccesibleRandomPosition(true, m);
        }
        ArrayList<Position> astar = Tools.Astar(m, pos, random_pos, path_type);
        for (int i = 0; i < size_of_tail; i++) {
            Tail tail = new Tail(m, astar.get(astar.size() - (2 + i)), "Tail", pv, path_type, lvl);
            snakeTail.add(tail);
            m.addMonster(tail);
        }
    }

    @Override
    public void move(Position pos) {
        Position old_pos = getPosition();
        super.move(pos);
        for (Tail t : snakeTail){
            Position acc = t.getPosition();
            t.move(old_pos);
            old_pos = acc;
        }
    }

    @Override
    public boolean updatePV(int pv, boolean limited) {
        boolean Alive = true;
        if(snakeTail.size()==0){
            Alive = super.updatePV(pv, limited);
        }
        return Alive;
    }


    @Override
    public String toString() {
        return super.toString() + "Q";
    }
}
