/*
**
*@author sunhuiqi Dec 31,2014 14:41
*/

$(document).ready(function(){	
$(".add").click(function(){
		add('/admin/account/add.do',{UserName:$("#userName").val(),password:$("#password").val()},"/admin/account/list?currentPage=1");
	});

});
