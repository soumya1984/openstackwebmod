/**
 * @author anshul
 */

function terminate(terminateButton) {
	var r = confirm("Terminate the instance?");
	if (r == true) {
		var nodeId = terminateButton.id;
		nodeId = nodeId.substr(2, nodeId.length - 1);
		$("#li-loading-image-div").removeClass("hidden");
		$.ajax({
			type : "GET",
			url : "terminateNode",
			data : "node=" + nodeId,
			success : function(data) {
				 $("#li-loading-image-div").addClass("hidden");
				$('#li_terminate_success').removeClass('hidden');
				var tim = window.setTimeout("li_hideMessage()", 7000);
				loadLI();
			},
			error : function() {
				$('#li_terminate_error').removeClass('hidden');
				var tim = window.setTimeout("li_hideMessage()", 7000);
			}
		});
	} 
}

function li_hideMessage(){
	$('#li_terminate_success').addClass('hidden');
	$('#li_terminate_error').addClass('hidden');
	$('#li_resume_success').addClass('hidden');
	$('#li_resume_error').addClass('hidden');
	$('#li_suspend_success').addClass('hidden');
	$('#li_suspend_error').addClass('hidden');
}

function suspend(suspendButton) {
	var r = confirm("Suspend the instance?");
	if (r == true) {
		var nodeId = suspendButton.id;
		nodeId = nodeId.substr(2, nodeId.length - 1);
		$("#li-loading-image-div").removeClass("hidden");
		$.ajax({
			type : "GET",
			url : "suspendNode",
			data : "node=" + nodeId,
			success : function(data) {
				 $("#li-loading-image-div").addClass("hidden");
				$('#li_suspend_success').removeClass('hidden');
				var tim = window.setTimeout("li_hideMessage()", 7000);
				loadLI();
				
			},
			error : function() {
				$('#li_suspend_error').removeClass('hidden');
				var tim = window.setTimeout("li_hideMessage()", 7000);
			}
		});
	} 
}

function resume(resumeButton) {
	var r = confirm("Resume the instance?");
	if (r == true) {
		var nodeId = resumeButton.id;
		nodeId = nodeId.substr(2, nodeId.length - 1);
		$("#li-loading-image-div").removeClass("hidden");
		$.ajax({
			type : "GET",
			url : "resumeNode",
			data : "node=" + nodeId,
			success : function(data) {
				 $("#li-loading-image-div").addClass("hidden");
				$('#li_resume_success').removeClass('hidden');
				var tim = window.setTimeout("li_hideMessage()", 7000);
				loadLI();
			},
			error : function() {
				$('#li_resume_error').removeClass('hidden');
				var tim = window.setTimeout("li_hideMessage()", 7000);
			}
		});
	} 
}


function loadLI_li() {
	
	alert("hello");
	$.ajax({
        url : "listinstances.jsp",
        success : function(data) {
        	 document.getElementById("contentDiv").innerHTML = data;
        	 $('#list_instances_ul').carouFredSel({
        			auto: false,
        			prev: '#prev_instance',
        			next: '#next_instance',
        			mousewheel: true,
        			swipe: {
        				onMouse: true,
        				onTouch: true
        			}
        		});
        },
        error : function() {
            alert("error loading instances");
        }
    });
}