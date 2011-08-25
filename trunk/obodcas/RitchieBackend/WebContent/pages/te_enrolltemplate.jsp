<%@ page import="ritchie.backend.utilities.Constants"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<link rel="stylesheet" type="text/css"
	href="/ritchie_backend/css/te_filedetails.css" title="1024px style"
	media="screen,projection" />
</head>

<s:if test="actionName=='teenrolltemplate'">
	<div class="errorMsgDiv"><s:actionmessage /> <s:actionerror /></div>
</s:if>

<s:form action="teenrolltemplate" method="post" theme="simple"
	enctype="multipart/form-data" cssClass="upload_form">

	<div class="title_box">
	<table>
		<tr>
			<td>Template Category</td>
			<td><s:select name="category" list="{'-- select --','Vendors','Service Centers','Service Men'}" /></td>
			<td>Upload File</td>
			<td><s:file name="upload" /></td>
			<td><s:submit value="Upload" cssClass="button" /></td>
		</tr>
	</table>

	</div>
</s:form>

<s:if test="actionName=='teextractpattern'">
	<div class="errorMsgDiv"><s:actionmessage /> <s:actionerror /></div>
</s:if>

<s:form action="teextractpattern" method="post" theme="simple"
	cssClass="file_list_display_form">
	<input name="pagefrom" type="hidden" value="enrolltemplate" />
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