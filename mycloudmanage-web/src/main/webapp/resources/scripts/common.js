/**
 * 
 *@author xuyizhen Dec 7, 2014 10:47 AM
 */
/**
 * 删除操作对应的js代码
 * @param {} url    删除操作对应的controller地址
 * @param {} jsonData 要删除的记录的ID，json格式
 * @param {} replace  删除成功后要跳转到的页面
 */
function remove(url,data,replace){
	if(confirm("确定删除？")){
	    showdiv("正在删除，请稍后!");
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
/**
 * 删除全部数据的js代码
 * @param {} url
 * @param {} replace
 */
function removeAll(url,replace){
	if(confirm("确定删除全部？")){
		$.ajax({
		 url:url,
		 dataType:"json",
		 success:function(data){
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
		 	alert(status);
		 } 
	});		
	}
}

/**
 * 添加操作对应的js代码
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function add(url,data,replace){
        showdiv("正在执行操作，请稍后！");   
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
/**
 * 编辑操作对应的js代码
 * @param {} url
 * @param {} data
 * @param {} replace
 */
function edit(url,data,replace){
	$.ajax({
		 url:url,
		 data:data,
		 dataType:"json",
		 success:function(data){
		 	if(!data.isLogin){
		 		alert("请登陆Login");
		 		window.location.replace("/login");
		 	}
		 	else if(!data.isAuth){
		 		alert("您没有权限Auth");
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
		 	alert(status);
		 	
		 } 
	});	
}
/**
 * 显示前一页的代码
 * @param {} url
 * @param {} currentPage
 * @param {} pageSize
 */
function showPrePage(url,currentPage){
	if(Number(currentPage)>1){
		var current=Number(currentPage)-1;
		window.location.replace(url+"?currentPage="+current);
	}
	else
	    alert("没有上一页");
}
/**
 * 显示后一页的代码
 * @param {} url
 * @param {} currentPage
 * @param {} totalPage
 * @param {} pageSize
 */
function showNextPage(url,currentPage,totalPage){
	if(Number(currentPage)<Number(totalPage)){
		var current=Number(currentPage)+1;
		window.location.replace(url+"?currentPage="+current);
	}
	else
	    alert("没有下一页");
}
/**
 * 显示第一页的代码
 * @param {} url
 * @param {} pageSize
 */
function showFirstPage(url,currentPage){
	if(Number(currentPage)==1)
		alert("已经是首页");
	else
		window.location.replace(url+"?currentPage=1");
}
/**
 * 显示最后一页的代码
 * @param {} url
 * @param {} totalPage
 * @param {} pageSize
 */
function showLastPage(url,currentPage,totalPage){
	if(Number(currentPage)==Number(totalPage))
		alert("已经是末页");
	else
	   window.location.replace(url+"?currentPage="+totalPage);
}

//关闭等待窗口
function closediv() {
    //Close Div 
    document.body.removeChild(document.getElementById("bgDiv"));
    document.getElementById("msgDiv").removeChild(document.getElementById("msgTitle"));
    document.body.removeChild(document.getElementById("msgDiv"));
}
//显示等待窗口
function showdiv(str) {
    var msgw, msgh, bordercolor;
    msgw = 400; //提示窗口的宽度 
    msgh = 100; //提示窗口的高度 
    bordercolor = "#e5e5e5"; //提示窗口的边框颜色 
    titlecolor = "#99CCFF"; //提示窗口的标题颜色 

    var sWidth, sHeight;
    sWidth = window.screen.availWidth;
    sHeight = window.screen.availHeight;

    var bgObj = document.createElement("div");
    bgObj.setAttribute('id', 'bgDiv');
    bgObj.style.position = "absolute";
    bgObj.style.top = "0";
    bgObj.style.background = "#777";
    bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
    bgObj.style.opacity = "0.6";
    bgObj.style.left = "0";
    bgObj.style.width = sWidth + "px";
    bgObj.style.height = sHeight + "px";
    document.body.appendChild(bgObj);
    var msgObj = document.createElement("div")
    msgObj.setAttribute("id", "msgDiv");
    msgObj.setAttribute("align", "center");
    msgObj.style.position = "absolute";
    msgObj.style.background = "white";
    msgObj.style.font = "12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
    msgObj.style.border = "1px solid " + bordercolor;
    msgObj.style.width = msgw + "px";
    msgObj.style.height = msgh + "px";
    //msgObj.style.top = (document.documentElement.scrollTop + (sHeight - msgh) / 2) + "px";
    msgObj.style.top = (document.documentElement.scrollTop + (sHeight - msgh) / 4) + "px";
    msgObj.style.left = (sWidth - msgw) / 2 + "px";
    var title = document.createElement("h4");
    title.setAttribute("id", "msgTitle");
    title.setAttribute("align", "right");
    title.style.margin = "0";
    title.style.padding = "3px";
    title.style.background = bordercolor;
    title.style.filter = "progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
    title.style.opacity = "0.75";
    title.style.border = "1px solid " + bordercolor;
    title.style.height = "18px";
    title.style.font = "12px Verdana, Geneva, Arial, Helvetica, sans-serif";
    title.style.color = "white";
    //title.style.cursor = "pointer";
    //title.innerHTML = "关闭";
    //title.onclick = closediv;
    document.body.appendChild(msgObj);
    document.getElementById("msgDiv").appendChild(title);
    var txt = document.createElement("p");
    txt.style.margin = "1em 0"
    txt.setAttribute("id", "msgTxt");
    txt.innerHTML = str;
    document.getElementById("msgDiv").appendChild(txt);
}
//屏蔽F5
document.onkeydown = mykeydown;
function mykeydown() {
    if (event.keyCode == 116) //屏蔽F5刷新键   
    {
        window.event.keyCode = 0;
        return false;
    }
}  


