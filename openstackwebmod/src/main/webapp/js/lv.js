var storage;
 var selected_instance;
 function createVolume(element)
 {
	 document.getElementById("view_volume").style.display = "none";
	 document.getElementById("create_volume").style.display = "block";
 }
 function viewVolume(element)
 {
	 document.getElementById("create_volume").style.display = "none";
	 document.getElementById("instances_vol").style.display = "none";
	 document.getElementById("view_volume").style.display = "block";
 }
 function displayinfo_lv(ele)
 {
	var max_volumes=parseInt(100+parseInt(ele.getAttribute("no_of_volumes")));
	var current_vol=ele.getAttribute("current_vol_no");
	//alert(max_volumes+" "+ current_vol);
	for(var i=100; i< max_volumes;i++)
	{
		//alert(i);
		document.getElementById("inst"+i).style.display = "none";
	}
	var instid = "inst" + current_vol;
	//alert("instid " + instid);
	document.getElementById(instid).style.display = "block";
	showInst(current_vol);
 }

 	function showInst(current_vol){
 		//alert("showInst " + current_vol);
		var ulid= "#inst_ul_" + current_vol;
		var previd = "#prev" + current_vol;
		var nextid = "#next" + current_vol;
		var pagerid = "#pager" + current_vol;
		$(function() {
			$(ulid).carouFredSel({
				auto: false,
				prev: previd,
				next: nextid,
				mousewheel: true,
				swipe: {
					onMouse: true,
					onTouch: true
				}
				})
		});
	}
 function attachVolume(element)
 {
 	var nodeid = element.getAttribute("instanceid");
 	var volumeid= element.getAttribute("volumeid");
 	//alert(nodeid+" "+ volumeid);
 	$("#lv-loading-image-div").removeClass("hidden");
 	$.ajax({
		type : "GET",
		url : "attach",
		data : "nodeid=" + nodeid + "&volumeid=" + volumeid,
		async : "false",
		success : function(data) {
			$.ajax({
				url : "lv.jsp",
				success : function(data) {
					document.getElementById("contentDiv").innerHTML = data;
					 $("#lv-loading-image-div").addClass("hidden");
					// alert("attach");
					 $('#lv_attach_success').removeClass('hidden');
					 //alert("Volume attached sucess!");
					 var tim = window.setTimeout("lv_hideMessage()", 2000);
					 //alert("Volume attached successfully!");
					refreshLV();
				},
				error : function() {
					$('#lv_attach_failure').removeClass('hidden');
					 var tim = window.setTimeout("lv_hideMessage()", 2000);
					 alert("Unable to attach volume!");
					//alert("error");
				}
			});
		},
		error : function() {
			alert("error here");
		}
	});
 }
 function lv_hideMessage(){
	 //alert("hide");
		$('#lv_attach_success').addClass('hidden');
		$('#lv_attach_failure').addClass('hidden');
		$('#lv_dettach_success').addClass('hidden');
		$('#lv_dettach_failure').addClass('hidden');
		
	}
 function detachVolume(element)
 {
 	var volumeid= element.getAttribute("volumeid");
 	var nodeid=element.getAttribute("instanceid");
 	//alert(volumeid+" "+nodeid);
 	$("#lv-loading-image-div").removeClass("hidden");
 	$.ajax({
		type : "GET",
		url : "detach",
		data : "nodeid=" + nodeid + "&volumeid=" + volumeid,
		success : function(data) {
			$.ajax({
				url : "lv.jsp",
				success : function(data) {
					document.getElementById("contentDiv").innerHTML = data;
					$("#lv-loading-image-div").addClass("hidden");
					//alert("dettach");
					$('#lv_dettach_success').removeClass('hidden');
					//alert("dttach sucess");
					 var tim = window.setTimeout("lv_hideMessage()", 2000);
					 //alert("Volume detached successfully!");
					refreshLV();
				},
				error : function() {
					//alert("error retrieving volumes list");
					$('#lv_dettach_failure').removeClass('hidden');
					 var tim = window.setTimeout("lv_hideMessage()", 2000);
					 //alert("Unable to detach volume!");
				}
			});
			
		},
		error : function() {
			alert("error");
		}
	});
 }
 
 function refreshLV() {
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
 }