/**
 *  *
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	
	$(".remove").click(function() {
		remove("/admin/host/remove",{hostId:$(this).attr('hostId')},"/admin/host/list?currentPage=1&pageSize=5");
	});
	$(".removeAll").click(function(){
		removeAll("/admin/host/removeAll","/admin/host/list?currentPage=1&pageSize=5");
	
	});
	$(".add").click(function(){
		add('/admin/host/add',{hostName:$("#hostName").val(),hostIp:$("#hostIp").val()},"/admin/host/list?currentPage=1&pageSize=5");
	});
	$(".edit").click(function(){
	    edit('/admin/host/edit',{"hostId":$("#hostId").val(),"hostName":$("#hostName").val(),"hostIp":$("#hostIp").val()},"/admin/host/list?currentPage=1&pageSize=5");
	});
	$(".reset").click(function(){
		$("#hostName").val("");
		$("#hostIp").val("");
	});	
	$(".resetEdit").click(function(){
		$("#hostName").val($("#preName").val());
		$("#hostIp").val($("#preIp").val());
	});	
	$(".prePage").click(function(){
	     showPrePage("/admin/host/list",$("#page").attr("currentPage"),5);
	});
	$(".nextPage").click(function(){
	     showNextPage("/admin/host/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"),5);
	});
	$(".firstPage").click(function(){
	     showFirstPage("/admin/host/list",5);
	});
	$(".lastPage").click(function(){
	     showLastPage("/admin/host/list",$("#page").attr("totalPage"),5);
	});
	
});
/**
 * 删除操作对应的js代码
 * @param {} url    删除操作对应的controller地址
 * @param {} jsonData 要删除的记录的ID，json格式
 * @param {} replace  删除成功后要跳转到的页面
 */
function remove(url,data,replace){
	if(confirm("确定删除？")){
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
 * 删除全部数据的js代码
 * @param {} url
 * @param {} replace
 */
function removeAll(url,replace){
	if(confirm("确定删除全部？")){
		$.ajax({
		 url:url,
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
 * 添加操作对应的js代码
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function add(url,data,replace){
	$.ajax({
		 type:"POST",
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
/**
 * 编辑操作对应的js代码
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function edit(url,data,replace){
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
/**
 * 显示前一页的代码
 * @param {} url
 * @param {} currentPage
 * @param {} pageSize
 */
function showPrePage(url,currentPage,pageSize){
	if(currentPage>1){
		var current=Number(currentPage)-1;
		window.location.replace(url+"?currentPage="+current+"&pageSize="+pageSize);
	}
	else
	    alert("没有上一页");
}
/**
 * 显示后一页的代码
 * @param {} url
 * @param {} currentPage
 * @param {} totalPage
 * @param {} pageSize
 */
function showNextPage(url,currentPage,totalPage,pageSize){
	if(currentPage<totalPage){
		var current=Number(currentPage)+1;
		window.location.replace(url+"?currentPage="+current+"&pageSize="+pageSize);
	}
	else
	    alert("没有下一页");
}
/**
 * 显示第一页的代码
 * @param {} url
 * @param {} pageSize
 */
function showFirstPage(url,pageSize){
	window.location.replace(url+"?currentPage=1&pageSize="+pageSize);
}
/**
 * 显示最后一页的代码
 * @param {} url
 * @param {} totalPage
 * @param {} pageSize
 */
function showLastPage(url,totalPage,pageSize){
	window.location.replace(url+"?currentPage="+totalPage+"&pageSize="+pageSize);
}


