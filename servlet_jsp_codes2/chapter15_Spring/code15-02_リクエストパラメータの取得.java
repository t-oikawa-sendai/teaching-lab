// コード15-2 リクエストパラメータの取得
public String handle(
@RequestParam String name, 
@RequestParam int age) { 
…
}
　リクエストパラメータを受け取る場合は、@RequestParamアノテーショ
ンを付けた引数を定義します。そして、その引数名をリクエストパラメータ
名と一致させます。そうすると、Springが自動的にリクエストパラメータの
解説nameに"minato"、
ageに23が代入される

452
第
V
部
値を代入してくれます。
　リクエストパラメータ名と引数名を一致させることが難しい場合は、
@RequestParamアノテーションに括弧を付けパラメータ名を指定します。