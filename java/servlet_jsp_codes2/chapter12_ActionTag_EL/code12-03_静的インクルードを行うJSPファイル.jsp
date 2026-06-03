<%-- コード12-3 静的インクルードを行うJSPファイル --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="common.jsp" %>
<%
LocalDate date = LocalDate.now();
DateTimeFormatter formatter = 
 DateTimeFormatter.ofPattern("MM月dd日");
String today = date.format(formatter);
%>
<!DOCTYPE html>
…（省略）…
<%= name %>さんの<%= today %>の運勢は･･･
…（省略）…
includeDirective.jsp（src/main/webappディレクトリ）
common.jspでimportした
クラスを使用
common.jspで定義した
変数を使用

342
第 IV
部