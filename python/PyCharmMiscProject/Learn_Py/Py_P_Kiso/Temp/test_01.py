# float は2進数近似なので誤差が出る
x = 0.0
for _ in range(10):
    x += 0.1
print(x)           # 1.0 のつもりでも…
print(x == 1.0)    # False になることが多い
print(f"{x:.20f}") # 誤差が見える
