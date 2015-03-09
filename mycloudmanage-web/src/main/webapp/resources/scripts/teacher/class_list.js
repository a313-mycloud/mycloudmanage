/**
 *  *教师的vm_list
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	

	$(".prePage").click(function(){
	     showPrePage("/teacher/class/list",$("#page").attr("currentPage"));
	});
	$(".nextPage").click(function(){
	     showNextPage("/teacher/class/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	$(".firstPage").click(function(){
	     showFirstPage("/teacher/class/list",$("#page").attr("currentPage"));
	});
	$(".lastPage").click(function(){
	     showLastPage("/teacher/class/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	
	$(".bind").click(function(){
		
		bind('/teacher/class/vm/bind.do',{"vmUuid":$("#classVmUuid").val(),"classId":$(this).attr('classId')},'/teacher/class/vm/list?currentPage=1&classId='+$(this).attr('classId'));
	});
	$(".unbind").click(function(){
		console.log($(this).attr('classId'));
		console.log($(this).attr('tvmUuid'));
		unbind('/teacher/class/vm/unbind.do',{"vmUuid":$(this).attr('tvmUuid'),"classId":$(this).attr('classId')},'/teacher/class/vm/list?currentPage=&classId='+$(this).attr('classId'));
	});
	
});
/**
 * 异步为课程绑定虚拟机
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function bind(url,data,replace){
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

/**
 * 异步为课程解除绑定虚拟机
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function unbind(url,data,replace){
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