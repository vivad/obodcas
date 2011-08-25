<?php
	/*session_start();
	if(isset($_POST['areaKey'])){
		$_SESSION['areaKey'] = $_POST['areaKey'];
		echo $_SESSION['areaKey'];
	}*/
	
	include("common\connection.php");

	$queryString = '';
	if (isset($_GET['q'])) {
		$queryString = strtolower($_GET['q']);
		//$areaKey = $_SESSION['areaKey'];
		if(strlen($queryString) >0) {
			/*if(strlen($areaKey) > 0){
				$areaKeyArray = explode("-",$areaKey);
				$query = "SELECT vendorname FROM rit_vendors WHERE vendorarea IN (SELECT areacode FROM rit_areamaster WHERE cityname='$areaKeyArray[0]' AND areaname='$areaKeyArray[1]') AND vendorname LIKE '$queryString%'";
			}else{
				$query = "SELECT vendorname FROM rit_vendors WHERE vendorname LIKE '$queryString%'";
			}
			echo $query;*/

			$query = "SELECT vendorname FROM rit_vendors WHERE vendorname LIKE '$queryString%'";
			$result = mysql_query($query) or die("There is an error in database please contact support team");
			while($row = mysql_fetch_array($result)){
					echo "$row[0]\n";
			}
		}
		else{
		return;
		}
	}
?>
