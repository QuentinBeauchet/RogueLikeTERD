package Model.Utils;

import Model.Entitys.AbstractItem;
import Model.Entitys.Inventaires.Inventory;
import Model.Map.Etage;
import Model.Map.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe servant a afficher le menu sur la droite du terminal
 * @author Yann-FORNER
 */
public class Menu {
    private Etage etage;
    private Map map;

    public Menu(Etage etage, Map map) {
        this.etage = etage;
        this.map = map;
    }

    public StringBuilder toStringByLine(int line ,StringBuilder sb ) {
        Inventory inv = Start.getPlayer().getInventory();
        var potionsList = inv.getPotions();

        //Menu
        sb.append(Affichage.BOLD).append(Affichage.BLUE).append("     ");
        if(line==0){
            sb.append("╔═════════════════════════════════════════════════════════════════════════════════╗");
        }
        else if(line==etage.getHeigth()-1){
            sb.append("╚═════════════════════════════════════════════════════════════════════════════════╝");
        }
        else{
            ArrayList<String> levelPlayer = new ArrayList(
                    List.of(" Niveau "+Start.getPlayer().getLvl()+ " ["+Start.getPlayer().getCURRENT_EXP()+"/"+Start.getPlayer().getMAX_EXP()+"] ") );
            if(line==1){
                sb.append(print_txt_in_menu_leftorRight(true,"  Etage n°"+(map.getIndexCurrent()+1)+ " | Joueur : "+ Start.getPlayer().getNom(),Affichage.PURPLE));
            }
            else if(line == 2){
                sb.append(print_txt_in_menu_leftorRight(false,Procedure.getSeed()+ "  ",Affichage.RED));
            }
            else if(line == 3){
                sb.append(print_txt_in_menu_leftorRight(false,make_a_border(levelPlayer,0),Affichage.BRIGTH_GREEN));
            }
            else if(line == 4){
                sb.append(print_txt_in_menu_leftorRight(false,make_a_border(levelPlayer,1),Affichage.BRIGTH_GREEN));
            }
            else if(line == 5){
                sb.append(print_txt_in_menu_leftorRight(false,make_a_border(levelPlayer,2),Affichage.BRIGTH_GREEN));
            }
            else if (line == 6){
                sb.append(print_txt_in_menu_center(Start.getPlayer().getClasse().getNom(),Affichage.YELLOW));
            }
            else if (line==7 && Start.getPlayer()!= null){
                String equipement = "Equipement : ";
                if (Start.getPlayer().getInventory().getWeapons()!=null && Start.getPlayer().getInventory().getWeapons().size() > 0 )equipement+=Start.getPlayer().getInventory().getWeapons().get(0).getNom()+"/";
                else equipement+="X/";
                if (Start.getPlayer().getInventory().getArmures()!=null && Start.getPlayer().getInventory().getArmures().size() > 0)equipement+=Start.getPlayer().getInventory().getArmures().get(0).getNom();
                else equipement+="X";
                sb.append(print_txt_in_menu_center(equipement,Affichage.YELLOW));
            }
            else if (line == 12){
                sb.append(print_txt_in_menu_center(TourManager.getTimer(),Affichage.BLUE));
            }
            else if(line == etage.getHeigth() - 4) {
                sb.append(print_txt_in_menu_leftorRight(true, " Vous avez " +  potionsList.size() + " potions :", Affichage.RED));
            }
            else if(line == etage.getHeigth() - 3) {
                StringBuilder potionsString = new StringBuilder();
                for(int i = 0; i < potionsList.size(); i++) {
                    if(i == 0)
                        potionsString.append("[" + potionsList.get(i).toString() + "] ");
                    else
                        potionsString.append(potionsList.get(i).toString() + " ");
                }
                sb.append(print_txt_in_menu_leftorRight(true, potionsString.toString(),Affichage.RED));
            }
            else {
                sb.append("║                                                                                 ║");
            }
        }
        sb.append("\n");


        return sb;
    }
    public static String print_txt_in_menu_leftorRight(boolean left, String message,String color){
        StringBuilder sb = new StringBuilder();
        if(message.length()<81){
            sb.append("║");
            sb.append(color);
            int spacing = (81 - message.length());
            if (!left)sb.append(" ".repeat(spacing));
            sb.append(message);
            if(left)sb.append(" ".repeat(spacing));
            sb.append(Affichage.BLUE);
            sb.append("║");
        }else{
            sb.append("║                                                                                 ║");
        }
        return sb.toString();
    }
    public static String print_txt_in_menu_center(String message,String color){//81
        StringBuilder sb = new StringBuilder();
        if(message.length()<81){
            sb.append("║");
            sb.append(color);
            int spacing = (81 - message.length())/2;
            sb.append(" ".repeat(spacing));
            sb.append(message);
            if((message.length()%2==0))++spacing;
            sb.append(" ".repeat(spacing));
            sb.append(Affichage.BLUE);
            sb.append("║");
        }else if (message.length()>81){
            sb.append("║                                                                                 ║");
        }
        else {
            sb.append("║").append(message).append("║");
        }

        return sb.toString();
    }

    public static String make_a_border(ArrayList<String> message,int lineNumber){
        StringBuilder sb = new StringBuilder();
        switch (lineNumber) {
            case 0 :
                for (String m : message
                     ) {
                    sb.append("╔");
                    sb.append("═".repeat(m.length()));
                    sb.append("╗");
                    sb.append(" ");
                }
                break;
            case 1 :
                for (String m : message
                ) {
                    sb.append("║");
                    sb.append(m);
                    sb.append("║");
                    sb.append(" ");
                }
                break;
            case 2 :
                for (String m : message
                ) {
                    sb.append("╚");
                    sb.append("═".repeat(m.length()));
                    sb.append("╝");
                    sb.append(" ");
                }
        }
        return sb.toString();
    }

    public StringBuilder menuToStringBuilder(){
        StringBuilder sb= new StringBuilder();
        for (int i = 0; i < etage.getHeigth(); i++) {
            this.toStringByLine(i ,sb);
        }
        return sb;
    }
}
