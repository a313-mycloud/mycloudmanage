
$(document).ready(function(){	

	
	
	$(".prePage").click(function(){
	    var url="/admin/account/list";
	    var currentPage=$("#page").attr("currentPage");
	    if(Number(currentPage)>1){
		   var current=Number(currentPage)-1;
		   window.location.replace(url+"?currentPage="+current+"&role="+$("#role").val());
		}
		else
	  	  alert("没有上一页");
	});
	$(".nextPage").click(function(){
	    var url="/admin/account/list";
	    var currentPage=$("#page").attr("currentPage");
	    var totalPage=$("#page").attr("totalPage");
	    if(Number(currentPage)<Number(totalPage)){
			var current=Number(currentPage)+1;
			window.location.replace(url+"?currentPage="+current+"&role="+$("#role").val());
	  }
	 else
	    alert("没有下一页");    
	});
	$(".firstPage").click(function(){
	     var url="/admin/account/list";
	     var currentPage=$("#page").attr("currentPage");
	     if(Number(currentPage)==1)
			alert("已经是首页");
		else
			window.location.replace(url+"?currentPage=1&role="+$("#role").val());
	});
	$(".lastPage").click(function(){
	     
	    var url="/admin/account/list";
	    var currentPage=$("#page").attr("currentPage");
	    var totalPage=$("#page").attr("totalPage");
	    
	    if(Number(currentPage)==Number(totalPage))
		   alert("已经是末页");
	    else
	         window.location.replace(url+"?currentPage="+totalPage+"&role="+$("#role").val());
	});
	$(".add").click(function(){
		add('/admin/account/add.do',{role:$("#role").val(),account:$("#account").val()},"/admin/account/list?role="+$("#role").val());
	});
	$(".reset").click(function(){
		$("#account").val("");
	});	
	
	$(".remove").click(function() {
		remove("/admin/account/remove.do",{account:$(this).attr('account')},"/admin/account/list?role="+$("#role").val());
	});

});
