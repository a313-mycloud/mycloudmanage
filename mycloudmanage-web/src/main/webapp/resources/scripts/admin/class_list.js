/**
 *  *
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	
	$(".remove").click(function() {
		remove("/admin/class/remove.do",{classId:$(this).attr('classId')},"/admin/class/list?currentPage=1");
	});
	
	$(".add").click(function(){
		add('/admin/class/add.do',{className:$("#className").val(),teacherAccount:$("#teacherAccount").val()},"/admin/class/list?currentPage=1");
	});
	
	$(".edit").click(function(){
	    edit('/admin/class/edit.do',{"classId":$("#classId").val(),"className":$("#className").val(),"teacherAccount":$("#teacherAccount").val()},"/admin/class/list?currentPage=1");
	});
	$(".reset").click(function(){
		$("#className").val("");
		$("#teacherAccount").val("");
		$("#account").val("");
		$("#username").val("");
	});	
	$(".resetEdit").click(function(){
		$("#className").val($("#preClassName").val());
		$("#teacherAccount").val($("#preTeacherAccount").val());
	});	
	
   $(".addstudent").click(function(){
	     add('/admin/class/addstudent.do',{classId:$("#classId").val(),account:$("#account").val(),username:$("#username").val()},"/admin/class/student/list?classId="+$("#classId").val());
	});	
	/**以后扩展用**/
	
	
	
	$(".prePage").click(function(){
	     showPrePage("/admin/class/list",$("#page").attr("currentPage"));
	});
	$(".nextPage").click(function(){
	     showNextPage("/admin/class/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	$(".firstPage").click(function(){
	     showFirstPage("/admin/class/list",$("#page").attr("currentPage"));
	});
	$(".lastPage").click(function(){
	     showLastPage("/admin/class/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	

	
});
