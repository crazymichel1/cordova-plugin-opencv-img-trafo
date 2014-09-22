var ImgTrafo = {
	
	methodOne: function() {
		// simple alert dialog message for testing
		var message = "Hello World!";
		
		// simple js callback functions for testing
		var successCallback = function() { };
		var errorCallback = function(message) { alert("Errors: " + message); };
		
		cordova.exec(
	            successCallback, // in case of success, execute this js function
	            errorCallback, // in case of error, execute this js function
	            'ImgTrafo', // call the native Java class with this name
	            'showAlertDialog', // there, execute the action with this name
	            [{                  // and pass this array of custom arguments to it
	                "message": message
	            }]
			);
			
	}	
};

module.exports = ImgTrafo; // export is important to access it from other js!
