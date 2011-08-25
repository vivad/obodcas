<%@ taglib prefix="s" uri="/struts-tags" %>

<table width="100%" border="1" cellspacing="0" cellpadding="0">
<tr>
<td width="1%" nowrap> </td>
<td width="19%" nowrap><font size="20"><b>Ritchie Street</b></font></td>
<td width="1%" nowrap>Hi, <%=session.getAttribute("firstname")%> <%=session.getAttribute("lastname")%></td>
<td width="1%" nowrap><a href="logout.action?">Sign out</a></td>
</tr>
</table>

