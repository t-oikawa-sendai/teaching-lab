# 1. 判定基準の定義 (タプル)
# インデックス 0:A, 1:B, 2:C, 3:D
試験結果_判定 = ("A", "B", "C", "D")

# 2. ユーザーからの入力を辞書に格納
試験_点数 = {
    "英語": int(input("英語は？ ")),
    "数学": int(input("数学は？ ")),
    "国語": int(input("国語は？ "))
}

print("-" * 30)

# --- 英語の判定 ---
ten_eng = 試験_点数["英語"]
if 80 <= ten_eng <= 100:
    hantei_eng = 試験結果_判定[0]
elif 70 <= ten_eng < 80:
    hantei_eng = 試験結果_判定[1]
elif 60 <= ten_eng < 70:
    hantei_eng = 試験結果_判定[2]
else:
    hantei_eng = 試験結果_判定[3]
print(f"英語: {ten_eng}点 -> 判定: {hantei_eng}")

# --- 数学の判定 ---
ten_math = 試験_点数["数学"]
if 80 <= ten_math <= 100:
    hantei_math = 試験結果_判定[0]
elif 70 <= ten_math < 80:
    hantei_math = 試験結果_判定[1]
elif 60 <= ten_math < 70:
    hantei_math = 試験結果_判定[2]
else:
    hantei_math = 試験結果_判定[3]
print(f"数学: {ten_math}点 -> 判定: {hantei_math}")

# --- 国語の判定 ---
ten_jp = 試験_点数["国語"]
if 80 <= ten_jp <= 100:
    hantei_jp = 試験結果_判定[0]
elif 70 <= ten_jp < 80:
    hantei_jp = 試験結果_判定[1]
elif 60 <= ten_jp < 70:
    hantei_jp = 試験結果_判定[2]
else:
    hantei_jp = 試験結果_判定[3]
print(f"国語: {ten_jp}点 -> 判定: {hantei_jp}")