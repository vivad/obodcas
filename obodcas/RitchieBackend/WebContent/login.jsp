<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IN ADMIN PANEL | Powered by INDEZINER</title>
<link rel="stylesheet" type="text/css" href="/ritchie_backend/css/style.css" title="1024px style" media="screen,projection"/>
<link rel="stylesheet" type="text/css" href="/ritchie_backend/css/niceforms-default.css" title="1024px style" media="screen,projection"/>
</head>

<body>
<div id="main_container">

	<div class="header_login">
    <div class="logo"><a href="#"><img src="/ritchie_backend/images/logo.gif" alt="" title="" border="0" /></a></div>
    </div>
    
	<div class="login_form">
		<s:actionerror/>
		<h3>Admin Panel Login</h3>
		
		<s:form action="welcome" theme="simple" name="login_form">
			<br><br><br><br>
			<dl>
				<dt>Username:</dt><dd><s:textfield name="username" cssClass="login_text_fields"/></dd>
				<dt>Password:</dt><dd><s:password name="password" cssClass="login_text_fields"/></dd>
			</dl>
			<br>
			<s:submit cssClass="login_submit"/>
			<br><br><br><br>			
        </s:form>
        
	</div>  
         
        <div class="footer_login">
    
    	<div class="left_footer_login">IN ADMIN PANEL | Powered by <a href="http://indeziner.com">INDEZINER</a></div>
    	<div class="right_footer_login"><a href="http://indeziner.com"><img src="/ritchie_backend/images/indeziner_logo.gif" alt="" title="" border="0" /></a></div>
    
    </div>

</div>		

</body>
</html>