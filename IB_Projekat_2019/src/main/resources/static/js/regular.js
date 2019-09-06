function downloadcert(){
	
	var usernameInput = $('#usernameInput');
	var username = usernameInput.val();

	
	$.ajax({
		url: 'https://localhost:443/api/users/get-file',
		type: 'get',
		data: {'username': username},
		success:function(response){
			alert("Saljem cert");
			console.log('Saljem cert');
			console.log(response);
		}
	}).fail(function(xhr, err) {
		console.log('Error request');
		console.log(err);	
	});
	
}	
	
//	$.get("http://localhost:8080/api/users/get-file", 
//	
//	{
//		'username':username
//	},
//	
//	function(response){
//		console.log('saljem cert');
//		console.log(response);
//	}
//	);
