<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
	<title>Template Engine</title>
	<meta name="pageselection" content="templateengine"> 
	<link rel="stylesheet" type="text/css" href="/ritchie_backend/css/navigation.css" title="1024px style" media="screen,projection" />
	<style type="text/css">
		.templateengine_form{margin:0px;}
	</style>
</head>


<body>

<s:set name="disubpage" value="disubpage"></s:set>

<s:form action="templateengine" cssClass="templateengine_form" theme="simple">
	<s:if test="%{#disubpage=='Enroll Template'}">
		<s:a href="templateengine.action?disubpagename=Enroll Template" cssStyle="background:#FFFF00;">Enroll Template</s:a>&nbsp;
	</s:if>
	<s:elseif test="%{#disubpage==null}">
		<s:a href="templateengine.action?disubpagename=Enroll Template"
			cssStyle="background:#FFFF00;">Enroll Template</s:a>&nbsp;
	</s:elseif>
	<s:else>
		<s:a href="templateengine.action?disubpagename=Enroll Template" cssStyle="background:#D6ADEB;">Enroll Template</s:a>&nbsp;
	</s:else>
	
	<s:if test="%{#disubpage=='Manage Template'}">
		<s:a href="templateengine.action?disubpagename=Manage Template"
			cssStyle="background:#FFFF00;">Manage Template</s:a>&nbsp;
	</s:if>
	<s:else>
		<s:a href="templateengine.action?disubpagename=Manage Template" cssStyle="background:#D6ADEB;">Manage Template</s:a>&nbsp;
	</s:else>
	
	<s:if test="%{#disubpage=='Map Data'}">
		<s:a href="templateengine.action?disubpagename=Map Data"
			cssStyle="background:#FFFF00;">Map Data</s:a>&nbsp;
	</s:if>
	<s:else>
		<s:a href="templateengine.action?disubpagename=Map Data" cssStyle="background:#D6ADEB;">Map Data</s:a>&nbsp;
	</s:else>
	
	<s:if test="%{#disubpage=='Generate Pages'}">
		<s:a href="templateengine.action?disubpagename=Generate Pages" cssStyle="background:#FFFF00;">Generate Pages</s:a>
	</s:if>
	<s:else>
		<s:a href="templateengine.action?disubpagename=Generate Pages" cssStyle="background:#D6ADEB;">Generate Pages</s:a>
	</s:else>
</s:form>

<hr style="height:1px" />
<s:if test="%{#disubpage=='Enroll Template'}">
	<s:include value="/pages/te_enrolltemplate.jsp" />
</s:if>
<s:elseif test="%{#disubpage=='Manage Template'}">
	<s:include value="/pages/te_managetemplate.jsp" />
</s:elseif>
<s:elseif test="%{#disubpage=='Map Data'}">
	<s:include value="/pages/te_mapdata.jsp" />
</s:elseif>
<s:elseif test="%{#disubpage=='Generate Pages'}">
	<s:include value="/pages/te_generatepages.jsp" />
</s:elseif>
<s:else>
	<s:include value="/pages/te_enrolltemplate.jsp" />
</s:else>
</body>