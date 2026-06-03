<%-- コード12-7 どこつぶのメイン画面（EL式&JSTL版） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>どこつぶ</title>
</head>
<body>
main.jsp（src/main/webapp/WEB-INF/jsp ディレクトリ）

362
第 IV
部
<h1>どこつぶメイン</h1>
<p>
<c:out value="${loginUser.name}" />さん、ログイン中
<a href="Logout">ログアウト</a>
</p>
<p><a href="Main">更新</a></p>
<form action="Main" method="post">
<input type="text" name="text">
<input type="submit" value="つぶやく">
</form>
<c:if test="${not empty errorMsg}">
<p><c:out value="${errorMsg}" /></p>
</c:if>
<c:forEach var="mutter" items="${mutterList}">
<p><c:out value="${mutter.userName}" />：
 <c:out value="${mutter.text}" /></p>
</c:forEach>
</body>
</html>
　動作確認の前に、JSTLのJARファイルをWEB-INF/libに配置するのを忘
れないようにしましょう。また、実行は、トップ画面から行ってください
（p.268）。実行結果はこれまでと変わりません。
すごいや！　Javaのコードが1つもないのに、同じ動きをして
くれてる！