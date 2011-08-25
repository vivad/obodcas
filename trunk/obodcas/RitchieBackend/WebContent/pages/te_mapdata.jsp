<%@ page import="ritchie.backend.utilities.Constants"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<link rel="stylesheet" type="text/css"
	href="/ritchie_backend/css/te_filedetails.css" title="1024px style"
	media="screen,projection" />
	<link rel="stylesheet" type="text/css"
	href="/ritchie_backend/css/te_assigndatapatterns.css" title="1024px style"
	media="screen,projection" />
</head>
<script type="text/javascript">

var xmlHttp;

function ajaxFunction()
{
    try
    {
        // Firefox, Opera 8.0+, Safari
        xmlHttp=new XMLHttpRequest();
    }
    catch (e)
    {
        // Internet Explorer
        try
        {
            xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e)
        {
            try
            {
                xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch (e)
            {
                alert("Your browser does not support AJAX!");
                return false;
            }
        }
    }
}

var tdNo = '';
function loadColumnNames(obj){
	//alert("hi");
	tdNo  = obj.parentNode.id.split('tabletd')[1];
	//alert('id:  '+tdNo);
	var selectObj = obj;
	var selectedIndex = selectObj.selectedIndex;
	var tableName = selectObj.options[selectedIndex].value;
	//alert("tableName:  "+tableName);
	getColumnNames(tableName);
}

function getColumnNames(tableName)
{
    ajaxFunction();
    var url='getDBTableColumnNamesAjax.action?tableName='+tableName;
    xmlHttp.open("GET",url,true);
    xmlHttp.onreadystatechange = columnNamesHandler;
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded charset=UTF-8");
    xmlHttp.setRequestHeader("Accept-Charset","UTF-8");
    xmlHttp.setRequestHeader("Connection", "close");
    xmlHttp.send(null);
}

function columnNamesHandler()
{
    var _inHTML = '';
	//alert('in response');
	
    if(xmlHttp.readyState==4 && xmlHttp.status==200)
    {
    	var responseXML = xmlHttp.responseXML;
		var xml = responseXML.getElementsByTagName("ColumnNames");
		var columns = xml[0].getElementsByTagName("ColumnName");

    	if(columns != null && columns.length != 0)
        {
    		_inHTML += '<select name="columnname">';
    		 for(var n=0;n<columns.length;n++)
             {
                 _inHTML += '<option value="'+columns[n].firstChild.nodeValue+'">';
				 _inHTML += columns[n].firstChild.nodeValue;
				 _inHTML += '</option>';
             }
    		_inHTML += '</select>';
        }else{
        	_inHTML += 'No column found for the selected table';
        }
		document.getElementById("columntd"+tdNo).innerHTML = _inHTML;
    }
}

function assignDataPatterns(){
	var tableObj = document.getElementById("patterntable");
	var rowCount = tableObj.getElementsByTagName("TR").length;
	alert('rowCount:  '+rowCount);

	var data = '';

	for(var i=1;i<rowCount;i++){
		
		var patternName = tableObj.rows[i].cells[0].childNodes[0].data;
		
		var tselectObj = tableObj.rows[i].cells[1].childNodes[0];
		var tselectIndex = tselectObj.selectedIndex;
		var selectedtableName = tselectObj[tselectIndex].value;



		var cselectObj = tableObj.rows[i].cells[2].childNodes[0];
		var cselectIndex = cselectObj.selectedIndex;
		var selectedcolumnName = cselectObj[cselectIndex].value;

		data += patternName;
		data += '-NTD-';
		data += selectedtableName;
		data += '-NTD-';
		data += selectedcolumnName;

		if(i+1<rowCount){
			data += '-NTR-';
		}
	}

	alert('data:  '+data);

	var templateno = document.getElementById("templateno").value;

	alert('templateno:  '+templateno);
	
 	ajaxFunction();

 	
 	
   	var url='assigndatapatternAjax.action?data='+data+'&templateno='+templateno;

   	alert('url:  '+url);
   	
   	xmlHttp.open("GET",url,true);
   	xmlHttp.onreadystatechange = assignDataPatternHandler;
   	xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded charset=UTF-8");
   	xmlHttp.setRequestHeader("Accept-Charset","UTF-8");
   	xmlHttp.setRequestHeader("Connection", "close");
   	xmlHttp.send(null);

}

function assignDataPatternHandler()
{
    var _inHTML = '';
    alert('in response');
    //alert( xmlHttp.responseText);
	
    if(xmlHttp.readyState==4 && xmlHttp.status==200)
    {
    	document.getElementById("errorMsgDiv").innerHTML = xmlHttp.responseText;
    	//alert( xmlHttp.responseText);
    	//var responseXML = xmlHttp.responseXML;
    }
}

</script>


<div class="errorMsgDiv" id="errorMsgDiv"><s:actionmessage /> <s:actionerror /></div>

<s:if test="actionName=='teextractpattern'">

<%
	int i = 0;
%>
<s:form action="temapdata" method="post" theme="simple" 
	cssClass="assign_data_pattern_form">
	
	All Patterns must be assigned
	<br>
	<br>
	<s:label value="Template Number: "/><s:property value="templateno"/>
	<br>
	<s:label value="Template Name: "/><s:property value="templatename"/>

	<br>
	<br>
	
	<input name="templateno" id="templateno" type="hidden" value="<%=request.getParameter("templateno")%>"></input>
	<table>
		<tbody id = "patterntable">
		<tr>
			<th>Pattern Name</th>
			<th>Table Name</th>
			<th>Column Name</th>
		</tr>
		<s:iterator value="keynames">
			<%
				i++;
			%>
			<tr>
				<td><s:property/></td>
				<td id="tabletd<%=i%>"><s:select id="tableNames"
					name="tableNames" list="tableNames"
					onchange="loadColumnNames(this);" /></td>
				<td id="columntd<%=i%>"><s:select id="columnNames"
					name="columnNames" list="columnNames" /></td>
			</tr>
		</s:iterator>
		</tbody>
	</table>
	
	<input type="button" value="Assign Data Patterns" onclick="assignDataPatterns();" />
</s:form>

</s:if>

<s:if test="actionName=='templateengine'">
<s:form action="teextractpattern" method="post" theme="simple" 
	cssClass="file_list_display_form">
	<input name="pagefrom" type="hidden" value="mapdata"/>
	<table class="file_list">
		<tr>
			<th></th>
			<th>Token No</th>
			<th>Template File Name</th>
			<th>Uploaded By</th>
			<th>Status</th>
			<th>Date Uploaded</th>
		</tr>
		<s:iterator value="fileDetailsList">
			<tr>
				<td><input type="radio" name="templateno"
					value="<s:property value="FILENO" />" /></td>
				<td><s:property value="FILENO" /></td>
				<td><s:property value="FILENAME" /></td>
				<td><s:property value="UPLOADEDBY" /></td>
				<td><s:property value="STATUS" /></td>
				<td><s:property value="UPLOADEDDATE" /></td>
			</tr>
		</s:iterator>
		<s:if test="filedetailsexist==false">
			<tr>
				<td colspan="6" align="center">No Template has been uploaded</td>
			</tr>
		</s:if>
	</table>

	<s:submit name="extractpatternsbutton" value="Extract Patterns"
		cssClass="extractpatterns" />
</s:form>
</s:if>