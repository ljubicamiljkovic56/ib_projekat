function register(){
		
	var usernameInput = $('#usernameInput');
	var passwordInput = $('#passwordInput');
	var emailInput = $('#emailInput');
	
	var username = usernameInput.val();
	var password = passwordInput.val();
	var email = emailInput.val();
	
	$.ajax({
		url: 'https://localhost:443/api/users/user/register',
		type: 'post',
		data: {'username':username, 'password':password, 'email':email},
		headers:{Accept: '*/*'},
		success:function(response){
			alert("Proveravam kredencijale...");
			console.log('Successful request');
			if(response != ''){
				alert('Registracija')
				alert('Uspesno ste se registrovali')
			}else {
				alert('Username vec postoji i ne mozete da se registrujete.')
			}
		}
	}).fail(function(xhr, err) {
		console.log('Error request');
		console.log(err);
		alert("Error");
	});
	
//	$.post("http://localhost:8080/api/users/user/register",
//			
//	{
//		'username': username, 
//		'password': password,
//		'email': email
//	},
//	function(response){
//		console.log('odgovor')
//		console.log(response);
//		if(response == ''){
//			alert('Username vec postoji i ne mozete da se registrujete.')
//		}else {
//			alert('Uspesno ste se registrovali.')
//		}
//		
//	}
//	);
}