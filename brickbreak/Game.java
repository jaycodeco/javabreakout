package brickbreak;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Rectangle;
// import java.util.Timer;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private boolean pause = false;
    private boolean loss = false;
    private boolean win = false;
    private int score = 0;
    private int total_brick = 21;

    private Timer timer;
    private int delay = 8;

    private int player_pos = 300;

    private int ball_x = 350;
    private int ball_y = 490;

    private int ball_xdir = 1;
    private int ball_ydir = -2;

    private LvlBuilder map;

    public Game() {
        total_brick = 10 * 11;
        map = new LvlBuilder(10, 11);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay, this);
        timer.start();
        this.score = 0;

    }

    public void paint(Graphics g) {
        // drawing the background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        // drawing map
        map.render((Graphics2D) g);

        // drawing the walls
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(680, 0, 3, 592);

        // drawing the paddles
        g.setColor(Color.GREEN);
        g.fillRect(player_pos, 500, 100, 8);

        // drawing the ball
        g.setColor(Color.RED);
        g.fillOval(ball_x, ball_y, 10, 10);

        // the score
        g.setColor(Color.WHITE);
        g.setFont(new Font("sans serif", Font.ROMAN_BASELINE, 20));
        g.drawString("#" + score, 600, 25);

        // game is paused
        if (!pause) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("sans serif", Font.BOLD, 45));
            g.drawString("PAUSED", 250, 50);

        }

        // ball going offset
        if (ball_y > 700) {
            play = false;
            loss = true;

            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(180, 10, 300, 250);

            g.setColor(Color.RED);
            g.setFont(new Font("sans serif", Font.BOLD, 25));
            g.drawString("Game over", 192, 50);
            g.drawString("you loosed", 192, 100);
            g.drawString("looser", 192, 150);
            g.drawString("Press ENTER to restart", 192, 200);
        }

        if (win) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(180, 10, 300, 250);

            g.setColor(Color.RED);
            g.setFont(new Font("sans serif", Font.BOLD, 25));
            g.drawString("Game over", 192, 50);
            g.drawString("you won", 192, 100);
            g.drawString("winner", 192, 150);
            g.drawString("Press ENTER to restart", 192, 200);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // addKeyListener(this);
        timer.start();
        if (play) {
            ball_x += ball_xdir;
            ball_y += ball_ydir;

            // collision with blocks
            A: for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brick_x = map.brick_w * j + 70;
                        int brick_y = map.brick_h * i + 70;

                        int brick_h = map.brick_h;
                        int brick_w = map.brick_w;

                        Rectangle rect = new Rectangle(brick_x, brick_y, brick_w, brick_h);
                        Rectangle ball_R = new Rectangle(ball_x, ball_y, 10, 10);

                        if (ball_R.intersects(rect)) {
                            map.knockOut(i, j);
                            total_brick--;
                            score += 5;

                            if ((ball_x + 19 <= rect.x) || (ball_x + 1 >= rect.x + rect.width)) {
                                ball_xdir *= -1;
                            } else {
                                ball_ydir *= -1;
                            }

                            break A;
                        }

                    }
                }
            }

            // collision with walls
            if (ball_x < 0) {
                ball_xdir *= -1;
            }
            if (ball_x > 680) {
                ball_xdir *= -1;
            }
            if (ball_y < 0) {
                ball_ydir *= -1;
            }

            // collision with pad
            if (new Rectangle(ball_x, ball_y, 10, 10).intersects(new Rectangle(player_pos, 500, 100, 8))) {
                ball_ydir *= -1;
            }

            if (total_brick == 0) {
                win = true;
                play = false;
            }

        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (player_pos >= 600) {
                player_pos = 600;
            } else {
                MoveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (player_pos <= 10) {
                player_pos = 3;
            } else {
                MoveLeftt();
            }
        }

        // pausing the game
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            play = !play;
            pause = !pause;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (loss) {
                score = 0;
                total_brick = 11 * 10;
                delay = 8;
                player_pos = 300;
                ball_x = 350;
                ball_y = 490;
                ball_xdir = 1;
                ball_ydir = -2;

                map = new LvlBuilder(10, 11);
                repaint();
            }
        }

    }

    private void MoveLeftt() {
        player_pos -= 20;
        if (!play)
            ball_x -= 20;
    }

    private void MoveRight() {
        player_pos += 20;
        if (!play)
            ball_x += 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}