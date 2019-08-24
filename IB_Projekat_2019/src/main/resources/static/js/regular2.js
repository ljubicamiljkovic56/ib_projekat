function downloadjks(){
	var usernameInput = $('#usernameInput');
	var username = usernameInput.val();
	
	$.get("http://localhost:8080/api/users/download", 
	
	{
		'username':username
	},
	
	function(response){
		console.log('saljem jks');
		console.log(response);
	}
	);
}