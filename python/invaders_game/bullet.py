"""プレイヤー弾・敵弾の描画と移動を担当するモジュール。"""

import pygame


class Bullet:
    """画面上を垂直方向に移動する弾。"""

    WIDTH = 4
    HEIGHT = 12

    def __init__(
        self,
        center_x: float,
        top_y: float,
        dy: float,
        from_player: bool = True,
    ) -> None:
        """
        Args:
            center_x: 弾の中心X座標
            top_y: 弾の上端Y座標
            dy: 1フレームあたりのY移動量（プレイヤー弾は負の値）
            from_player: Trueならプレイヤー弾、Falseなら敵弾
        """
        self.from_player = from_player
        self.dy = dy
        self.alive = True
        self.rect = pygame.Rect(0, 0, self.WIDTH, self.HEIGHT)
        self.rect.centerx = int(center_x)
        self.rect.top = int(top_y)

    def update(self) -> None:
        """位置を更新する。"""
        self.rect.y += int(self.dy)

    def draw(self, surface: pygame.Surface) -> None:
        """弾を描画する。"""
        if self.from_player:
            color = (120, 255, 120)
        else:
            color = (255, 80, 80)
        pygame.draw.rect(surface, color, self.rect)

    def cull_if_off_screen(self, screen_height: int) -> None:
        """画面外に出たら消滅扱いにする。"""
        if self.rect.bottom < 0 or self.rect.top > screen_height:
            self.alive = False
