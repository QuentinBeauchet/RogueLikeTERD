package Model.Map.Strategy;


import Model.Utils.Affichage;
import Model.Map.Cell;
import Model.Map.Room;

public class NormalRoomStrategy extends RoomStrategy{
    @Override
    public void composeRoom(Room r) {
        super.composeRoom(r);
        Cell.CellType type = Cell.CellType.NORMAL;
        type.setString(Affichage.GREEN+'.');
        r.fillMap(new Cell(true, type));
    }
}
