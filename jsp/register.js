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


$(function() {
	$( ".accordion" ).accordion();
});


