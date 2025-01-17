package Model.Map.Room_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Room;
import Model.Utils.Affichage;
import Model.Utils.Procedure;

import java.io.Serializable;

/**
 * Classe abstraite définissant le comportement des rooms
 * @author Yann,Quentin,JP
 */
public abstract class RoomStrategy implements Serializable {
    /**
     * Génère une room
     * @param r la room
     * @author Yann,Quentin
     */
    public void composeRoom(Room r){
        r.fillMap(new Cell(true, new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BRIGTH_YELLOW, ".")));
        setStyleCell(r);
    }

    /**
     * Setter le style room
     * @param r la room
     * @author Quentin
     */
    protected void setStyleCell(Room r){
        Cell.Style palmier = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY,"\uD83C\uDF34", "T");
        Cell.Style tree = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.GREY,"\uD83C\uDF33", "T");
        Cell.Style barierre = new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.BLACK,"\uD83D\uDEA7", "Ø");
        for (int i = 0; i < Procedure.getRandomInt(4,0); i++) {
            int rand = Procedure.getRandomInt(1, 0);
            if(rand == 0)
                r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, palmier);
            else
                r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(true, tree);
        }
        for (int i = 0; i < Procedure.getRandomInt(1,0); i++) {
            r.get(Procedure.getAccesibleRandomPosition(false, r)).updateCell(false, barierre);
        }
    }

    /**
     * Définis la distance minimale entre une salle et une autre
     * @param etage etage courant
     * @param room la room
     * @param DistanceMin la distance minimale
     * @return un booleen
     * @author Quentin
     */
    protected boolean noCollision(Etage etage,Room room,int DistanceMin){
        for (int y = 0; y < room.getHeigth()+DistanceMin*2; y++) {
            for (int x = 0; x < room.getWidth()+DistanceMin*2; x++) {
                if (etage.get(Math.max(Math.min(room.getAbsolutePos().getX() + x - DistanceMin, etage.getWidth()-1), 0), Math.max(Math.min(room.getAbsolutePos().getY() + y - DistanceMin, etage.getHeigth()-1),0)).getType() != Cell.Style.CellType.VOID){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Génère les monstres de la rooms
     * @param r Room faisant office de lieu de génération
     * @author Quentin,JP
     */
    public abstract void setMonsters(Room r);

    /**
     * Génère les items de la rooms
     * @param r Room faisant office de lieu de génération
     * @author JP
     */
    public abstract void setItems(Room r);

    /**
     *Renvoie si oui on non il y a une collision avec une autre salle
     * @param etage l'etage courant
     * @param room la salle
     * @return un booleen
     */
    public abstract boolean noCollision(Etage etage,Room room);

}
