
<script src="js/jquery_pagination.js" type="text/javascript"></script>

<?php
	include("pagination_constants.php");

	$previous_link_value = $_GET['previouslinkvalue'];
	$total_count = $_GET['totalcount'];

	$page_Display_Start_Count = $previous_link_value;

	$hasPrevious = false;
	$embed_previous_link_value = 0;

	if($page_Display_Start_Count != 1 && $page_Display_Start_Count > $links_per_page){
		$hasPrevious = true;

		if($page_Display_Start_Count/$links_per_page >= 1){
			$embed_previous_link_value = $page_Display_Start_Count-$links_per_page;
		}
	}

	$hasNext = true;
	$embed_next_link_value = $page_Display_Start_Count+$links_per_page;

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
?>
		&nbsp;<a href="#" id="<?php echo $i; ?>"><?php echo $i; ?></a>&nbsp;
<?php 
	}
?>
		</div>
<?php
	if(hasNext == true){
?>
		<span style="float:left;">&nbsp;&nbsp;&nbsp;</span>
		<a href="#" id="nextAnchor" name="<?php echo $embed_next_link_value.'&&'.$total_count; ?>">Next</a>
<?php
	}
?>



