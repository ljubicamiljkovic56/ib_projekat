function downloadcert(){
	
	var usernameInput = $('#usernameInput');
	var username = usernameInput.val();
	
	$.get("http://localhost:8080/api/users/get-file", 
	
	{
		'username':username
	},
	
	function(response){
		console.log('saljem cert');
		console.log(response);
	}
	);
}
