$(document).ready(function(){	
	$(".remove").click(function() {
		remove($(this).attr("hostId"));
	});
	$(".add").click(function(){
		add($("#hostName").val(),$("#hostIp").val());
	});
	$(".edit").click(function(){
	    edit($("#hostId").val(),$("#hostName").val(),$("#hostIp").val());
	});
	$(".reset").click(function(){
		$("#hostName").val("");
		$("#hostIp").val("");
	});	
	$(".resetEdit").click(function(){
		$("#hostName").val($("#preName").val());
		$("#hostIp").val($("#preIp").val());
	});	
	
});
function remove(id){
	if(confirm("确定删除？")){
		$.ajax({
		 url:'/admin/host/remove',
		 data:{"hostId":id},
		 dataType:"json",
		 success:function(data){
		 	
		 	if(!data.isLogin){
		 		alert("请登陆");
		 		window.location.replace("/login");
		 	}
		 	else if(!data.isAuth){
		 		alert("您没有权限");
		 	}
		 	else{
		 		if(!data.isSuccess){
		 			alert(data.message);
		 		}
		 		else{
		 			window.location.replace("/admin/host/list?currentPage=1&perPage=5");
		 		}
		 	}
		 },
		 error:function(data,status){
		 	alert(status);
		 } 
	});		
	}
}
function add(hostName,hostIp){
	$.ajax({
		 type:"POST",
		 url:'/admin/host/add',
		 data:{"hostName":hostName,"hostIp":hostIp},
		 dataType:"json",
		 success:function(data){
		 	if(!data.isLogin){
		 		alert("请登陆");
		 		window.location.replace("/login");
		 	}
		 	else if(!data.isAuth){
		 		alert("您没有权限");
		 	}
		 	else{
		 		if(!data.isSuccess){
		 			alert(data.message);
		 		}
		 		else{
		 			window.location.replace("/admin/host/list?currentPage=1&perPage=5");
		 		}
		 	}
		 },
		 error:function(data,status){
		 	alert(status);
		 } 
	});	
}
function edit(hostId,hostName,hostIp){
	$.ajax({
		 url:'/admin/host/edit',
		 data:{"hostId":hostId,"hostName":hostName,"hostIp":hostIp},
		 dataType:"json",
		 success:function(data){
		 	if(!data.isLogin){
		 		alert("请登陆Login");
		 		window.location.replace("/login");
		 	}
		 	else if(!data.isAuth){
		 		alert("您没有权限Auth");
		 	}
		 	else{
		 		if(!data.isSuccess){
		 			alert(data.message);
		 		}
		 		else{
		 			window.location.replace("/admin/host/list?currentPage=1&perPage=5");
		 		}
		 	}
		 },
		 error:function(data,status){
		 	alert(status);
		 	
		 } 
	});	
}



