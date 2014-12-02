$(document).ready(function(){
	$(".delete_btn").click(function () {
		var account = $(this).attr("user_id");
		
		/*var r = false;
		if (confirm('确定删除' + account + '吗？?')) {
			alert("xxxx");
			r = true;
		}*/
		
		$.ajax({
			type:"GET",
			url:"/admin/account/delete.do",
			data:{"userAccount" : account},
			dataType:"json",
			success:function(data) {
				alert(data);
				location.reload();
			}
		});
		
//		var r=false; if(confirm('确定删除' + account + '吗？?')) r=true; event.returnValue=r; return r;
	});
});

function deleteAction() {
   var r=false; if(confirm('确定删除吗？?')) r=true; event.returnValue=r; return r;
	
}


