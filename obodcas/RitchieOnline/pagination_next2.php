
<head>
<script src="js/jquery_pagination.js" type="text/javascript"></script>
</head>

<body>

<?php
	include("pagination_constants.php");

	$next_link_value = $_GET['nextlinkvalue'];
	$total_count = $_GET['totalcount'];

	echo 'Next Link Value:  '.$next_link_value;
	echo 'Total Count:  '.$total_count;

	$previous_link_value = ($next_link_value-$links_per_page);
	$page_Display_Start_Count = ($total_count-($per_page*($next_link_value-1)))/$per_page;

	echo 'page_Display_Start_Count:  '.$page_Display_Start_Count;

	if(($total_count-($per_page*($next_link_value-1)))%($page_Display_Start_Count*$per_page) > 0){
		$page_Display_Start_Count += 1;
	}

	echo 'page_Display_Start_Count:  '.$page_Display_Start_Count;

	if($previous_link_value == 0){
		$previous_link_value = 1;
	}
?>

<a href="#" id="previousAnchor" name="<?php echo $previous_link_value.'&&'.$total_count; ?>">Previous</a>
<ul id="pagination">

<?php
	for($i = $next_link_value; $i < ($next_link_value + $page_Display_Start_Count); $i++){
?>
	<li id="<?php echo $i; ?>"><?php echo $i; ?></li>
<?php 
	}
?>


</ul>

</body>
