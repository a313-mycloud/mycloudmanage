/**
 *  *
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	
	
	
	$(".edit").click(function(){
	    edit('/student/vm/edit.do',{"vmUuid":$("#vmUuid").val(),"vmName":$("#vmName").val(),"showType":$("#showType").val()},"/student/vm/list?currentPage=1");
	});
	$(".resetEdit").click(function(){
		$("#vmName").val($("#preName").val());
		$("#showType").val($("#preShowType").val());
	});	
	$(".start").click(function(){
		start('/student/vm/start.do',{"vmUuid":$(this).attr("vmUuid")},"/student/vm/list?currentPage=1");
	});
	$(".shutdown").click(function(){
		shutdown('/student/vm/shutdown.do',{"vmUuid":$(this).attr("vmUuid")},"/student/vm/list?currentPage=1");
	});
	$(".prePage").click(function(){
	     showPrePage("/student/vm/list",$("#page").attr("currentPage"));
	});
	$(".nextPage").click(function(){
	     showNextPage("/student/vm/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	$(".firstPage").click(function(){
	     showFirstPage("/student/vm/list",$("#page").attr("currentPage"));
	});
	$(".lastPage").click(function(){
	     showLastPage("/student/vm/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	
});
/**
 * 异步关闭虚拟机
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function shutdown(url,data,replace){
	if(confirm("可能丢失数据，确定关闭？")){
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
}
/**
 * 异步开启虚拟机
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function start(url,data,replace){
	$.ajax({
		 url:url,
		 data:data,
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
		 			window.location.replace(replace);
		 		}
		 	}
		 },
		 error:function(data,status){
		 	alert(status);
		 } 
	});	
}
