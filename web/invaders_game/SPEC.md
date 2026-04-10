# プログラム仕様書 - Web版

本書は `web/invaders_game/` 配下の実装（HTML5 Canvas + JavaScript）に基づく仕様です。

## システム概要

- **目的**: ブラウザ上で動作するスペースインベーダー風 2D シューティングゲームを提供する。
- **構成**: `index.html` が Canvas と複数のスクリプトを順に読み込み、グローバルスコープ上のクラスでゲームを構成する（モジュールバンドラは不使用）。
- **駆動方式**: `requestAnimationFrame` によるフレームループ。前フレームからの経過時間 `dtMs`（上限 50ms）を用いてクールダウンや敵射撃間隔を積算する。

## ファイル構成と各モジュールの責務

| ファイル | 責務 |
|----------|------|
| `index.html` | ページ構造、スタイル、`<canvas id="gameCanvas">`、全 JS の読み込み順の定義 |
| `js/main.js` | Canvas 要素の取得、`Game` インスタンス生成、`start()` 呼び出しのみ |
| `js/game.js` | 定数オブジェクト `GameConfig`、`Game` クラス（状態、初期化、ループ、入力、衝突、レベル、勝敗、HUD・オーバーレイ） |
| `js/player.js` | `Player` クラス（自機の移動・描画・位置リセット） |
| `js/enemy.js` | `Enemy` クラス（1 体）、`EnemySwarm` クラス（群の移動・下降・侵攻判定・射撃者選択） |
| `js/bullet.js` | `Bullet` クラス（自機弾・敵弾の移動・画面外削除・描画） |
| `js/barrier.js` | `Barrier` クラス（セル格子・耐久による段階的破壊・描画） |

読み込み順: `player.js` → `enemy.js` → `bullet.js` → `barrier.js` → `game.js` → `main.js`（後続が先行クラスに依存）。

## クラス仕様（全クラスのプロパティ・メソッド・引数・戻り値）

### `Player`

| 種別 | 名前 | 説明 |
|------|------|------|
| 静的プロパティ | `WIDTH`, `HEIGHT`, `BASE_SPEED` | 自機サイズと横移動速度（数値） |
| コンストラクタ | `constructor(screenWidth, groundY)` | 画面幅と地面 Y。自機を中央下付近に配置 |
| インスタンス | `screenWidth`, `rect` | `rect` は `{ x, y, width, height }` |
| メソッド | `getBounds()` | 戻り値: 上記 `rect` の参照 |
| メソッド | `resetPosition()` | 戻り値: なし。自機 X を中央に戻す |
| メソッド | `update(leftPressed, rightPressed)` | 戻り値: なし。左右入力で移動し画面内にクランプ |
| メソッド | `draw(ctx)` | 戻り値: なし。三角形風の自機を描画 |

### `Enemy`

| 種別 | 名前 | 説明 |
|------|------|------|
| 静的プロパティ | `WIDTH`, `HEIGHT`, `SCORE_BY_ROW` | 敵サイズ、行ごとの得点配列 |
| コンストラクタ | `constructor(x, y, rowIndex, colIndex)` | 位置と格子インデックス |
| インスタンス | `rowIndex`, `colIndex`, `rect`, `alive` | `rect` は AABB |
| メソッド | `getBounds()` | 戻り値: `rect` |
| メソッド | `isAlive()` / `setAlive(v)` | 生存フラグ |
| メソッド | `getScoreValue()` | 戻り値: `number`（行に応じた得点） |
| メソッド | `draw(ctx)` | 戻り値: なし |

### `EnemySwarm`

| 種別 | 名前 | 説明 |
|------|------|------|
| 静的定数 | `COLS`, `ROWS`, `GAP_X`, `GAP_Y`, `MARGIN_SIDE` | 列数・行数・間隔・端マージン |
| コンストラクタ | `constructor(screenWidth, level, topY)` | レベルに応じた `moveSpeed`・`descentStep` を設定し編隊生成 |
| インスタンス | `screenWidth`, `topY`, `moveSpeed`, `descentStep`, `direction`, `enemies` | `enemies` は `Enemy[]` |
| メソッド | `getEnemies()` | 戻り値: `Enemy[]` |
| メソッド | `aliveCount()` | 戻り値: `number` |
| メソッド | `update()` | 戻り値: なし。生存敵を横移動、端で反転＋一斉下降 |
| メソッド | `pickRandomShooter()` | 戻り値: `Enemy \| null`（各列最下段の生存敵からランダム） |
| メソッド | `invasionLineReached(invasionY)` | 戻り値: `boolean` |
| メソッド | `draw(ctx)` | 戻り値: なし |

### `Bullet`

| 種別 | 名前 | 説明 |
|------|------|------|
| 静的プロパティ | `WIDTH`, `HEIGHT` | 弾のサイズ |
| コンストラクタ | `constructor(centerX, topY, dy, fromPlayer)` | 位置、Y 速度、自機弾かどうか |
| インスタンス | `fromPlayer`, `dy`, `alive`, `rect` | |
| メソッド | `getBounds()` | 戻り値: `rect` |
| メソッド | `isFromPlayer()`, `isAlive()`, `setAlive(v)` | |
| メソッド | `update()` | 戻り値: なし。`rect.y += dy` |
| メソッド | `cullIfOffScreen(canvasHeight)` | 戻り値: なし。上下画面外で `alive = false` |
| メソッド | `draw(ctx)` | 戻り値: なし |

### `Barrier`

| 種別 | 名前 | 説明 |
|------|------|------|
| 静的定数 | `MAX_CELL_HP` | セル最大耐久（4） |
| コンストラクタ | `constructor(centerX, topY)` | クラシック風に下部中央を欠いた格子のセルを生成 |
| インスタンス | `blocks` | `{ x, y, w, h, hp }[]` |
| メソッド（静的） | `_intersectsRect(a, b)` | 内部用 AABB 判定 |
| メソッド | `collideBullet(bulletRect)` | 戻り値: `boolean`。命中時 `hp` を 1 減らし 0 でセル削除 |
| メソッド（静的） | `_colorForHp(hp)` | 描画色（内部） |
| メソッド | `draw(ctx)` | 戻り値: なし |

### `GameConfig`（`game.js` 内オブジェクト）

| プロパティ | 意味 |
|------------|------|
| `CANVAS_WIDTH`, `CANVAS_HEIGHT` | 800, 600 |
| `MAX_LEVEL` | 3（レベル 0〜3） |
| `INITIAL_LIVES` | 3 |
| `PLAYER_SHOOT_COOLDOWN_MS` | 300 |
| `ENEMY_SHOOT_INTERVAL_BASE_MS` | 950 |
| `BG_COLOR` | 背景色文字列 |

### `Game`

| 種別 | 名前 | 説明 |
|------|------|------|
| 静的定数 | `STATE_PLAYING`, `STATE_PAUSED`, `STATE_GAME_OVER`, `STATE_GAME_CLEAR` | 状態文字列 |
| コンストラクタ | `constructor(canvas)` | 2D コンテキスト取得、キー購読、`spawnStage()` |
| 主要インスタンス | `canvas`, `ctx`, `groundY`, `invasionY`, `score`, `level`, `lives`, `state`, `player`, `swarm`, `barriers`, `playerBullets`, `enemyBullets`, 入力フラグ、ループ用 `_lastFrameTime`, `_rafId` | |
| メソッド | `spawnStage()` | 戻り値: なし。プレイヤー・敵群・4 バリア・弾リスト・クールダウンを初期化 |
| メソッド | `restartFromBeginning()` | 戻り値: なし。スコア 0、レベル 0、残機初期化、`STATE_PLAYING` で `spawnStage` |
| メソッド | `start()` | 戻り値: なし。`requestAnimationFrame` で `_loop` 開始 |
| メソッド | `stop()` | 戻り値: なし。`cancelAnimationFrame` とキー解除 |
| メソッド（内部） | `_enemyShootIntervalMs()` | 戻り値: `number`（レベルで短くなる、下限 380） |
| メソッド（内部） | `_tryPlayerShoot(dtMs)`, `_enemyTryShoot(dtMs)` | 射撃処理 |
| メソッド（内部） | `_updateBullets()` | 弾移動と配列整理 |
| メソッド（内部） | `_collidePlayerBullets()`, `_collideEnemyBullets()` | バリア・敵・自機との衝突 |
| メソッド（内部） | `_rectsIntersect(a, b)` | AABB 交差 |
| メソッド（内部） | `_onPlayerHit()` | 残機減算、自機位置リセット、敵弾クリア、0 でゲームオーバー |
| メソッド（内部） | `_checkStageProgress()` | 全滅時: レベル 3 ならゲームクリア、否则レベルアップして `spawnStage` |
| メソッド（内部） | `_checkInvasion()` | 侵攻ライン到達でゲームオーバー |
| メソッド（内部） | `_updatePlaying(dtMs)` | 1 フレームのプレイ更新 |
| メソッド（内部） | `_onKeyDown`, `_onKeyUp` | キーボード（ArrowLeft/Right, Space, Escape, KeyR） |
| メソッド（内部） | `_loop(timestamp)` | rAF コールバック、`dtMs` 算出、更新・描画 |
| メソッド（内部） | `_draw()`, `_drawHud()`, `_drawOverlay(title, subtitle)` | 描画 |

## ゲームロジック仕様

### 初期化処理

1. `main.js` が `#gameCanvas` を取得し `new Game(canvas)`、`game.start()`。
2. `Game` コンストラクタで Canvas サイズを `GameConfig` に合わせ、`groundY`・`invasionY` を決定。
3. `spawnStage()` で `Player`、現在 `level` に応じた `EnemySwarm`、バリア 4 基、弾配列空、射撃タイマ初期化。

### ゲームループ（requestAnimationFrame）

1. `_loop(timestamp)` で `dtMs = min(50, timestamp - last)` を計算し `_lastFrameTime` を更新。
2. `state === playing` のときのみ `_updatePlaying(dtMs)` を実行。
3. 毎フレーム `_draw()` で背景・敵・バリア・自機・弾・HUD を描画し、停止／ゲームオーバー／クリア時はオーバーレイを重ねる。
4. 次フレームを `requestAnimationFrame(_loop)` で予約。

### キー入力処理

- **ArrowLeft / ArrowRight**: `leftPressed` / `rightPressed`（`preventDefault` によりスクロール抑制）。
- **Space（keydown 中）**: `spaceHeld` で連射意図を表現（実際の発射はクールダウンで制限）。
- **Escape**: `playing` ↔ `paused` をトグル。
- **KeyR**: `paused` / `game_over` / `game_clear` 時に `restartFromBeginning()`。

### 衝突判定ロジック

- **自機弾**: 各弾について全バリアに対し `Barrier.collideBullet`（先に当たったら弾消滅）。続けて各生存敵の AABB と交差なら敵撃破・得点加算・弾消滅。
- **敵弾**: 同様にバリア後、自機 AABB と交差なら `_onPlayerHit()`。

### レベル管理ロジック

- 初期レベル 0。敵全滅時:
  - `level >= MAX_LEVEL`（3）なら `STATE_GAME_CLEAR`。
  - 否则 `level++` し `spawnStage()`（敵速度・下降・射撃が強化された新ステージ）。

`EnemySwarm` の `moveSpeed = 1.2 + level * 0.85`、`descentStep = 12 + level * 3`。敵弾の垂直速度は `4 + level * 1.2`。

### 勝敗判定ロジック

- **ゲームオーバー**: `lives === 0`（被弾）、または `EnemySwarm.invasionLineReached(invasionY)`（`invasionY = groundY - Player.HEIGHT - 8`）。
- **ゲームクリア**: レベル 3 のステージで敵 `aliveCount() === 0`。

## 定数一覧（画面サイズ・速度・色等）

| 名称 | 値 / 内容 |
|------|-----------|
| キャンバス | 800×600 |
| `Player` サイズ | 48×28、`BASE_SPEED` 6 |
| `Enemy` サイズ | 36×28、得点行別 30,25,20,15,10 |
| 敵編隊 | 10 列×5 行、横ギャップ 12、縦 10、端マージン 40 |
| 敵フォーメーション上端 | Y = 72（`spawnStage` 引数） |
| 自機弾速度 | dy = -11（毎フレームピクセル） |
| 敵弾速度 | dy = 4 + level × 1.2 |
| 自機射撃クールダウン | 300 ms |
| 敵射撃間隔 | `max(380, 950 - level×140)` ms ごとに 1 発候補 |
| バリアセル | 11×5 格子（一部欠損）、セル約 7×6、`MAX_CELL_HP` 4 |
| バリア配置 | `groundY - 110`、X は画面幅の 1/10, 3/10, 5/10, 7/10 相当の中心 |
| 背景色 | `#0c0e1c` |
| 状態定数 | `playing` / `paused` / `game_over` / `game_clear` |

## 状態遷移図（テキスト形式）

```
[（初回・Rで再開）]
        |
        v
   +---------+
   | playing |<-------------------+
   +---------+                    |
        | |                        |
   ESC  | | 全敵撃破              |
        | |    |                   |
        v |    | level<3           |
   +---------+ |    v              |
   | paused  | | spawnStage       |
   +---------+ | level++           |
        | |    +-------------------+
   ESC  | |
        | +--> level==3 かつ 全滅 --> [game_clear]
        |
        | 侵攻 or 残機0
        v
   [game_over]

paused / game_over / game_clear から R --> スコア0・level0・lives初期化 --> playing
```

## 既知の制約事項

- **タッチ操作未対応**: キーボード前提。モバイルではプレイしづらい。
- **`file://` とキー入力**: 多くのブラウザでは問題なく動作するが、環境によっては Canvas にフォーカスが無い場合の挙動に注意（本実装は `window` にキーイベントを登録）。
- **高精度な物理表現なし**: 斜め移動や回転はなく、軸平行矩形の衝突のみ。
- **音声なし**: SE・BGM は未実装。
- **永続化なし**: ハイスコアや設定の保存は行わない。
