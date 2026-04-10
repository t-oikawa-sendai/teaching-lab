/**
 * ファイル名：game.js
 * 説明：ゲーム全体の状態・初期化・ゲームループ・入力・衝突・レベル・勝敗判定・HUD描画を担当する。
 * Date　：2026-04-10
 * Author：Takashi Oikawa
 */

/** ゲーム全体の定数・設定 */
const GameConfig = {
  CANVAS_WIDTH: 800,
  CANVAS_HEIGHT: 600,
  /** レベル0〜3の4段階（レベル3クリアでゲームクリア） */
  MAX_LEVEL: 3,
  INITIAL_LIVES: 3,
  PLAYER_SHOOT_COOLDOWN_MS: 300,
  ENEMY_SHOOT_INTERVAL_BASE_MS: 950,
  BG_COLOR: "#0c0e1c",
};

/**
 * Canvas 上でスペースインベーダー風ゲームを動かすメインクラス。
 */
class Game {
  static STATE_PLAYING = "playing";
  static STATE_PAUSED = "paused";
  static STATE_GAME_OVER = "game_over";
  static STATE_GAME_CLEAR = "game_clear";

  /**
   * @param {HTMLCanvasElement} canvas
   */
  constructor(canvas) {
    this.canvas = canvas;
    this.ctx = /** @type {CanvasRenderingContext2D} */ (canvas.getContext("2d"));
    canvas.width = GameConfig.CANVAS_WIDTH;
    canvas.height = GameConfig.CANVAS_HEIGHT;

    this.groundY = GameConfig.CANVAS_HEIGHT - 48;
    this.invasionY = this.groundY - Player.HEIGHT - 8;

    this.score = 0;
    this.level = 0;
    this.lives = GameConfig.INITIAL_LIVES;
    this.state = Game.STATE_PLAYING;

    /** @type {Player | null} */
    this.player = null;
    /** @type {EnemySwarm | null} */
    this.swarm = null;
    /** @type {Barrier[]} */
    this.barriers = [];
    /** @type {Bullet[]} */
    this.playerBullets = [];
    /** @type {Bullet[]} */
    this.enemyBullets = [];

    this.shootCooldownMs = 0;
    this.enemyShootAccumMs = 0;

    this.leftPressed = false;
    this.rightPressed = false;
    this.spaceHeld = false;

    this._lastFrameTime = 0;
    this._rafId = 0;

    this._onKeyDown = this._onKeyDown.bind(this);
    this._onKeyUp = this._onKeyUp.bind(this);
    this._loop = this._loop.bind(this);

    window.addEventListener("keydown", this._onKeyDown);
    window.addEventListener("keyup", this._onKeyUp);

    this.spawnStage();
  }

  /** 現在レベルに応じてステージを生成する。 */
  spawnStage() {
    this.player = new Player(GameConfig.CANVAS_WIDTH, this.groundY);
    this.swarm = new EnemySwarm(GameConfig.CANVAS_WIDTH, this.level, 72);
    this.barriers = [];
    const barrierTop = this.groundY - 110;
    for (let i = 0; i < 4; i++) {
      const cx = (GameConfig.CANVAS_WIDTH * (2 * i + 1)) / 10;
      this.barriers.push(new Barrier(cx, barrierTop));
    }
    this.playerBullets = [];
    this.enemyBullets = [];
    this.shootCooldownMs = 0;
    this.enemyShootAccumMs = 0;
  }

  /** スコア・レベル・残機を初期化して最初からやり直す。 */
  restartFromBeginning() {
    this.score = 0;
    this.level = 0;
    this.lives = GameConfig.INITIAL_LIVES;
    this.state = Game.STATE_PLAYING;
    this.spawnStage();
  }

  /** @returns {number} */
  _enemyShootIntervalMs() {
    return Math.max(380, GameConfig.ENEMY_SHOOT_INTERVAL_BASE_MS - this.level * 140);
  }

  /**
   * @param {number} dtMs
   */
  _tryPlayerShoot(dtMs) {
    this.shootCooldownMs = Math.max(0, this.shootCooldownMs - dtMs);
    if (!this.spaceHeld || this.shootCooldownMs > 0 || !this.player) return;
    const pr = this.player.getBounds();
    this.playerBullets.push(
      new Bullet(pr.x + pr.width / 2, pr.y, -11, true)
    );
    this.shootCooldownMs = GameConfig.PLAYER_SHOOT_COOLDOWN_MS;
  }

  /**
   * @param {number} dtMs
   */
  _enemyTryShoot(dtMs) {
    if (!this.swarm) return;
    this.enemyShootAccumMs += dtMs;
    if (this.enemyShootAccumMs < this._enemyShootIntervalMs()) return;
    this.enemyShootAccumMs = 0;
    const shooter = this.swarm.pickRandomShooter();
    if (!shooter) return;
    const er = shooter.getBounds();
    const vy = 4 + this.level * 1.2;
    this.enemyBullets.push(
      new Bullet(er.x + er.width / 2, er.y + er.height, vy, false)
    );
  }

  _updateBullets() {
    const h = GameConfig.CANVAS_HEIGHT;
    for (const b of this.playerBullets) {
      b.update();
      b.cullIfOffScreen(h);
    }
    for (const b of this.enemyBullets) {
      b.update();
      b.cullIfOffScreen(h);
    }
    this.playerBullets = this.playerBullets.filter((b) => b.isAlive());
    this.enemyBullets = this.enemyBullets.filter((b) => b.isAlive());
  }

  _collidePlayerBullets() {
    for (const bullet of this.playerBullets) {
      if (!bullet.isAlive()) continue;
      const br = bullet.getBounds();
      for (const barrier of this.barriers) {
        if (barrier.collideBullet(br)) {
          bullet.setAlive(false);
          break;
        }
      }
      if (!bullet.isAlive()) continue;
      if (!this.swarm) continue;
      for (const enemy of this.swarm.getEnemies()) {
        if (!enemy.isAlive()) continue;
        const er = enemy.getBounds();
        if (this._rectsIntersect(br, er)) {
          enemy.setAlive(false);
          bullet.setAlive(false);
          this.score += enemy.getScoreValue();
          break;
        }
      }
    }
    this.playerBullets = this.playerBullets.filter((b) => b.isAlive());
  }

  _collideEnemyBullets() {
    for (const bullet of this.enemyBullets) {
      if (!bullet.isAlive()) continue;
      const br = bullet.getBounds();
      for (const barrier of this.barriers) {
        if (barrier.collideBullet(br)) {
          bullet.setAlive(false);
          break;
        }
      }
      if (!bullet.isAlive()) continue;
      if (this.player && this._rectsIntersect(br, this.player.getBounds())) {
        bullet.setAlive(false);
        this._onPlayerHit();
      }
    }
    this.enemyBullets = this.enemyBullets.filter((b) => b.isAlive());
  }

  /**
   * @param {{x:number,y:number,width:number,height:number}} a
   * @param {{x:number,y:number,width:number,height:number}} b
   */
  _rectsIntersect(a, b) {
    return (
      a.x < b.x + b.width &&
      a.x + a.width > b.x &&
      a.y < b.y + b.height &&
      a.y + a.height > b.y
    );
  }

  _onPlayerHit() {
    this.lives -= 1;
    if (this.player) this.player.resetPosition();
    this.enemyBullets = [];
    if (this.lives <= 0) {
      this.state = Game.STATE_GAME_OVER;
    }
  }

  _checkStageProgress() {
    if (!this.swarm || this.swarm.aliveCount() > 0) return;
    if (this.level >= GameConfig.MAX_LEVEL) {
      this.state = Game.STATE_GAME_CLEAR;
      return;
    }
    this.level += 1;
    this.spawnStage();
  }

  _checkInvasion() {
    if (!this.swarm) return;
    if (this.swarm.invasionLineReached(this.invasionY)) {
      this.state = Game.STATE_GAME_OVER;
    }
  }

  /**
   * プレイ中の1フレーム更新。
   * @param {number} dtMs
   */
  _updatePlaying(dtMs) {
    if (!this.player || !this.swarm) return;
    this.player.update(this.leftPressed, this.rightPressed);
    this._tryPlayerShoot(dtMs);
    this.swarm.update();
    this._enemyTryShoot(dtMs);
    this._updateBullets();
    this._collidePlayerBullets();
    this._collideEnemyBullets();
    if (this.state !== Game.STATE_PLAYING) return;
    this._checkInvasion();
    if (this.state !== Game.STATE_PLAYING) return;
    this._checkStageProgress();
  }

  /**
   * @param {KeyboardEvent} e
   */
  _onKeyDown(e) {
    const code = e.code;
    if (code === "ArrowLeft") {
      this.leftPressed = true;
      e.preventDefault();
    } else if (code === "ArrowRight") {
      this.rightPressed = true;
      e.preventDefault();
    } else if (code === "Space") {
      this.spaceHeld = true;
      e.preventDefault();
    } else if (code === "Escape") {
      if (
        this.state === Game.STATE_PLAYING ||
        this.state === Game.STATE_PAUSED
      ) {
        this.state =
          this.state === Game.STATE_PLAYING
            ? Game.STATE_PAUSED
            : Game.STATE_PLAYING;
      }
      e.preventDefault();
    } else if (code === "KeyR") {
      if (
        this.state === Game.STATE_GAME_OVER ||
        this.state === Game.STATE_GAME_CLEAR ||
        this.state === Game.STATE_PAUSED
      ) {
        this.restartFromBeginning();
      }
      e.preventDefault();
    }
  }

  /**
   * @param {KeyboardEvent} e
   */
  _onKeyUp(e) {
    const code = e.code;
    if (code === "ArrowLeft") this.leftPressed = false;
    else if (code === "ArrowRight") this.rightPressed = false;
    else if (code === "Space") this.spaceHeld = false;
  }

  /**
   * @param {number} timestamp
   */
  _loop(timestamp) {
    if (!this._lastFrameTime) this._lastFrameTime = timestamp;
    const dtMs = Math.min(50, timestamp - this._lastFrameTime);
    this._lastFrameTime = timestamp;

    if (this.state === Game.STATE_PLAYING) {
      this._updatePlaying(dtMs);
    }

    this._draw();
    this._rafId = requestAnimationFrame(this._loop);
  }

  /** ゲームループ開始 */
  start() {
    this._lastFrameTime = 0;
    this._rafId = requestAnimationFrame(this._loop);
  }

  /** ループ停止（ページ離脱時など） */
  stop() {
    cancelAnimationFrame(this._rafId);
    window.removeEventListener("keydown", this._onKeyDown);
    window.removeEventListener("keyup", this._onKeyUp);
  }

  _draw() {
    const ctx = this.ctx;
    const w = GameConfig.CANVAS_WIDTH;
    const h = GameConfig.CANVAS_HEIGHT;

    ctx.fillStyle = GameConfig.BG_COLOR;
    ctx.fillRect(0, 0, w, h);

    if (this.swarm) this.swarm.draw(ctx);
    for (const b of this.barriers) b.draw(ctx);
    if (this.player) this.player.draw(ctx);
    for (const b of this.playerBullets) b.draw(ctx);
    for (const b of this.enemyBullets) b.draw(ctx);

    this._drawHud();

    if (this.state === Game.STATE_PAUSED) {
      this._drawOverlay("PAUSED", "ESC: 再開 / R: 最初から");
    } else if (this.state === Game.STATE_GAME_OVER) {
      this._drawOverlay("GAME OVER", "R: 最初から再開");
    } else if (this.state === Game.STATE_GAME_CLEAR) {
      this._drawOverlay("GAME CLEAR!", "R: 最初から再開");
    }
  }

  _drawHud() {
    const ctx = this.ctx;
    ctx.font = "bold 20px sans-serif";
    ctx.fillStyle = "#e6e6e6";
    const margin = 8;
    ctx.fillText(`SCORE ${this.score}`, margin, margin + 18);
    ctx.fillText(`LIVES ${this.lives}`, margin + 220, margin + 18);
    ctx.fillText(`LEVEL ${this.level}`, margin + 400, margin + 18);
    if (this.state === Game.STATE_PLAYING) {
      ctx.font = "14px sans-serif";
      ctx.fillStyle = "#8899aa";
      ctx.fillText("ESC: 一時停止", margin + 560, margin + 18);
    }
  }

  /**
   * @param {string} title
   * @param {string} subtitle
   */
  _drawOverlay(title, subtitle) {
    const ctx = this.ctx;
    const w = GameConfig.CANVAS_WIDTH;
    const h = GameConfig.CANVAS_HEIGHT;

    ctx.fillStyle = "rgba(0,0,0,0.67)";
    ctx.fillRect(0, 0, w, h);

    ctx.font = "bold 48px sans-serif";
    ctx.fillStyle = "#ffffc8";
    const tw = ctx.measureText(title).width;
    ctx.fillText(title, (w - tw) / 2, h / 2 - 20);

    ctx.font = "18px sans-serif";
    ctx.fillStyle = "#dcdcdc";
    const sw = ctx.measureText(subtitle).width;
    ctx.fillText(subtitle, (w - sw) / 2, h / 2 + 40);
  }
}
