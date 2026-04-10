# プログラム仕様書 - Java版

## システム概要

本システムは **JDK25 + Swing（追加ライブラリなし）** で実装した、スペースインベーダー風の2Dシューティングゲームである。  
プレイヤー（自機）は左右移動と弾発射を行い、敵群（フォーメーション）を全滅させることでレベルを進行する。

- レベル: **0〜3（全4段階）**
- ゲーム状態: `playing` / `game_over` / `game_clear`
- メインループ: `javax.swing.Timer`（約60FPS）で更新・再描画する

## ファイル構成と各クラスの責務

- `Main.java`
  - エントリーポイント。`JFrame` を生成して `GamePanel` を表示するのみ。
  - GUI初期化は `SwingUtilities.invokeAndWait()` 内で行う。
- `GamePanel.java`
  - ゲーム状態（スコア／残機／レベル／状態文字列）管理
  - `Timer` によるゲームループ（更新と `repaint()`）
  - キー入力（←→スペース/R/ESC）
  - ステージ生成（敵群・バリア・弾の初期化）
  - 衝突判定（弾×バリア、弾×敵、敵弾×自機）
  - 勝敗判定（侵攻ライン到達／被弾による残機0／全滅）
  - 描画（背景、敵、バリア、自機、弾、HUD、オーバーレイ）
- `Player.java`
  - 自機の矩形、左右移動、画面内クランプ、被弾後の位置リセット、描画
- `Enemy.java`
  - 敵1体の矩形、行・列インデックス、alive、得点、描画
- `EnemySwarm.java`
  - 敵フォーメーション（5行×10列）の生成
  - 左右移動、端で反転＋下降、侵攻ライン判定
  - 各列の最前列（最下段）から射撃者を選びランダムに返す
- `Bullet.java`
  - 弾の矩形、移動量（dy）、自機弾/敵弾属性、alive、移動・画面外消滅、描画
- `Barrier.java`
  - バリアのセル矩形リスト生成、弾衝突でセル1つ削除（段階的破壊）、描画

## クラス仕様（全クラスのフィールド・メソッド・引数・戻り値）

### `Main`（`Main.java`）

#### フィールド
- `private static JFrame mainWindow`
  - トップレベルウィンドウ参照（保険として保持）

#### メソッド
- `public static void main(String[] args)`
  - `SwingUtilities.invokeAndWait(Runnable)` 内で `JFrame` を生成し、`GamePanel` を表示する。
  - 例外（`InterruptedException`, `InvocationTargetException`）は標準エラーへ出力する。

### `GamePanel`（`GamePanel.java`）

#### 定数（抜粋）
- `WIDTH: int = 800` 画面幅
- `HEIGHT: int = 600` 画面高さ
- `TIMER_MS: int = 16` タイマー周期（約60FPS）
- `MAX_LEVEL: int = 3` 最大レベル（0〜3）
- `INITIAL_LIVES: int = 3` 初期残機
- `PLAYER_SHOOT_COOLDOWN_MS: int = 300` 自機射撃クールダウン（ms）
- `ENEMY_SHOOT_INTERVAL_BASE_MS: int = 950` 敵射撃の基準間隔（ms）
- `STATE_PLAYING: String = "playing"`
- `STATE_GAME_OVER: String = "game_over"`
- `STATE_GAME_CLEAR: String = "game_clear"`

#### フィールド（主要）
- `private final Timer timer` ゲームループ用タイマー
- `private int groundY` 地面ライン（`HEIGHT - 48`）
- `private int invasionY` 侵攻ライン（`groundY - Player.HEIGHT - 8`）
- `private int score` スコア
- `private int level` 現在レベル
- `private int lives` 残機
- `private String state` 状態文字列
- `private Player player` 自機
- `private EnemySwarm swarm` 敵群
- `private final List<Barrier> barriers` バリア（4基）
- `private final List<Bullet> playerBullets` 自機弾
- `private final List<Bullet> enemyBullets` 敵弾
- `private int shootCooldownMs` 自機射撃クールダウン残量
- `private int enemyShootAccumMs` 敵射撃タイマ蓄積
- `private boolean leftPressed/rightPressed/spaceHeld` 入力状態

#### メソッド（主要）
- `public GamePanel()`
  - 初期化（サイズ、背景、地面・侵攻ライン、スコア/レベル/残機、ステージ生成）
  - `timer = new Timer(TIMER_MS, this); timer.start();`
  - `KeyListener` を登録して入力状態を更新する
- `private void spawnStage()`
  - `player` 生成、`swarm` 生成、バリア4基生成、弾リスト初期化、タイマ初期化
- `private void restartFromBeginning()`
  - スコア・レベル・残機・状態を初期化し `spawnStage()` を呼ぶ
- `private int enemyShootIntervalMs() -> int`
  - `max(380, ENEMY_SHOOT_INTERVAL_BASE_MS - level * 140)`
- `private void tryPlayerShoot(int dtMs)`
  - クールダウン更新後、スペースが押されていてクールダウン0なら弾生成
  - 自機弾の `dy = -11.0`
- `private void enemyTryShoot(int dtMs)`
  - 射撃間隔を満たしたら、`swarm.pickRandomShooter()` から射撃者を選び敵弾生成
  - 敵弾の `dy = 4.0 + level * 1.2`
- `private void updateBullets()`
  - 全弾の移動と画面外消滅、`alive == false` をリストから除去
- `private void collidePlayerBullets()`
  - 自機弾×バリア: 当たれば弾消滅
  - 自機弾×敵: 当たれば敵を撃破し弾消滅、スコア加算
- `private void collideEnemyBullets()`
  - 敵弾×バリア: 当たれば弾消滅
  - 敵弾×自機: 当たれば `onPlayerHit()`
- `private void onPlayerHit()`
  - `lives--`、自機位置リセット、敵弾クリア、残機0で `state = game_over`
- `private void checkStageProgress()`
  - 敵全滅ならレベル進行、最終（レベル3）クリアで `state = game_clear`
- `private void checkInvasion()`
  - `swarm.invasionLineReached(invasionY)` で侵攻判定、到達で `state = game_over`
- `private void updatePlaying(int dtMs)`
  - プレイ中1フレーム更新をまとめて実施（入力→移動→射撃→弾更新→衝突→侵攻→レベル進行）
  - 被弾で状態が変わった場合は同フレームの後続判定を抑制する
- `public void actionPerformed(ActionEvent e)`
  - `state == playing` の場合のみ `updatePlaying(TIMER_MS)` を実行し `repaint()`
- `protected void paintComponent(Graphics g)`
  - 背景、敵、バリア、自機、弾、HUD、必要ならオーバーレイを描画
- `private void drawHud(Graphics2D g2)`
  - 画面上部に SCORE / LIVES / LEVEL を表示
- `private void drawOverlay(Graphics2D g2, String title, String subtitle)`
  - 半透明ベール上にタイトル・サブタイトルを中央表示

### `Player`（`Player.java`）

#### 定数
- `WIDTH: int = 48`
- `HEIGHT: int = 28`
- `BASE_SPEED: int = 6`

#### フィールド
- `private final int screenWidth`
- `private final Rectangle rect`

#### メソッド
- `public Player(int screenWidth, int groundY)`
  - 自機を画面中央の地面ラインに配置する
- `public Rectangle getBounds() -> Rectangle`
  - 当たり判定用矩形を返す
- `public void resetPosition()`
  - X座標を画面中央に戻す（Yは維持）
- `public void update(boolean leftPressed, boolean rightPressed)`
  - 左右移動と画面内クランプ
- `public void draw(Graphics2D g2)`
  - 自機を簡易キャノン形状で描画

### `Enemy`（`Enemy.java`）

#### 定数
- `WIDTH: int = 36`
- `HEIGHT: int = 28`
- `SCORE_BY_ROW: int[] = {30, 25, 20, 15, 10}`

#### フィールド
- `private final int rowIndex`
- `private final int colIndex`
- `private final Rectangle rect`
- `private boolean alive`

#### メソッド
- `public Enemy(int x, int y, int rowIndex, int colIndex)`
- `public int getRowIndex() -> int`
- `public int getColIndex() -> int`
- `public Rectangle getBounds() -> Rectangle`
- `public boolean isAlive() -> boolean`
- `public void setAlive(boolean alive)`
- `public int getScoreValue() -> int`
  - 行インデックスに応じた得点を返す
- `public void draw(Graphics2D g2)`
  - `alive` のみ描画する

### `EnemySwarm`（`EnemySwarm.java`）

#### 定数
- `COLS: int = 10`
- `ROWS: int = 5`
- `GAP_X: int = 12`
- `GAP_Y: int = 10`
- `MARGIN_SIDE: int = 40`

#### フィールド
- `private final int screenWidth`
- `private final int topY`
- `private final double moveSpeed`（`1.2 + level * 0.85`）
- `private final int descentStep`（`12 + level * 3`）
- `private int direction`（1 or -1）
- `private final List<Enemy> enemies`
- `private final Random random`

#### メソッド
- `public EnemySwarm(int screenWidth, int level, int topY)`
- `private void buildFormation()`
  - 5×10の敵を中央寄せで配置する
- `public List<Enemy> getEnemies() -> List<Enemy>`
  - 変更不可ビューを返す
- `public int aliveCount() -> int`
- `public void update()`
  - 左右移動、端で反転して全体を `descentStep` 下降させる
- `public Enemy pickRandomShooter() -> Enemy | null`
  - 各列の最下段生存敵を候補とし、ランダムに1体返す（なければnull）
- `public boolean invasionLineReached(int invasionY) -> boolean`
  - いずれかの敵下端が侵攻ライン以上ならtrue
- `public void draw(Graphics2D g2)`

### `Bullet`（`Bullet.java`）

#### 定数
- `WIDTH: int = 4`
- `HEIGHT: int = 12`

#### フィールド
- `private final boolean fromPlayer`
- `private final double dy`
- `private boolean alive`
- `private final Rectangle rect`

#### メソッド
- `public Bullet(double centerX, double topY, double dy, boolean fromPlayer)`
- `public Rectangle getBounds() -> Rectangle`
- `public boolean isFromPlayer() -> boolean`
- `public boolean isAlive() -> boolean`
- `public void setAlive(boolean alive)`
- `public void update()`
  - `rect.y += (int) dy`
- `public void cullIfOffScreen(int screenHeight)`
  - 画面外なら `alive=false`
- `public void draw(Graphics2D g2)`
  - 自機弾は緑、敵弾は赤で描画

### `Barrier`（`Barrier.java`）

#### フィールド
- `private final List<Rectangle> blocks`
  - バリアを構成するセル矩形のリスト

#### メソッド
- `public Barrier(int centerX, int topY)`
  - 11×5セル相当を生成し、最下段中央をくり抜く
- `public boolean collideBullet(Rectangle bulletRect) -> boolean`
  - どれかのセルと衝突したらセルを1つ削除しtrue
- `public void draw(Graphics2D g2)`
  - 残っているセルを描画

## ゲームロジック仕様

### 初期化処理

- `Main.main()` が `JFrame` を生成して `GamePanel` を表示する。
- `GamePanel()` で以下を初期化する。
  - 画面サイズ、背景色
  - `groundY = HEIGHT - 48`
  - `invasionY = groundY - Player.HEIGHT - 8`
  - `score = 0`, `level = 0`, `lives = INITIAL_LIVES`
  - `spawnStage()` で自機・敵群・バリアを生成
  - `Timer` を生成し `start()` する
  - `KeyListener` 登録

### ゲームループ（javax.swing.Timer）

- `Timer(TIMER_MS, this)` により `actionPerformed()` が周期的に呼ばれる。
- `state == playing` の場合のみ `updatePlaying(TIMER_MS)` を実行する。
- 毎フレーム `repaint()` を呼び、`paintComponent()` で描画する。

### キー入力処理

- `KeyAdapter` で押下／解放を処理し、押されている状態を boolean フィールドに保持する。
- 押下時:
  - ←→: `leftPressed/rightPressed` 更新
  - SPACE: `spaceHeld` 更新（押しっぱなしで連射、ただしクールダウンあり）
  - R: `game_over` / `game_clear` のとき `restartFromBeginning()`
  - ESC: `System.exit(0)`

### 衝突判定ロジック

- 自機弾:
  - バリアに当たると弾消滅（バリアはセル1つ削除）
  - 敵に当たると敵撃破・弾消滅・スコア加算
- 敵弾:
  - バリアに当たると弾消滅（バリアはセル1つ削除）
  - 自機に当たると `onPlayerHit()`（残機減少、敵弾クリア、必要ならゲームオーバー）

### レベル管理ロジック

- `checkStageProgress()` により、敵全滅でレベルを進める。
- `level >= MAX_LEVEL` で全滅した場合は `state = game_clear`。
- それ以外は `level++` して `spawnStage()` を呼ぶ。

### 勝敗判定ロジック

- 敗北:
  - `swarm.invasionLineReached(invasionY)` が true（侵攻ライン到達）
  - `lives <= 0`（被弾で残機0）
- 勝利:
  - `swarm.aliveCount() == 0`（敵全滅）かつ `level >= MAX_LEVEL`（レベル3）→ `game_clear`

## 定数一覧（画面サイズ・速度・色等）

### 画面・タイマー
- `GamePanel.WIDTH = 800`
- `GamePanel.HEIGHT = 600`
- `GamePanel.TIMER_MS = 16`

### 自機
- `Player.WIDTH = 48`
- `Player.HEIGHT = 28`
- `Player.BASE_SPEED = 6`
- 自機弾速度: `dy = -11.0`
- 自機射撃クールダウン: `PLAYER_SHOOT_COOLDOWN_MS = 300`

### 敵
- 敵サイズ: `Enemy.WIDTH = 36`, `Enemy.HEIGHT = 28`
- 形成: `EnemySwarm.ROWS = 5`, `EnemySwarm.COLS = 10`
- 移動:
  - `moveSpeed = 1.2 + level * 0.85`
  - `descentStep = 12 + level * 3`
- 敵弾:
  - 速度: `dy = 4.0 + level * 1.2`
  - 射撃間隔: `max(380, 950 - level * 140)`

### バリア
- 基数: 4
- 形状: 11×5セル相当（最下段中央をくり抜き）

### 色（RGB）
- 背景: `(12, 14, 28)`
- HUD文字: `(230, 230, 230)`
- 自機: `(180, 220, 255)` など
- 自機弾: `(120, 255, 120)`
- 敵弾: `(255, 80, 80)`
- バリア: `(90, 220, 120)`

## 状態遷移図（テキスト形式）

```
[playing]
  | 敵全滅 && level < 3  -> level++ / spawnStage()
  | 敵全滅 && level == 3 -> [game_clear]
  | 敵が侵攻ライン到達   -> [game_over]
  | 自機被弾で lives==0  -> [game_over]
  v
[game_over]
  | R -> restartFromBeginning() -> [playing]
  | ESC -> 終了

[game_clear]
  | R -> restartFromBeginning() -> [playing]
  | ESC -> 終了
```

## 既知の制約事項

- `Timer` 周期は固定（`TIMER_MS=16`）で、厳密な dt 計測は行っていない（環境によって体感速度が多少変わり得る）。
- 自機弾は「押しっぱなし」で連射できるが、クールダウンにより発射頻度が制限される。
- 敵の移動量は `double` から `int` に丸めているため、環境によっては微小な差が出る可能性がある。
- 画像アセットはスクリーンショットのみで、スプライトは図形描画（矩形）で表現している。

