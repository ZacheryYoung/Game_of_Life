import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

public class test extends Applet implements Runnable {
    private final int SIZE = 50;
    private final int CELL_Size = 10;
    private Color cell = new Color(0, 0, 0);
    private Color space = new Color(255, 255, 255);
    private boolean[][] table = new boolean[SIZE][SIZE];
    private Thread animator;
    private int delay;
    private boolean running;

    public void run() {
        long tm = System.currentTimeMillis();
        while (Thread.currentThread() == animator) {
            if (running)
                repaint();
            try {
                tm += delay;
                Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void init() {
        animator = new Thread(this);
        delay = 80;
        running = false;
        setBackground(new Color(0, 0, 0));
    }

    @Override
    public void start() {
        animator.start();
    }

    @Override
    public void stop() {
        animator = null;
    }

    @Override
    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public void update(Graphics g) {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++) {
                g.setColor(table[x][y] ? cell : space);
                g.fillRect(x * CELL_Size, y * CELL_Size, CELL_Size - 1, CELL_Size - 1);
            }
    }
}