var SamplePlugin = {
	
	methodOne: function() {
		// simple alert dialog message for testing
		var message = "Hello World!";
		
		// simple js callback functions for testing
		var successCallback = function() { };
		var errorCallback = function(message) { alert("Error: " + message); };
		
		cordova.exec(
	            successCallback, // in case of success, execute this js function
	            errorCallback, // in case of error, execute this js function
	            'SamplePlugin', // call the native Java class with this name
	            'showAlertDialog', // there, execute the action with this name
	            [{                  // and pass this array of custom arguments to it
	                "message": message
	            }]
			);
			
	}	
};

module.exports = SamplePlugin; // export is important to access it from other js!
