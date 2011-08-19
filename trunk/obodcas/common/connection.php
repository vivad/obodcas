<?php
# Type="MYSQL"
# HTTP="true"


############# online database host
#$hostname_ritchie = "mysql";
#$database_ritchie = "ritchie";
#$username_ritchie = "ritchie";
#$password_ritchie = "gegwvtb";

############# localhost database host
#$hostname_ritchie = 'localhost';
#$database_ritchie = 'ritchie';
#$username_ritchie = 'root';
#$password_ritchie = '';
#$ritchie = mysql_connect($hostname_ritchie, $username_ritchie, $password_ritchie) or trigger_error(mysql_error(),E_USER_ERROR);
#mysql_select_db($database_ritchie,$ritchie);



	$con = mysql_connect("localhost","root","mysql") or die ('Error Connectiong to mysql: '.mysql_error());
	$dbname = "ritchie";
	mysql_select_db($dbname,$con) or die ("Select Error: ".mysql_error());






?>