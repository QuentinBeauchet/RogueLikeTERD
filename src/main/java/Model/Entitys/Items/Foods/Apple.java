package Model.Entitys.Items.Foods;

import Model.Map.Etage;
import Model.Utils.Position;

/**
 * Consommable de nourriture
 * @author JP
 */
public class Apple extends AbstractFood {
    public Apple(Etage etage, Position position, String nom) {
        super(etage, position, nom);
    }
}
