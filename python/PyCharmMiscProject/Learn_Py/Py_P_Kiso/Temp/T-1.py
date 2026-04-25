
x = 0.0
y = 0.1
print(f"{y:.20f}")

for _ in range(10):
    x += y
print(x)
print(x == 1.0)
print(f"{x:.20f}")
