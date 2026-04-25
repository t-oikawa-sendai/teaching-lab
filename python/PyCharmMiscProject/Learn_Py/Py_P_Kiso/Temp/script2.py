from decimal import Decimal, getcontext

# 1. 通常の浮動小数点（float）の誤差
print(f"floatの結果: {0.1 + 0.2}")  # 0.30000000000000004

# 2. Decimalを使った正確な計算
# 注意：Decimalに渡す数値は「文字列」で渡すのが鉄則です
a = Decimal('0.1')
b = Decimal('0.2')
result = a + b
print(f"Decimalの結果: {result}")  # 0.3

# 3. 桁数の指定（任意精度）
# 有効桁数を50桁に設定してみる
getcontext().prec = 50
d = Decimal('1') / Decimal('7')
print(f"1/7の50桁表示: {d}")