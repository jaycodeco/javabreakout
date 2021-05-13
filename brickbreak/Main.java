package brickbreak;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame obj = new JFrame();
        Game world = new Game();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("break out");
        obj.setResizable(true);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(world);
    }
}