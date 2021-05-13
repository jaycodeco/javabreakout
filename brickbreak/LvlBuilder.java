package brickbreak;

import java.awt.Graphics2D;
import java.util.Random;
import java.awt.BasicStroke;
import java.awt.Color;

public class LvlBuilder {
    public int map[][];
    public int brick_w;
    public int brick_h;
    private Random rand = new Random();

    private Color crlr[];

    public LvlBuilder(int r, int c) {
        brick_h = 15;
        brick_w = 50;
        map = new int[r][c];
        crlr = new Color[6];
        crlr[0] = Color.BLUE;
        crlr[1] = Color.PINK;
        crlr[2] = Color.MAGENTA;
        crlr[3] = Color.RED;
        crlr[4] = Color.GREEN;
        crlr[5] = Color.BLUE;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = rand.nextInt(4) + 1;
            }
        }
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(crlr[map[i][j]]);
                    g.fillRect(j * brick_w + 70, i * brick_h + 70, brick_w, brick_h);

                    g.setStroke(new BasicStroke());
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brick_w + 70, i * brick_h + 70, brick_w, brick_h);
                }
            }
        }
    }

    public void knockOut(int r, int c) {
        map[r][c] = 0;
    }
}
