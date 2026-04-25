#import random

# 1から9までのランダムな数値を100万回生成して合計
# range(10**6) は 1,000,000回 を意味します
#total = sum([random.randint(1, 9) for _ in range(10**7)])

#print(f"合計値: {total}")

import random
from utils import ReliableStopwatch

# 1. 準備：普通の小数（float）のリストを作る
# この時点で、コンピュータ内部では 0.100000000000000005... となっています
choices = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9]

total = 0.0  # float型の 0

print("float版（誤差あり）1,000万回計算を開始...")

# 2. 計測
with ReliableStopwatch():
    for _ in range(10**8):
        # 普通の小数同士を 1,000万回足していく
        total += random.choice(choices)

# 3. 結果表示
# そのまま表示すると丸められてしまうことがあるので、小数点以下20桁まで表示してみます
print(f"最終合計値: {total:,}")
print(f"詳細な値  : {total:.20f}")