/**
 *  *教师的vm_list
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	

	$(".prePage").click(function(){
	   var currentPage=$("#page").attr("currentPage");
		var totalPage=$("#page").attr("totalPage");
		var classId=$("#classId").val();
		
		if(Number(currentPage)>1){
		var current=Number(currentPage)-1;
		window.location.replace("/admin/class/student/list?currentPage="+current+"&classId="+Number(classId));
		}
		else
		    alert("没有上一页");

	});
	$(".nextPage").click(function(){
	   var currentPage=$("#page").attr("currentPage");
		var totalPage=$("#page").attr("totalPage");
		var classId=$("#classId").val();
		
		if(Number(currentPage)<Number(totalPage)){
		var current=Number(currentPage)+1;
		window.location.replace("/admin/class/student/list?currentPage="+current+"&classId="+Number(classId));
		}
		else
		    alert("没有下一页");
	  
	});
	$(".firstPage").click(function(){
	    var currentPage=$("#page").attr("currentPage");
		var totalPage=$("#page").attr("totalPage");
		var classId=$("#classId").val();
	   
		if(Number(currentPage)==1)
		alert("已经是首页");
	    else
		window.location.replace("/admin/class/student/list?currentPage=1&classId="+Number(classId));
	});
	$(".lastPage").click(function(){
		var currentPage=$("#page").attr("currentPage");
		var totalPage=$("#page").attr("totalPage");
		var classId=$("#classId").val();
		
	   if(Number(currentPage)==Number(totalPage))
		alert("已经是末页");
	  else
	   window.location.replace("/admin/class/student/list?currentPage="+totalPage+"&classId="+Number(classId));
	    
	});
});