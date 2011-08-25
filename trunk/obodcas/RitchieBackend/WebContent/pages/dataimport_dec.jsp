<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<div class="panel">
<div class="panel-title"><decorator:title /></div>

<table width="50%" border="1" cellspacing="0" cellpadding="0">

	<tr>
		<td width="16%" hight="20" valign="top" nowrap><script
			type="text/javascript">window.status = "Loading: Navigation...";</script>
		<%@ include file="/pages/includes/dataimport_navigation.jsp"%>
		</td>
	</tr>
	<tr>
		<td valign="top"><br>
		<script type="text/javascript">window.status = "Loading: Document body...";</script>
		<!--<div class="docBody"><decorator:body /></div>--> <!-- <div><decorator:body /></div>-->
		<decorator:body /></td>
	</tr>
</table>
</div>