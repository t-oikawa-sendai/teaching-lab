/**
 * ファイル名：Enemy.java
 * 説明：1体の敵の状態・得点・描画を担当する。
 * 作成日：2026-04-10
 * 作者：takashioikawa
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/** 1体の敵。行番号に応じた得点を持つ。 */
public class Enemy {

    public static final int WIDTH = 36;
    public static final int HEIGHT = 28;

    /** 上の行ほど高得点（行インデックスに対応） */
    private static final int[] SCORE_BY_ROW = {30, 25, 20, 15, 10};

    private final int rowIndex;
    private final int colIndex;
    private final Rectangle rect;
    private boolean alive = true;

    public Enemy(int x, int y, int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.rect = new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public Rectangle getBounds() {
        return rect;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /** 撃破時のスコア。 */
    public int getScoreValue() {
        int idx = Math.min(rowIndex, SCORE_BY_ROW.length - 1);
        return SCORE_BY_ROW[idx];
    }

    /** 敵を描画する。 */
    public void draw(Graphics2D g2) {
        if (!alive) {
            return;
        }
        int hue = 200 + rowIndex * 10;
        g2.setColor(new Color(hue, 120, 220));
        g2.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 8, 8);
        g2.setColor(new Color(40, 20, 60));
        g2.drawRoundRect(rect.x, rect.y, rect.width, rect.height, 8, 8);
    }
}
