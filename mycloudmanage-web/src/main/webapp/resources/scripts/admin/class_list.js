/**
 *  *
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	
	$(".remove").click(function() {
                console.log("lalalalal;");
		remove("/admin/class/remove.do",{classId:$(this).attr('classId')},"/admin/class/list?currentPage=1");
	});
	$(".add").click(function(){
                console.log("running add function")
		add('/admin/class/add.do',{className:$("#className").val(),teacherAccount:$("#teacherAccount").val()},"/admin/class/list?currentPage=1");
	});
	$(".edit").click(function(){
            console.log("running edit function")
	    edit('/admin/class/edit.do',{"classId":$("#classId").val(),"className":$("#className").val(),"teacherAccount":$("#teacherAccount").val()},"/admin/class/list?currentPage=1");
	});
	$(".reset").click(function(){
		$("#className").val("");
		$("#teacherAccount").val("");
	});	
	$(".resetEdit").click(function(){
		$("#className").val($("#preName").val());
		$("#teacherAccount").val($("#preAccount").val());
	});	
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
