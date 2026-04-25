from decimal import Decimal  # Decimalを使うためのインポート

y = Decimal("0.1")

#print(f"DECIMAL {y:.20f}")

x = Decimal("0")
for _ in range(10):
    x += y

print("-------- DECIMAL 無編集表示 ---------")
print(x)

print("-------- DECIMAL 10桁表示 ----------")
print(f"{x:.10f}")

print("-------- DECIMAL 20桁表示 ----------")
print(f"{x:.20f}")

print("-------- DECIMAL == 1 判定表示 -----")
print(x == Decimal("1"))
