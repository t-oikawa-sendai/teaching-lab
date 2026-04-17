/**
 * ファイル名：GamePanel.java
 * 説明：ゲーム状態・メインループ（タイマー）・入力・更新・描画・衝突・勝敗判定を担当する。
 * 作成日：2026-04-10
 * 作者：takashioikawa
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

/** スペースインベーダー風ゲームのパネル。タイマーで更新・再描画を行う。 */
public class GamePanel extends JPanel implements ActionListener {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    /** 約60FPS */
    public static final int TIMER_MS = 16;
    /** レベル0〜3の4段階（最終クリアでゲームクリア） */
    public static final int MAX_LEVEL = 3;
    public static final int INITIAL_LIVES = 3;
    public static final int PLAYER_SHOOT_COOLDOWN_MS = 300;
    public static final int ENEMY_SHOOT_INTERVAL_BASE_MS = 950;

    private static final String STATE_PLAYING = "playing";
    private static final String STATE_GAME_OVER = "game_over";
    private static final String STATE_GAME_CLEAR = "game_clear";

    private final Timer timer;
    private final Font fontHud = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private final Font fontLarge = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    private final Font fontHint = new Font(Font.SANS_SERIF, Font.PLAIN, 18);

    private int groundY;
    private int invasionY;

    private int score;
    private int level;
    private int lives;
    private String state = STATE_PLAYING;

    private Player player;
    private EnemySwarm swarm;
    private final List<Barrier> barriers = new ArrayList<>();
    private final List<Bullet> playerBullets = new ArrayList<>();
    private final List<Bullet> enemyBullets = new ArrayList<>();

    private int shootCooldownMs;
    private int enemyShootAccumMs;

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spaceHeld;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(12, 14, 28));
        setFocusable(true);

        groundY = HEIGHT - 48;
        invasionY = groundY - Player.HEIGHT - 8;

        score = 0;
        level = 0;
        lives = INITIAL_LIVES;
        spawnStage();

        timer = new Timer(TIMER_MS, this);
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyReleased(e);
            }
        });
    }

    private void handleKeyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        } else if (code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        } else if (code == KeyEvent.VK_SPACE) {
            spaceHeld = true;
        } else if (code == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if (code == KeyEvent.VK_R && (STATE_GAME_OVER.equals(state) || STATE_GAME_CLEAR.equals(state))) {
            restartFromBeginning();
        }
    }

    private void handleKeyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        } else if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        } else if (code == KeyEvent.VK_SPACE) {
            spaceHeld = false;
        }
    }

    /** 現在レベルに応じてステージを生成する。 */
    private void spawnStage() {
        player = new Player(WIDTH, groundY);
        swarm = new EnemySwarm(WIDTH, level, 72);
        barriers.clear();
        int barrierTop = groundY - 110;
        for (int i = 0; i < 4; i++) {
            int cx = (int) (WIDTH * (2 * i + 1) / 10.0);
            barriers.add(new Barrier(cx, barrierTop));
        }
        playerBullets.clear();
        enemyBullets.clear();
        shootCooldownMs = 0;
        enemyShootAccumMs = 0;
    }

    /** スコア・レベル・残機を初期化して最初からやり直す。 */
    private void restartFromBeginning() {
        score = 0;
        level = 0;
        lives = INITIAL_LIVES;
        state = STATE_PLAYING;
        spawnStage();
    }

    private int enemyShootIntervalMs() {
        return Math.max(380, ENEMY_SHOOT_INTERVAL_BASE_MS - level * 140);
    }

    private void tryPlayerShoot(int dtMs) {
        shootCooldownMs = Math.max(0, shootCooldownMs - dtMs);
        if (!spaceHeld || shootCooldownMs > 0) {
            return;
        }
        Rectangle pr = player.getBounds();
        playerBullets.add(new Bullet(
                pr.x + pr.width / 2.0,
                pr.y,
                -11.0,
                true));
        shootCooldownMs = PLAYER_SHOOT_COOLDOWN_MS;
    }

    private void enemyTryShoot(int dtMs) {
        enemyShootAccumMs += dtMs;
        if (enemyShootAccumMs < enemyShootIntervalMs()) {
            return;
        }
        enemyShootAccumMs = 0;
        Enemy shooter = swarm.pickRandomShooter();
        if (shooter == null) {
            return;
        }
        Rectangle er = shooter.getBounds();
        double vy = 4.0 + level * 1.2;
        enemyBullets.add(new Bullet(
                er.x + er.width / 2.0,
                er.y + er.height,
                vy,
                false));
    }

    private void updateBullets() {
        for (Bullet b : playerBullets) {
            b.update();
            b.cullIfOffScreen(HEIGHT);
        }
        for (Bullet b : enemyBullets) {
            b.update();
            b.cullIfOffScreen(HEIGHT);
        }
        removeDead(playerBullets);
        removeDead(enemyBullets);
    }

    private static void removeDead(List<Bullet> list) {
        list.removeIf(b -> !b.isAlive());
    }

    /** 自機弾とバリア・敵の衝突。 */
    private void collidePlayerBullets() {
        for (Bullet bullet : playerBullets) {
            if (!bullet.isAlive()) {
                continue;
            }
            Rectangle br = bullet.getBounds();
            for (Barrier barrier : barriers) {
                if (barrier.collideBullet(br)) {
                    bullet.setAlive(false);
                    break;
                }
            }
            if (!bullet.isAlive()) {
                continue;
            }
            for (Enemy enemy : swarm.getEnemies()) {
                if (enemy.isAlive() && br.intersects(enemy.getBounds())) {
                    enemy.setAlive(false);
                    bullet.setAlive(false);
                    score += enemy.getScoreValue();
                    break;
                }
            }
        }
        removeDead(playerBullets);
    }

    /** 敵弾とバリア・自機の衝突。 */
    private void collideEnemyBullets() {
        boolean shouldClearEnemyBullets = false;
        for (Bullet bullet : enemyBullets) {
            if (!bullet.isAlive()) {
                continue;
            }
            Rectangle br = bullet.getBounds();
            for (Barrier barrier : barriers) {
                if (barrier.collideBullet(br)) {
                    bullet.setAlive(false);
                    break;
                }
            }
            if (!bullet.isAlive()) {
                continue;
            }
            if (br.intersects(player.getBounds())) {
                bullet.setAlive(false);
                onPlayerHit();
                shouldClearEnemyBullets = true;
                break;
            }
        }
        if (shouldClearEnemyBullets) {
            enemyBullets.clear();
        }
        removeDead(enemyBullets);
    }

    private void onPlayerHit() {
        lives -= 1;
        player.resetPosition();
        if (lives <= 0) {
            state = STATE_GAME_OVER;
        }
    }

    private void checkStageProgress() {
        if (swarm.aliveCount() > 0) {
            return;
        }
        if (level >= MAX_LEVEL) {
            state = STATE_GAME_CLEAR;
            return;
        }
        level += 1;
        spawnStage();
    }

    private void checkInvasion() {
        if (swarm.invasionLineReached(invasionY)) {
            state = STATE_GAME_OVER;
        }
    }

    /** プレイ中の1フレーム更新。 */
    private void updatePlaying(int dtMs) {
        player.update(leftPressed, rightPressed);
        tryPlayerShoot(dtMs);
        swarm.update();
        enemyTryShoot(dtMs);
        updateBullets();
        collidePlayerBullets();
        collideEnemyBullets();
        if (!STATE_PLAYING.equals(state)) {
            return;
        }
        checkInvasion();
        if (!STATE_PLAYING.equals(state)) {
            return;
        }
        checkStageProgress();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (STATE_PLAYING.equals(state)) {
            updatePlaying(TIMER_MS);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        swarm.draw(g2);
        for (Barrier barrier : barriers) {
            barrier.draw(g2);
        }
        player.draw(g2);
        for (Bullet b : playerBullets) {
            b.draw(g2);
        }
        for (Bullet b : enemyBullets) {
            b.draw(g2);
        }

        drawHud(g2);

        if (STATE_GAME_OVER.equals(state)) {
            drawOverlay(g2, "GAME OVER", "R: 最初から再開 / ESC: 終了");
        } else if (STATE_GAME_CLEAR.equals(state)) {
            drawOverlay(g2, "GAME CLEAR!", "R: 最初から再開 / ESC: 終了");
        }
    }

    private void drawHud(Graphics2D g2) {
        g2.setFont(fontHud);
        g2.setColor(new Color(230, 230, 230));
        int margin = 8;
        g2.drawString("SCORE " + score, margin, margin + 18);
        g2.drawString("LIVES " + lives, margin + 220, margin + 18);
        g2.drawString("LEVEL " + level, margin + 400, margin + 18);
    }

    private void drawOverlay(Graphics2D g2, String title, String subtitle) {
        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        g2.setFont(fontLarge);
        g2.setColor(new Color(255, 255, 200));
        FontMetrics fmLarge = g2.getFontMetrics();
        int titleW = fmLarge.stringWidth(title);
        g2.drawString(title, (WIDTH - titleW) / 2, HEIGHT / 2 - 20);

        g2.setFont(fontHint);
        g2.setColor(new Color(220, 220, 220));
        FontMetrics fmHint = g2.getFontMetrics();
        int subW = fmHint.stringWidth(subtitle);
        g2.drawString(subtitle, (WIDTH - subW) / 2, HEIGHT / 2 + 40);
    }
}
