/**
 * ファイル名：player.js
 * 説明：自機の移動・位置リセット・描画を担当する。
 * Date　：2026-04-10
 * Author：Takashi Oikawa
 */

/** 左右移動する自機。 */
class Player {
  static WIDTH = 48;
  static HEIGHT = 28;
  /** 1フレームあたりの横移動量 */
  static BASE_SPEED = 6;

  /**
   * @param {number} screenWidth 画面幅（ピクセル）
   * @param {number} groundY 地面（自機が載るライン）のY座標
   */
  constructor(screenWidth, groundY) {
    this.screenWidth = screenWidth;
    this.rect = {
      x: (screenWidth - Player.WIDTH) / 2,
      y: groundY - Player.HEIGHT,
      width: Player.WIDTH,
      height: Player.HEIGHT,
    };
  }

  /** @returns {{x:number,y:number,width:number,height:number}} */
  getBounds() {
    return this.rect;
  }

  /** 残機減少後などに画面中央へ戻す。 */
  resetPosition() {
    this.rect.x = (this.screenWidth - Player.WIDTH) / 2;
  }

  /**
   * ←→キー入力に応じて横移動し、画面内に収める。
   * @param {boolean} leftPressed 左キーが押されているか
   * @param {boolean} rightPressed 右キーが押されているか
   */
  update(leftPressed, rightPressed) {
    let dx = 0;
    if (leftPressed) dx -= Player.BASE_SPEED;
    if (rightPressed) dx += Player.BASE_SPEED;
    this.rect.x += dx;
    const margin = 8;
    if (this.rect.x < margin) this.rect.x = margin;
    const maxX = this.screenWidth - Player.WIDTH - margin;
    if (this.rect.x > maxX) this.rect.x = maxX;
  }

  /**
   * @param {CanvasRenderingContext2D} ctx
   */
  draw(ctx) {
    const { x, y, width, height } = this.rect;
    ctx.fillStyle = "#6ec8ff";
    ctx.strokeStyle = "#1a5080";
    ctx.lineWidth = 2;
    ctx.beginPath();
    ctx.moveTo(x + width / 2, y);
    ctx.lineTo(x + width, y + height);
    ctx.lineTo(x, y + height);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();
  }
}
