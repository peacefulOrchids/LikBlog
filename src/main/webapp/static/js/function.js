// JavaScript Document
window.onload = function(){
	//showChater();
	//scrollChater();
}
//window.onscroll = scrollChater;
//window.onresize = scrollChater;

function FocusItem(obj)
{
	obj.parentNode.parentNode.className = "current";
	var msgBox = obj.parentNode.getElementsByTagName("span")[0];
	msgBox.innerHTML = "";
	msgBox.className = "";
}
var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",
		23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",
		41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",
		52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",
		71:"台湾",81:"香港",82:"澳门",91:"国外"};
		
 /**
 输入前17位
 * 生成第18位身份验证码，根据国家标准 GB 11643-1999
 * 计算最后一位数字
 */
function idcardVerify(idcard_base){
    if (idcard_base.length != 17){
        return false;
    }
    //加权因子
    var factor = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
    var verify_number_list = ['1','0','X','9','8','7','6','5','4','3','2'];

    var checkNum = 0;
    for (var i = 0;i<idcard_base.length;i++){
        checkNum += parseInt(idcard_base.substr(i,1) * factor[i]);
    }
    var mod = checkNum % 11;
    var verify_number = verify_number_list[mod];
    return verify_number;
}
function CheckItem(obj)
{
	obj.parentNode.parentNode.className = "";
	var msgBox = obj.parentNode.getElementsByTagName("span")[0];
	var dentityCode=$("#dentityCode").val();
	var gender = $("#sex:checked").val();
	switch(obj.id) {
		case "userName":
			if(obj.value == "") {
				msgBox.innerHTML = "用户名不能为空";
				msgBox.className = "error";
				return false;
			}
			break;
		case "passWord":
			if(obj.value == "") {
				msgBox.innerHTML = "密码不能为空";
				msgBox.className = "error";
				return false;
			}
			break;
		case "rePassWord":
			if(obj.value == "") {
				msgBox.innerHTML = "确认密码不能为空";
				msgBox.className = "error";
				return false;
			} else if(obj.value != document.getElementById("passWord").value) {
				msgBox.innerHTML = "两次输入的密码不相同";
				msgBox.className = "error";
				return false;
			}
			break;
		case "veryCode":
			if(obj.value == "") {
				msgBox.innerHTML = "验证码不能为空";
				msgBox.className = "error";
				return false;
			}
			break;
		case "birthday":
			if(obj.value == "") {
				msgBox.innerHTML = "出生日期不能为空";
				msgBox.className = "error";
				return false;
			}
			break;
		case "email":
			var str=obj.value;
			if(str == "") {
				msgBox.innerHTML = "邮箱不能为空";
				msgBox.className = "error";
				return false;
			}
			 //在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
			var Expression=/\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/; 
			var objExp=new RegExp(Expression);		//创建正则表达式对象
			if(objExp.test(str)!=true){				//通过正则表达式进行验证
				msgBox.innerHTML = "邮箱格式错误";
				msgBox.className = "error";
				return false;
			}
			break;
		case "dentityCode":
 			$("#address").val(aCity[parseInt(dentityCode.substr(0,2))]);
			if(dentityCode==""){
	 			$("#dentityCodeError").attr("class","error");
	 			$("#dentityCodeError").html("身份证号不能为空！");
				return false;
			}else{
				var iSum=0 ;
		 		if(!(/^\d{17}(\d|x)$/i.test(dentityCode.trim()))) {
		 			$("#dentityCodeError").html("你输入的身份证长度或格式错误");
		 			$("#dentityCodeError").attr("class","error");
		 			return false;
		 		}
		 		dentityCode=dentityCode.replace(/x$/i,"a");
		 		if(aCity[parseInt(dentityCode.substr(0,2))]==null) {
		 			$("#dentityCodeError").attr("class","error");
		 		    $("#dentityCodeError").html("你的身份证地区非法");
		 			return false;
		 		}
		 		var sBirthday=dentityCode.substr(6,4)+"-"+Number(dentityCode.substr(10,2))+"-"+Number(dentityCode.substr(12,2));
		 		var d=new Date(sBirthday.replace(/-/g,"/")) ;
		 		if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
	 			    $("#dentityCodeError").html("身份证上的出生日期非法");
		 			$("#dentityCodeError").attr("class","error");
		 			return false;
		 		}
		 		for(var i = 17;i>=0;i --) {
		 			iSum += (Math.pow(2,i) % 11) * parseInt(dentityCode.charAt(17 - i),11) ;
		 		}
		 		if(iSum%11!=1) {
		 			$("#dentityCodeError").html("你输入的身份证号非法");
		 			$("#dentityCodeError").attr("class","error");
		 			var i=idcardVerify(dentityCode.substring(0,17));
		 			//alert('你的最后一位应该是:'+i);
		 			return false;
		 		}
		        var sex=dentityCode.substr(16,1)%2?"男":"女";
	 			if(sex != gender){
		 			//alert("你输入的身份证号性别不符");
	 				$("#dentityCodeError").html("你输入的身份证号性别不符");
		 			$("#dentityCodeError").attr("class","error");
		 			return false;
	 			}
			} 
			break;
		case "mobile":
			if(obj.value == "") {
				msgBox.innerHTML = "手机号码不能为空";
				msgBox.className = "error";
				return false;
			}
			var reg = /^1[3|4|5|8|7][0-9]\d{4,8}$/;
			var objExp=new RegExp(reg);		//创建正则表达式对象
			if(objExp.test(obj.value)!=true){				//通过正则表达式进行验证
				msgBox.innerHTML = "手机号码错误";
				msgBox.className = "error";
				return false;
			}
			break;
		case "address":
			if(obj.value == "") {
				msgBox.innerHTML = "收货地址不能为空";
				msgBox.className = "error";
				return false;
			}
			var a = aCity[parseInt(dentityCode.substr(0,2))];
	 		if(a!=obj.value) {
				msgBox.innerHTML = "收货地址要和身份证地址相同";
	 			alert("收货地址要和身份证地址相同");
	 			return false;
	 		}
	}
	return true;
}

function checkForm(frm)
{
	var els = frm.getElementsByTagName("input");
	for(var i=0; i<els.length; i++) {
		if((els[i].getAttribute("onblur")) == "CheckItem(this);") {
			if(!CheckItem(els[i])) return false;
		}
	}
	return true;
}
function showChater()
{
	var _chater = document.createElement("div");
	_chater.setAttribute("id", "chater");
	var _dl = document.createElement("dl");
	var _dt = document.createElement("dt");
	var _dd = document.createElement("dd");
	var _a = document.createElement("a");
	_a.setAttribute("href", "#");
	_a.onclick = openRoom;
	_a.appendChild(document.createTextNode("在线聊天"));
	_dd.appendChild(_a);
	_dl.appendChild(_dt);
	_dl.appendChild(_dd);
	_chater.appendChild(_dl);
	document.body.appendChild(_chater);
}
function openRoom()
{
	window.open("chat-room.html","chater","status=0,scrollbars=0,menubar=0,location=0,width=600,height=400");
}

function scrollChater()
{
	var chater = document.getElementById("chater");
	var scrollTop = document.documentElement.scrollTop;
	var scrollLeft = document.documentElement.scrollLeft;
	chater.style.left = scrollLeft + document.documentElement.clientWidth - 92 + "px";
	chater.style.top = scrollTop + document.documentElement.clientHeight - 25 + "px";
}

function inArray(array, str)
{
	for(a in array) {
		if(array[a] == str) return true;
	}
	return false;
}

function setCookie(name,value)
{
  var Days = 30;
  var exp  = new Date();
  exp.setTime(exp.getTime() + Days*24*60*60*1000);
  document.cookie = name + "="+ escape(value) +";expires="+ exp.toGMTString();
}

function getCookie(name)
{
  var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
  if(arr != null) return unescape(arr[2]); return null;
}

function delCookie(name)
{
  var exp = new Date();
  exp.setTime(exp.getTime() - 1);
  var cval=getCookie(name);
  if(cval!=null) document.cookie=name +"="+cval+";expires="+exp.toGMTString();
}

