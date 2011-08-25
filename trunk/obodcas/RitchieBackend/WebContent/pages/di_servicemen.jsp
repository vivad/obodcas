<%@ page import="ritchie.backend.utilities.Constants"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<head>
<sx:head />
<link rel="stylesheet" type="text/css" href="/ritchie_backend/css/di_upload.css" title="1024px style" media="screen,projection">
</head>

<div class="baseDiv">

<div class="title_box">Bulk Upload</div>

<s:if test="actionName=='diservicemenupload'">
	<div class="errorMsgDiv"><s:actionmessage /> <s:actionerror /></div>
</s:if>

<s:form action="diservicemenupload" method="post" theme="simple"
	enctype="multipart/form-data" cssClass="upload_form">

	<dl>
		<dt>Upload File</dt>
		<dd><s:file name="upload" /><s:submit value="Upload"
			cssClass="button" /> <s:a
			href="ditemplatedownload.action?pagename=servicemen" cssClass="template">Download the template</s:a>
		</dd>
	</dl>

	
	<table class="file_list">
		<tr>
			<th>Token No</th>
			<th>File Name</th>
			<th>Uploaded By</th>
			<th>Status</th>
			<th>Date Uploaded</th>
		</tr>
		<s:iterator value="fileDetailsList">
			<tr>
				<td><s:property value="FILENO" /></td>
				<td><s:property value="FILENAME" /></td>
				<td><s:property value="UPLOADEDBY" /></td>
				<td><s:property value="STATUS" /></td>
				<td><s:property value="UPLOADEDDATE" /></td>
			</tr>
		</s:iterator>
	</table>
</s:form>

<div class="title_box">Data Entry</div>

<s:if test="actionName=='diservicemendataentry'">
	<div class="errorMsgDiv"><s:actionmessage /> <s:actionerror /></div>
</s:if>

<s:form action="diservicemendataentry" id="diservicemendataentry_form"
	cssClass="dataentry_form" theme="simple">
	<s:url id="areaurl" value="/divendorgetarea.action" />
	<s:url id="servicementypeurl" value="/diservicemengetservicementype.action" />
	<table>
		<tr>
			<td><s:label>Area *</s:label></td>
			<td><sx:autocompleter dataFieldName="vendorareamap"
				name="vendorarea" keyName="areacode" href="%{areaurl}"
				autoComplete="true" loadOnTextChange="true" loadMinimumCount="1"
				formId="diservicemendataentry_form" searchType="startstring"
				preload="true" dropdownWidth="158" /></td>
			<td><s:label>Mobile No 1 *</s:label></td>
			<td>
			<table>
				<tr>
					<td><s:textfield name="mobile1" /></td>
					<td><s:label>Email id 1</s:label></td>
					<td><s:textfield name="email1" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td><s:label>Service Men Type *</s:label></td>
			<td><sx:autocompleter dataFieldName="servicementypemap"
				name="servicementype" keyName="servicementypecode" href="%{servicementypeurl}"
				autoComplete="true" loadOnTextChange="true" loadMinimumCount="1"
				formId="diservicemendataentry_form" preload="true"
				searchType="startstring" dropdownWidth="158" /></td>
			<td><s:label>Mobile No 2</s:label></td>
			<td>
			<table>
				<tr>
					<td><s:textfield name="mobile2" /></td>
					<td><s:label>Email id 2</s:label></td>
					<td><s:textfield name="email2" /></td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td><s:label>Name *</s:label></td>
			<td><s:textfield name="name" /></td>
			<td><s:label>Telephone *</s:label></td>
			<td>
			<table>
				<tr>
					<td><s:textfield name="telephone" /></td>
					<td><s:label>Website</s:label></td>
					<td><s:textfield name="website" /></td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td><s:label>Description of service *</s:label></td>
			<td><s:textarea name="description" /></td>
			<td>
			<table>
				<tr>
					<td><s:label>Address *</s:label></td>
				</tr>
				<tr>
					<td><s:label>Map Data</s:label></td>
				</tr>
			</table>
			</td>
			<td>
			<table>
				<tr>
					<td><s:textfield name="address" /></td>
				</tr>
				<tr>
					<td><s:textfield name="mapdata" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td colspan="2">
			<table>
				<tr>
					<td><s:label>Area of Coverage *</s:label></td>
					<td><s:textfield name="coveragearea" /></td>
				</tr>
			</table>
			</td>
			<td colspan="2">
			<table>
				<tr>
					<td colspan="4">
					<hr></hr>
					</td>
				</tr>
				<tr>
					<td><s:label>Service Enrolled</s:label></td>
					<td><s:select name="serviceenrolledstatus"
						list="{'No','Yes'}" /></td>
					<td><s:label>Active</s:label></td>
					<td><s:select name="activestatus" list="{'Yes','No'}" />
					</td>
				</tr>
				<tr>
					<td><s:label>Search Service Men</s:label></td>
					<td colspan="2"><s:textfield name="idforsearch">
					</s:textfield></td>
					<td><s:submit value="Search" name="searchbutton" /></td>
				</tr>
				<tr>
					<td colspan="4">
					<hr></hr>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td colspan="2"><s:reset value="Reset" cssClass="reset" /> <s:submit
						name="dataentrybutton" value="Save" cssClass="save" /> <s:submit
						name="dataentrybutton" value="Draft" cssClass="save" /> <s:submit
						name="dataentrybutton" value="Save & Send for Approval"
						cssClass="approval" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</div>
