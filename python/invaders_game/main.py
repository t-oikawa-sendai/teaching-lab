"""エントリーポイント。ゲームの起動のみを行う。"""

from game import Game


def main() -> None:
    Game().run()


if __name__ == "__main__":
    main()
