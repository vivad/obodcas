$(document).ready(function()
{
	//Display Loading Image
	function Display_Load()
	{
		$("#loading").fadeIn(900,0);
	};

	//Hide Loading Image
	function Hide_Load()
	{
		$("#loading").fadeOut('slow');
		//alert('inside hide load()');
	};

	//Default Starting Page Results
	$("#pagination a:first")
	.css({'color' : '#FF0084'})
	.css({'border' : 'none'});
	Display_Load();
	//var query = escape($("#queryElement").val());
	//$("#searchcontent").load("vendor_pagination_data.php?page=1&query=" + query, Hide_Load());	
	var keyArea = $("#keyArea").val();
	var keyVendor = $("#keyVendor").val();
	var keyKeyword = $("#keyKeyword").val();
	$("#searchcontent").load("vendor_pagination_data.php?page=1&keyArea=" + keyArea + "&keyVendor=" + keyVendor + "&keyKeyword=" +keyKeyword, Hide_Load());	
});