
$(document).ready(function(){	
	$(".add").click(function(){
	   add('/student/disk/add.do',{"diskName":$("#diskName").val(),"diskSize":$("#diskSize").val(),"diskDesc":$("#diskDesc").val()},'/student/disk/list?currentPage=1');
	});
	
	
	$(".remove").click(function(){
	   remove('/student/disk/remove.do',{"diskUuid":$(this).attr('diskUuid')},'/student/disk/list?currentPage=1');
	});
	
	$(".attach").click(function(){
	   attach('/student/disk/attach.do',{"diskUuid":$("#diskUuid").val(),"vmUuid":$("#vmUuid").val()},'/student/disk/list?currentPage=1');
	});
	$(".resetEdit").click(function(){	
		$("#vmUuid").val($("#preVmUuid").val());
	});	
	$(".unload").click(function(){
	   unload('/student/disk/unload.do',{"diskUuid":$(this).attr('diskUuid')},'/student/disk/list?currentPage=1');
	});
	
	$(".edit").click(function(){
	    edit('/student/tvm/edit.do',{"vmUuid":$("#vmUuid").val(),"vmName":$("#vmName").val(),"showType":$("#showType").val(),"vmDesc":$("#vmDesc").val(),"showPassword":$("#vmPassword").val(),"vmVcpu":$("#vmVcpu").val(),"vmMemory":$("#vmMemory").val()},"/student/tvm/list?currentPage=1");
	});
	
	$(".prePage").click(function(){
	     showPrePage("/student/disk/list",$("#page").attr("currentPage"));
	});
	$(".nextPage").click(function(){
	     showNextPage("/student/disk/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	$(".firstPage").click(function(){
	     showFirstPage("/student/disk/list",$("#page").attr("currentPage"));
	});
	$(".lastPage").click(function(){
	     showLastPage("/student/disk/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	
	
	
});
/**
 * 卸载
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function attach(url,data,replace){
	if(confirm("挂载后需要重启虚拟机方能使用硬盘，确定挂载？")){
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
 * 卸载
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function unload(url,data,replace){
	if(confirm("确定卸载？")){
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
