var imported = document.createElement('script');
imported.src = 'plugin/js/jquery.thumbnailScroller.js';
document.getElementsByTagName('head')[0].appendChild(imported);

function logout() {
	$.ajax({
		type : "GET",
		url : "logout",
		//TODO : username should be sent to the user for session id
		success : function(data) {
			window.location = 'login.jsp';
		}
	});
}

function highlightFunc(element) {
		//alert(element.id);
		if (element.id == "instances") {
			$("#loading-lv-image").removeClass("hidden");
			loadLI(); 
			
			$('a[href="#&instances"]').addClass("navActive");
			$('a[href="#&volumes"]').removeClass("navActive");
			$('a[href="#&support"]').removeClass("navActive");
			$('#instances_div').removeClass("hidden");
			$('#volumes_div').addClass("hidden");
			$('#h_listInstances_link').addClass("selected_tile");
            $('#h_createInstances_link').removeClass("selected_tile");
            $('#h_listVolumes_link').removeClass("selected_tile");
            $('#h_createVolumes_link').removeClass("selected_tile");
            $('#h_deleteVolumes_link').removeClass("selected_tile");
			
		}
		if (element.id == "volumes" ) {
			$("#loading-lv-image").removeClass("hidden");
			loadLV();
			$('a[href="#&volumes"]').addClass("navActive");
			$('a[href="#&instances"]').removeClass("navActive");
			$('a[href="#&support"]').removeClass("navActive");
			$('#volumes_div').removeClass("hidden");
			$('#instances_div').addClass("hidden");
			$('#h_listInstances_link').removeClass("selected_tile");
            $('#h_createInstances_link').removeClass("selected_tile");
            $('#h_listVolumes_link').addClass("selected_tile");
            $('#h_createVolumes_link').removeClass("selected_tile");
            $('#h_deleteVolumes_link').removeClass("selected_tile");
			
		}
	}
	
	function loadCV(){
		$('#h_listInstances_link').removeClass("selected_tile");
        $('#h_createInstances_link').removeClass("selected_tile");
        $('#h_listVolumes_link').removeClass("selected_tile");
        $('#h_createVolumes_link').addClass("selected_tile");
        $('#h_deleteVolumes_link').removeClass("selected_tile");
		$.ajax({
            url : "cv.jsp",
            success : function(data) {
            	document.getElementById("contentDiv").innerHTML = "";
                document.getElementById("contentDiv").innerHTML = data;
            },
            error : function() {
                alert("error loading instances");
            }
        });
	}
	
	function loadCI() {
		$('#h_listInstances_link').removeClass("selected_tile");
        $('#h_createInstances_link').addClass("selected_tile");
        $('#h_listVolumes_link').removeClass("selected_tile");
        $('#h_createVolumes_link').removeClass("selected_tile");
        $('#h_deleteVolumes_link').removeClass("selected_tile");
		$.ajax({
            url : "CI.jsp",
            success : function(data) {
//            	alert('check 1');
            	
                document.getElementById("contentDiv").innerHTML = data;
                pageLoad();
            },
            error : function() {
                alert("error loading instances");
            }
        });
	}
	
	function loadLI() {
		$('#h_listInstances_link').addClass("selected_tile");
        $('#h_createInstances_link').removeClass("selected_tile");
        $('#h_listVolumes_link').removeClass("selected_tile");
        $('#h_createVolumes_link').removeClass("selected_tile");
        $('#h_deleteVolumes_link').removeClass("selected_tile");
		$.ajax({
            url : "listinstances.jsp",
            success : function(data) {
            	$("#loading-lv-image").addClass("hidden");
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
	
	function loadDV() {
		$('#h_listInstances_link').removeClass("selected_tile");
        $('#h_createInstances_link').removeClass("selected_tile");
        $('#h_listVolumes_link').removeClass("selected_tile");
        $('#h_createVolumes_link').removeClass("selected_tile");
        $('#h_deleteVolumes_link').addClass("selected_tile");
		$.ajax({
            url : "dv.jsp",
            success : function(data) {
            	 document.getElementById("contentDiv").innerHTML = data;
            	 $('#delete_vol_ul').carouFredSel({
            			auto: false,
            			prev: '#prev_delete_vol',
            			next: '#next_delete_vol',
            			mousewheel: true,
            			swipe: {
            				onMouse: true,
            				onTouch: true
            			}
            		});
            },
            error : function() {
                alert("error in delete volume");
            }
        });
	}
	
	function loadLV() {
		$('#h_listInstances_link').removeClass("selected_tile");
        $('#h_createInstances_link').removeClass("selected_tile");
        $('#h_listVolumes_link').addClass("selected_tile");
        $('#h_createVolumes_link').removeClass("selected_tile");
        $('#h_deleteVolumes_link').removeClass("selected_tile");
		$.ajax({
            url : "lv.jsp",
            success : function(data) {
    			 $("#loading-lv-image").addClass("hidden");
            	 document.getElementById("contentDiv").innerHTML = data;
            	 $(function() {
      				$("#volumes_ul").carouFredSel({
      					auto: false,
      					prev: '#prevVol',
      					next: '#nextVol',
      					mousewheel: true,
      					swipe: {
      						onMouse: true,
      						onTouch: true
      					}
      				});
             	 });
            },
            error : function() {
                alert("error loading instances");
            }
        }); 
	}