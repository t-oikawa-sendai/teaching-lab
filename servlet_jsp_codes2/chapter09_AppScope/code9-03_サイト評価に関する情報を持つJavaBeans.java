// コード9-3 サイト評価に関する情報を持つJavaBeans
package model; 
import java.io.Serializable;

public class SiteEV implements Serializable {
private int like; // よいねの数
private int dislike; // よくないねの数

public SiteEV() {
SiteEV.java
（model パッケージ）
よいねを
増やして
了解
URLを入力
（サーバーで実行）
よいねを
クリック
http://localhost:8080/
example/MinatoIndex
よいね：0人
よくないね：0人
よいね：1人
よくないね：0人
minatoIndex.jsp
minatoIndex.jsp
湊くんのサイトの
トップ画面
湊くんのサイトの
トップ画面
MinatoIndex
MinatoIndex アプリケーションスコープ
よいね=0
よくないね=0
SiteEV
よいね=0
よくないね=0
SiteEV
SiteEVLogic
よいね=1
よくないね=0
SiteEV
取得
取得
取得
保存
保存
action=like
 図9-4 評価ボタン機能のしくみ