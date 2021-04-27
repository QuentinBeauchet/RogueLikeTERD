package Model.Map.Etage_Strategy;

import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.RoomFactory;
import Model.Utils.Affichage;
import Model.Utils.Procedure;


/**
 * Création de l'étage du marchand, cet étage comporte une seule salle avec un marchand
 * @author Gillian
 */
public class MarchandEtageStrategy extends EtageStrategy{


    @Override
    public void composeEtage(Etage etage) {
        Procedure.setRandomRooms(etage, this, RoomFactory.roomType.MARCHAND);
        EtageFusion(etage,new Cell.Style(Cell.Style.CellType.NORMAL, Affichage.YELLOW));
        setMonsters(etage);
        setItems(etage);
    }

    @Override
    public int getNbrMaxRoom() {
        return 1;
    }
}