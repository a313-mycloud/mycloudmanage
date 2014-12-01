$(document).ready(function(){	
	$(".remove").click(function() {
		remove($(this).attr("hostId"));
	});
	$(".add").click(function(){
		add($("#hostName").val(),$("#hostIp").val());
	});
	$(".edit").click(function(){
	    edit($("#hostId"),$("#hostName").val(),$("#hostIp").val());
	});
	
});
function remove(id){
	if(confirm("确定删除？")){
		$.ajax({
		 url:'/admin/host/remove',
		 data:{"hostId":id},
		 dataType:"json",
		 success:function(data){
		 	
		 	if(data.isLogin){
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
		 			window.location.replace("/admin/host/list");
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
		 			window.location.replace("/admin/host/list");
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
		 data:{"hostId":id,"hostName":hostName,"hostIp":hostIp},
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
		 			window.location.replace("/admin/host/list");
		 		}
		 	}
		 },
		 error:function(data,status){
		 	alert(status);
		 } 
	});	
}


