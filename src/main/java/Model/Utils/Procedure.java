package Model.Utils;

import Exceptions.CollisionRoom;
import Model.Map.Cell;
import Model.Map.Etage;
import Model.Map.Etage_Strategy.EtageStrategy;
import Model.Map.Room;
import Model.Map.RoomFactory;
import Model.Map.Room_Strategy.RoomStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Toutes les fonctions aleatoires.
 * @author Quentin,Yann
 */
public class Procedure {
    private static final Random rand=new Random();

    /**
     * Renvoit le random de la procedure.
     * @return Random
     * @author Quentin
     */
    public static Random getRand(){
        return rand;
    }


    /**
     * Renvoit un entier aleatoire entre min et max inclus.
     * @param Max int
     * @param Min int
     * @return Entier
     * @author Quentin
     */
    public static int getRandomInt(int Max, int Min){
        return rand.nextInt(Max - Min + 1) + Min;
    }

    /**
     * Renvoit une Position random : new Position( MaxWidth & MinWidth , MaxHeigtth & MinHeigth).
     * @param MaxWidth int
     * @param MaxHeigth int
     * @param MinWidth int
     * @param MinHeigth int
     * @return Position
     * @author Quentin
     */
    private static Position getRandomPosition(int MaxWidth, int MaxHeigth, int MinWidth, int MinHeigth) {
        int posX = rand.nextInt((MaxWidth - MinWidth)) + MinWidth;
        int posY = rand.nextInt((MaxHeigth - MinHeigth)) + MinHeigth;
        return new Position(posX, posY);
    }

    /**
     * Renvoit une position random dans l'Etage e.
     * @param e Etage
     * @return Position
     */
    public static Position getRandomPosition(Etage e){
        return getRandomPosition(e.getWidth(),e.getHeigth(),1,1);
    }

    /**
     * Renvoit une position random absolue dans la Room r.
     * @param r Room
     * @return Position
     * @author Quentin
     */
    public static Position getRandomPosition(Room r){
        Position abs = r.getAbsolutePos();
        return getRandomPosition(r.getWidth()+abs.getX(),r.getHeigth()+abs.getY(),abs.getX(), abs.getY());
    }

    /**
     * Renvoit une position random dans la Room r.
     * @param r Room
     * @return Position
     * @author Quentin
     */
    public static Position getRelativeRandomPosition(Room r){
        return getRandomPosition(r.getWidth(),r.getHeigth(),0,0);
    }

    /**
     * Renvoit une Position aleatoire dans l'Etage/Room accesible, si isEntityGeneration est vrai
     * alors la position est aussi sans AbstractAlive et non Reservé.
     * @param isEntityGeneration boolean
     * @param e Etage
     * @param r Room: optionel remplace l'etage
     * @return Position
     * @author Quentin
     */
    public static Position getAccesibleRandomPosition(boolean isEntityGeneration,Etage e,Room ... r){
        Position pos = r.length==1 ? getRandomPosition(r[0]) : getRandomPosition(e);
        if(isEntityGeneration) {
            Cell c = e.get(pos);
            while(!c.isAccesible() || c.getEntity()!=null || c.isReserved()){
                pos = r.length==1 ? getRandomPosition(r[0]) : getRandomPosition(e);
                c = e.get(pos);
            }
        }
        else {
            while(!e.get(pos).isAccesible()){
                pos = r.length==1 ? getRandomPosition(r[0]) : getRandomPosition(e);
            }
        }
        return pos;
    }

    /**
     * Renvoit une Room generé aleatoirement.
     * @param MinSize int
     * @param MaxSize int
     * @return Room
     * @author Quentin
     */
    public static Room getRandomRoom(int MinSize, int MaxSize, RoomStrategy strategy) {
        Position pos = getRandomPosition(MaxSize, MaxSize, MinSize, MinSize);
        return new Room(pos.getX(),pos.getY(),strategy);
    }
    /**
     * Renvoit une Room generé aleatoirement avec une taille impaire pour avoir un centre.
     * @param MinSize int
     * @param MaxSize int
     * @return Room
     * @author Quentin
     */
    public static Room getRandomImpairSizeRoom(int MinSize, int MaxSize, RoomStrategy strategy) {
        int size = rand.nextInt((MaxSize - MinSize)) + MinSize;
        if(size%2==0)++size;
        return new Room(size,size,strategy);
    }

    /**
     * Ajoute les rooms aleatoirement dans l'etage selon les differentes strategy.
     * @param etage Etage
     * @param etageStrategy Strategy de l'etage
     * @param roomTypes Liste des strategy des rooms.
     * @author Yann,Quentin
     */
    public static void setRandomRooms(Etage etage,EtageStrategy etageStrategy ,ArrayList<RoomFactory.roomType> roomTypes){
        int nbrRooms = 0;
        long t1 = System.currentTimeMillis();
        while (nbrRooms < etageStrategy.getNbrMaxRoom() && nbrRooms < roomTypes.size()  &&  (System.currentTimeMillis()-t1<500) ){
            Room r = RoomFactory.getNewRoom(roomTypes.get(nbrRooms));
            Position pos =  getRandomPosition(etage.getWidth()-1 - r.getWidth(), etage.getHeigth()-1 - r.getHeigth(),1,1).somme(1,1);
            try {
                r.setAbsolutePos(pos);
                etage.addRoom(r);
                nbrRooms++;
            }catch (CollisionRoom ignored){}
        }
        Collections.sort(etage.getRooms());
    }

    /**
     * Genere nbrMaxRooms dans l'Etage.
     * @param etage Etage
     * @param type RoomFactory.roomType
     * @author Quentin
     */
    public static void setRandomRooms(Etage etage, EtageStrategy etageStrategy, RoomFactory.roomType type) {
        int nbrRooms = 0;
        long t1 = System.currentTimeMillis();
        while (nbrRooms < etageStrategy.getNbrMaxRoom() && (System.currentTimeMillis()-t1<500)) {
            Room r = RoomFactory.getNewRoom(type);
            Position pos;
            if (etageStrategy.getNbrMaxRoom() == 1) {
                 pos = new Position(((etage.getWidth()-1)/2)- r.getWidth()/2,(etage.getHeigth())/2- r.getHeigth()/2);
            }else {
                 pos = getRandomPosition(etage.getWidth()-1 - r.getWidth(), etage.getHeigth()-1 - r.getHeigth(),1,1).somme(1,1);
            }
            try {
                r.setAbsolutePos(pos);
                etage.addRoom(r);
                nbrRooms++;
            } catch (CollisionRoom ignored) {}
        }
        Collections.sort(etage.getRooms());
    }

    /**
     * Genere les escaliers aleatoirement dans un etage.
     * @param etage Etage
     * @author Quentin
     */
    public static void setRandomUPnDOWN(Etage etage) {
        setRandomUP(etage);
        setRandomDOWN(etage);
    }

    /**
     * Escalier vers l'etage du dessus.
     * @param etage Etage
     * @author Quentin
     */
    public static void setRandomUP(Etage etage){
        Position pos = getAccesibleRandomPosition(false, etage);
        etage.setUp(pos);
        etage.get(pos).updateCell(true, new Cell.Style(Cell.Style.CellType.UP));
    }

    /**
     * Escalier vers l'etage du dessous.
     * @param etage Etage
     * @author Quentin
     */
    public static void setRandomDOWN(Etage etage){
        Position pos = getAccesibleRandomPosition(false, etage);
        etage.setDown(pos);
        etage.get(pos).updateCell(true, new Cell.Style(Cell.Style.CellType.DOWN));
    }

}