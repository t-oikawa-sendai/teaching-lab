"""シールド（バリア）の段階的破壊を担当するモジュール。"""

from typing import List

import pygame


class Barrier:
    """
    バリア1基。小さな矩形セルの集合として保持し、
    弾が当たったセルから順に欠けていく（段階的破壊）。
    """

    def __init__(self, center_x: int, top_y: int) -> None:
        """
        Args:
            center_x: バリア全体の中心X
            top_y: バリア上端Y
        """
        self.blocks: List[pygame.Rect] = []
        cols, rows = 11, 5
        cell_w, cell_h = 7, 6
        total_w = cols * cell_w
        left = center_x - total_w // 2
        # 下部中央をくり抜いたクラシックに近い形状
        for r in range(rows):
            for c in range(cols):
                if r == rows - 1 and 2 <= c <= cols - 3:
                    continue
                rect = pygame.Rect(left + c * cell_w, top_y + r * cell_h, cell_w - 1, cell_h - 1)
                self.blocks.append(rect)

    def collide_bullet(self, bullet_rect: pygame.Rect) -> bool:
        """
        弾との衝突を検査する。当たったセルを1つ削除しTrueを返す。
        """
        for i, block in enumerate(self.blocks):
            if block.colliderect(bullet_rect):
                del self.blocks[i]
                return True
        return False

    def draw(self, surface: pygame.Surface) -> None:
        """残っているセルを描画する。"""
        for block in self.blocks:
            pygame.draw.rect(surface, (90, 220, 120), block)
            pygame.draw.rect(surface, (20, 80, 30), block, width=1)
