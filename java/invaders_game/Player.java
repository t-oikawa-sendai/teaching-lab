/**
 * ファイル名：Player.java
 * 説明：自機の移動・位置リセット・描画を担当する。
 * 作成日：2026-04-10
 * 作者：takashioikawa
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/** 左右移動する自機。 */
public class Player {

    public static final int WIDTH = 48;
    public static final int HEIGHT = 28;
    /** 1フレームあたりの横移動量 */
    public static final int BASE_SPEED = 6;

    private final int screenWidth;
    private final Rectangle rect;

    public Player(int screenWidth, int groundY) {
        this.screenWidth = screenWidth;
        this.rect = new Rectangle(0, 0, WIDTH, HEIGHT);
        rect.x = (screenWidth - WIDTH) / 2;
        rect.y = groundY - HEIGHT;
    }

    public Rectangle getBounds() {
        return rect;
    }

    /** 残機減少後などに画面中央へ戻す。 */
    public void resetPosition() {
        rect.x = (screenWidth - WIDTH) / 2;
    }

    /**
     * ←→キー入力に応じて横移動し、画面内に収める。
     *
     * @param leftPressed  左キーが押されているか
     * @param rightPressed 右キーが押されているか
     */
    public void update(boolean leftPressed, boolean rightPressed) {
        int dx = 0;
        if (leftPressed) {
            dx -= BASE_SPEED;
        }
        if (rightPressed) {
            dx += BASE_SPEED;
        }
        rect.x += dx;
        if (rect.x < 0) {
            rect.x = 0;
        }
        if (rect.x + WIDTH > screenWidth) {
            rect.x = screenWidth - WIDTH;
        }
    }

    /** 自機を描画する（簡易キャノン形状）。 */
    public void draw(Graphics2D g2) {
        int bodyX = rect.x + 4;
        int bodyY = rect.y + 3;
        int bodyW = WIDTH - 8;
        int bodyH = HEIGHT - 6;
        g2.setColor(new Color(180, 220, 255));
        g2.fillRoundRect(bodyX, bodyY, bodyW, bodyH, 6, 6);
        int tipW = 10;
        int tipH = 8;
        int tipX = rect.x + (WIDTH - tipW) / 2;
        g2.setColor(new Color(140, 200, 255));
        g2.fillRect(tipX, rect.y, tipW, tipH);
    }
}
