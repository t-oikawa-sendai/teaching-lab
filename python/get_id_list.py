import csv
import requests


def search_stats_ids(app_id, keyword, output_csv_path):
    # 統計表情報取得API
    url = f"https://api.e-stat.go.jp/rest/3.0/app/json/getStatsList?appId={app_id}&searchWord={keyword}"

    try:
        response = requests.get(url)
        response.raise_for_status()
        data = response.json()

        stats_list = data["GET_STATS_LIST"]["DATALIST_INF"]["TABLE_INF"]
        if isinstance(stats_list, dict):
            stats_list = [stats_list]

        rows = []
        for table in stats_list:
            table_id = table["@id"]
            title = table["TITLE"]["$"]
            rows.append([table_id, title])

        with open(output_csv_path, "w", newline="", encoding="utf-8-sig") as f:
            writer = csv.writer(f)
            writer.writerow(["ID", "タイトル"])
            writer.writerows(rows)

        print(f"保存しました: {output_csv_path}（{len(rows)} 件）")

    except Exception as e:
        print(f"エラーが発生しました: {e}")


if __name__ == "__main__":
    APP_ID = "fdcb7fc1c4acd0ae2d0b034bb42e9367aecf8e09"
    search_stats_ids(APP_ID, "国勢調査 人口", "国勢調査_人口.csv")
