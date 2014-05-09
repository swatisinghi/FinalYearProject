function showForm(option) { 
	if($(option).val() == 's') {
		$('#facultyForm').hide(); $('#studentForm').show(); 
	}
	else {
		$('#studentForm').hide(); $('#facultyForm').show(); 
	}
} 

$(function() {
	$( ".datePicker" ).datepicker({dateFormat:"yy-mm-dd" });
});

$(function() {
	$( ".tabs" ).tabs();
});


function validateSendForm() {
	$('#sendProcessingMsg').show();
	var toEmail = $('#email').val();
	var msg = $('#msg').val();
	if(toEmail == null || toEmail.trim().length() == 0) {
		$('#sendProcessingMsg').html('Please enter an email id');
		return false;
	}

	if(msg == null || msg.trim().length() == 0) {
		return confirm('Do you want to send blank message?');
	}
	return true;
}

function validateSendForm() {
	
	return true;
}


$(function() {
	$( ".accordion" ).accordion();
});

$(function() {
	var availableTags = [
		
	];
	$( ".tags" ).autocomplete({
		source: availableTags
	});
});

