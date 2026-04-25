# PyCharmMiscProject venv 運用（Mac）

## 作業場所
~/Learning/Python/PyCharmMiscProject

## 使うPython（必ずvenv）
source .venv/bin/activate

## 確認
python -c "import sys; print(sys.executable)"

## 主要パッケージ確認
python -c "import pandas, matplotlib, openpyxl; print('OK')"

## 依存固定（更新後に実行）
python -m pip freeze > requirements.txt
