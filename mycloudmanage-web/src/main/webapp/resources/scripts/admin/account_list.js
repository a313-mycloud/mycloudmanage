$(document).ready(function(){
	$(".delete_btn").click(function () {
		confirm("确定删除？");
		var id = $(this).attr("id").split("_")[1];
		alert(id);
	});
});

function deleteAction() {
	alert("delete");
}