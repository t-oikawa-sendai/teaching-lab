/**
 * ファイル名：enemy.js
 * 説明：1体の敵と、敵群の移動・下降・侵攻判定・射撃者選択を担当する。
 * Date　：2026-04-10
 * Author：Takashi Oikawa
 */

/** 1体の敵。行番号に応じた得点を持つ。 */
class Enemy {
  static WIDTH = 36;
  static HEIGHT = 28;

  /** 上の行ほど高得点 */
  static SCORE_BY_ROW = [30, 25, 20, 15, 10];

  /**
   * @param {number} x 左上X
   * @param {number} y 左上Y
   * @param {number} rowIndex 行インデックス（0が最上段）
   * @param {number} colIndex 列インデックス
   */
  constructor(x, y, rowIndex, colIndex) {
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
    this.rect = { x, y, width: Enemy.WIDTH, height: Enemy.HEIGHT };
    this.alive = true;
  }

  /** @returns {{x:number,y:number,width:number,height:number}} */
  getBounds() {
    return this.rect;
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

  /** @returns {number} 撃破時のスコア */
  getScoreValue() {
    const idx = Math.min(this.rowIndex, Enemy.SCORE_BY_ROW.length - 1);
    return Enemy.SCORE_BY_ROW[idx];
  }

  /**
   * @param {CanvasRenderingContext2D} ctx
   */
  draw(ctx) {
    if (!this.alive) return;
    const { x, y, width, height } = this.rect;
    const hue = 280 - this.rowIndex * 25;
    ctx.fillStyle = `hsl(${hue}, 70%, 55%)`;
    ctx.strokeStyle = `hsl(${hue}, 80%, 30%)`;
    ctx.lineWidth = 2;
    ctx.fillRect(x, y, width, height);
    ctx.strokeRect(x, y, width, height);
    ctx.fillStyle = "rgba(0,0,0,0.35)";
    ctx.fillRect(x + 6, y + 8, width - 12, 6);
  }
}

/** 敵フォーメーションの左右移動と端での下降を制御する。 */
class EnemySwarm {
  static COLS = 10;
  static ROWS = 5;
  static GAP_X = 12;
  static GAP_Y = 10;
  static MARGIN_SIDE = 40;

  /**
   * @param {number} screenWidth
   * @param {number} level レベル0〜3
   * @param {number} topY フォーメーション上端Y
   */
  constructor(screenWidth, level, topY) {
    this.screenWidth = screenWidth;
    this.topY = topY;
    this.moveSpeed = 1.2 + level * 0.85;
    this.descentStep = 12 + level * 3;
    /** 1: 右向き、-1: 左向き */
    this.direction = 1;
    /** @type {Enemy[]} */
    this.enemies = [];
    this._buildFormation();
  }

  _buildFormation() {
    this.enemies = [];
    const { COLS, ROWS, GAP_X, GAP_Y } = EnemySwarm;
    const totalW = COLS * Enemy.WIDTH + (COLS - 1) * GAP_X;
    const startX = (this.screenWidth - totalW) / 2;
    for (let row = 0; row < ROWS; row++) {
      for (let col = 0; col < COLS; col++) {
        const x = startX + col * (Enemy.WIDTH + GAP_X);
        const y = this.topY + row * (Enemy.HEIGHT + GAP_Y);
        this.enemies.push(new Enemy(x, y, row, col));
      }
    }
  }

  /** @returns {Enemy[]} */
  getEnemies() {
    return this.enemies;
  }

  /** @returns {number} 生存敵の数 */
  aliveCount() {
    return this.enemies.filter((e) => e.isAlive()).length;
  }

  /** 全体を横移動し、端に触れたら反転して一段下降する。 */
  update() {
    const alive = this.enemies.filter((e) => e.isAlive());
    if (alive.length === 0) return;

    const dx = Math.round(this.moveSpeed * this.direction);
    for (const e of alive) {
      e.getBounds().x += dx;
    }

    let hitEdge = false;
    for (const e of alive) {
      const b = e.getBounds();
      if (b.x <= EnemySwarm.MARGIN_SIDE && this.direction < 0) {
        hitEdge = true;
        break;
      }
      if (b.x + Enemy.WIDTH >= this.screenWidth - EnemySwarm.MARGIN_SIDE && this.direction > 0) {
        hitEdge = true;
        break;
      }
    }

    if (hitEdge) {
      this.direction *= -1;
      for (const e of alive) {
        e.getBounds().y += this.descentStep;
      }
    }
  }

  /**
   * 各列で最も下にいる生存敵のうち1体をランダムに選ぶ。
   * @returns {Enemy | null}
   */
  pickRandomShooter() {
    /** @type {Map<number, Enemy>} */
    const columns = new Map();
    for (const e of this.enemies) {
      if (!e.isAlive()) continue;
      const col = e.colIndex;
      const prev = columns.get(col);
      if (!prev || e.getBounds().y > prev.getBounds().y) {
        columns.set(col, e);
      }
    }
    const choices = Array.from(columns.values());
    if (choices.length === 0) return null;
    return choices[Math.floor(Math.random() * choices.length)];
  }

  /**
   * いずれかの生存敵の下端が侵攻ライン以上なら true。
   * @param {number} invasionY
   * @returns {boolean}
   */
  invasionLineReached(invasionY) {
    for (const e of this.enemies) {
      if (e.isAlive() && e.getBounds().y + Enemy.HEIGHT >= invasionY) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param {CanvasRenderingContext2D} ctx
   */
  draw(ctx) {
    for (const e of this.enemies) e.draw(ctx);
  }
}
