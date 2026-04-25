#!/usr/bin/env python3
# update_taimen.py
# Drive API files.export を使って GoogleスプレッドシートをXLSXで取得し、
# スクリプトと同じフォルダの gsheet_export.xlsx を更新する。
#
# 依存: 標準ライブラリのみ（pip不要）
# 入力: fileId（必須）
# 終了コード: 0=成功 / 非0=失敗
#
# 同一フォルダに oauth_client.env が必要:
#   CLIENT_ID=...
#   CLIENT_SECRET=...
#   REFRESH_TOKEN=...

from __future__ import annotations

import json
import os
import sys
import time
import urllib.parse
import urllib.request
from pathlib import Path


TOKEN_URL = "https://oauth2.googleapis.com/token"
DRIVE_EXPORT_URL_TEMPLATE = "https://www.googleapis.com/drive/v3/files/{file_id}/export"
XLSX_MIME = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"


def now_str() -> str:
    return time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())


def log_write(log_path: Path, msg: str) -> None:
    line = f"{now_str()}  {msg}\n"
    log_path.parent.mkdir(parents=True, exist_ok=True)
    with log_path.open("a", encoding="utf-8") as f:
        f.write(line)


def read_env_file(env_path: Path) -> dict[str, str]:
    if not env_path.exists():
        raise FileNotFoundError(f"env file not found: {env_path}")

    data: dict[str, str] = {}
    for raw in env_path.read_text(encoding="utf-8").splitlines():
        s = raw.strip()
        if not s or s.startswith("#"):
            continue
        if "=" not in s:
            continue
        k, v = s.split("=", 1)
        data[k.strip()] = v.strip()
    return data


def http_post_form(url: str, form: dict[str, str], headers: dict[str, str] | None = None) -> tuple[int, bytes]:
    body = urllib.parse.urlencode(form).encode("utf-8")
    req = urllib.request.Request(url, data=body, method="POST")
    req.add_header("Content-Type", "application/x-www-form-urlencoded")
    if headers:
        for k, v in headers.items():
            req.add_header(k, v)

    with urllib.request.urlopen(req, timeout=60) as resp:
        return resp.getcode(), resp.read()


def http_get(url: str, headers: dict[str, str] | None = None) -> tuple[int, bytes, dict[str, str]]:
    req = urllib.request.Request(url, method="GET")
    if headers:
        for k, v in headers.items():
            req.add_header(k, v)

    with urllib.request.urlopen(req, timeout=120) as resp:
        code = resp.getcode()
        body = resp.read()
        h = {k: v for k, v in resp.headers.items()}
        return code, body, h


def refresh_access_token(client_id: str, client_secret: str, refresh_token: str, log_path: Path) -> str:
    code, body = http_post_form(
        TOKEN_URL,
        {
            "client_id": client_id,
            "client_secret": client_secret,
            "refresh_token": refresh_token,
            "grant_type": "refresh_token",
        },
    )

    # JSONとして読み取る
    try:
        payload = json.loads(body.decode("utf-8", errors="replace"))
    except Exception:
        payload = {"raw": body.decode("utf-8", errors="replace")}

    if code != 200:
        raise RuntimeError(f"token refresh failed (HTTP {code}): {payload}")

    access_token = payload.get("access_token")
    if not access_token:
        raise RuntimeError(f"token refresh response missing access_token: {payload}")

    log_write(log_path, "TOKEN refresh OK")
    return access_token


def validate_xlsx_bytes(data: bytes) -> None:
    # XLSXはZIPなので先頭がPKで始まるのが基本
    if len(data) < 4 or data[:2] != b"PK":
        # GoogleのHTMLやエラーレスポンス混入検出
        head = data[:200].decode("utf-8", errors="replace")
        raise RuntimeError(f"downloaded data is not XLSX/ZIP (missing PK). head={head!r}")


def main() -> int:
    script_dir = Path(__file__).resolve().parent
    env_path = script_dir / "oauth_client.env"
    out_xlsx = script_dir / "gsheet_export.xlsx"
    log_path = script_dir / "update.log"

    if len(sys.argv) < 2 or not sys.argv[1].strip():
        log_write(log_path, "ERROR: fileId is required")
        print("ERROR: fileId is required", file=sys.stderr)
        return 2

    file_id = sys.argv[1].strip()

    try:
        log_write(log_path, f"START fileId={file_id}")

        env = read_env_file(env_path)
        client_id = env.get("CLIENT_ID", "")
        client_secret = env.get("CLIENT_SECRET", "")
        refresh_token = env.get("REFRESH_TOKEN", "")

        missing = [k for k in ("CLIENT_ID", "CLIENT_SECRET", "REFRESH_TOKEN") if not env.get(k)]
        if missing:
            raise RuntimeError(f"missing keys in oauth_client.env: {missing}")

        token = refresh_access_token(client_id, client_secret, refresh_token, log_path)

        export_url = DRIVE_EXPORT_URL_TEMPLATE.format(file_id=file_id)
        query = urllib.parse.urlencode({"mimeType": XLSX_MIME})
        full_url = f"{export_url}?{query}"

        code, body, hdrs = http_get(full_url, headers={"Authorization": f"Bearer {token}"})
        if code != 200:
            head = body[:500].decode("utf-8", errors="replace")
            raise RuntimeError(f"export failed (HTTP {code}). head={head!r}")

        validate_xlsx_bytes(body)

        # 原子更新（途中で壊れないように一旦tmp）
        tmp = out_xlsx.with_suffix(".xlsx.tmp")
        tmp.write_bytes(body)
        tmp.replace(out_xlsx)

        log_write(log_path, f"OK updated: {out_xlsx.name} ({len(body)} bytes)")
        return 0

    except Exception as e:
        msg = f"ERROR: {type(e).__name__}: {e}"
        log_write(log_path, msg)
        print(msg, file=sys.stderr)
        return 1

    finally:
        log_write(log_path, "END")


if __name__ == "__main__":
    raise SystemExit(main())