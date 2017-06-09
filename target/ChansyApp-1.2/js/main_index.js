// index.js

// request message on server
//Calls SimpleServlet to get the "Hello World" message
xhrGet("SimpleServlet", function(responseText){
	// add to document

	//var mytitle = document.getElementById('username');
	//mytitle.innerHTML = responseText;
	//console.log("Response"+responseText);
	//Get JSON issues from sentry
	var obj = JSON.parse(responseText);
	console.log(obj);
	var j=0;
	var stat = "danger";

	for (var key in obj){

	    console.log(obj[j].status);
	    if((obj[j].status).localeCompare("resolved") == 0){
	    	stat = "success";
	    }else if ((obj[j].status).localeCompare("unresolved") == 0){
	    	stat = "info";
	    }

	    $('#sentryIssues tr:last').after('<tr data-sentry="'+JSON.stringify(obj[j])+'"><td>'+ obj[j].title +'</td><td>'+obj[j].firstSeen+'</td><td>'+obj[j].type+'</td><td><span class="label label-'+stat+'">'+obj[j].status+'</span> </td><td>'+obj[j].permalink+'</td><td><div class="btn-group"><a class="btn btn-primary" href="#"><i class="icon_plus_alt2"></i></a><a href="#myModal'+obj[j].id+'" data-toggle="modal" class="btn btn-warning" class="btn btn-primary">View</a><a class="btn btn-success" href="#"><i class="icon_check_alt2"></i></a><a class="btn btn-danger" href="#"><i class="icon_close_alt2"></i></a></div></td> </tr>');
			$('#sentryDetail').prepend('<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal'+obj[j].id+'" class="modal fade" style="display: none;"> <div class="modal-dialog chansymodal"> <div class="modal-content"> <div class="modal-header"> <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button> <h4 class="modal-title">Incident Details</h4> </div> <div class="modal-body"> <section class="panel"> <header class="panel-heading tab-bg-primary "> <ul class="nav nav-tabs"> <li class="active"><a data-toggle="tab" href="#chansyED'+obj[j].id+'">Error Details</a></li> <li class=""><a data-toggle="tab" href="#chansyAD'+obj[j].id+'">Application Details</a></li> <li class="">  <a data-toggle="tab" href="#chansyI'+obj[j].id+'">Issue</a></li> <li><a data-toggle="tab" href="#chansyR'+obj[j].id+'">Recommendations</a> </li> </ul> </header> <div class="panel-body"> <div class="tab-content"><div class="tab-pane" id="chansyED'+obj[j].id+'" class="tab-pane active"> <form class="form-horizontal" role="form"><div class="form-group"><label for="inputEmail1" class="col-lg-2 control-label">Error Description</label><div class="col-lg-10"><input type="email" class="form-control" id="inputEmail4" placeholder="Email"></div></div><div class="form-group"><label for="inputPassword1" class="col-lg-2 control-label">Application Details</label><div class="col-lg-10"><input type="password" class="form-control" id="inputPassword4" placeholder="Password"></div></div><div class="form-group"><label for="inputPassword1" class="col-lg-2 control-label">Application</label><div class="col-lg-10"><input type="password" class="form-control" id="inputPassword4" placeholder="Password"></div></div><div class="form-group"><div class="col-lg-offset-2 col-lg-10"><button type="submit" class="btn btn-info">Repair</button></div></div></form></div> <div id="chansyAD'+obj[j].id+'" class="tab-pane">Application Details </div> <div id="chansyI'+obj[j].id+'" class="tab-pane">Issue</div> <div id="chansyR'+obj[j].id+'" class="tab-pane">Recommendations</div>  </div> </div> </section> </div> </div> </div> </div> ');

			j++;

	 }

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
        url: "http://localhost:9080/ChansyApp/LogClassifier",
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
