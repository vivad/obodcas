<%@ page import="ritchie.backend.utilities.Constants"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<link rel="stylesheet" type="text/css"
	href="/ritchie_backend/css/te_filedetails.css" title="1024px style"
	media="screen,projection" />
</head>

<s:if test="actionName=='temanagetemplate'">
	<div class="errorMsgDiv"><s:actionmessage /> <s:actionerror /></div>
</s:if>

<s:form action="temanagetemplate" method="post" theme="simple" 
	cssClass="file_list_display_form">

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
				<td><input type="checkbox" name="checkbox" value="<s:property value="FILENO" />"/></td>
				<td><s:property value="FILENO" /></td>
				<td><s:property value="FILENAME" /></td>
				<td><s:property value="UPLOADEDBY" /></td>
				<td><s:property value="STATUS" /></td>
				<td><s:property value="UPLOADEDDATE" /></td>
			</tr>
		</s:iterator>
		<s:if test="filedetailsexist==false">
			<tr>
				<td colspan="6" align="center">No Template exists to manage</td>
			</tr>
		</s:if>
	</table>
	
	<s:submit name="button" value="Make Inactive"
		cssClass="inactivebutton" />

	<s:submit name="button" value="Make Active"
		cssClass="activebutton" />
</s:form>