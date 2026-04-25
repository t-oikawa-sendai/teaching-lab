# # 1. 浮動小数点数（float）での計算
# balance_float = 100_000_000.0  # 1億円
# deposit = 1.0                  # 1円
#
# for _ in range(100):
#     balance_float += deposit
#
# print(f"floatの残高  : {balance_float:,.1f}円")
# # 結果: 100,000,000.0円 （1円も増えていない！）
#
# # 2. 正確な計算（Decimal）での計算
# from decimal import Decimal
# balance_dec = Decimal('100000000.0')
# deposit_dec = Decimal('1.0')
#
# for _ in range(100):
#     balance_dec += deposit_dec
#
# print(f"Decimalの残高: {balance_dec:,.1f}円")
# # 結果: 100,000,100.0円 （正解！）

# 1. 100兆円（15桁）ならギリギリ動く
balance_ok = 100_000_000_000_000.0
print(f"100兆 + 1 = {balance_ok + 1.0:,.1f}") # 増える

# 2. 1京（けい）円（16桁）にすると...
balance_ng = 10_000_000_000_000_000.0
print(f"  1京 + 1 = {balance_ng + 1.0:,.1f}") # 消える！！