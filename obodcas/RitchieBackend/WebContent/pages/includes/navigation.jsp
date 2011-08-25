<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>


<head>
<link rel="stylesheet" type="text/css" href="/ritchie_backend/css/navigation.css" title="1024px style" media="screen,projection" />
</head>

<decorator:usePage id="thePage" />
<% String pageselection = thePage.getProperty("meta.pageselection"); %> 

<% if(pageselection != null && pageselection.equalsIgnoreCase("dataimport")){ %>
	<a href="dataimport.action?" style="background:#FFFF00;">Data Import</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="dataimport.action?" style="background:#D6ADEB;">Data Import</a>&nbsp;&nbsp;
<%
}
%>


<% if(pageselection != null && pageselection.equalsIgnoreCase("templateengine")){%>

	<a href="templateengine.action?" style="background:#FFFF00;">Template Engine</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="templateengine.action?" style="background:#D6ADEB;">Template Engine</a>&nbsp;&nbsp;
<%
}
%>


<% if(pageselection != null && pageselection.equalsIgnoreCase("managepages")){ %>
	<a href="managepages.action?" style="background:#FFFF00;">Manage Pages</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="managepages.action?" style="background:#D6ADEB;">Manage Pages</a>&nbsp;&nbsp;
<%
}
%>

<% if(pageselection != null && pageselection.equalsIgnoreCase("managedata")){ %>
	<a href="managedata.action?" style="background:#FFFF00;">Manage Data</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="managedata.action?" style="background:#D6ADEB;">Manage Data</a>&nbsp;&nbsp;
<%
}
%>

<% if(pageselection != null && pageselection.equalsIgnoreCase("managestaff")){ %>
	<a href="managestaff.action?" style="background:#FFFF00;">Manage Staff</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="managestaff.action?" style="background:#D6ADEB;">Manage Staff</a>&nbsp;&nbsp;
<%
}
%>

<% if(pageselection != null && pageselection.equalsIgnoreCase("manageschemes")){ %>
	<a href="manageschemes.action?" style="background:#FFFF00;">Manage Schemes</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="manageschemes.action?" style="background:#D6ADEB;">Manage Schemes</a>&nbsp;&nbsp;
<%
}
%>

<% if(pageselection != null && pageselection.equalsIgnoreCase("mailer")){ %>
	<a href="mailer.action?" style="background:#FFFF00;">Mailer</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="mailer.action?" style="background:#D6ADEB;">Mailer</a>&nbsp;&nbsp;
<%
}
%>

<% if(pageselection != null && pageselection.equalsIgnoreCase("stat")){ %>
	<a href="stat.action?" style="background:#FFFF00;">Stat</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="stat.action?" style="background:#D6ADEB;">Stat</a>&nbsp;&nbsp;
<%
}
%>

<% if(pageselection != null && pageselection.equalsIgnoreCase("settings")){ %>
	<a href="settings.action?" style="background:#FFFF00;">Settings</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="settings.action?" style="background:#D6ADEB;">Settings</a>&nbsp;&nbsp;
<%
}
%>

<% if(pageselection != null && pageselection.equalsIgnoreCase("reports")){ %>
	<a href="reports.action?" style="background:#FFFF00;">Reports</a>&nbsp;&nbsp;
<% 	
}else{%>
	<a href="reports.action?" style="background:#D6ADEB;">Reports</a>&nbsp;&nbsp;
<%
}
%>


<!--<a href="dataimport.action?">Data Import</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="templateengine.action?">Template Engine</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="managepages.action?">Manage Pages</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="managedata.action?">Manage Data</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="managestaff.action?">Manage Staff</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="manageschemes.action?">Manage Schemes</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="mailer.action?">Mailer</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="stat.action?">Stat</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="settings.action?">Settings</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="reports.action?">Reports</a>&nbsp;&nbsp;&nbsp;&nbsp;
-->