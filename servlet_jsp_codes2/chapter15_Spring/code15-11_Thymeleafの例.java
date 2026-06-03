// コード15-11 Thymeleafの例
<p>[[${loginUser.name}]]さん、ログイン中</p>

<th:block th:if="${errorMsg}">
<p>[[${errorMsg}]]</p>
</th:block>

<th:block th:each="mutter : ${mutterList}">
<p>[[${mutter.userName}]]：[[${mutter.text}]]</p>
</th:block>

468
第
V
部
　このように、ThymeleafにはJSP（特にJSTL）と多くの共通点があります。
そのため、本書でJSPを学んだ方であれば、Thymeleafへの移行もスムーズ
に進められるでしょう。
　なお、Springプロジェクトでビューの開発にJSPを使用する場合は、設定
が必要です。
　具体的な手順については、Web付録を参照してください。
　この設定では、JSPファイルの配置場所は、これまでと同じになるように
しています。
・直接リクエストされるJSP：src/main/webapp
・フォワードされるJSP：src/main/webapp/WEB-INF/jsp
　ただし、Springプロジェクトの作成方法によっては、src/main以下のフォ
ルダが自動で作成されない場合があります。
　その場合は、必要なフォルダを手動で作成してください。