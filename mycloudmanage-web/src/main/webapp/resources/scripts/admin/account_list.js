
$(document).ready(function(){	

	$(".prePage").click(function(){
	     showPrePage("/admin/account/list",$("#page").attr("currentPage"));
	});
	$(".nextPage").click(function(){
	     showNextPage("/admin/account/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	$(".firstPage").click(function(){
	     showFirstPage("/admin/account/list",$("#page").attr("currentPage"));
	});
	$(".lastPage").click(function(){
	     showLastPage("/admin/account/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	
	$(".add").click(function(){
		add('/admin/account/add.do',{role:$("#role").val(),account:$("#account").val()},"/admin/account/list");
	});
	$(".reset").click(function(){
		$("#account").val("");
	});	
	
	$(".remove").click(function() {
		remove("/admin/account/remove.do",{account:$(this).attr('account')},"/admin/account/list");
	});

});
