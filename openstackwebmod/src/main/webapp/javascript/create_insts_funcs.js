    function submitFunction() {
        //alert("Call to the submit function ");
        // If mode is basic, validate and put together the JSON and send to backend
        var vmreq = {
            "serverName" : "",
            "imageName" : "CMPE-MINI",
            "flavour"   : "m1.tiny"
        };
    
        var num_insts = $("#num-insts").val;
        var error_spin = "<div id=\"errspin\" ><font color=\"red\">Error: Please set number of VMs between 1 and 5</font></div>";
        var error_name = "<div id=\"errname\" ><font color=\"red\">Error: Instance Name must start with a letter and be non-empty.</font></div>";
                
        //var request = {"request":[{"servername":"MY VM 3","imageName":"CMPE-MINI","flavour":"m1.tiny"},{"servername":"MY VM 4","imageName":"CMPE-MINI","flavour":"m1.tiny"}]};
        var url = "rest/create_insts";
       
       /* 
        $.post(
            url,
            request,
            function (data, status) {
                console("Data from the server is "+data);
                console("Status returned was "+status);
            });
            */
        
        if($('input:radio("name==radio_choice"):checked').val() == "Basic"){
            //alert("Spin value "+"#num-insts".readonly);
            if ($("#num-insts").val() < 1 || $("#num-insts").val() > 5 || !($.isNumeric($("#num-insts").val())) ) {
                //alert("Found illegal number of inst!");
                if ($("#errspin").length > 0) {
                    //do nothing, error msg is already ther
                } else {
                    $("#basic-create").append($(error_spin));        
                }
            } else {
                if ($("#errspin").length > 0) {
                    $("#errspin").remove();
                }
            }
            if ( $("#vm-name").val().match(/^\d+/) || ($("#vm-name").val().match(/^$/)) ) {
                if (!($("#errname").length > 0)) {
                    $(error_name).insertAfter($("#vm-name"));
                }
            } else {
               if ($("#errname").length > 0) {
                    $("#errname").remove();
                }                
            }
            if (! ($("#errspin").length > 0 || $("#errname").length > 0) ) {  // if we've made it this far without an error
                //alert("Made it to this spot.. now to make a request...");
                var instname = $("#vm-name").val();
                var flavor = $("#vm-flavor").val();
                var image = $("#vm-image").val();
                var vmnum = $("#num-insts").val();
                var req = {
                    request:[]
                };
                if (vmnum > 1) {
                    for (var i=0 ; i < vmnum ; i++) {
                        tempreq = Object.create(vmreq);
                        tempreq["servername"] = instname+"_"+i;
                        tempreq["imageName"] = image;
                        tempreq["flavour"] = flavor;
                        req.request[req.request.length] = tempreq;
                        // Send request to REST and change back to main tab
                        // if this doesn't work, try $.ajax for more options
                        /*$.post(
                            url,
                            req,
                            function (data, status) {
                                console("Data from the server is "+data);
                                console("Status returned was "+status);
                            });
                        */
                    }
                    $("#active-inst-button").click();
                        
                } else {
                    tempreq = Object.create(vmreq);
                    tempreq["serverName"] = instname;
                    tempreq["imageName"] = image;
                    tempreq["flavour"] = flavor;
                    req.request[0] = tempreq;
                    
                    // Send request to REST and change back to main tab
                    /*$.post(
                            url,
                            req,
                            function (data, status) {
                                console("Data from the server is "+data);
                                console("Status returned was "+status);
                            });
                        */
                    $("#active-inst-button").click();
                    
                }
            }
        } // if mode is service, validate prefix and send the service type to the createService function
        else if($('input:radio("name==radio_choice"):checked').val() == "Services") {
            request = createService();
            // Send request to REST and change back to main tab
                    /*$.post(
                            url,
                            request,
                            function (data, status) {
                                console("Data from the server is "+data);
                                console("Status returned was "+status);
                            });
                        */
            //console.log(JSON.stringify(request));
            $("#active-inst-button").click();
        }
    }
    
    function resetFunction() {
        // For some reason, if the radio was set to Service, the form wasn't triggering
        // the change event of the radio.  So I'm manually saying that if
        // reset is called and the form was set to service.  Set it back to Basic
        //var value = $('input:radio(\"name==radio_choice\"):checked').val();
        //alert("Called Reset Function "+value);
        if($('input:radio("name==radio_choice"):checked').val() == "Basic"){
            $("#service-create").hide();
            $("#basic-create").show();
        }
        else if($('input:radio("name==radio_choice"):checked').val() == "Services") {
            $("#service-create").hide();
            $("#basic-create").show();
        }
        
        // if validation error is still there, remove it
        if ($("#errspin").length > 0) {
            $("#errspin").remove();
        }
    }
    
    function createService() {
        
        //alert("Call to createService function");
        var instname = $("#serve-name").val();
        var flavor = "m1.tiny";
        var image = "CMPE-MINI";
        var scalable = $("#serve-scale").val();

        var vmreq = {
            "serverName" : "",
            "imageName" : "CMPE-MINI",
            "flavour"   : "m1.tiny"
        };
        
        var req = {
            request:[]
        };
              
        if ($("#serve-type option:selected").val() =="SmallApplication") {
            tempreq = Object.create(vmreq);

            tempreq["serverName"] = instname;
            // Hardcoded right now..
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[0] = tempreq;            
        }
        else if ($("#serve-type option:selected").val() =="WebSite") {
            tempreq = Object.create(vmreq);

            tempreq["serverName"] = instname+"_webserver";
            // Hardcoded right now..
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[0] = tempreq;

            tempreq = Object.create(vmreq);            
            tempreq["serverName"] = instname+"_sqldb";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[1] = tempreq;
        }
        else if ($("#serve-type option:selected").val() =="LargeWebSite") {
            
            // Hardcoded right now..
            //tempreq["imageName"] = image;
            //tempreq["flavour"] = flavor;
            tempreq = Object.create(vmreq);            
            tempreq["serverName"] = instname+"_web";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[0] = tempreq;
            
            tempreq = Object.create(vmreq);            
            tempreq["serverName"] = instname+"_web2";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[1] = tempreq;

            tempreq = Object.create(vmreq);            
            tempreq["serverName"] = instname+"_sqldb";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[2] = tempreq;        
        }
        else if ($("#serve-type option:selected").val() == "EnterpriseCloud") {
            tempreq = Object.create(vmreq);
            // Hardcoded right now..
            //tempreq["imageName"] = image;
            //tempreq["flavour"] = flavor;
            
            tempreq["serverName"] = instname+"_ecloud";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[0] = tempreq;
            tempreq = Object.create(vmreq);            
            tempreq["serverName"] = instname+"_ecloud1";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[1] = tempreq;
            tempreq = Object.create(vmreq);            
            tempreq["serverName"] = instname+"_sqldb";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[2] = tempreq;
            tempreq = Object.create(vmreq);            
            tempreq["serverName"] = instname+"_ec_webapps";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[3] = tempreq;
            tempreq = Object.create(vmreq);            
            tempreq["serverName"] = instname+"_ec_payroll";
            tempreq["imageName"] = image;
            tempreq["flavour"] = flavor;
            req.request[4] = tempreq;        

        }
        
        return req;

    }

