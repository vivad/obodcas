<?php  
	$con = mysql_connect("localhost","ritchie","gegwvtb") or die ('Error Connectiong to mysql: '.mysql_error());
	$dbname = "ritchie";
	mysql_select_db($dbname,$con) or die ("Select Error: ".mysql_error());
	
	?>
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
			
			<!-- index-content -->
			<div class="vendors">
			<?php 
			$vendorlistsql = mysql_query("select vendorid, vendorarea, vendortype, vendorname, vendordescription, contactperson1, contactperson2, telephone1, telephone2, telephone3, mobile1, mobile2, mobile3, fax, address, email1, email2, email3, website, mapdata, createdfrom, created_date, createdby, enrolled, status, approved, draft from rit_vendors limit 0,30") or die(mysql_error());
			?>
			
				<div class="cl">&nbsp;</div>
				<?php
				$colcount=3;
				$rowcount=10;
				
				while($row = mysql_fetch_array($vendorlistsql ,MYSQL_BOTH))  { 
				
				 if($columncount==3) {
					echo "<ul>";
				 }
					
					echo "<li> ";
				    echo "	<div class='vendors-info'> ";
				    echo "		<h3> ".$row['vendorname']."</h3> ";
				    echo "		<div class='vendors-desc'> ";
					echo "			<h4>".$row['vendortype']."</h4> ";
				    echo "			<p> ".$row['contactperson1']. "," .$row['contactperson2']." <br/> ";
					echo " 			</p> ";
				    echo "		</div>";
				    echo "	</div>";
			    	echo " </li>" ;
					$columncount=$coulmncount-1;
				 if($columncount==0) {
				 echo "</ul>";
					$columncount=3;
				 }

				
				}
				?>
				
				
				<ul>
				    <li>
				    	<div class="vendors-info">
				    		<h3>.{VendorName}</h3>
				    		<div class="vendors-desc">
								<h4>abc</h4>
				    			<p></p>
				    		</div>
				    	</div>
			    	</li>
			        <li>
				    	<div class="vendors-info">
				    		<h3>.{VendorName}</h3>
				    		<div class="vendors-desc">
								<h4>abc</h4>
				    			<p></p>
				    		</div>
				    	</div>
			    	</li>
			    	<li class="last">
				    	<div class="vendors-info">
				    		<h3>Omega Electronix</h3>
				    		<div class="vendors-desc">								
				    			<p>
2-D(GF-8) Wallers Road, Anjuman Building, Mount Road
Chennai , Tamil Nadu
India,600002<br/>  -044.4234567
								</p>
				    		</div>
				    	</div>
			    	</li>
				</u><div class="cl">&nbsp;</div>
				
			</div>

			<!-- End index-content -->
			
		</div>
		<!-- End Content -->
		
		<!-- Sidebar -->
		<?php 
			require_once('common/sidebar.php');
		?>	
		<!-- End Sidebar -->
		
		<div class="cl">&nbsp;</div>
	</div>
	<!-- End Main -->
<?php 
	 require_once('common/footer.php');
 ?>	