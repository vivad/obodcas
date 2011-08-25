<script src="js/jquery_pagination.js" type="text/javascript"></script>
<?php
	include("pagination_constants.php");

	$next_link_value = $_GET['nextlinkvalue'];
	$total_count = $_GET['totalcount'];

	$page_Display_Start_Count = $next_link_value;

	$hasPrevious = true;
	$embed_previous_link_value = $page_Display_Start_Count-$links_per_page;

	$hasNext = false;

	if($total_count > (($page_Display_Start_Count-1)+ $links_per_page)*$per_page){
		$hasNext = true;
		$embed_next_link_value = $page_Display_Start_Count+$links_per_page;
	}

	if($hasPrevious == true){
?>
		<a href="#" id="previousAnchor" name="<?php echo $embed_previous_link_value.'&&'.$total_count; ?>">Previous</a> 
		<span style="float:left;">&nbsp;&nbsp;&nbsp;</span>
<?php
	}
?>

	<div id="pagination">
	
	<?php
	for($i = $page_Display_Start_Count; $i <= ($page_Display_Start_Count-1)+$links_per_page ; $i++){
		if($total_count-(($i-1)*$per_page) > 0){
?>

			&nbsp;<a href="#" id="<?php echo $i; ?>"><?php echo $i; ?></a>&nbsp;

<?php
		}else{
			break;
		}
	}

	?>
		</div>
<?php
		if($hasNext == true){
?>
		<span style="float:left;">&nbsp;&nbsp;&nbsp;</span>
		<a href="#" id="nextAnchor" name="<?php echo $embed_next_link_value.'&&'.$total_count; ?>">Next</a>
<?php
	}
?>




