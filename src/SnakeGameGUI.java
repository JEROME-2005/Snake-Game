import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;


public class SnakeGameGUI extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int UNIT_SIZE = 20;
    private final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE); // remove
    private final int DELAY = 150; // speed of the game

    private final Deque<Point> snake = new LinkedList<>(); // snake body
    private Point food;
    private int score = 0;
    private char direction = 'R'; // U, D, L, R
    private boolean running = false;
    private Timer timer;
    private Random random;

    // Constructor
    public SnakeGameGUI() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        random = new Random();
        startGame();
    }

    public void startGame() {
        snake.clear();
        snake.add(new Point(0, 0)); // initial snake
        score = 0;
        direction = 'R';
        running = true;
        spawnFood();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void spawnFood() {
        while (true) {
            food = new Point(random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE,
                    random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE);
            if (!snake.contains(food)) break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Food
            g.setColor(Color.red);
            g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);

            // Snake
            for (Point p : snake) {
                g.setColor(Color.green);
                g.fillRect(p.x, p.y, UNIT_SIZE, UNIT_SIZE);
            }

            // Score
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Score: " + score, 10, 20);
        } else {
            gameOver(g);
        }
    }

    public void move() {
        Point head = snake.peekLast();
        int newX = head.x;
        int newY = head.y;

        switch (direction) {
            case 'U': newY -= UNIT_SIZE; break;
            case 'D': newY += UNIT_SIZE; break;
            case 'L': newX -= UNIT_SIZE; break;
            case 'R': newX += UNIT_SIZE; break;
        }

        Point newHead = new Point(newX, newY);

        // Collision checks
        if (newX < 0 || newX >= WIDTH || newY < 0 || newY >= HEIGHT || snake.contains(newHead)) {
            running = false;
            timer.stop();
            return;
        }

        snake.addLast(newHead);

        // Check food
        if (newHead.equals(food)) {
            score++;
            spawnFood();
        } else {
            snake.pollFirst(); // remove tail if no food
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (WIDTH - metrics.stringWidth("Game Over!")) / 2, HEIGHT / 2);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Final Score: " + score, WIDTH / 2 - 60, HEIGHT / 2 + 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_ENTER:
                if (!running) startGame(); // restart game
                break;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    // ------------ MAIN METHOD ------------
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game - DSA with Swing");
        SnakeGameGUI gamePanel = new SnakeGameGUI();
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
