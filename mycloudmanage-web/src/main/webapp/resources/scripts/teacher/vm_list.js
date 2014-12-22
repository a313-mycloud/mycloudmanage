/**
 *  *
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	
	$(".edit").click(function(){
	    edit('/teacher/vm/edit.do',{"vmUuid":$("#vmUuid").val(),"vmName":$("#vmName").val(),"showType":$("#showType").val(),"vmDesc":$("#vmDesc").val(),"showPassword":$("#vmPassword").val(),"vmVcpu":$("#vmVcpu").val(),"vmMemory":$("#vmMemory").val()},"/teacher/vm/list?currentPage=1");
	});
	$(".resetEdit").click(function(){
		console.log($("#preName").val());
		console.log($("#preShowType").val());
		$("#vmName").val($("#preName").val());
		$("#showType").val($("#preShowType").val());
		$("#vmDesc").val($("#preVmDesc").val());
		$("#vmPassword").val($("#preShowPassword").val());
		$("#vmVcpu").val($("#preVmVcpu").val());
		$("#vmMemory").val($("#preVmMemory").val());
		$("#showType").val($("#preShowType").val());
	});	
	
	$(".add").click(function(){
		add('/teacher/vm/add.do',{"vmName":$("#vmName").val(),"vmDesc":$("#vmDesc").val(),
		"vmVcpu":$("#vmVcpu").val(),"vmMemory":$("#vmMemory").val(),"srcVmUuid":$("#srcVmUuid").val(),
		"showType":$("#showType").val(),"password":$("#password").val()},"/teacher/vm/list?currentPage=1");
		
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
		start('/teacher/vm/start.do',{"vmUuid":$(this).attr("vmUuid")},"/teacher/vm/list?currentPage="+$("#page").attr("currentPage"));
	});
	$(".shutdown").click(function(){
		shutdown('/teacher/vm/shutdown.do',{"vmUuid":$(this).attr("vmUuid")},"/teacher/vm/list?currentPage="+$("#page").attr("currentPage"));
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
function showPass(){ 			
	$("#box").html("<input class='text-input small-input'  type='text' id='vmPassword'  value="+$("#vmPassword").val()+"><a href='javascript:hidePass()' class='button '>隐藏密码</a>");
	//$("#box").html("<input type='text' id='vmPassword'  value="+$("#vmPassword").val()+"><a  class='button hidePass'>隐藏密码</a>");
}
function hidePass(){   
    $("#box").html("<input class='text-input small-input'  type='password' id='vmPassword' value="+$("#vmPassword").val()+"><a href='javascript:showPass()' class='button '>显示密码</a>");
	//$("#box").html("<input type='password' id='vmPassword' value="+$("#vmPassword").val()+"><a  class='button showPass '>显示密码</a>");
} 
