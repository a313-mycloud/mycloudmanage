$(document).ready(function(){	
	refresh();
});

function refresh() {
	$.ajax({
		 url:"/admin/monitor/getlist.do",
		 data:"",
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
		 			render(JSON.parse(data.data));
		 	//		setTimeout("refresh()", 2000);
		 		}
		 	}
		 },
		 error:function(data,status){
		 	alert(status);
		 } 
	});		
}

function render(monitorDataList) {
	$("#tbody").find(".content_tr").remove();
	for (var i = 0; i < monitorDataList.length; i++) {
		var tr = createTrByPerformanceMonitorDTO(monitorDataList[i]);
		if (i % 2 == 0) {
			tr.attr("class", "content_tr alt-row");
		}
		$("#tbody").append(tr);
	}
}

function createTrByPerformanceMonitorDTO(performanceMonitorDTO) {
	var tr = $("#template_tr").clone();
	tr.attr("id", performanceMonitorDTO.id);
	tr.attr("class", "content_tr");
	tr.removeAttr("style");
	tr.find(".aliaseName").html(performanceMonitorDTO.aliaseName);
	tr.find(".ip").html(performanceMonitorDTO.ip);
	tr.find(".status").html(performanceMonitorDTO.status);
	tr.find(".cores").html(performanceMonitorDTO.cores);
	tr.find(".loadAverage").html(performanceMonitorDTO.loadAverage);
	tr.find(".totalMem").html(performanceMonitorDTO.totalMem);
	tr.find(".usedMem").html(performanceMonitorDTO.usedMem);
	tr.find(".sendRate").html(performanceMonitorDTO.sendRate);
	tr.find(".receiveRate").html(performanceMonitorDTO.receiveRate);
	var remove_btn = tr.find(".remove");
	remove_btn.click(function(){
		remove('/admin/monitor/remove.do',{"id": performanceMonitorDTO.id},'/admin/monitor/list?currentPage=1');
	});
	return tr;
}
