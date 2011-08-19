<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>RitchieStreets.in | Online Digital Street | VivaLabs Production </title>
	<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
	<!--[if lte IE 6]><link rel="stylesheet" href="css/ie6.css" type="text/css" media="all" /><![endif]-->
	<meta name="google-site-verification" content="O_YVs2gKMtdMC9XNQ3UKPDwD82MNki_nW3J2qI7cJb0" />
	<meta name="keywords" content="Ritchiestreets.in, ritchie street, ritchiestreet, ritchie, street, ritchie street chennai, ritchistreet, ritchestreet,ritchie street chennai pricelist, chennai ritchie street price list, ritchie street price, ritchie street prices, ritchie street price list, ritchie street computershops, ritchie street chennai shops, ritchie street shop, ritchie street shops, ritchie street electronics, ritchie street website, electronic hub, chennai ritchie streets " />
	<meta name="description" content="ritchiestreets.in, Latest prices of computer peripherals in Ritchie Street Chennai.Complete information about all the computer shops in Ritchie street.Complete information about all the service centers in Ritchie street.Complete information about all the products in Ritchie street." />

	<!-- JS -->
	<script src="js/jquery-1.4.1.min.js" type="text/javascript"></script>
	<script src="js/jquery.jcarousel.pack.js" type="text/javascript"></script>
	<script src="js/jquery-func.js" type="text/javascript"></script>
	<!-- End JS -->

	<!-- Jquery Auto Complete -->
	<link rel="stylesheet" href="css/jquery.autocomplete.css" type="text/css" />
	<script type="text/javascript" src="js/jquery.autocomplete.js"></script>
	<!-- End of Auto Complete -->

	<script type="text/javascript">

			jQuery(document).ready(function () {
				jQuery("#cityId").autocomplete("getcitylist.php");
				jQuery("#vendorStrId").autocomplete("getVendorNameList.php");
			});
	</script>

</head>
<body>

<!-- Shell -->
<div class="shell">

<?php
	 require_once('common/top_menu.php');
 ?>

	<!-- Main -->
	<div id="main">
		<div class="cl">&nbsp;</div>

		<!-- Content -->
		<div id="content">

			<!-- Content Slider -->
			<?php
			require_once('common/sub-slider.php');
			?>
			<!-- End Content Slider -->
			<form method="post" action="vendors.php">
				<div id="searchDiv">
					<table border="0" width='100%'>
						<tr>
								<td> Area : <input type="text" name="city" id="cityId"></td>
								<td> Vendor <input type="text" name="vendorStr" id="vendorStrId"></td>
								<td> Keyword : <input type="text" name="keywordStr"></td>
								<td> <input type="submit" value="Search"> </td>
						</tr>
					</table>
				</div>
			</form>

			<?php
				$keyArea = $_POST['city'];
				$keyVendor = $_POST['vendorStr'];
				$keyKeyword = $_POST['keywordStr'];

				$queryString = '';
				$globalVendors = 'no';
				
				if(strlen($keyArea) != 0 || strlen($keyVendor) != 0 || strlen($keyKeyword) != 0){
					/*echo 'city: '.$keyArea;
					echo 'vendor name: '.$keyVendor;
					echo 'keyword: '.$keyKeyword;*/

				    if(strlen($keyArea) > 0 && strlen($keyVendor) == 0 && strlen($keyKeyword) == 0){
						$areaArray = explode("-",$keyArea);
						$queryString = "SELECT vendorname,vendordescription,address FROM rit_vendors WHERE vendorarea IN (SELECT areacode FROM rit_areamaster WHERE cityname='$areaArray[0]' AND areaname='$areaArray[1]')";
					}else if(strlen($keyArea) == 0 && strlen($keyVendor) > 0 && strlen($keyKeyword) == 0){
						$queryString = "SELECT vendorname,vendordescription,address FROM rit_vendors WHERE vendorname LIKE '$keyVendor%'";
					}else if(strlen($keyArea) == 0 && strlen($keyVendor) == 0 && strlen($keyKeyword) > 0){
						$queryString = " SELECT vendorname,vendordescription,address FROM rit_vendors 
										 WHERE vendorname LIKE '%$keyKeyword%' 
										 OR vendordescription LIKE '%$keyKeyword%' 
										 OR contactperson1 LIKE '%$keyKeyword%'
										 OR contactperson2 LIKE '%$keyKeyword%'
										 OR address LIKE '%$keyKeyword%'";
					}else if(strlen($keyArea) > 0 && strlen($keyVendor) > 0 && strlen($keyKeyword) == 0){
						$areaArray = explode("-",$keyArea);
						$queryString = "SELECT vendorname,vendordescription,address FROM rit_vendors WHERE 
										vendorarea IN (SELECT areacode FROM rit_areamaster WHERE cityname='$areaArray[0]' AND areaname='$areaArray[1]')
										OR vendorname LIKE '$keyVendor%'";
					}else if(strlen($keyArea) > 0 && strlen($keyVendor) == 0 && strlen($keyKeyword) > 0){
						$areaArray = explode("-",$keyArea);
						$queryString = "SELECT vendorname,vendordescription,address FROM rit_vendors WHERE 
										vendorarea IN (SELECT areacode FROM rit_areamaster WHERE cityname='$areaArray[0]' AND areaname='$areaArray[1]')
										OR vendorname LIKE '%$keyKeyword%'
										OR vendordescription LIKE '%$keyKeyword%' 
										OR contactperson1 LIKE '%$keyKeyword%' 
										OR contactperson2 LIKE '%$keyKeyword%' 
										OR address LIKE '%$keyKeyword%'";
					}else if(strlen($keyArea) == 0 && strlen($keyVendor) > 0 && strlen($keyKeyword) > 0){
						$areaArray = explode("-",$keyArea);
						$queryString = "SELECT vendorname,vendordescription,address FROM rit_vendors WHERE vendorname LIKE 'b%' 
										OR vendordescription LIKE '%b%' 
										OR contactperson1 LIKE '%b%' 
										OR contactperson2 LIKE '%b%' 
										OR address LIKE '%b%'";
					}else if(strlen($keyArea) > 0 && strlen($keyVendor) > 0 && strlen($keyKeyword) > 0){
						$areaArray = explode("-",$keyArea);
						$queryString = "SELECT vendorname,vendordescription,address FROM rit_vendors WHERE 
										vendorarea IN (SELECT areacode FROM rit_areamaster WHERE cityname='$areaArray[0]' AND areaname='$areaArray[1]')
										OR vendorname LIKE '$keyVendor%' 
										OR vendordescription LIKE '%$keyKeyword%' 
										OR contactperson1 LIKE '%$keyKeyword%'
										OR contactperson2 LIKE '%$keyKeyword%'
										OR address LIKE '%$keyKeyword%'";
					}
				}
				else{
					$globalVendors = 'yes';
					$queryString = " SELECT vendorname,vendordescription,address FROM rit_vendors WHERE enrolled='Yes'";
				}

				//echo $queryString;

				$countQueryArray = explode("SELECT vendorname,vendordescription,address",$queryString);
				$countQuery = "SELECT COUNT(*)".$countQueryArray[1];
				$totalCount = 0;

				//echo $countQuery;
				//echo $queryString;

				include("common\connection.php");
				$isResult = 'no';
				$recordCount = 0;

				$countResult = mysql_query($countQuery) or die("There is an error in database please contact support team");
				while($count = mysql_fetch_array($countResult)){
					echo "Row count:  ".$count[0];
					$totalCount = $count[0];
				}

				$output = "";

				if($totalCount > 0){
					$currenIndex = 1;
					$noOfRecPerPage = 5;
					$noOfLinksPerPage = 3;

					$totalLinks = $totalCount/$noOfRecPerPage;

					if(){
					}

					
					$output = "<div>";
				}

				
				if($totalCount > 0){
					$output = '<div class="vendors">';
					$output = $output.'<div class="cl">&nbsp;</div>';
					$output = $output.'<ul>';

					$result = mysql_query($queryString) or die("There is an error in database please contact support team");
					while($row = mysql_fetch_array($result)){
						$recordCount = $recordCount+1;
						$isResult = 'yes';
						//echo "$row[0]"." "."$row[1]"." "."$row[2]\n";
					
						if($recordCount == 3){
							$output = $output.'<li class="last">';
						}else{
							$output = $output.'<li>';
						}
						
						$output = $output.'<div class="vendors-info">';
						$output = $output.'<h3>'.$row[0].'</h3>';
						$output = $output.'<div class="vendors-desc">';
						$output = $output.'<p><b>'.$row[1].'</b><br/>'.$row[2].'</p>';
						$output = $output.'</div>';
						$output = $output.'</div>';
						$output = $output.'</li>';
					}

					$output = $output.'</ul><div class="cl">&nbsp;</div>';
					$output = $output.'</div>';
					$output = $output.'</div>';
				}


				if($globalVendors == 'no' && $totalCount == 0){
					$output = 'No vendors found for the given search';
				}else if($globalVendors == 'yes' && $totalCount == 0){
					$output = 'No vendors available';
				}

				echo $output;
			?>
			
		<!-- Sidebar -->
		<?php
			// require_once('common/sidebar.php');
		?>
		<!-- End Sidebar -->

		<div class="cl">&nbsp;</div>
	</div>
	<!-- End Main -->

<?php
	 require_once('common/footer.php');
 ?>