$(document).ready(function()
{
	//Display Loading Image
	function Display_Load()
	{
		$("#loading").fadeIn(900,0);
		//$("#loading").html("<img src="bigLoader.gif" />");
	};


	//Hide Loading Image
	function Hide_Load()
	{
		$("#loading").fadeOut('slow');
		//alert('inside hide load()');
	};

	//Pagination Click
	$("#pagination a").click(function(){
		//alert('inside pagination div <a> click');
		Display_Load();
		//CSS Styles
		$("#pagination a")
		.css({'border' : 'solid #dddddd 1px'})
		.css({'color' : '#0063DC'});

		$(this)
		.css({'color' : '#FF0084'})
		.css({'border' : 'none'});

		loadSearchContent(this.id);
	});

	$("#firstAnchor").click(function(){
		var first_link_value = (this.name).split('&&')[0];
		var total_count = (this.name).split('&&')[1];
		loadSearchContent(first_link_value);
		
	});

	
	$("#lastAnchor").click(function(){
		var last_link_value = (this.name).split('&&')[0];
		var total_count = (this.name).split('&&')[1];
		loadSearchContent(last_link_value);
	});



	//$("#nextAnchor").click(function(){
	$("#paginationdiv #nextAnchor").click(function(){
		var next_link_value = (this.name).split('&&')[0];
		var total_count = (this.name).split('&&')[1];
		$("#paginationdiv").load("pagination_next.php?nextlinkvalue=" + next_link_value + "&totalcount=" + total_count, Hide_Load());
		loadSearchContent(next_link_value);
	});

	$("#previousAnchor").click(function(){
		var previous_link_value = (this.name).split('&&')[0];
		var total_count = (this.name).split('&&')[1];
		$("#paginationdiv").load("pagination_previous.php?previouslinkvalue=" + previous_link_value + "&totalcount=" + total_count, Hide_Load());
		loadSearchContent(previous_link_value);
	});

	function loadSearchContent(pageNum){
		var keyArea = $("#keyArea").val();
		var keyVendor = $("#keyVendor").val();
		var keyKeyword = $("#keyKeyword").val();
		$("#searchcontent").load("vendor_pagination_data.php?page=" + pageNum + "&keyArea=" + keyArea + "&keyVendor=" + keyVendor + "&keyKeyword=" +keyKeyword, Hide_Load());	
		//var query = escape($("#queryElement").val());
		//$("#searchcontent").load("vendor_pagination_data.php?page=" + pageNum + "&query=" + query, Hide_Load());
	}

	$("#previousAnchor")
		.css({'float' : 'left'});

	$("#pagination")
		.css({'float' : 'left'});

	$("#nextAnchor")
		.css({'float' : 'left'});
	
});