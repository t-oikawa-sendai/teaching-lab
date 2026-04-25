#
# =============================================================================
# Float と Decimal の浮動小数点誤差を検証するプログラム
# 目的: ランダムな小数値の累積加算において float 型が持つ
#       固有の精度限界を Decimal 型との比較により定量的に評価する
# Date:2026/02/22 Author.T.Oikawa
# =============================================================================
import random
from decimal import Decimal

error_count = 0
trials = 100000

for _ in range(trials):
    values = [round(random.uniform(0, 1), 10) for _ in range(20)]

    float_sum = sum(values)
    decimal_sum = float(sum(Decimal(str(v)) for v in values))

    if float_sum != decimal_sum:
        error_count += 1

print(f"試行回数: {trials}")
print(f"誤差発生: {error_count}")
print(f"発生確率: {error_count / trials * 100:.1f}%")

# 1回分の詳細
values = [round(random.uniform(0, 1), 10) for _ in range(20)]
float_sum = sum(values)
decimal_sum = float(sum(Decimal(str(v)) for v in values))

print(f"\n-------- 1回分の詳細 --------")
print(f"float:   {float_sum:.20f}")
print(f"Decimal: {decimal_sum:.20f}")
print(f"差:      {(float_sum - decimal_sum):.20f}")