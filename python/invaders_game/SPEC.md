# プログラム仕様書

## システム概要
本システムは pygame のみを用いて実装した、スペースインベーダー風の2Dシューティングゲームである。
プレイヤー（自機）は左右移動と弾発射を行い、敵群（フォーメーション）を全滅させることでレベルを進行する。
レベルは 0〜3 の全4段階で、レベルが上がるほど敵の移動速度・下降量・敵弾の速度・射撃頻度が増加する。

ゲーム状態は `playing` / `game_over` / `game_clear` の3状態で管理される。

## ファイル構成と各モジュールの責務
- `main.py`
  - エントリーポイントのみ。`Game().run()` を呼び出してゲームを開始する。
- `game.py`
  - `Game` クラスを定義し、初期化、メインループ、イベント処理、更新、描画、HUD、勝敗判定、レベル進行を管理する。
- `player.py`
  - `Player` クラスを定義し、自機の移動・位置制限・描画を担当する。
- `enemy.py`
  - `Enemy`（敵単体）と `EnemySwarm`（敵群）を定義し、フォーメーションの移動・下降・侵攻判定・敵弾発射者の選択・描画を担当する。
- `bullet.py`
  - `Bullet` クラスを定義し、プレイヤー弾／敵弾の移動・描画・画面外削除を担当する。
- `barrier.py`
  - `Barrier` クラスを定義し、複数セルからなるバリアの生成・段階的破壊・描画を担当する。

## クラス仕様（全クラスのプロパティ・メソッド・引数・戻り値）

### `Game`（`game.py`）
ゲーム全体の状態・ループを管理するクラス。

#### クラス定数
- `WIDTH: int = 800` 画面幅
- `HEIGHT: int = 600` 画面高さ
- `FPS: int = 60` フレームレート
- `MAX_LEVEL: int = 3` 最大レベル（0〜3）
- `INITIAL_LIVES: int = 3` 初期残機
- `PLAYER_SHOOT_COOLDOWN_MS: int = 300` 自機射撃クールダウン（ms）
- `ENEMY_SHOOT_INTERVAL_BASE_MS: int = 950` 敵射撃の基準間隔（ms）

#### インスタンスプロパティ（主要）
- `screen: pygame.Surface` 描画先
- `clock: pygame.time.Clock` フレーム制御
- `font: pygame.font.Font` HUD用フォント（サイズ28）
- `font_large: pygame.font.Font` オーバーレイ用フォント（サイズ56）
- `font_hint: pygame.font.Font` ヒント用フォント（サイズ22）
- `ground_y: int` 自機の接地Y（`HEIGHT - 48`）
- `invasion_y: int` 侵攻ラインY（`ground_y - Player.HEIGHT - 8`）
- `score: int` スコア
- `level: int` 現在レベル（0〜3）
- `lives: int` 残機
- `state: str` `"playing" | "game_over" | "game_clear"`
- `player: Player` 自機
- `swarm: EnemySwarm` 敵群
- `barriers: list[Barrier]` バリア群（4基）
- `player_bullets: list[Bullet]` 自機弾
- `enemy_bullets: list[Bullet]` 敵弾
- `_shoot_cooldown_ms: int` 自機射撃クールダウン残量
- `_enemy_shoot_accum_ms: int` 敵射撃タイマ蓄積

#### メソッド仕様
- `__init__(self) -> None`
  - pygame初期化、画面生成、各種値初期化後に `_spawn_stage()` を呼ぶ。
- `_spawn_stage(self) -> None`
  - 現在レベルのステージを生成する。
  - 自機生成、敵群生成、バリア4基生成、弾リスト初期化、タイマ初期化を行う。
- `_restart_from_beginning(self) -> None`
  - スコア・レベル・残機を初期化し、`state="playing"` で `_spawn_stage()` を呼ぶ。
- `_enemy_shoot_interval_ms(self) -> int`
  - 敵射撃間隔（ms）を返す。
  - `max(380, ENEMY_SHOOT_INTERVAL_BASE_MS - level * 140)`。
- `_try_player_shoot(self, keys: pygame.key.ScancodeWrapper, dt_ms: int) -> None`
  - スペースキー押下かつクールダウン0なら自機弾を生成し、クールダウンを再設定する。
  - 自機弾の `dy=-11`（上向き）。
- `_enemy_try_shoot(self, dt_ms: int) -> None`
  - タイマが射撃間隔を超えたら、敵群から発射者を選び敵弾を生成する。
  - 敵弾の `dy = 4.0 + level * 1.2`（下向き）。
- `_update_bullets(self) -> None`
  - 全弾の移動（`Bullet.update()`）と画面外削除（`Bullet.cull_if_off_screen()`）を行い、`alive` がFalseの弾をリストから除去する。
- `_collide_player_bullets(self) -> None`
  - 自機弾とバリアの衝突：当たればバリアセルを1つ削除し弾を消す。
  - 自機弾と敵の衝突：当たれば敵を撃破し弾を消し、`score += enemy.score_value`。
- `_collide_enemy_bullets(self) -> None`
  - 敵弾とバリアの衝突：当たればバリアセルを1つ削除し弾を消す。
  - 敵弾と自機の衝突：当たれば `_on_player_hit()` を呼ぶ。
- `_on_player_hit(self) -> None`
  - 残機を減らし、自機を中央へ戻し、敵弾を全消去する。
  - 残機が0以下なら `state="game_over"` にする。
- `_check_stage_progress(self) -> None`
  - 敵全滅ならレベル進行。
  - `level >= MAX_LEVEL` の場合は `state="game_clear"`。
  - それ以外は `level += 1` して `_spawn_stage()`。
- `_check_invasion(self) -> None`
  - `EnemySwarm.invasion_line_reached(invasion_y)` がTrueなら `state="game_over"`。
- `_update_playing(self, keys: pygame.key.ScancodeWrapper, dt_ms: int) -> None`
  - プレイ中の1フレーム更新をまとめて行う。
  - 被弾で `state!="playing"` になった場合、侵攻判定とステージ進行を行わずに終了する（同フレームの優先順位制御）。
- `_draw_hud(self) -> None`
  - 画面上部に `SCORE` / `LIVES` / `LEVEL` を描画する。
- `_draw_overlay(self, title: str, subtitle: str) -> None`
  - 半透明ベール上にタイトル・サブタイトルを描画する。
- `_draw(self) -> None`
  - 背景、敵、バリア、自機、弾、HUD、必要ならオーバーレイを描画し、`pygame.display.flip()`。
- `run(self) -> None`
  - メインループ。
  - `QUIT` で終了、`ESC` で終了、`R` で（ゲームオーバー／クリア時に）最初から再開。
  - `state=="playing"` の場合のみ `_update_playing()` を呼ぶ。

### `Player`（`player.py`）
自機の移動と描画を行う。

#### クラス定数
- `WIDTH: int = 48`
- `HEIGHT: int = 28`
- `BASE_SPEED: int = 6` 1フレームの移動量

#### インスタンスプロパティ
- `screen_width: int` 画面幅（移動制限に使用）
- `rect: pygame.Rect` 当たり判定・描画位置

#### メソッド仕様
- `__init__(self, screen_width: int, ground_y: int) -> None`
  - 自機を画面中央の地面ラインに配置する。
- `reset_position(self) -> None`
  - 自機を画面中央に戻す（Yは維持）。
- `update(self, keys: pygame.key.ScancodeWrapper) -> None`
  - ←→入力で左右移動し、画面外へ出ないように制限する。
- `draw(self, surface: pygame.Surface) -> None`
  - 自機を簡易形状（矩形＋砲身）で描画する。

### `Enemy`（`enemy.py`）
敵1体の当たり判定・描画・得点を持つ。

#### クラス定数
- `WIDTH: int = 36`
- `HEIGHT: int = 28`
- `SCORE_BY_ROW: tuple[int, ...] = (30, 25, 20, 15, 10)`

#### インスタンスプロパティ
- `row_index: int` 行番号（0が最上段）
- `col_index: int` 列番号
- `rect: pygame.Rect` 当たり判定・描画位置
- `alive: bool` 生存フラグ

#### メソッド仕様
- `__init__(self, x: int, y: int, row_index: int, col_index: int) -> None`
  - 指定位置に敵を生成する。
- `score_value(self) -> int`（`@property`）
  - 撃破時の得点を返す。`row_index` に応じて `SCORE_BY_ROW` から決定する。
- `draw(self, surface: pygame.Surface) -> None`
  - `alive` の場合のみ矩形で描画する。

### `EnemySwarm`（`enemy.py`）
敵のフォーメーション管理（移動・下降・侵攻判定・発射者選択・描画）を行う。

#### クラス定数
- `COLS: int = 10`
- `ROWS: int = 5`
- `GAP_X: int = 12`
- `GAP_Y: int = 10`
- `MARGIN_SIDE: int = 40` 端の折り返しマージン

#### インスタンスプロパティ（主要）
- `screen_width: int`
- `level: int`
- `top_y: int`
- `move_speed: float`（`1.2 + level * 0.85`）
- `descent_step: int`（`12 + level * 3`）
- `direction: int`（`1` 右、`-1` 左）
- `enemies: list[Enemy]`

#### メソッド仕様
- `__init__(self, screen_width: int, level: int, top_y: int = 72) -> None`
  - レベルに応じた速度・下降量を設定し、フォーメーションを生成する。
- `_build_formation(self) -> None`
  - `ROWS × COLS` の格子状に敵を配置する。
- `alive_count(self) -> int`
  - 生存している敵数を返す。
- `update(self) -> None`
  - 生存敵を横移動し、端に触れたら `direction` を反転し `descent_step` だけ下降する。
- `pick_random_shooter(self) -> Enemy | None`
  - 各列で最も下にいる生存敵を候補にし、その中からランダムに1体返す。候補がなければ `None`。
- `invasion_line_reached(self, invasion_y: int) -> bool`
  - いずれかの生存敵の `rect.bottom >= invasion_y` なら True を返す。
- `draw(self, surface: pygame.Surface) -> None`
  - 全敵の `draw()` を呼ぶ。

### `Bullet`（`bullet.py`）
弾（自機弾・敵弾）の移動・描画・生存管理を行う。

#### クラス定数
- `WIDTH: int = 4`
- `HEIGHT: int = 12`

#### インスタンスプロパティ
- `from_player: bool` Trueなら自機弾、Falseなら敵弾
- `dy: float` 1フレームのY移動量
- `alive: bool` 生存フラグ
- `rect: pygame.Rect` 当たり判定・描画位置

#### メソッド仕様
- `__init__(self, center_x: float, top_y: float, dy: float, from_player: bool = True) -> None`
  - 指定位置と速度で弾を生成する。
- `update(self) -> None`
  - `rect.y += int(dy)` で移動する。
- `draw(self, surface: pygame.Surface) -> None`
  - 自機弾は緑系、敵弾は赤系で描画する。
- `cull_if_off_screen(self, screen_height: int) -> None`
  - 画面外（上/下）に出たら `alive=False` にする。

### `Barrier`（`barrier.py`）
複数セルで構成されたバリア1基。被弾したセルを1つずつ削除することで段階的破壊を表現する。

#### インスタンスプロパティ
- `blocks: list[pygame.Rect]` バリアのセル集合

#### メソッド仕様
- `__init__(self, center_x: int, top_y: int) -> None`
  - `11 × 5` のセルを基に、下部中央をくり抜いた形状で生成する。
- `collide_bullet(self, bullet_rect: pygame.Rect) -> bool`
  - `bullet_rect` と衝突したセルがあればそのセルを1つ削除し True を返す。なければ False。
- `draw(self, surface: pygame.Surface) -> None`
  - 残っているセルを描画する。

## ゲームロジック仕様

### 初期化処理
- `Game.__init__()` で pygame 初期化、画面生成、フォント生成、スコア/レベル/残機/状態の初期化を行う。
- `ground_y = HEIGHT - 48` に自機の接地ラインを置く。
- `invasion_y = ground_y - Player.HEIGHT - 8` を侵攻ラインとする。
- `_spawn_stage()` により以下を生成/初期化する。
  - 自機 `Player(WIDTH, ground_y)`
  - 敵群 `EnemySwarm(WIDTH, level)`
  - バリア4基（`center_x` を等間隔、`top_y = ground_y - 110`）
  - 弾リストとタイマ

### メインループ
- `Game.run()` が以下を繰り返す。
  - `dt_ms = clock.tick(FPS)` によりフレーム時間（ms）を取得
  - イベント処理
    - `QUIT` または `ESC` で終了
    - `R` は `state in ("game_over", "game_clear")` のとき `_restart_from_beginning()`
  - `keys = pygame.key.get_pressed()` を取得
  - `state == "playing"` の場合のみ `_update_playing(keys, dt_ms)` を実行
  - `_draw()` で描画

### 衝突判定ロジック
衝突判定はすべて `pygame.Rect.colliderect()` により矩形で行う。

- 自機弾
  - バリアに当たればセルを1つ削除し弾を消す
  - 敵に当たれば敵を撃破し弾を消し、スコア加算
- 敵弾
  - バリアに当たればセルを1つ削除し弾を消す
  - 自機に当たれば `_on_player_hit()` を呼ぶ

### レベル管理ロジック
- 敵全滅（`EnemySwarm.alive_count() == 0`）で `_check_stage_progress()` が発火する。
- `level < MAX_LEVEL` の場合:
  - `level += 1`
  - `_spawn_stage()` により次レベルの敵群（速度増加）とバリアを再生成する。
- `level >= MAX_LEVEL` の場合:
  - `state = "game_clear"`

### 勝敗判定ロジック
- 敗北（`game_over`）は以下で決定する。
  - 敵弾が自機に衝突し残機が0以下になった場合
  - 敵が侵攻ラインに到達した場合（`EnemySwarm.invasion_line_reached(invasion_y)`）
- 勝利（`game_clear`）はレベル3で敵全滅した場合に決定する。
- 更新順序の優先度
  - 被弾で `state` が `playing` でなくなった場合は、そのフレーム中に侵攻判定・ステージ進行を行わない（`_update_playing()` 内の早期return）。

## 定数一覧（画面サイズ・速度・色等）

### 画面・フレーム
- 画面: `WIDTH=800`, `HEIGHT=600`
- FPS: `60`

### 自機
- サイズ: `Player.WIDTH=48`, `Player.HEIGHT=28`
- 移動: `Player.BASE_SPEED=6`（1フレーム）
- 射撃クールダウン: `300ms`
- 自機弾速度: `dy=-11`

### 敵
- 敵サイズ: `Enemy.WIDTH=36`, `Enemy.HEIGHT=28`
- 配置: `EnemySwarm.ROWS=5`, `EnemySwarm.COLS=10`, `GAP_X=12`, `GAP_Y=10`
- 端マージン: `MARGIN_SIDE=40`
- 移動速度: `move_speed = 1.2 + level * 0.85`
- 下降量: `descent_step = 12 + level * 3`
- 得点: `SCORE_BY_ROW = (30, 25, 20, 15, 10)`
- 敵弾速度: `dy = 4.0 + level * 1.2`
- 敵射撃間隔: `max(380, 950 - level * 140)`（ms）

### バリア
- 1基あたりセル: 11列×5行（下段中央は欠ける）

### 描画色（RGB）
- 背景: `(12, 14, 28)`
- HUD文字: `(230, 230, 230)`
- 自機: `(180, 220, 255)` / 砲身 `(140, 200, 255)`
- 自機弾: `(120, 255, 120)`
- 敵弾: `(255, 80, 80)`
- バリア: `(90, 220, 120)` / 枠 `(20, 80, 30)`
- オーバーレイ: ベール `(0, 0, 0, 170)`、タイトル `(255, 255, 200)`

## 状態遷移図（テキスト形式で可）

```
            +-------------------+
            |      起動         |
            +---------+---------+
                      |
                      v
            +-------------------+
            |    playing        |
            +----+--------+-----+
                 |        |
     敵全滅      |        | 敗北条件成立
 (level<3)       |        | (侵攻 or 被弾で残機0)
                 |        v
                 |   +------------+
                 |   | game_over  |
                 |   +-----+------+
                 |         |
                 |     Rで再開
                 |         v
                 |   +------------+
                 +-->+  playing   |
                     +------------+

    敵全滅(level==3)
                 |
                 v
           +--------------+
           | game_clear   |
           +------+-------+
                  |
              Rで再開
                  v
              playing
```

※ `ESC` はどの状態からでも終了（`running=False`）。

## 既知の制約事項
- **グラフィックは矩形ベースの簡易描画**で、スプライト画像やアニメーションはない。
- **自機弾は複数同時発射が可能**（クラシック版の「1発制限」は設けていない）。
- 敵群の横移動は `int(move_speed)` による丸めが入るため、速度が小さい場合は実質1px単位で動く。
- サウンド、BGM、ハイスコア保存などの永続化機能は実装していない。
- 敵弾はランダム発射であり、難易度はレベルと乱数に依存する（完全な固定パターンではない）。

