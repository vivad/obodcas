<%@ page
	import="ritchie.backend.utilities.Constants,com.opensymphony.xwork2.ActionContext"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>

<link rel="stylesheet" type="text/css" href="/ritchie_backend/css/di_masters.css" title="1024px style" media="screen,projection" />
<SCRIPT LANGUAGE="JavaScript">
	function popUp(URL) {
	day = new Date();
	id = day.getTime();
	eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,width=400,height=400');");
	}
	</script>
<s:head />
</head>

<div class="errorDiv">
<s:if test="actionName=='diareamaster'">
	<s:actionmessage />
	<s:actionerror />
</s:if>
<s:if test="actionName=='divendortypemaster'">
	<s:actionmessage />
	<s:actionerror />
</s:if>
<s:if test="actionName=='diservicetypemaster'">
	<s:actionmessage />
	<s:actionerror />
</s:if>
					<s:if test="actionName=='diservicementypemaster'">
						<s:actionmessage />
						<s:actionerror />
					</s:if>
</div>
<div class="baseDiv">

<div class="leftDiv">

<s:form action="diareamaster" theme="simple" cssClass="master_areaform">
	<dl>
		<dt>City</dt>
		<dd><s:textfield name="city"/></dd>
	</dl>
	<dl>
		<dt>Area</dt>
		<dd><s:textfield name="area"/><span>View All</span></dd>
	</dl>
	<dl>
		<dt></dt>
		<dd><span class="space">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<s:reset value="Reset" cssClass="reset" /> <s:submit name="areabutton"
			value="Save" cssClass="save" /> <s:submit name="areabutton"
			value="Save & Send for Approval" cssClass="approval" /></dd>
	</dl>
</s:form>

</div>

<div class="rightDiv">
<s:form action="divendortypemaster" theme="simple" cssClass="other_form">
<dl>
	<dt>Vendor Type</dt>
	<dd><s:textfield name="vendorType"/><span>View All</span></dd>
</dl>
<dl>
	<dt></dt>
	<dd>
		<span class="space">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<s:reset value="Reset" cssClass="reset" /> 
		<s:submit name="vendorTypeButton" value="Save" cssClass="save" />
		<s:submit name="vendorTypeButton" value="Save & Send for Approval" cssClass="approval" />
	</dd>
</dl>
</s:form>

<s:form action="diservicetypemaster" theme="simple" cssClass="other_form">
<dl>
	<dt>Service Type</dt>
	<dd><s:textfield name="serviceType"/><span>View All</span></dd>
</dl>
<dl>
	<dt></dt>
	<dd>
		<span class="space">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<s:reset value="Reset" cssClass="reset" /> 
		<s:submit name="serviceTypeButton" value="Save" cssClass="save" />
		<s:submit name="serviceTypeButton" value="Save & Send for Approval" cssClass="approval" />
	</dd>
</dl>
</s:form>

<s:form action="diservicementypemaster" theme="simple" cssClass="servicemen_form">
<dl>
	<dt>Service Men Type</dt>
	<dd><s:textfield name="serviceMenType" cssClass="servicemen"/><span>View All</span></dd>
</dl>
<dl>
	<dt></dt>
	<dd>
		<span class="space">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<s:reset value="Reset" cssClass="reset" /> 
		<s:submit name="serviceMenTypeButton" value="Save" cssClass="save" />
		<s:submit name="serviceMenTypeButton" value="Save & Send for Approval" cssClass="approval" />
	</dd>
</dl>
</s:form>
				
</div>

</div>