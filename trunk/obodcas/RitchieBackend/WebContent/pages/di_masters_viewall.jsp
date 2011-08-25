<%@ taglib prefix="s" uri="/struts-tags"%>

<% int i=0; %>

<table border="1">
  <tr>
  	<th>S.No.</th>
    <th>City</th>
    <th>Name</th>
  </tr>
  <s:iterator value="areaMaster">
  <tr>
  	<td><%=++i%></td>
    <td><s:property value="cityName"/></td>
    <td><s:property value="areaName"/></td>
  </tr>
  </s:iterator>
</table>
