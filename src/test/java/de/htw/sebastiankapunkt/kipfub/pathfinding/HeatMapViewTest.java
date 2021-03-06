package de.htw.sebastiankapunkt.kipfub.pathfinding;

import de.htw.sebastiankapunkt.kipfub.game.HeatMapController;
import de.htw.sebastiankapunkt.kipfub.model.Node;
import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.representation.ViewController;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static de.htw.sebastiankapunkt.kipfub.game.GameController.NUMBER;
import static de.htw.sebastiankapunkt.kipfub.game.GameController.SCALE;
import static de.htw.sebastiankapunkt.kipfub.model.ScaledField.maxScore;

public class HeatMapViewTest {

    @Test
    public void collectHeatMapTest() {
        ViewController view = new ViewController();
        ScaledField[][] field = new ScaledField[NUMBER][NUMBER];
        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                field[x][y] = new ScaledField(x * SCALE, y * SCALE, true);
                field[x][y].score = Math.random() * maxScore;
            }
        }

        field[32][32].score = 0;
        field[33][32].score = 0;
        field[34][32].score = 0;
        field[35][32].score = 0;

        field[32][33].score = 0;
        field[33][33].score = 0;
        field[34][33].score = 0;
        field[35][33].score = 0;

        field[32][34].score = 6;
        field[33][34].score = 6;
        field[34][34].score = 0;
        field[35][34].score = 0;

        field[32][35].score = 0;
        field[33][35].score = 0;
        field[34][35].score = 0;
        field[35][35].score = 0;

        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                view.drawScaledField(field[x][y]);
            }
        }

        Map<Node, Double> sum = new HashMap<>();

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                sum.put(new Node(x, y), sumOfRange(x, y, field));
            }
        }

        Map<Node, Double> arr = new HashMap<>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                arr.put(new Node(x, y), sumOfRange(x, y, sum));
            }
        }

        view.drawSum(arr, 128);

        while (true) {

        }
    }

    private Double sumOfRange(int fromX, int fromY, ScaledField[][] field) {
        double sum = 0;
        int count = 0;

        for (int x = fromX * 4; x < 4 * (fromX + 1); x++) {
            for (int y = fromY * 4; y < 4 * (fromY + 1); y++) {
                count++;
                sum += field[x][y].getScore();
            }
        }

        return sum / count;
    }

    private Double sumOfRange(int fromX, int fromY, Map<Node, Double> field) {
        double sum = 0;
        int count = 0;

        for (int x = fromX * 2; x < 2 * (fromX + 1); x++) {
            for (int y = fromY * 2; y < 2 * (fromY + 1); y++) {
                count++;
                sum += field.get(new Node(x, y));
            }
        }

        return sum / count;
    }

    @Test
    public void testWhat() {
        ScaledField[][] field = new ScaledField[NUMBER][NUMBER];
        for (int x = 0; x < NUMBER; x++) {
            for (int y = 0; y < NUMBER; y++) {
                field[x][y] = new ScaledField(x * SCALE, y * SCALE, true);
                field[x][y].score = Math.random() * maxScore;
            }
        }

        field[32][32].score = 0;
        field[33][32].score = 0;
        field[34][32].score = 0;
        field[35][32].score = 0;

        field[32][33].score = 0;
        field[33][33].score = 0;
        field[34][33].score = 0;
        field[35][33].score = 0;

        field[32][34].score = 6;
        field[33][34].score = 6;
        field[34][34].score = 0;
        field[35][34].score = 0;

        field[32][35].score = 0;
        field[33][35].score = 0;
        field[34][35].score = 0;
        field[35][35].score = 0;

        HeatMapController m = new HeatMapController(field);
        m.createHeatMap();
        m.getSecondLayerMax(0);
        m.getSecondLayerMax(1);
        m.getSecondLayerMax(2);

        while (true) {

        }
    }
}
