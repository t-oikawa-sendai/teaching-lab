# demo_ebbinghaus.py
# 実行: python demo_ebbinghaus.py

import math


def retention(t: float, strength: float) -> float:
    """保持率 R(t) = exp(-t/strength)"""
    return math.exp(-t / strength)


def simulate(days: int = 30, threshold: float = 0.60) -> None:
    # strength = 忘れにくさ。初期値は適当でOK（モデルなので）
    strength = 1.5  # 日
    boost = 1.8     # 復習したら強度が増える倍率

    t = 0.0
    reviews = []

    while t < days:
        r = retention(t, strength)
        if r < threshold:
            reviews.append((t, r, strength))
            strength *= boost  # 復習で強くなる（忘れにくくなる）
        t += 0.25  # 0.25日 = 6時間刻みでチェック

    print("=== Ebbinghaus-like demo (model) ===")
    print(f"days={days}, threshold={threshold}")
    print("review_time(day)  retention  strength(before)")
    for day, r, s in reviews:
        print(f"{day:14.2f}  {r:9.3f}  {s:15.3f}")

    if not reviews:
        print("復習なしでも閾値を下回りませんでした（strength設定を小さくしてみて）")


if __name__ == "__main__":
    simulate()
