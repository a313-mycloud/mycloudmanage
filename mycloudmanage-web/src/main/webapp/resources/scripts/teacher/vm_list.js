/**
 *  *教师的vm_list
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	
	$(".remove").click(function(){
	   remove('/teacher/vm/remove.do',{vmUuid:$(this).attr('vmUuid')},'/teacher/vm/list?currentPage=1');
	});
	$(".convert").click(function(){
		console.log($(this).attr('vmUuid'));
		convert('/teacher/vm/convert.do',{vmUuid:$(this).attr('vmUuid')},'/teacher/vm/list?currentPage=1');
	});
	$(".edit").click(function(){
		if(confirm("编辑后需要重启方能生效，确定编辑？")){
	    edit('/teacher/vm/edit.do',{vmNetworkType:$("#vmNetworkType").val(),vmUuid:$("#vmUuid").val(),vmName:$("#vmName").val(),showType:$("#showType").val(),vmDesc:$("#vmDesc").val(),showPassword:$("#vmPassword").val(),vmVcpu:$("#vmVcpu").val(),vmMemory:$("#vmMemory").val()},"/teacher/vm/list?currentPage=1");
	}
	});
	$(".resetEdit").click(function(){
		
		$("#vmName").val($("#preName").val());
		$("#showType").val($("#preShowType").val());
		$("#vmDesc").val($("#preVmDesc").val());
		$("#vmPassword").val($("#preShowPassword").val());
		$("#vmVcpu").val($("#preVmVcpu").val());
		$("#vmMemory").val($("#preVmMemory").val());
		$("#showType").val($("#preShowType").val());
		$("#vmNetworkType").val($("#preVmNetworkType").val());
	});	
	
	$(".add").click(function(){
		add('/teacher/vm/add.do',{vmName:$("#vmName").val(),vmDesc:$("#vmDesc").val(),
		vmVcpu:$("#vmVcpu").val(),vmMemory:$("#vmMemory").val(),srcVmUuid:$("#srcVmUuid").val(),
		showType:$("#showType").val(),showPassword:$("#vmPassword").val(),vmNetworkType:$("#vmNetworkType").val()},"/teacher/vm/list?currentPage=1");
		
	});
	
	$(".reset").click(function(){
         $("#vmName").val("");
         $("#vmDesc").val("");
		 $("#vmVcpu").val("");
		 $("#vmMemory").val("");
		 $("#srcVmUuid").get(0).selectedIndex=0;
		 $("#showType").val("");
		 $("#password").val("");
	});	
	$(".start").click(function(){
			console.log(" use for test:teacher_vm_start");
		start('/teacher/vm/start.do',{vmUuid:$(this).attr("vmUuid")},"/teacher/vm/list?currentPage="+$("#page").attr("currentPage"));
	});
	$(".shutdown").click(function(){
			console.log(" use for test:teacher_vm_shutdown");
		shutdown('/teacher/vm/shutdown.do',{vmUuid:$(this).attr("vmUuid")},"/teacher/vm/list?currentPage="+$("#page").attr("currentPage"));
	});
	$(".prePage").click(function(){
	     showPrePage("/teacher/vm/list",$("#page").attr("currentPage"));
	});
	$(".nextPage").click(function(){
	     showNextPage("/teacher/vm/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	$(".firstPage").click(function(){
	     showFirstPage("/teacher/vm/list",$("#page").attr("currentPage"));
	});
	$(".lastPage").click(function(){
	     showLastPage("/teacher/vm/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	
	
	
});
/**
 * 异步关闭虚拟机
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function shutdown(url,data,replace){
	if(confirm("请先确认虚拟机内无数据操作，否则可能丢失数据，确定关闭？")){
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
	$("#box").html("<input class='text-input small-input'  type='text' id='vmPassword'  value="+$("#vmPassword").val()+"><a href='javascript:hidePass()' class='button '>隐藏密码</a>");
	//$("#box").html("<input type='text' id='vmPassword'  value="+$("#vmPassword").val()+"><a  class='button hidePass'>隐藏密码</a>");
}
function hidePass(){   
    $("#box").html("<input class='text-input small-input'  type='password' id='vmPassword' value="+$("#vmPassword").val()+"><a href='javascript:showPass()' class='button '>显示密码</a>");
	//$("#box").html("<input type='password' id='vmPassword' value="+$("#vmPassword").val()+"><a  class='button showPass '>显示密码</a>");
} 
function convert(url,data,replace){
	if(confirm("转换前需要关闭虚拟机，确定转换？")){
	    showdiv("正在转换虚拟机，请稍后!");
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