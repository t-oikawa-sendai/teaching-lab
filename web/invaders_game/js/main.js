/**
 * ファイル名：main.js
 * 説明：エントリーポイント。Canvas を取得して Game を生成し、ループを開始する。
 * Date　：2026-04-10
 * Author：Takashi Oikawa
 */

(function () {
  const canvas = document.getElementById("gameCanvas");
  if (!canvas || !(canvas instanceof HTMLCanvasElement)) {
    console.error("gameCanvas が見つかりません。");
    return;
  }
  const game = new Game(canvas);
  game.start();
})();
