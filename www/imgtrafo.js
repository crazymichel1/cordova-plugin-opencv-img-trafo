var imgtrafo = {
	test: function() {
		// test values
		var startDate = new Date("September 24, 2013 8:00:00");
        var endDate = new Date("September 24, 2013 18:00:00");
        var title = "PhoneGap Day";
        var location = "Amsterdam";
        var notes = "Arrive on time, don't want to miss out!";
        var success = function() { alert("Success"); };
        var error = function(message) { alert("Oopsie! " + message); };
		
		cordova.exec(
			successCallback, // success callback function
            errorCallback, // error callback function
            'Calendar', // mapped to our native Java class
            'addCalendarEntry', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "title": title,
                "description": notes,
                "eventLocation": location,
                "startTimeMillis": startDate.getTime(),
                "endTimeMillis": endDate.getTime()
            }]
		);
	}
	
	test2: function() {
    	alert('test');
	}
	
};

module.exports = imgtrafo; // export is important!
