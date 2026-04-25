import random
from decimal import Decimal
from utils import ReliableStopwatch

# 準備
total = Decimal('0')
y = Decimal('0.1')  # Decimalの小数

print("Decimal版（1,000万回）計算中...")

with ReliableStopwatch():
    for _ in range(10**7):
        total += y  # 0.1を足し続ける

print(f"Decimalの結果: {total}")