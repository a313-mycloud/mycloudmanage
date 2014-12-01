$(document).ready(function(){
	$(".delete_btn").click(function () {
		var account = $(this).attr("user_id");
		var r=false; if(confirm('确定删除' + account + '吗？?')) r=true; event.returnValue=r; return r;
	});
});

function deleteAction() {
   var r=false; if(confirm('确定删除吗？?')) r=true; event.returnValue=r; return r;
	
}
