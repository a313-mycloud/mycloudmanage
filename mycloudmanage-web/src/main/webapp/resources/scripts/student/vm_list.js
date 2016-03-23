/**
 *  *学生的vm_list
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	
	$(function(){
		$("#showType").val($("#preShowType").val());
	});
	$(".edit").click(
	function(){
		if(confirm("编辑后需要重启方能生效，确定编辑？")){
	    edit('/student/vm/edit.do',{"vmNetworkType":$("#vmNetworkType").val(),"vmUuid":$("#vmUuid").val(),"vmName":$("#vmName").val(),"showType":$("#showType").val(),"vmDesc":$("#vmDesc").val(),"showPassword":$("#vmPassword").val(),"vmVcpu":$("#vmVcpu").val(),"vmMemory":$("#vmMemory").val()},"/student/vm/list?currentPage=1");
	}
	});
	$(".resetEdit").click(function(){
		$("#vmName").val($("#preName").val());
		$("#vmDesc").val($("#preVmDesc").val());
	});	
	
	
	
	$(".start").click(function(){
			console.log(" use for test:student_vm_start");
		start('/student/vm/start.do',{"vmUuid":$(this).attr("vmUuid")},"/student/vm/list?currentPage="+$("#page").attr("currentPage"));
	});
	$(".shutdown").click(function(){
			console.log(" use for test:student_vm_shutdown");
		shutdown('/student/vm/shutdown.do',{"vmUuid":$(this).attr("vmUuid")},"/student/vm/list?currentPage="+$("#page").attr("currentPage"));
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
	if(confirm("请确认虚拟机内无数据操作，否则可能丢失数据，确定关闭？")){
        showdiv("正在关闭虚拟机，请稍后!");
		$.ajax({
		 url:url,
		 data:data,
		 dataType:"json",
		 success:function(data){
			closediv();
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
			closediv();
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
    showdiv("正在启动虚拟机，请稍后!");
	$.ajax({
		 url:url,
		 data:data,
		 dataType:"json",
		 success:function(data){
            closediv();
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
            closediv();
		 	alert(status);
		 } 
	});	
}
function showPass(){ 			
	$("#box").html("<input type='text' id='vmPassword'  value="+$("#vmPassword").val()+"><a href='javascript:hidePass()' class='button '>隐藏密码</a>");
	//$("#box").html("<input type='text' id='vmPassword'  value="+$("#vmPassword").val()+"><a  class='button hidePass'>隐藏密码</a>");
}
function hidePass(){   
    $("#box").html("<input type='password' id='vmPassword' value="+$("#vmPassword").val()+"><a href='javascript:showPass()' class='button '>显示密码</a>");
	//$("#box").html("<input type='password' id='vmPassword' value="+$("#vmPassword").val()+"><a  class='button showPass '>显示密码</a>");
} 


