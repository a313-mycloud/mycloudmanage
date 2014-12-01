$(document).ready(function(){
	console.log("hha");
	$(".delete_btn").click(function () {
		var id = $(this).attr("id").split("_")[1];
		alert(id);
	});
});

function deleteAction() {
   var r=false; if(confirm('确定删除吗？?')) r=true; event.returnValue=r; return r;
        //confirm("确定删除吗？");
	
}
