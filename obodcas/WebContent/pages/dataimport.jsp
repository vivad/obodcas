<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<title>Data Import</title>
<meta name="pageselection" content="dataimport">
<link rel="stylesheet" type="text/css" href="/ritchie_backend/css/navigation.css" title="1024px style" media="screen,projection" />

<style type="text/css">
.dataimport_form{margin:0px;}
</style>
</head>
<body>

<s:set name="disubpage" value="disubpage"></s:set>

<s:form action="dataimport" cssClass="dataimport_form" theme="simple">
	<s:if test="%{#disubpage=='Masters'}">
		<s:a href="dataimport.action?disubpagename=Masters" cssStyle="background:#FFFF00;">Masters</s:a>&nbsp;
	</s:if>
	<s:elseif test="%{#disubpage==null}">
		<s:a href="dataimport.action?disubpagename=Masters"
			cssStyle="background:#FFFF00;">Masters</s:a>&nbsp;
	</s:elseif>
	<s:else>
		<s:a href="dataimport.action?disubpagename=Masters" cssStyle="background:#D6ADEB;">Masters</s:a>&nbsp;
	</s:else>
	
	<s:if test="%{#disubpage=='Vendors'}">
		<s:a href="dataimport.action?disubpagename=Vendors"
			cssStyle="background:#FFFF00;">Vendors</s:a>&nbsp;
	</s:if>
	<s:else>
		<s:a href="dataimport.action?disubpagename=Vendors" cssStyle="background:#D6ADEB;">Vendors</s:a>&nbsp;
	</s:else>
	
	<s:if test="%{#disubpage=='Service Centers'}">
		<s:a href="dataimport.action?disubpagename=Service Centers"
			cssStyle="background:#FFFF00;">Service Centers</s:a>&nbsp;
	</s:if>
	<s:else>
		<s:a href="dataimport.action?disubpagename=Service Centers" cssStyle="background:#D6ADEB;">Service Centers</s:a>&nbsp;
	</s:else>
	
	<s:if test="%{#disubpage=='Service Men'}">
		<s:a href="dataimport.action?disubpagename=Service Men" cssStyle="background:#FFFF00;">Service Men</s:a>
	</s:if>
	<s:else>
		<s:a href="dataimport.action?disubpagename=Service Men" cssStyle="background:#D6ADEB;">Service Men</s:a>
	</s:else>
</s:form>

<hr style="height:1px" />
<s:if test="%{#disubpage=='Masters'}">
	<s:include value="/pages/di_masters.jsp" />
</s:if>
<s:elseif test="%{#disubpage=='Vendors'}">
	<s:include value="/pages/di_vendors.jsp" />
</s:elseif>
<s:elseif test="%{#disubpage=='Service Centers'}">
	<s:include value="/pages/di_servicecenters.jsp" />
</s:elseif>
<s:elseif test="%{#disubpage=='Service Men'}">
	<s:include value="/pages/di_servicemen.jsp" />
</s:elseif>
<s:else>
	<s:include value="/pages/di_masters.jsp" />
</s:else>
</body>


