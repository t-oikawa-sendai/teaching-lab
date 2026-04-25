import time

class ReliableStopwatch:
    """
    NTP等のシステム時刻修正の影響を受けない高精度タイマー。
    monotonic(単調増加)クロックを使用して計測します。
    """
    def __enter__(self):
        # システム時刻ではなく、経過時間のみをカウントするタイマーを参照
        self.start_point = time.perf_counter()
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.end_point = time.perf_counter()
        self.elapsed = self.end_point - self.start_point
        print(f"\n[計測完了] NTP非依存タイマー")
        print(f"実行時間: {self.elapsed:.8f} seconds")