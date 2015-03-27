
$(document).ready(function(){	
	$(".add").click(function(){
	   add('/admin/disk/add.do',{diskName:$("#diskName").val(),diskSize:$("#diskSize").val(),diskDesc:$("#diskDesc").val()},'/admin/disk/list?currentPage=1');
	});
	
	
	$(".remove").click(function(){
	   remove('/admin/disk/remove.do',{diskUuid:$(this).attr('diskUuid')},'/admin/disk/list?currentPage=1');
	});
	$(".edit").click(function(){
	   edit('/admin/disk/edit.do',{diskUuid:$("#diskUuid").val(),diskName:$("#diskName").val()},'/admin/disk/list?currentPage=1');
	});
	
	$(".attach").click(function(){
	   attach('/admin/disk/attach.do',{diskUuid:$("#diskUuid").val(),vmUuid:$("#vmUuid").val()},'/admin/disk/list?currentPage=1');
	});
	$(".resetEdit").click(function(){	
		$("#diskName").val($("#preName").val());
	});	
	$(".unload").click(function(){
	   unload('/admin/disk/unload.do',{diskUuid:$(this).attr('diskUuid')},'/admin/disk/list?currentPage=1');
	});
	
	$(".prePage").click(function(){
	     showPrePage("/admin/disk/list",$("#page").attr("currentPage"));
	});
	$(".nextPage").click(function(){
	     showNextPage("/admin/disk/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	$(".firstPage").click(function(){
	     showFirstPage("/admin/disk/list",$("#page").attr("currentPage"));
	});
	$(".lastPage").click(function(){
	     showLastPage("/admin/disk/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	
	
	
});
/**
 * 卸载
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function attach(url,data,replace){
	if(confirm("确定挂载？")){
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
	if(confirm("请先关闭该硬盘关联的虚拟机，否则可能丢失数据，确定卸载？")){
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
