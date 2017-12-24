import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

public class test extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener {
    private final int SIZE = 50;
    private final int CELL_Size = 10;
    private Color cell = new Color(0, 0, 0);
    private Color space = new Color(255, 255, 255);
    private boolean[][] table = new boolean[SIZE][SIZE];
    private int[][] neighbors = new int[SIZE][SIZE];
    private Thread animator;
    private int delay;
    private boolean running;

    public void run() {
        long tm = System.currentTimeMillis();
        while (Thread.currentThread() == animator) {
            if (running) {
                getNeighbors();
                nextWorld();
                repaint();
            }
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

        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);

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

    public void getNeighbors() {
        for (int r = 0; r < SIZE; r++) {// row
            for (int c = 0; c < SIZE; c++) {// col
                if (r - 1 >= 0 && c - 1 >= 0 && table[r - 1][c - 1])
                    neighbors[r][c]++;
                if (r - 1 >= 0 && table[r - 1][c])
                    neighbors[r][c]++;
                if (r - 1 >= 0 && c + 1 < SIZE && table[r - 1][c + 1])
                    neighbors[r][c]++;
                if (c - 1 >= 0 && table[r][c - 1])
                    neighbors[r][c]++;
                if (c + 1 < SIZE && table[r][c + 1])
                    neighbors[r][c]++;
                if (r + 1 < SIZE && table[r + 1][c])
                    neighbors[r][c]++;
                if (r + 1 < SIZE && c + 1 < SIZE && table[r + 1][c + 1])
                    neighbors[r][c]++;
                if (r + 1 < SIZE && c - 1 >= 0 && table[r + 1][c - 1])
                    neighbors[r][c]++;
            }
        }
    }

    public void nextWorld() {
        for (int r = 0; r < SIZE; r++) {// row
            for (int c = 0; c < SIZE; c++) {// col
                if (neighbors[r][c] == 3)
                    table[r][c] = true;
                if (neighbors[r][c] < 2)
                    table[r][c] = false;
                if (neighbors[r][c] >= 4)
                    table[r][c] = false;
                neighbors[r][c] = 0;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int cellX = e.getX() / CELL_Size;
        int cellY = e.getY() / CELL_Size;
        table[cellX][cellY] = !e.isControlDown();
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        this.mousePressed(e);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
            running = !running;
            repaint();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

}