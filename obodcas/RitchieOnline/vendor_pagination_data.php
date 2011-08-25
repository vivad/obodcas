<?php
include("common\connection.php");
include("pagination_constants.php");

if($_GET)
{
	$page=$_GET['page'];
	//$query = $_GET['query'];
	$keyArea = $_GET['keyArea'];
	$keyVendor = $_GET['keyVendor'];
	$keyKeyword = $_GET['keyKeyword'];

	//echo 'page number::::  '.$page;
}


$queryString = '';

if(strlen($keyArea) != 0 || strlen($keyVendor) != 0 || strlen($keyKeyword) != 0){

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
	$queryString = " SELECT vendorname,vendordescription,address FROM rit_vendors WHERE enrolled='Yes'";
}

$start = ($page-1)*$per_page;
$sql = $queryString." limit $start,$per_page";
//echo '<br>$sql:  '.$sql;
$result = mysql_query($sql);
?>
<div class="vendors">
	<div class="cl">&nbsp;</div>
	<ul>
	
<?php
	$i = 0;
	while($row = mysql_fetch_array($result))
	{
		$vname = $row[0];
		$vdes = $row[1];
		$vadd = $row[2];

		if(++$i%3 == 0){
?>
			<li class="last">
<?php
			
		}
			else{
?>
				<li>
<?php
			}
?>
	
			<div class="vendors-info">
				
				<h3><?php echo $vname; ?></h3>
				<div class="vendors-desc">
					<p><b> <?php echo $vdes; ?></b><br/><?php echo $vadd; ?></p>
				</div>
			</div>
		</li>

<?php
	}
?>
	</ul>
	<div class="cl">&nbsp;</div>
</div>
