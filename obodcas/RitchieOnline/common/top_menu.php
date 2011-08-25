	<!-- Header -->	
	<div id="header">
		<h1 id="logo"><a href="index.php">Ritchie Streets</a></h1>	
		
		<!-- helpline -->
		<div id="helpline">
			<a href="support.php" class="helpline-link"><u><b> Customer Support</b></u></a>
			<div class="cl">&nbsp;</div>
			<span><strong>+91 893 99 00 33 1</strong></span>
			<div class="cl">&nbsp;</div>
			<span><strong>+91 893 11 00 22 1</strong></span>
		</div>
		<!-- End helpline -->		
		<?php  
			$id=0;
			if(isset($_GET['id'])) {  
				$id=$_GET['id'];
			} else { $id=1; }
		?>		
		<!-- Navigation -->
		<div id="navigation">
			<ul>
			    <li><a href="index.php?id=1" <?php  if($id=='1') { ?> class="active" <?php } ?> >Home </a></li>
				<li><a href="vendors.php?id=2" <?php  if($id=='2') { ?> class="active" <?php } ?> >Vendor Shops</a></li>
			    <li><a href="servicecenters.php?id=3" <?php  if($id=='3') { ?> class="active" <?php } ?> >Service Centers</a></li>
			 <!--   <li><a href="computers.php?id=4" <?php  if($id=='4') { ?> class="active" <?php } ?> >Desktops </a></li>
			    <li><a href="laptops.php?id=5" <?php  if($id=='5') { ?> class="active" <?php } ?> >Laptops</a></li> 
				<li><a href="mobiles.php?id=6" <?php  if($id=='6') { ?> class="active" <?php } ?> >Mobiles</a></li>	 
				<li><a href="gadgets.php?id=7" <?php  if($id=='7') { ?> class="active" <?php } ?> >Gadgets</a></li>
				<li><a href="spares.php?id=8" <?php  if($id=='8') { ?> class="active" <?php }  ?> >Spares</a></li> 
				<li><a href="special.php?id=9" <?php  if($id=='9') { ?> class="active" <?php } ?> > New Release</a> </li>  -->
			</ul>
		</div>
		<!-- End Navigation -->
	</div>
	<!-- End Header -->