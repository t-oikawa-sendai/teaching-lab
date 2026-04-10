"""敵単体と敵群（フォーメーション移動）を担当するモジュール。"""

from __future__ import annotations

import random
from typing import List, Optional

import pygame


class Enemy:
    """1体の敵。行番号に応じた得点を持つ。"""

    WIDTH = 36
    HEIGHT = 28

    # 上の行ほど高得点（クラシックに近い配分）
    SCORE_BY_ROW = (30, 25, 20, 15, 10)

    def __init__(self, x: int, y: int, row_index: int, col_index: int) -> None:
        self.row_index = row_index
        self.col_index = col_index
        self.rect = pygame.Rect(x, y, self.WIDTH, self.HEIGHT)
        self.alive = True

    @property
    def score_value(self) -> int:
        """撃破時のスコア。"""
        idx = min(self.row_index, len(self.SCORE_BY_ROW) - 1)
        return self.SCORE_BY_ROW[idx]

    def draw(self, surface: pygame.Surface) -> None:
        """敵を描画する。"""
        if not self.alive:
            return
        hue = 200 + self.row_index * 10
        color = (hue, 120, 220)
        pygame.draw.rect(surface, color, self.rect, border_radius=4)
        pygame.draw.rect(surface, (40, 20, 60), self.rect, width=1, border_radius=4)


class EnemySwarm:
    """複数行×列の敵をまとめて左右移動・下降させる。"""

    COLS = 10
    ROWS = 5
    GAP_X = 12
    GAP_Y = 10
    MARGIN_SIDE = 40

    def __init__(
        self,
        screen_width: int,
        level: int,
        top_y: int = 72,
    ) -> None:
        """
        Args:
            screen_width: 画面幅（端での折り返し判定に使用）
            level: 0〜3。大きいほど移動が速い
            top_y: フォーメーション上端
        """
        self.screen_width = screen_width
        self.level = level
        self.top_y = top_y
        # レベルが上がるほど速く、下降量もやや増える
        self.move_speed = 1.2 + level * 0.85
        self.descent_step = 12 + level * 3
        self.direction = 1  # 1: 右, -1: 左
        self.enemies: List[Enemy] = []
        self._build_formation()

    def _build_formation(self) -> None:
        """敵を格子状に配置する。"""
        self.enemies.clear()
        total_w = self.COLS * Enemy.WIDTH + (self.COLS - 1) * self.GAP_X
        start_x = (self.screen_width - total_w) // 2
        for row in range(self.ROWS):
            for col in range(self.COLS):
                x = start_x + col * (Enemy.WIDTH + self.GAP_X)
                y = self.top_y + row * (Enemy.HEIGHT + self.GAP_Y)
                self.enemies.append(Enemy(x, y, row, col))

    def alive_count(self) -> int:
        """生存敵の数。"""
        return sum(1 for e in self.enemies if e.alive)

    def update(self) -> None:
        """全体を横移動し、端に触れたら反転して一段下降する。"""
        alive = [e for e in self.enemies if e.alive]
        if not alive:
            return

        dx = self.move_speed * self.direction
        for e in alive:
            e.rect.x += int(dx)

        hit_edge = False
        for e in alive:
            if e.rect.left <= self.MARGIN_SIDE and self.direction < 0:
                hit_edge = True
                break
            if e.rect.right >= self.screen_width - self.MARGIN_SIDE and self.direction > 0:
                hit_edge = True
                break

        if hit_edge:
            self.direction *= -1
            for e in alive:
                e.rect.y += int(self.descent_step)

    def pick_random_shooter(self) -> Optional[Enemy]:
        """弾を発射できる敵を1体ランダムに選ぶ（各列で最も下の生存敵を候補にする）。"""
        columns: dict[int, Enemy] = {}
        for e in self.enemies:
            if not e.alive:
                continue
            prev = columns.get(e.col_index)
            if prev is None or e.rect.bottom > prev.rect.bottom:
                columns[e.col_index] = e
        if not columns:
            return None
        return random.choice(list(columns.values()))

    def invasion_line_reached(self, invasion_y: int) -> bool:
        """いずれかの敵が自機ライン（指定Y）に到達したか。"""
        for e in self.enemies:
            if e.alive and e.rect.bottom >= invasion_y:
                return True
        return False

    def draw(self, surface: pygame.Surface) -> None:
        """生存している敵をすべて描画する。"""
        for e in self.enemies:
            e.draw(surface)
