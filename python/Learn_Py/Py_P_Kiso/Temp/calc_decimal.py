import random
from decimal import Decimal
from utils import ReliableStopwatch

# 準備：10,000万個のランダムな「インデックス」を共通で使う
# これにより、両方の計算で「全く同じランダムな順序」で足し算が行われます
N = 10**8
indices = [random.randint(0, 8) for _ in range(N)]

# --- 1. Float版の準備 ---
choices_f = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9]
total_f = 0.0

print(f"1. Float版（{N:,}回）計測中...")
with ReliableStopwatch():
    for i in indices:
        # リストから取り出して足すだけの純粋なループ
        total_f += choices_f[i]

print(f"Float合計  : {total_f:.20f}") # 誤差が出る


# --- 2. Decimal版の準備 ---
choices_d = [Decimal('0.1'), Decimal('0.2'), Decimal('0.3'),
             Decimal('0.4'), Decimal('0.5'), Decimal('0.6'),
             Decimal('0.7'), Decimal('0.8'), Decimal('0.9')]
total_d = Decimal('0')

print(f"\n2. Decimal版（{N:,}回）計測中...")
with ReliableStopwatch():
    for i in indices:
        # リストから取り出して足すだけの純粋なループ
        total_d += choices_d[i]

print(f"Decimal合計: {total_d}") # 誤差なし