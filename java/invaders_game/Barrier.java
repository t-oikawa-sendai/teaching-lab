/**
 * ファイル名：Barrier.java
 * 説明：バリア1基のセル集合・弾衝突による段階的破壊・描画を担当する。
 * 作成日：2026-04-10
 * 作者：takashioikawa
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * バリア1基。小さな矩形セルの集合として保持し、
 * 弾が当たったセルから順に欠けていく（段階的破壊）。
 */
public class Barrier {

    private final List<Rectangle> blocks = new ArrayList<>();

    /**
     * @param centerX バリア全体の中心X
     * @param topY    バリア上端Y
     */
    public Barrier(int centerX, int topY) {
        int cols = 11;
        int rows = 5;
        int cellW = 7;
        int cellH = 6;
        int totalW = cols * cellW;
        int left = centerX - totalW / 2;
        // 下部中央をくり抜いたクラシックに近い形状
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (r == rows - 1 && c >= 2 && c <= cols - 3) {
                    continue;
                }
                blocks.add(new Rectangle(left + c * cellW, topY + r * cellH, cellW - 1, cellH - 1));
            }
        }
    }

    /**
     * 弾との衝突を検査する。当たったセルを1つ削除し true を返す。
     *
     * @param bulletRect 弾の矩形
     * @return 何かしらのセルに当たったか
     */
    public boolean collideBullet(Rectangle bulletRect) {
        Iterator<Rectangle> it = blocks.iterator();
        while (it.hasNext()) {
            Rectangle block = it.next();
            if (block.intersects(bulletRect)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /** 残っているセルを描画する。 */
    public void draw(Graphics2D g2) {
        for (Rectangle block : blocks) {
            g2.setColor(new Color(90, 220, 120));
            g2.fillRect(block.x, block.y, block.width, block.height);
            g2.setColor(new Color(20, 80, 30));
            g2.drawRect(block.x, block.y, block.width, block.height);
        }
    }
}
