/**
 * ファイル名：Bullet.java
 * 説明：自機弾・敵弾の移動・画面外判定・描画を担当する。
 * 作成日：2026-04-10
 * 作者：takashioikawa
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/** 画面上を垂直方向に移動する弾。 */
public class Bullet {

    public static final int WIDTH = 4;
    public static final int HEIGHT = 12;

    private final boolean fromPlayer;
    private final double dy;
    private boolean alive = true;
    private final Rectangle rect;

    /**
     * @param centerX   弾の中心X
     * @param topY      弾の上端Y
     * @param dy        1フレームあたりのY移動量（自機弾は負）
     * @param fromPlayer true なら自機弾
     */
    public Bullet(double centerX, double topY, double dy, boolean fromPlayer) {
        this.fromPlayer = fromPlayer;
        this.dy = dy;
        this.rect = new Rectangle(0, 0, WIDTH, HEIGHT);
        rect.x = (int) Math.round(centerX - WIDTH / 2.0);
        rect.y = (int) Math.round(topY);
    }

    public Rectangle getBounds() {
        return rect;
    }

    public boolean isFromPlayer() {
        return fromPlayer;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /** 位置を更新する。 */
    public void update() {
        rect.y += (int) dy;
    }

    /** 画面外に出たら消滅扱いにする。 */
    public void cullIfOffScreen(int screenHeight) {
        if (rect.y + HEIGHT < 0 || rect.y > screenHeight) {
            alive = false;
        }
    }

    /** 弾を描画する。 */
    public void draw(Graphics2D g2) {
        if (fromPlayer) {
            g2.setColor(new Color(120, 255, 120));
        } else {
            g2.setColor(new Color(255, 80, 80));
        }
        g2.fillRect(rect.x, rect.y, rect.width, rect.height);
    }
}
