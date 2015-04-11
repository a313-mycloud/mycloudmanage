
$(document).ready(function(){	
	
	$(".convert").click(function(){
		console.log($(this).attr('vmUuid'));
		convert('/teacher/tvm/convert.do',{vmUuid:$(this).attr('vmUuid')},'/teacher/tvm/list?currentPage=1');
	});
	$(".edit").click(function(){
	    edit('/teacher/tvm/edit.do',{"vmUuid":$("#vmUuid").val(),"vmName":$("#vmName").val(),"showType":$("#showType").val(),"vmDesc":$("#vmDesc").val(),"showPassword":$("#vmPassword").val(),"vmVcpu":$("#vmVcpu").val(),"vmMemory":$("#vmMemory").val()},"/teacher/tvm/list?currentPage=1");
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
function convert(url,data,replace){
	if(confirm("相关的课程将不能再使用该虚拟机，确定转换？")){
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
function showPass(){ 			
	$("#box").html("<input class='text-input small-input'  type='text' id='vmPassword'  value="+$("#vmPassword").val()+"><a href='javascript:hidePass()' class='button '>隐藏密码</a>");
	//$("#box").html("<input type='text' id='vmPassword'  value="+$("#vmPassword").val()+"><a  class='button hidePass'>隐藏密码</a>");
}
function hidePass(){   
    $("#box").html("<input class='text-input small-input'  type='password' id='vmPassword' value="+$("#vmPassword").val()+"><a href='javascript:showPass()' class='button '>显示密码</a>");
	//$("#box").html("<input type='password' id='vmPassword' value="+$("#vmPassword").val()+"><a  class='button showPass '>显示密码</a>");
} 
