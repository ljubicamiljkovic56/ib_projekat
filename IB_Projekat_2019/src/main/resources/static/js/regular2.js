function downloadjks(){
	var usernameInput = $('#usernameInput');
	var username = usernameInput.val();
	
//	$.get("http://localhost:8080/api/users/download", 
//	
//	{
//		'username':username
//	},
//	
//	function(response){
//		console.log('saljem jks');
//		console.log(response);
//	}
//	);
	
	$.ajax({
		url: 'https://localhost:443/api/users/download',
		type: 'get',
		data: {'username': username},
		success:function(response){
			alert("Saljem jks");
			console.log('Saljem jks');
			console.log(response);
		}
	}).fail(function(xhr, err) {
		console.log('Error request');
		console.log(err);	
	});
}