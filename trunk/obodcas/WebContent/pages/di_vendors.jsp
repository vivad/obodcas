<%@ page import="ritchie.backend.utilities.Constants"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<head>
<sx:head />
<link rel="stylesheet" type="text/css"
	href="/ritchie_backend/css/di_upload.css" title="1024px style"
	media="screen,projection" />
</head>

<div class="baseDiv">

<div class="title_box">Bulk Upload</div>

<s:if test="actionName=='divendorsupload'">
	<div class="errorMsgDiv"><s:actionmessage /> <s:actionerror /></div>
</s:if>

<s:form action="divendorsupload" method="post" theme="simple"
	enctype="multipart/form-data" cssClass="upload_form">
	<dl>
		<dt>Upload File</dt>
		<dd><s:file name="upload" /><s:submit value="Upload"
			cssClass="button" /> <s:a
			href="ditemplatedownload.action?pagename=vendors" cssClass="template">Download the template</s:a>
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

<s:if test="actionName=='divendordataentry'">
	<div class="errorMsgDiv"><s:actionmessage /> <s:actionerror /></div>
</s:if>

<s:form action="divendordataentry" id="divendordataentry_form"
	cssClass="dataentry_form" theme="simple">
	<s:url id="areaurl" value="/divendorgetarea.action" />
	<s:url id="vendortypeurl" value="/divendorgetvendortype.action" />
	<table>
		<tr>
			<td><s:label>Vendor Area *</s:label></td>
			<td><sx:autocompleter dataFieldName="vendorareamap"
				name="vendorarea" keyName="areacode" href="%{areaurl}"
				autoComplete="true" loadOnTextChange="true" loadMinimumCount="1"
				formId="divendordataentry_form" searchType="startstring"
				preload="true" dropdownWidth="158" /></td>
			<td><s:label>Vendor Mobile No 1 *</s:label></td>
			<td>
			<table>
				<tr>
					<td><s:textfield name="vendormobile1" /></td>
					<td><s:label>Vendor Email id 1</s:label></td>
					<td><s:textfield name="vendoremail1" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td><s:label>Vendor Type *</s:label></td>
			<td><sx:autocompleter dataFieldName="vendortypemap"
				name="vendortype" keyName="vendortypecode" href="%{vendortypeurl}"
				autoComplete="true" loadOnTextChange="true" loadMinimumCount="1"
				formId="divendordataentry_form" preload="true"
				searchType="startstring" dropdownWidth="158" /></td>
			<td><s:label>Vendor Mobile No 2</s:label></td>
			<td>
			<table>
				<tr>
					<td><s:textfield name="vendormobile2" /></td>
					<td><s:label>Vendor Email id 2</s:label></td>
					<td><s:textfield name="vendoremail2" /></td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td><s:label>Vendor Name *</s:label></td>
			<td><s:textfield name="vendorname" /></td>
			<td><s:label>Vendor Mobile No 3</s:label></td>
			<td>
			<table>
				<tr>
					<td><s:textfield name="vendormobile3" /></td>
					<td><s:label>Vendor Website</s:label></td>
					<td><s:textfield name="vendorwebsite" /></td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td><s:label>Vendor Description</s:label></td>
			<td><s:textarea name="vendordescription" /></td>
			<td>
			<table>
				<tr>
					<td><s:label>Vendor Address *</s:label></td>
				</tr>
				<tr>
					<td><s:label>Vendor Map Data</s:label></td>
				</tr>
			</table>
			</td>
			<td>
			<table>
				<tr>
					<td><s:textfield name="vendoraddress" /></td>
				</tr>
				<tr>
					<td><s:textfield name="vendormapdata" /></td>
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
					<td><s:label>Vendor Contact Person 1</s:label></td>
					<td><s:textfield name="vendorcontactperson1" /></td>
				</tr>
				<tr>
					<td><s:label>Vendor Contact Person 2</s:label></td>
					<td><s:textfield name="vendorcontactperson2" /></td>
				</tr>
				<tr>
					<td><s:label>Vendor Telephone 1 *</s:label></td>
					<td><s:textfield name="vendortelephone1" /></td>
				</tr>
				<tr>
					<td><s:label>Vendor Telephone 2</s:label></td>
					<td><s:textfield name="vendortelephone2" /></td>
				</tr>
				<tr>
					<td><s:label>Vendor Telephone 3</s:label></td>
					<td><s:textfield name="vendortelephone3" /></td>
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
					<td><s:label>Catalogue Enrolled</s:label></td>
					<td><s:select name="catalogueenrolledstatus"
						list="{'No','Yes'}" /></td>
					<td><s:label>Vendor Active</s:label></td>
					<td><s:select name="vendoractivestatus" list="{'Yes','No'}" />
					</td>
				</tr>
				<tr>
					<td><s:label>Search Vendor</s:label></td>
					<td colspan="2"><s:textfield name="vendoridforsearch">
					</s:textfield></td>
					<td><s:submit value="Search" name="searchbutton"/></td>
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
