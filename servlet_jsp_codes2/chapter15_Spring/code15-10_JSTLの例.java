// コード15-10 JSTLの例
<p><c:out value="${loginUser.name}" />さん、ログイン中</p>

<c:if test="${not empty errorMsg}">
<p><c:out value="${errorMsg}" /></p>
</c:if>

<c:forEach var="mutter" items="${mutterList}">
<p><c:out value="${mutter.userName}" />：
 <c:out value="${mutter.text}" /></p>
</c:forEach>
　このコードを、Thymeleafで記述すると次のようになります。