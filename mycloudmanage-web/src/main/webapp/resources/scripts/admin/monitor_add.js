
$(document).ready(function(){	
	$("#monitor_add_btn").click(function(){
		var data = {"aliaseName" : $("#aliaseName").val(), "ip" : $("#ip").val()};
		addMonitor("/admin/monitor/add.do", data, "/admin/monitor/list?currentPage=1");
	});
});

function addMonitor(url, data, replace) {
	$.ajax({
		 url:url,
		 data:data,
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
		 			window.location.replace(replace);
		 		}
		 	}
		 },
		 error:function(data,status){
		 	alert(status);
		 } 
	});		
}