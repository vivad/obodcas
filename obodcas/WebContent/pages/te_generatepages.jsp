<%@ page
	import="ritchie.backend.utilities.Constants,ritchie.backend.utilities.ApplicationProperties"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<head>
<link rel="stylesheet" type="text/css"
	href="/ritchie_backend/css/te_filedetails.css" title="1024px style"
	media="screen,projection" />
</head>


<script type="text/javascript"><!--

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

function setEnrollmentIDInSession(buttonObject,linkFrom){
	alert('hi');
	//alert('linkFrom:  '+linkFrom);
	var tr = buttonObject.parentNode.parentNode;
	var td = tr.cells[0];
	var uniqueenrollmentid = td.childNodes[0].value;		
	alert('uniqueenrollmentid:  '+uniqueenrollmentid);  

	ajaxFunction();
	//var url='tevalidatepageAjax.action?uniqueenrollmentid='+uniqueenrollmentid;
	var url='tegeneratepages.action?uniqueEnrollmentID='+uniqueenrollmentid+'&linkFrom='+linkFrom;
	//Asynchronous AJAX call
    //xmlHttp.open("GET",url,true);
    //synchronous AJAX call
    xmlHttp.open("GET",url,false);
    //xmlHttp.onreadystatechange = openPageHandler;
    xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded charset=UTF-8");
    xmlHttp.setRequestHeader("Accept-Charset","UTF-8");
    xmlHttp.setRequestHeader("Connection", "close");
    xmlHttp.send(null);

    alert('before');
    document.tegeneratepages.submit();
 }

function openPageHandler()
{
    var _inHTML = '';
	alert('in response');
	
    if(xmlHttp.readyState==4 && xmlHttp.status==200)
    {
    	alert('suceess');
    	document.tegeneratepages.submit();
    }
}
--></script>

<div class="errorDiv">
	<s:actionmessage />
	<s:actionerror />
</div>

<s:form action="tegeneratepages" method="post" theme="simple" name="tegeneratepages"
	cssClass="file_list_display_form">

	<s:if test="hasPrevious==true">
		<a href="tegeneratepages.action?previousValue=<s:property value="previousValue"/>" style="background:#D6ADEB;">Previous</a>
	</s:if>
	<s:iterator value="links" status="stat">
		<s:if test="#stat.count==boldedLink">
			<a href="tegeneratepages.action?linkNo=<s:property/>" style="background:#FFFF00;"><s:property /></a>
		</s:if>
		<s:else>
			<a href="tegeneratepages.action?linkNo=<s:property/>" style="background:#D6ADEB;"><s:property /></a>
		</s:else>
	</s:iterator>
	<s:if test="hasNext==true">
		<a href="tegeneratepages.action?nextValue=<s:property value="nextValue"/>" style="background:#D6ADEB;">Next</a>
	</s:if>

	<table class="file_list">
		<tr>
			<th><input type="checkbox" /></th>
			<th>Category</th>
			<th>Enrollment Id</th>
			<th>Validate</th>
			<th>Path</th>
			<th>Generate</th>
			<th>Status</th>
			<th>View Page</th>
		</tr>
		<s:iterator value="enrolledRecords">
			<tr>
				<td><input type="checkbox" name="enrollmentidcheckbox"
					value="<s:property value="uniqueenrollmentid" />" /></td>
				<td><s:property value="category" /></td>
				<td><s:property value="enrollmentid" /></td>
				<td><s:a onclick="javascript:setEnrollmentIDInSession(this,'Validate');">Validate</s:a></td>
				<td><%=ApplicationProperties.hm.get(Constants.PAGE_PATH)%></td>
				<td><s:a onclick="javascript:setEnrollmentIDInSession(this,'Generate');">Generate</s:a></td>
				<td><s:property value="pagecreatedstatus"/></td>
				<td><s:a onclick="javascript:setEnrollmentIDInSession(this,'View Page');">View Page</s:a></td>
			</tr>
		</s:iterator>
		<s:if test="enrolledRecords.size==0">
			<tr>
				<td colspan="6" align="center">No records found</td>
			</tr>
		</s:if>
	</table>
	<s:if test="enrolledRecords.size>0">
		<s:submit name="generatepage" value="Generate All"
			cssClass="extractpatterns" />
	</s:if>
</s:form>