// index.js


var basedir = "chansy.mybluemix.net";
var domain = document.domain;
if(domain.localeCompare((basedir) != 0){
	basedir = "localhost:9080"
}

// request message on server
//Calls SimpleServlet to get the "Hello World" message
xhrGet("SimpleServlet", function(responseText){
	// add to document
	var mytitle = document.getElementById('username');
	mytitle.innerHTML = responseText;

}, function(err){
	console.log(err);
});

/*
//Calls LogClassifier to get the "Hello World" message
xhrGet("LogClassifier", function(responseText){
	// add to document
	var mytitle = document.getElementById('javaerrorClassifier');
	mytitle.innerHTML = responseText;

}, function(err){
	console.log(err);
});

*/

$("#logClassify").click(function(){
	var logline = $("#logline").val();
	var data = { logline: logline };

	var str = "info";
	$.ajax( {
        url: basedir +'/ChansyApp/LogClassifier',
        method: 'POST',
        data: data,
        success:function(res) {
           var r = res.split("-");
           console.log(r[1]);
           $("#logclass").html(r[0]);
           $("#logscore").html((r[1] * 100).toFixed(2));
           if(r[0] == "JavaError"){
        	   str = "danger";
           }else if(r[0] == "JavaWarning")
           {str = "warning";}

           $("#logclass").addClass("label-"+str);
           $("#logscore").addClass("bg-"+str);

           console.log(res);
        }
     });
});

/*
xhrPost("LogClassifier", function(responseText){
	// add to document
	var logline = "";
	logline = document.getElementById('logline').value;
	console.log(logline+responseText);


}, function(err){
	console.log(err);
});
*/


//utilities
function createXHR(){
	if(typeof XMLHttpRequest != 'undefined'){
		return new XMLHttpRequest();
	}else{
		try{
			return new ActiveXObject('Msxml2.XMLHTTP');
		}catch(e){
			try{
				return new ActiveXObject('Microsoft.XMLHTTP');
			}catch(e){}
		}
	}
	return null;
}

function xhrGet(url, callback, errback){
	var xhr = new createXHR();
	xhr.open("GET", url, true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				callback(xhr.responseText);
			}else{
				errback('service not available');
			}
		}
	};
	xhr.timeout = 3000;
	xhr.ontimeout = errback;
	xhr.send();
}

function xhrPost(url, callback, errback){
	var xhr = new createXHR();
	xhr.open("POST", url, true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				callback(xhr.responseText);
			}else{
				errback('service not available');
			}
		}
	};
	xhr.timeout = 3000;
	xhr.ontimeout = errback;
	xhr.send();
}

function parseJson(str){
	return window.JSON ? JSON.parse(str) : eval('(' + str + ')');
}
function prettyJson(str){
	// If browser does not have JSON utilities, just print the raw string value.
	return window.JSON ? JSON.stringify(JSON.parse(str), null, '  ') : str;
}
