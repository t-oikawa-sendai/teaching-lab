/**
 * ファイル名：bullet.js
 * 説明：自機弾・敵弾の移動・画面外判定・描画を担当する。
 * Date　：2026-04-10
 * Author：Takashi Oikawa
 */

/** 画面上を垂直方向に移動する弾。 */
class Bullet {
  static WIDTH = 4;
  static HEIGHT = 12;

  /**
   * @param {number} centerX 弾の中心X
   * @param {number} topY 弾の上端Y
   * @param {number} dy 1フレームあたりのY移動量（自機弾は負）
   * @param {boolean} fromPlayer true なら自機弾
   */
  constructor(centerX, topY, dy, fromPlayer) {
    this.fromPlayer = fromPlayer;
    this.dy = dy;
    this.alive = true;
    this.rect = {
      x: Math.round(centerX - Bullet.WIDTH / 2),
      y: Math.round(topY),
      width: Bullet.WIDTH,
      height: Bullet.HEIGHT,
    };
  }

  /** @returns {{x:number,y:number,width:number,height:number}} */
  getBounds() {
    return this.rect;
  }

  isFromPlayer() {
    return this.fromPlayer;
  }

  isAlive() {
    return this.alive;
  }

  /**
   * @param {boolean} v
   */
  setAlive(v) {
    this.alive = v;
  }

  update() {
    this.rect.y += this.dy;
  }

  /**
   * 画面外なら消滅させる。
   * @param {number} canvasHeight
   */
  cullIfOffScreen(canvasHeight) {
    if (this.rect.y + this.rect.height < 0 || this.rect.y > canvasHeight) {
      this.alive = false;
    }
  }

  /**
   * @param {CanvasRenderingContext2D} ctx
   */
  draw(ctx) {
    if (!this.alive) return;
    const { x, y, width, height } = this.rect;
    if (this.fromPlayer) {
      ctx.fillStyle = "#ffff88";
      ctx.strokeStyle = "#aa8800";
    } else {
      ctx.fillStyle = "#ff6666";
      ctx.strokeStyle = "#880000";
    }
    ctx.fillRect(x, y, width, height);
    ctx.strokeRect(x, y, width, height);
  }
}
