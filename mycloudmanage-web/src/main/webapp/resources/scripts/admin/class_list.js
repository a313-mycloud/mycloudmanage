/**
 *  *
 * @author xuyizhen Dec 7, 2014 10:47 AM
 */

$(document).ready(function(){	
	$(".remove").click(function() {
		remove("/admin/class/remove.do",{classId:$(this).attr('classId')},"/admin/class/list?currentPage=1");
	});
	$(".removeAll").click(function(){
		removeAll("/admin/class/removeAll.do","/admin/class/list?currentPage=1");
	
	});
	$(".add").click(function(){
		add('/admin/class/add.do',{className:$("#className").val(),teacherAccount:$("#teacherAccount").val()},"/admin/class/list?currentPage=1");
	});
	$(".edit").click(function(){
	    edit('/admin/host/edit.do',{"hostId":$("#hostId").val(),"hostName":$("#hostName").val(),"hostIp":$("#hostIp").val()},"/admin/host/list?currentPage=1");
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
	     showPrePage("/admin/host/list",$("#page").attr("currentPage"));
	});
	$(".nextPage").click(function(){
	     showNextPage("/admin/host/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	$(".firstPage").click(function(){
	     showFirstPage("/admin/host/list",$("#page").attr("currentPage"));
	});
	$(".lastPage").click(function(){
	     showLastPage("/admin/host/list",$("#page").attr("currentPage"),$("#page").attr("totalPage"));
	});
	
});