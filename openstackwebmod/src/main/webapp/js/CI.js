var numberOfDrops = 0;
var isOsSet=false;
var isFlavorSet=false;

function addFlavor(id)
{
	numberOfDrops=numberOfDrops+1;
	isFlavorSet=true;
	var textval = document.getElementById(id).textContent || element.innerText;
	var flavorNode=document.createElement("A");
	flavorNode.setAttribute("class", "tile");
	flavorNode.setAttribute("class", "hidden");
	flavorNode.setAttribute("class", "flavor-tile");
	var flavor = id.substr(2,id.length-2);
	flavorNode.setAttribute("id", "f"+flavor);
	document.getElementById("ci-dropzone").appendChild(flavorNode);
	var br = document.createElement('br');
	document.getElementById("f"+flavor).appendChild(br);
	var textnode=document.createTextNode("Flavor: "+ textval); 
	document.getElementById("f"+flavor).appendChild(textnode);
	
	var deleteIcon = document.createElement("A");
	deleteIcon.setAttribute("title", "Delete");
	deleteIcon.setAttribute("class", "ci-delete_icon");
	deleteIcon.setAttribute("id", "ci-flavor-delete");
	document.getElementById("f"+flavor).appendChild(deleteIcon);
	document.getElementById("ci-flavor-delete").onclick = removeFlavor;
	$(document.getElementById("f"+flavor)).removeClass("hidden");
	$(document.getElementById("f"+flavor)).addClass("selected");
	$(document.getElementById("f"+flavor)).animate({"width":230},400);

//	$(document.getElementById("f"+flavor)).getElementsByTagName('a')[0].addClass("selected");
	$("#ci-flavor-delete").addClass("selected");

	if(numberOfDrops==1)
	{
		$("#cl-flavor-list").addClass("hidden");
		$("#cl-image-list").removeClass("hidden");
		$("#ci-images").addClass("subNavItemActive");
		$("#ci-flavors").removeClass("subNavItemActive");
	}

}

function addOS(id)
{
	numberOfDrops=numberOfDrops+1;
	isOsSet=true;
	var textval = document.getElementById(id).textContent || element.innerText;
	var node=document.createElement("A");
	node.setAttribute("class", "tile");
	node.setAttribute("class", "hidden");
	node.setAttribute("class", "image-tile");
	var osType = id.substr(2,id.length-2);
	node.setAttribute("id", "o"+osType);
	document.getElementById("ci-dropzone").appendChild(node);
	var br = document.createElement('br');
	document.getElementById("o"+osType).appendChild(br);
	var textnode=document.createTextNode("OS: "+ textval); 
	document.getElementById("o"+osType).appendChild(textnode);
		
	var deleteIcon = document.createElement("A");
	deleteIcon.setAttribute("title", "Delete");
	deleteIcon.setAttribute("class", "ci-delete_icon");
	deleteIcon.setAttribute("id", "ci-image-delete");
	document.getElementById("o"+osType).appendChild(deleteIcon);
	document.getElementById("ci-image-delete").onclick = removeOS;
	
	$(document.getElementById("o"+osType)).removeClass("hidden");
	$(document.getElementById("o"+osType)).addClass("selected");
	$(document.getElementById("o"+osType)).animate({"width":230},400);
//	$(document.getElementById("o"+osType)).getElementsByTagName('a')[0].addClass("selected");
	$("#ci-image-delete").addClass("selected");
	if(numberOfDrops==1)
	{
		$("#cl-flavor-list").removeClass("hidden");
		$("#cl-image-list").addClass("hidden");
		$("#ci-flavors").addClass("subNavItemActive");
		$("#ci-images").removeClass("subNavItemActive");
	}


}
function removeFlavor()
{	
	document.getElementById("ci-dropzone").removeChild((document.getElementsByClassName("flavor-tile"))[0]);
	numberOfDrops = numberOfDrops-1;
	isFlavorSet=false;
}

function removeOS()
{
	document.getElementById("ci-dropzone").removeChild(document.getElementsByClassName("image-tile")[0]);
	numberOfDrops = numberOfDrops-1;
	isOsSet=false;
	
}

function refreshPage()
{
	isOsSet=false;
	isFlavorSet=false;
	numberOfDrops=0;
	var arr1 = document.getElementsByClassName('image-tile');
	var arr2 = document.getElementsByClassName('flavor-tile');
	elem1=arr1[0];
	elem2=arr2[0];
	document.getElementById("ci-dropzone").removeChild(elem1);
	document.getElementById("ci-dropzone").removeChild(elem2);
}

function resetSubNav()
{
	if(numberOfDrops== 0 || (numberOfDrops==1 && isFlavorSet==true))
	{
		$("#cl-flavor-list").addClass("hidden");
		$("#cl-image-list").removeClass("hidden");
		$("#ci-flavors").removeClass("subNavItemActive");
		$("#ci-images").addClass("subNavItemActive");
	}
	else if(numberOfDrops==1 && isOsSet ==true)
	{
		$("#cl-flavor-list").removeClass("hidden");
		$("#cl-image-list").addClass("hidden");
		$("#ci-flavors").addClass("subNavItemActive");
		$("#ci-images").removeClass("subNavItemActive");
	}
	
}


function pageLoad() {
	
	resetSubNav();
	isOsSet=false;
	isFlavorSet=false;
	numberOfDrops=0;
	$(".ci-flavor-li").click(function() {
		if(isFlavorSet == true)
		{
			$("#ci_error2").removeClass("hidden");
			$("#ci_error2").addClass("ci-warning");
			var time = window.setTimeout(function(){$("#ci_error2").removeClass("ci-warning");$('#ci_error2').addClass('hidden');}, 3000);
		}
		else
			addFlavor((this.getElementsByTagName('a'))[0].getAttribute('id'));
		
	});

	$(".ci-image-li").click(function() {
		if(isOsSet == true)
		{
			$("#ci_error1").removeClass("hidden");
			$("#ci_error1").addClass("ci-warning");
			var time = window.setTimeout(function(){$("#ci_error1").removeClass("ci-warning");$('#ci_error1').addClass('hidden');}, 3000);
		}
		else
			addOS((this.getElementsByTagName('a'))[0].getAttribute('id'));
		
	});
	

	$("#ci-images").click(function(){
		$("#cl-flavor-list").addClass("hidden");
		$("#cl-image-list").removeClass("hidden");
		$("#ci-images").addClass("subNavItemActive");
		$("#ci-flavors").removeClass("subNavItemActive");
		
	});
	
	$("#ci-flavors").click(function(){
		$("#cl-flavor-list").removeClass("hidden");
		$("#cl-image-list").addClass("hidden");
		$("#ci-flavors").addClass("subNavItemActive");
		$("#ci-images").removeClass("subNavItemActive");
	});
	

	$("#ci-create-button").click(function() {
		if(numberOfDrops < 2)
			alert("Image and flavor both need to be added to create an instance");
		else
		{
			var r = confirm("Create an instance?");
			if (r == true) {
				osId = $("#ci-dropzone").children(".image-tile").attr('id');
				os = osId.substr(1, osId.length - 1);
				flavorId = $("#ci-dropzone").children(".flavor-tile").attr('id');
				flavor = flavorId.substr(1, flavorId.length - 1);
				$("#ci-loading-image-div").addClass("ci-loading-image");
				$("#ci-loading-image-div").removeClass("hidden");
				$.ajax({
					type : "GET",
					url : "create",
					data : "image=" + os + "&flavor=" + flavor,
					success : function(data) {
						$("#ci-loading-image-div").removeClass("ci-loading-image");
						$("#ci-loading-image-div").addClass("hidden");
		                $('#ci_success').removeClass('hidden');
		                $('#ci_success').addClass('ci-box-download');
						refreshPage();
						var tim = window.setTimeout(function(){$('#ci_success').removeClass('ci-box-download');$('#ci_success').addClass('hidden');}, 7000);
					},
					error : function() {
						$("#ci-loading-image-div").removeClass("ci-loading-image");
						$("#ci-loading-image-div").addClass("hidden");
						$("#ci_error").removeClass("hidden");
						var time = window.setTimeout(function(){$('#ci_error').addClass('hidden');}, 3000);
					}
				});
			} else
				alert("Instance Not Created");
		}
	});
	
}
