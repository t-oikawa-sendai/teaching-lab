/**
 * ファイル名：EnemySwarm.java
 * 説明：複数行×列の敵群の移動・下降・侵攻判定・射撃者選択を担当する。
 * 作成日：2026-04-10
 * 作者：takashioikawa
 */

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** 敵フォーメーションの左右移動と端での下降を制御する。 */
public class EnemySwarm {

    public static final int COLS = 10;
    public static final int ROWS = 5;
    private static final int GAP_X = 12;
    private static final int GAP_Y = 10;
    private static final int MARGIN_SIDE = 40;

    private final int screenWidth;
    private final int topY;
    /** レベルが上がるほど速く、下降量も増える */
    private final double moveSpeed;
    private final int descentStep;
    /** 1: 右向き、-1: 左向き */
    private int direction = 1;

    private final List<Enemy> enemies = new ArrayList<>();
    private final Random random = new Random();

    public EnemySwarm(int screenWidth, int level, int topY) {
        this.screenWidth = screenWidth;
        this.topY = topY;
        this.moveSpeed = 1.2 + level * 0.85;
        this.descentStep = 12 + level * 3;
        buildFormation();
    }

    /** 敵を格子状に配置する。 */
    private void buildFormation() {
        enemies.clear();
        int totalW = COLS * Enemy.WIDTH + (COLS - 1) * GAP_X;
        int startX = (screenWidth - totalW) / 2;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = startX + col * (Enemy.WIDTH + GAP_X);
                int y = topY + row * (Enemy.HEIGHT + GAP_Y);
                enemies.add(new Enemy(x, y, row, col));
            }
        }
    }

    public List<Enemy> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    /** 生存敵の数。 */
    public int aliveCount() {
        int n = 0;
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                n++;
            }
        }
        return n;
    }

    /** 全体を横移動し、端に触れたら反転して一段下降する。 */
    public void update() {
        List<Enemy> alive = new ArrayList<>();
        for (Enemy e : enemies) {
            if (e.isAlive()) {
                alive.add(e);
            }
        }
        if (alive.isEmpty()) {
            return;
        }

        int dx = (int) (moveSpeed * direction);
        for (Enemy e : alive) {
            e.getBounds().x += dx;
        }

        boolean hitEdge = false;
        for (Enemy e : alive) {
            if (e.getBounds().x <= MARGIN_SIDE && direction < 0) {
                hitEdge = true;
                break;
            }
            if (e.getBounds().x + Enemy.WIDTH >= screenWidth - MARGIN_SIDE && direction > 0) {
                hitEdge = true;
                break;
            }
        }

        if (hitEdge) {
            direction *= -1;
            for (Enemy e : alive) {
                e.getBounds().y += descentStep;
            }
        }
    }

    /**
     * 各列で最も下にいる生存敵のうち1体をランダムに選ぶ。
     *
     * @return 射撃可能な敵。いなければ null
     */
    public Enemy pickRandomShooter() {
        Map<Integer, Enemy> columns = new HashMap<>();
        for (Enemy e : enemies) {
            if (!e.isAlive()) {
                continue;
            }
            int col = e.getColIndex();
            Enemy prev = columns.get(col);
            if (prev == null || e.getBounds().y + Enemy.HEIGHT > prev.getBounds().y + Enemy.HEIGHT) {
                columns.put(col, e);
            }
        }
        if (columns.isEmpty()) {
            return null;
        }
        List<Enemy> choices = new ArrayList<>(columns.values());
        return choices.get(random.nextInt(choices.size()));
    }

    /** いずれかの生存敵の下端が侵攻ライン以上なら true。 */
    public boolean invasionLineReached(int invasionY) {
        for (Enemy e : enemies) {
            if (e.isAlive() && e.getBounds().y + Enemy.HEIGHT >= invasionY) {
                return true;
            }
        }
        return false;
    }

    /** 生存している敵をすべて描画する。 */
    public void draw(Graphics2D g2) {
        for (Enemy e : enemies) {
            e.draw(g2);
        }
    }
}
