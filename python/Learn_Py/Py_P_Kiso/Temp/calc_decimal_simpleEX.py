import tempfile
import webbrowser
from decimal import Decimal
from html import escape
from pathlib import Path

from utils import ReliableStopwatch

# 準備
total = Decimal("0")
y = Decimal("0.1")  # Decimalの小数

print("Decimal版（1,000万回）計算中…（完了後にブラウザで表示します）")

sw = ReliableStopwatch()
with sw:
    for _ in range(10**7):
        total += y  # 0.1を足し続ける

result_value = escape(str(total))
elapsed_sec = f"{sw.elapsed:.8f}"

html = f"""<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Decimal計算（1,000万回）</title>
  <style>
    body {{
      font-family: system-ui, -apple-system, "Segoe UI", sans-serif;
      margin: 2rem;
      line-height: 1.6;
      color: #222;
    }}
    h1 {{ font-size: 1.35rem; margin-bottom: 0.5rem; }}
    .meta {{ color: #555; margin-bottom: 1.25rem; }}
    .box {{
      background: #f4f4f5;
      border: 1px solid #ddd;
      border-radius: 8px;
      padding: 1rem 1.25rem;
      font-family: ui-monospace, SFMono-Regular, Menlo, Consolas, monospace;
      white-space: pre-wrap;
      word-break: break-all;
    }}
  </style>
</head>
<body>
  <h1>Decimal版（1,000万回）計算中</h1>
  <p class="meta">計算は完了しました。以下が結果です。</p>
  <div class="box">Decimalの結果: {result_value}

実行時間: {elapsed_sec} 秒</div>
</body>
</html>
"""

with tempfile.NamedTemporaryFile(
    mode="w", encoding="utf-8", suffix=".html", delete=False
) as f:
    f.write(html)
    tmp_path = f.name

webbrowser.open(Path(tmp_path).as_uri())
