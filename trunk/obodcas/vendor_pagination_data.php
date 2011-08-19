<?php
include("common\connection.php");
include("pagination_constants.php");

if($_GET)
{
	$page=$_GET['page'];
	$query = $_GET['query'];

	//echo 'page number::::  '.$page;
}

$start = ($page-1)*$per_page;
$sql = $query." limit $start,$per_page";
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
