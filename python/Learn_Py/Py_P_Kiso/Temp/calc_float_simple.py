import random
from utils import ReliableStopwatch

# 準備
total = 0.0
y = 0.1  # 普通の小数

print("Float版（1,000万回）計算中...")

with ReliableStopwatch():
    for _ in range(10**7):
        total += y  # 0.1を足し続ける

print(f"Floatの結果: {total:.20f}")