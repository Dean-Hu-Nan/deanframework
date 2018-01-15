<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎</title>
</head>
<body>
<h1>Hello 当前时间：${current}</h1>
<b>欢迎使用Dean FrameWork，这是您的第一个页面</b>
<br/>
<br/>
<form method="post" action="${pageContext.request.contextPath}/upload" enctype="multipart/form-data">
    <input name="demo_file" type="file"/>
    <input type="submit" value="测试上传">
</form>
</body>
</html>
