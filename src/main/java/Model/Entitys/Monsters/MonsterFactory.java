package Model.Entitys.Monsters;

import Model.Map.Etage;
import Model.Utils.Position;
import Model.Utils.Procedure;
import Model.Utils.Tools;

public class MonsterFactory {
    public enum MonsterType {
        GHOST, ZOMBIE, RAT, BEE
    }

    private static Ghost getNewGhost(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Ghost(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Zombie getNewZombie(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Zombie(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Rat getNewRat(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Rat(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    private static Bee getNewBee(Etage etage, Position pos, String nom, int pv, int force, int vision_radius, int Agro, int update_rate_ms, int path_type){
        return new Bee(etage,pos,nom,pv,force,vision_radius,Agro,update_rate_ms,path_type);
    }

    public static AbstractMonster getNewMonster(Etage etage, MonsterType m){
        switch (m){
            case GHOST -> {
                return getNewGhost(etage, Procedure.getAccesibleRandomPosition(true,etage),"Ghost", 10,3,10,10,700, Tools.PATH_GHOST);
            }
            case ZOMBIE -> {
                return getNewZombie(etage, Procedure.getAccesibleRandomPosition(true,etage),"ZOMBIE",25,5,5,30,1500, Tools.PATH_CROSS);
            }
            case RAT -> {
                return getNewRat(etage, Procedure.getAccesibleRandomPosition(true,etage),"RAT",1,1,15,15,300, Tools.PATH_DIAG);
            }
            case BEE -> {
                return getNewBee(etage, Procedure.getAccesibleRandomPosition(true,etage),"BEE",20,5,15,20,900, Tools.PATH_CROSS);
            }
            default -> {
                return null;
            }
        }
    }

    public static AbstractMonster getNewMonster(Etage etage, MonsterType m, int pv){
        switch (m){
            case BEE -> {
                return getNewBee(etage,Procedure.getAccesibleRandomPosition(true,etage),"BEE", pv,5,15,20,900, Tools.PATH_CROSS);
            }
            default -> {
                return null;
            }
        }
    }
}
