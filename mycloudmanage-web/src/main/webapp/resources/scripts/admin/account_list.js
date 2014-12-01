$(document).ready(function(){
	console.log("hha");
	$(".delete_btn").click(function () {
		var id = $(this).attr("id").split("_")[1];
		alert(id);
	});
});

function deleteAction() {
	alert("delete");
}