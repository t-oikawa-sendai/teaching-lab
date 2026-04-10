"""自機の入力処理・移動・描画を担当するモジュール。"""

import pygame


class Player:
    """左右移動する自機。"""

    WIDTH = 48
    HEIGHT = 28
    BASE_SPEED = 6

    def __init__(self, screen_width: int, ground_y: int) -> None:
        """
        Args:
            screen_width: 画面幅
            ground_y: 自機の下端が揃うY座標（地面ライン）
        """
        self.screen_width = screen_width
        self.rect = pygame.Rect(0, 0, self.WIDTH, self.HEIGHT)
        self.rect.midbottom = (screen_width // 2, ground_y)

    def reset_position(self) -> None:
        """残機減少後などに中央へ戻す。"""
        self.rect.midbottom = (self.screen_width // 2, self.rect.bottom)

    def update(self, keys: pygame.key.ScancodeWrapper) -> None:
        """←→キーで横移動。画面内にクランプする。"""
        dx = 0
        if keys[pygame.K_LEFT]:
            dx -= self.BASE_SPEED
        if keys[pygame.K_RIGHT]:
            dx += self.BASE_SPEED
        self.rect.x += dx
        if self.rect.left < 0:
            self.rect.left = 0
        if self.rect.right > self.screen_width:
            self.rect.right = self.screen_width

    def draw(self, surface: pygame.Surface) -> None:
        """自機を描画する（簡易キャノン形状）。"""
        body = self.rect.inflate(-8, -6)
        pygame.draw.rect(surface, (180, 220, 255), body, border_radius=3)
        tip_w = 10
        tip = pygame.Rect(0, 0, tip_w, 8)
        tip.midtop = (self.rect.centerx, self.rect.top)
        pygame.draw.rect(surface, (140, 200, 255), tip)
