<?php
	include("common\connection.php");
	$queryString = '';
	if (isset($_GET['q'])) {

  	  $queryString = strtolower($_GET['q']);

  	  		if(strlen($queryString) >0) {
	  					$query = "SELECT DISTINCT concat(cityname,'-',areaname),areacode FROM rit_areamaster WHERE cityname LIKE '$queryString%'";
	  					$result = mysql_query($query) or die("There is an error in database please contact support team");
	  						while($row = mysql_fetch_array($result)){
									echo "$row[0]|$row[0]\n";
							}
			 }
			 else{
			 return;
			 }
	}

?>