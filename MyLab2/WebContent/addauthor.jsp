<%@ page contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<style type="text/css">
	body{
		background-image:url(d.jpg);
		background-size:100% 100%;
		}
	</style></head>
<body>
   <div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="page-header">
				<h1>
					数据库中没有该作者请先添加作者 <small>Subtext for header</small>
				</h1>
			</div>
		</div>
    <center>
    <s:form action="Addauthor">
        <s:textfield name="isbn" label="ISBN"/>
        <s:textfield name="authorid" label="编号" readonly="true"/>
        <s:textfield name="name" label="作家名" readonly="true"/>
        <s:textfield name="age" label="年龄"/>
        <s:textfield name="country" label="国籍"/>
        <s:submit value="添加"/>
    </s:form>
    </center>
</body>
</html>