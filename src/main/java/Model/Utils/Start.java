package Model.Utils;import Model.Entitys.BasicPlayer;import Model.Entitys.Monsters.AbstractMonster;import Model.Entitys.Monsters.MonsterFactory;import Model.Map.Map;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;import java.util.Random;public class Start {    private static Map map;    private static BasicPlayer player;    public static void startGame() throws IOException {        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));        TourManager tm = getInfo(reader);        getPlayer().getEtage().addMonster(MonsterFactory.getNewMonster(getPlayer().getEtage(), MonsterFactory.MonsterType.SNAKE));        while (true) {            tm.playTour();        }    }    private static TourManager getInfo(BufferedReader reader) throws IOException {        Affichage.start();        switch (reader.readLine()) {            case "1" -> {                return init(reader);            }            case "2" -> //TODO sauvegarde                    System.exit(1);            case "3" -> System.exit(0);        }        return getInfo(reader);    }    private static TourManager init(BufferedReader reader) throws IOException {        System.out.print(Affichage.BLUE+"JOUEUR: "+Affichage.RESET);        String nom = reader.readLine();        player=new BasicPlayer(20,nom.length()==0 ? "Tu" : nom,100,30);        System.out.print(Affichage.BRIGTH_PURPLE+"Numero seed: "+Affichage.RESET);        long seed_value;        try{            seed_value = Long.parseLong(reader.readLine());        }        catch (NumberFormatException e){            seed_value =new Random().nextLong();        }        Procedure.setSeed(seed_value);        map = new Map();        return new TourManager(reader, player, map, map.getCurrent());    }    public static BasicPlayer getPlayer(){        return player;    }    public static Map getMap() {        return map;    }}