/**
 * @author amulya 
 */
function cv_create() {
	if(cv_validate() == true) {
		var name = document.getElementById("cv-volume-name");
		var size = document.getElementById("cv-size");
		$.ajax({
			type : "GET",
			url : "volume",
			data : "name=" + name.value + "&size=" + size.value,
			success : function(data) {
				$('#cv_success').removeClass('hidden');
				var tim = window.setTimeout("hideMessage()", 3000);
				clearFields();
			},
			error : function() {
				$('#cv_error').removeClass('hidden');
				var tim = window.setTimeout("hideMessage()", 3000);
				clearFields();
			}
		});
	} else {
		return false;
	}
}

function clearFields(){
	document.getElementById("cv-volume-name").value="";
	document.getElementById("cv-size").value="";
}

function hideMessage(){
	$('#cv_error').addClass('hidden');
	$('#cv_error_form').addClass('hidden');
	$('#cv_success').addClass('hidden');
	$('#cv_error_size').addClass('hidden');
}



function cv_validate() {
	var name = document.getElementById("cv-volume-name");
	var size = document.getElementById("cv-size");
	var isnum = /^\d+$/.test(size.value);
	if(name.value==''){
		$('#cv_error_form').removeClass('hidden');
		var tim = window.setTimeout("hideMessage()", 5000);
		return false;
	} 
	if (size == '') {
		$('#cv_error_form').removeClass('hidden');
		var tim = window.setTimeout("hideMessage()", 5000);
		return false;
	} 
	if(isnum == false) {
		$('#cv_error_size').removeClass('hidden');
		var tim = window.setTimeout("hideMessage()", 5000);
		return false;
	}
	return true;
}

$('#cv-volume-name').keyup(function(){
    hideMessage();
});

//on keydown, clear the countdown 
$('#cv-size').keydown(function(){
    hideMessage();
});
