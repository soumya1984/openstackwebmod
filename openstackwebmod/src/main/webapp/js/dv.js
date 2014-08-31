function deleteVolume(element)
 {
	 var vol_id=(element.getAttribute('dv_volumeid')).substr(2,(element.getAttribute('dv_volumeid')).length-2);
	 var child = document.getElementById("dv-status"+vol_id);
	 
	 var status = child.getAttribute("status");
//	 alert(status);
	 if(status == 0)
	 {
		 $("#dv_error").removeClass("hidden");
		 var time = window.setTimeout(function(){$('#dv_error').addClass('hidden');}, 3000);
	 }
	 else
	 {
	
		 $("#dv-loading-image-div").addClass("dv-loading-image");
		 $("#dv-loading-image-div").removeClass("hidden");
		 $.ajax({
				type : "GET",
				url : "delete",
				data : "nodeid=" + vol_id ,
				async : "false",
				success : function(data) {
					
					$.ajax({
						url : "dv.jsp",
						success : function(data) {
							document.getElementById("dv-volumes-list").innerHTML = data;
							$("#dv-loading-image-div").removeClass("dv-loading-image");
							$("#dv-loading-image-div").addClass("hidden");
			                $('#dv_success').removeClass('hidden');
			                $('#dv_success').addClass('dv-box-download');
							var tim = window.setTimeout(function(){$('#dv_success').removeClass('dv-box-download');$('#dv_success').addClass('hidden');}, 4000);
						},
						error : function() {
							$("#dv-loading-image-div").removeClass("dv-loading-image");
							$("#dv-loading-image-div").addClass("hidden");
							alert("error");
							$("#dv_error").removeClass("hidden");
							var time = window.setTimeout(function(){$('#dv_error').addClass('hidden');}, 3000);

						}
					});
				},
				error : function() {
					alert("error here");
					$("#dv-loading-image-div").removeClass("dv-loading-image");
					$("#dv-loading-image-div").addClass("hidden");
					$("#dv_error").removeClass("hidden");
					var time = window.setTimeout(function(){$('#dv_error').addClass('hidden');}, 3000);
				}
			});

	 }
 }