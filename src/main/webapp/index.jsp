<%@page language="java" contentType="text/html; charset=UTF-8" %>

<html>
<body>
<h2>Hello World!</h2>

springMVC 上传文件
<form name="form2" action="/manage/product/upload.do" method="post"  enctype="multipart/form-data">
    <input type="file" name="uploadFile">
    <input type="submit" value="upload"/>
</form>
富文本图片 上传文件
<form name="form2" action="/manage/product/richtext_img_upload.do" method="post"  enctype="multipart/form-data">
    <input type="file" name="uploadFile">
    <input type="submit" value="upload"/>
</form>


</body>
</html>
