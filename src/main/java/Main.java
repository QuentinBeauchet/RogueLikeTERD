import Model.Entitys.BasicPlayer;import Model.Map.Etage;import Model.Map.Map;import Model.Utils.Affichage;import Model.Utils.Procedure;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;import java.util.Random;public class Main {    private static long seed;    private static Map map;    private static BasicPlayer player;    private static Etage etage;    public static void main(String[] args) throws IOException {        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));        init(reader);        affichage();        System.getProperty("os.name");        while (true) {            Tour(reader.readLine());        }    }    private static void init(BufferedReader reader) throws IOException {        System.out.print("Numero seed ? ");        long seed_value;        try{            seed_value = Long.parseLong(reader.readLine());        }        catch (NumberFormatException e){            seed_value =new Random().nextLong();        }        seed=seed_value;        Procedure.setSeed(seed);        map = new Map();        etage = map.getCurrent();        player = map.getPlayer();    }    private static void Tour(String input){        switch (input) {            case "z" , "\u001B[A" -> player.moveUp();            case "q" , "\u001B[D" -> player.moveLeft();            case "s" , "\u001B[B" -> player.moveDown();            case "d" , "\u001B[C" -> player.moveRight();            case "exit" -> System.exit(0);            default -> System.out.println("Wrong key:"+input);        }        switch (etage.get(map.getPlayer().getPosition()).getType()){            case UP :                map.UP();                break;            case DOWN :                map.DOWN();                break;            case TRAP_ROOM :                map.TRAP_ROOM();                break;            default:                break;        }        etage=map.getCurrent();        affichage();    }    private static void affichage(){        System.out.print(Affichage.CLEAR);        System.out.println(seed);        System.out.println(etage);        System.out.print(Affichage.RESET+"Etage n°"+(map.getIndexCurrent()+1));        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");        System.out.print("Enter your key: ");    }}