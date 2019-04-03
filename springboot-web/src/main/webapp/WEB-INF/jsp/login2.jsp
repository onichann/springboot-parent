<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wutong
  Date: 2018/4/2
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="./include.jsp" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<%--<c:if test="${!empty error}">--%>
    <%--<span style="color: red; ">${error}</span>--%>
<%--</c:if>--%>
<%--<form action="<c:url value="/"/>" method="post">--%>
    <%--用户名:<input type="text" name="username" value="admin">--%>
    <%--<br>--%>
    <%--密码：--%>
    <%--<input type="password" name="password" id="2" value="000000">--%>
    <%--<br>--%>
    <%--<input type="submit" name="" id="1" value="登录">--%>
<%--</form>--%>

<form id="formid" target="frame1"  action="${ctx}/upload/upload" method="post" enctype="multipart/form-data" >
    <input name="id" value="abc111" type="hidden"/>
    <input type="file" name="file"  multiple/>
    <input type="submit" value="submit" multiple="multiple">
    <input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<iframe name="frame1" frameborder="0" height="40"></iframe>

<iframe name="frame2" frameborder="0" width="100%" height="700" src="/file/display3"></iframe>

<input type="button" value="接口数据传递" onclick="aaa()" style="display: none"/>
<script>
   function aaa(){
       var obj={"name":"姓名","ids":['1','2']}
       $.ajax({
           url: "${ctx}/test/checkParamters",
           type: 'POST',
           contentType:"application/json",
           data:JSON.stringify(obj),
           cache: false,
           async: true,
           dataType: 'json',
           success: function (data) {
              alert(JSON.stringify(data))
           },
           error: function (msg) {
               alert("调用失败");
           }
       });

   }

</script>
</body>
</html>
