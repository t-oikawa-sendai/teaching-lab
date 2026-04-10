"""ゲーム全体の状態管理・衝突判定・メインループを担当するモジュール。"""

from typing import List

import pygame

from barrier import Barrier
from bullet import Bullet
from enemy import EnemySwarm
from player import Player


class Game:
    """ウィンドウ・入力・更新・描画を束ねるゲーム本体。"""

    WIDTH = 800
    HEIGHT = 600
    FPS = 60
    # レベル0〜3の4段階（最後のステージをクリアするとゲームクリア）
    MAX_LEVEL = 3
    INITIAL_LIVES = 3
    PLAYER_SHOOT_COOLDOWN_MS = 300
    ENEMY_SHOOT_INTERVAL_BASE_MS = 950

    def __init__(self) -> None:
        pygame.init()
        pygame.display.set_caption("スペースインベーダー風")
        self.screen = pygame.display.set_mode((self.WIDTH, self.HEIGHT))
        self.clock = pygame.time.Clock()
        self.font = pygame.font.Font(None, 28)
        self.font_large = pygame.font.Font(None, 56)
        self.font_hint = pygame.font.Font(None, 22)

        self.ground_y = self.HEIGHT - 48
        # 敵の下端がこのY以上なら自機ライン到達とみなす
        self.invasion_y = self.ground_y - Player.HEIGHT - 8

        self.score = 0
        self.level = 0
        self.lives = self.INITIAL_LIVES
        # playing / game_over / game_clear
        self.state = "playing"

        self.player: Player
        self.swarm: EnemySwarm
        self.barriers: List[Barrier]
        self.player_bullets: List[Bullet] = []
        self.enemy_bullets: List[Bullet] = []

        self._shoot_cooldown_ms = 0
        self._enemy_shoot_accum_ms = 0

        self._spawn_stage()

    def _spawn_stage(self) -> None:
        """現在のレベルに応じてステージ（敵・バリア・自機位置）を生成する。"""
        self.player = Player(self.WIDTH, self.ground_y)
        self.swarm = EnemySwarm(self.WIDTH, self.level)
        barrier_top = self.ground_y - 110
        # 画面幅に沿って4基を等間隔配置
        xs = [int(self.WIDTH * (2 * i + 1) / 10) for i in range(4)]
        self.barriers = [Barrier(cx, barrier_top) for cx in xs]
        self.player_bullets.clear()
        self.enemy_bullets.clear()
        self._shoot_cooldown_ms = 0
        self._enemy_shoot_accum_ms = 0

    def _restart_from_beginning(self) -> None:
        """スコア・レベル・残機を初期化して最初からやり直す。"""
        self.score = 0
        self.level = 0
        self.lives = self.INITIAL_LIVES
        self.state = "playing"
        self._spawn_stage()

    def _enemy_shoot_interval_ms(self) -> int:
        """レベルが上がるほど敵の射撃間隔が短くなる。"""
        return max(380, self.ENEMY_SHOOT_INTERVAL_BASE_MS - self.level * 140)

    def _try_player_shoot(self, keys: pygame.key.ScancodeWrapper, dt_ms: int) -> None:
        """スペースキーでクールダウン付き射撃。"""
        self._shoot_cooldown_ms = max(0, self._shoot_cooldown_ms - dt_ms)
        if not keys[pygame.K_SPACE] or self._shoot_cooldown_ms > 0:
            return
        speed = -11
        bullet = Bullet(
            float(self.player.rect.centerx),
            float(self.player.rect.top),
            speed,
            from_player=True,
        )
        self.player_bullets.append(bullet)
        self._shoot_cooldown_ms = self.PLAYER_SHOOT_COOLDOWN_MS

    def _enemy_try_shoot(self, dt_ms: int) -> None:
        """ランダムな最前列の敵から下向き弾を発射。"""
        self._enemy_shoot_accum_ms += dt_ms
        if self._enemy_shoot_accum_ms < self._enemy_shoot_interval_ms():
            return
        self._enemy_shoot_accum_ms = 0
        shooter = self.swarm.pick_random_shooter()
        if shooter is None:
            return
        vy = 4.0 + self.level * 1.2
        b = Bullet(
            float(shooter.rect.centerx),
            float(shooter.rect.bottom),
            vy,
            from_player=False,
        )
        self.enemy_bullets.append(b)

    def _update_bullets(self) -> None:
        """弾の移動と画面外削除。"""
        for b in self.player_bullets:
            b.update()
            b.cull_if_off_screen(self.HEIGHT)
        for b in self.enemy_bullets:
            b.update()
            b.cull_if_off_screen(self.HEIGHT)
        self.player_bullets = [b for b in self.player_bullets if b.alive]
        self.enemy_bullets = [b for b in self.enemy_bullets if b.alive]

    def _collide_player_bullets(self) -> None:
        """自機弾とバリア・敵の衝突処理。"""
        for bullet in self.player_bullets:
            if not bullet.alive:
                continue
            for barrier in self.barriers:
                if barrier.collide_bullet(bullet.rect):
                    bullet.alive = False
                    break
            if not bullet.alive:
                continue
            for enemy in self.swarm.enemies:
                if enemy.alive and enemy.rect.colliderect(bullet.rect):
                    enemy.alive = False
                    bullet.alive = False
                    self.score += enemy.score_value
                    break

    def _collide_enemy_bullets(self) -> None:
        """敵弾とバリア・自機の衝突処理。"""
        for bullet in self.enemy_bullets:
            if not bullet.alive:
                continue
            for barrier in self.barriers:
                if barrier.collide_bullet(bullet.rect):
                    bullet.alive = False
                    break
            if not bullet.alive:
                continue
            if bullet.rect.colliderect(self.player.rect):
                bullet.alive = False
                self._on_player_hit()

    def _on_player_hit(self) -> None:
        """自機被弾：残機を減らし、弾をクリア。0ならゲームオーバー。"""
        self.lives -= 1
        self.player.reset_position()
        self.enemy_bullets.clear()
        if self.lives <= 0:
            self.state = "game_over"

    def _check_stage_progress(self) -> None:
        """全滅時に次レベルへ進むか、最終クリアかを判定。"""
        if self.swarm.alive_count() > 0:
            return
        if self.level >= self.MAX_LEVEL:
            self.state = "game_clear"
            return
        self.level += 1
        self._spawn_stage()

    def _check_invasion(self) -> None:
        """敵が自機ラインに達したらゲームオーバー。"""
        if self.swarm.invasion_line_reached(self.invasion_y):
            self.state = "game_over"

    def _update_playing(self, keys: pygame.key.ScancodeWrapper, dt_ms: int) -> None:
        """1フレーム分のプレイ中ロジック。"""
        self.player.update(keys)
        self._try_player_shoot(keys, dt_ms)
        self.swarm.update()
        self._enemy_try_shoot(dt_ms)
        self._update_bullets()
        self._collide_player_bullets()
        self._collide_enemy_bullets()
        # 被弾でゲームオーバーになった場合は侵攻判定・ステージ進行を行わない
        if self.state != "playing":
            return
        self._check_invasion()
        if self.state != "playing":
            return
        self._check_stage_progress()

    def _draw_hud(self) -> None:
        """スコア・残機・レベルを画面上部に表示する。"""
        margin = 8
        score_surf = self.font.render(f"SCORE {self.score}", True, (230, 230, 230))
        lives_surf = self.font.render(f"LIVES {self.lives}", True, (230, 230, 230))
        level_surf = self.font.render(f"LEVEL {self.level}", True, (230, 230, 230))

        self.screen.blit(score_surf, (margin, margin))
        self.screen.blit(lives_surf, (margin + 220, margin))
        self.screen.blit(level_surf, (margin + 400, margin))

    def _draw_overlay(self, title: str, subtitle: str) -> None:
        """ゲームオーバー／クリア時の半透明オーバーレイ。"""
        veil = pygame.Surface((self.WIDTH, self.HEIGHT), pygame.SRCALPHA)
        veil.fill((0, 0, 0, 170))
        self.screen.blit(veil, (0, 0))

        title_surf = self.font_large.render(title, True, (255, 255, 200))
        sub_surf = self.font_hint.render(subtitle, True, (220, 220, 220))

        tr = title_surf.get_rect(center=(self.WIDTH // 2, self.HEIGHT // 2 - 30))
        sr = sub_surf.get_rect(center=(self.WIDTH // 2, self.HEIGHT // 2 + 40))
        self.screen.blit(title_surf, tr)
        self.screen.blit(sub_surf, sr)

    def _draw(self) -> None:
        """全スプライトとUIを描画する。"""
        self.screen.fill((12, 14, 28))
        self.swarm.draw(self.screen)
        for barrier in self.barriers:
            barrier.draw(self.screen)
        self.player.draw(self.screen)
        for b in self.player_bullets:
            b.draw(self.screen)
        for b in self.enemy_bullets:
            b.draw(self.screen)
        self._draw_hud()

        if self.state == "game_over":
            self._draw_overlay("GAME OVER", "R: 最初から再開 / ESC: 終了")
        elif self.state == "game_clear":
            self._draw_overlay("GAME CLEAR!", "R: 最初から再開 / ESC: 終了")

        pygame.display.flip()

    def run(self) -> None:
        """メインループ：イベント処理・更新・描画。"""
        running = True
        while running:
            dt_ms = self.clock.tick(self.FPS)

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    running = False
                elif event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_ESCAPE:
                        running = False
                    elif event.key == pygame.K_r and self.state in ("game_over", "game_clear"):
                        self._restart_from_beginning()

            keys = pygame.key.get_pressed()
            if self.state == "playing":
                self._update_playing(keys, dt_ms)

            self._draw()

        pygame.quit()
