/**
 * ファイル名：barrier.js
 * 説明：バリア1基のセル集合・弾衝突による段階的破壊（耐久）・描画を担当する。
 * Date　：2026-04-10
 * Author：Takashi Oikawa
 */

/**
 * バリア1基。小さな矩形セルの集合と各セルの耐久（最大4）を持ち、
 * 弾が当たるたびに耐久が減り、0でセルが消える。
 */
class Barrier {
  /** 1セルあたりの最大耐久（段階数） */
  static MAX_CELL_HP = 4;

  /**
   * @param {number} centerX バリア全体の中心X
   * @param {number} topY バリア上端Y
   */
  constructor(centerX, topY) {
    const cols = 11;
    const rows = 5;
    const cellW = 7;
    const cellH = 6;
    const totalW = cols * cellW;
    const left = centerX - totalW / 2;
    /** @type {{x:number,y:number,w:number,h:number,hp:number}[]} */
    this.blocks = [];
    for (let r = 0; r < rows; r++) {
      for (let c = 0; c < cols; c++) {
        if (r === rows - 1 && c >= 2 && c <= cols - 3) continue;
        this.blocks.push({
          x: left + c * cellW,
          y: topY + r * cellH,
          w: cellW - 1,
          h: cellH - 1,
          hp: Barrier.MAX_CELL_HP,
        });
      }
    }
  }

  /**
   * 矩形同士の交差判定（AABB）
   * @param {{x:number,y:number,width:number,height:number}} a
   * @param {{x:number,y:number,w:number,h:number}} b
   */
  static _intersectsRect(a, b) {
    return (
      a.x < b.x + b.w &&
      a.x + a.width > b.x &&
      a.y < b.y + b.h &&
      a.y + a.height > b.y
    );
  }

  /**
   * 弾との衝突を検査する。当たったセルの耐久を1減らし、0なら削除。当たりがあれば true。
   * @param {{x:number,y:number,width:number,height:number}} bulletRect
   * @returns {boolean}
   */
  collideBullet(bulletRect) {
    for (let i = 0; i < this.blocks.length; i++) {
      const block = this.blocks[i];
      if (!Barrier._intersectsRect(bulletRect, block)) continue;
      block.hp -= 1;
      if (block.hp <= 0) {
        this.blocks.splice(i, 1);
      }
      return true;
    }
    return false;
  }

  /**
   * 耐久に応じた塗り色
   * @param {number} hp
   */
  static _colorForHp(hp) {
    if (hp >= 4) return { fill: "#5adc78", stroke: "#145022" };
    if (hp === 3) return { fill: "#4aba68", stroke: "#12401c" };
    if (hp === 2) return { fill: "#389850", stroke: "#0e3014" };
    return { fill: "#287038", stroke: "#0a2010" };
  }

  /**
   * @param {CanvasRenderingContext2D} ctx
   */
  draw(ctx) {
    for (const block of this.blocks) {
      const { fill, stroke } = Barrier._colorForHp(block.hp);
      ctx.fillStyle = fill;
      ctx.strokeStyle = stroke;
      ctx.fillRect(block.x, block.y, block.w, block.h);
      ctx.strokeRect(block.x, block.y, block.w, block.h);
    }
  }
}
