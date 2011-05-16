<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<html>
<head>
<title>Ritchie Street - <decorator:title/></title>
<decorator:head />
</head>
<body bgcolor="#FFFFFF">
<script type="text/javascript">window.status = "Loading: <decorator:title default="Ritchie Street" />...";</script>
<%@ include file="/pages/includes/header.jsp"%>

<table width="100%" border="1" cellspacing="0" cellpadding="0">
	<tr>
		<td width="1%" nowrap> </td>
		<td width="16%" hight="20" valign="top" nowrap>
			<%@ include file="/pages/includes/navigation.jsp" %>
		</td>
	</tr>

	<tr>
		<td width="2%" nowrap> </td>
		<td valign="top">
		<div style="margin:5px 0px;">
		<decorator:body/>
		</div>
		</td>
		<td width="1%" nowrap> </td>
	</tr>
</table>

<%@ include file="/pages/includes/footer.jsp" %>
<%@ include file="/pages/includes/copyright.jsp" %>
</body>
</html>