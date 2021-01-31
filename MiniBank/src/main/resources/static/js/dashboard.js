$(document).ready(function() {
	
	$("#successAlertBtn").click(function(e) {
		$('#successAlert').alert('close');
	});

	$('#depositCheck').change(function() {
	    if (this.checked) {
	        $('#depositSubmitBtn').prop('disabled', false);
	    } else {
	        $('#depositSubmitBtn').prop('disabled', true);
	    }
	});
	
	$('#withdrawCheck').change(function() {
	    if (this.checked) {
	        $('#withdrawSubmitBtn').prop('disabled', false);
	    } else {
	        $('#withdrawSubmitBtn').prop('disabled', true);
	    }
	});
	
	$('#transferCheck').change(function() {
	    if (this.checked) {
	        $('#transferSubmitBtn').prop('disabled', false);
	    } else {
	        $('#transferSubmitBtn').prop('disabled', true);
	    }
	});
});