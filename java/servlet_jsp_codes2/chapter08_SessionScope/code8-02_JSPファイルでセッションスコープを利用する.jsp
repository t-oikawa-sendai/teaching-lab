<%-- コード8-2 JSPファイルでセッションスコープを利用する --%>
<%@ page language="java" contentType="text/html; 
pageEncoding="UTF-8" %>
<%@ page import="model.Human" %>
<% 
// セッションスコープからインスタンスを取得
Human h = (Human)session.getAttribute("human"); 
%>
<!DOCTYPE html>
…（省略）…
<%= h.getName() %>さんは<%= h.getAge() %>歳です
…（省略）…
取得するインスタンスの
クラスをインポート