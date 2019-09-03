function register(){
		
	var usernameInput = $('#usernameInput');
	var passwordInput = $('#passwordInput');
	var emailInput = $('#emailInput');
	
	var username = usernameInput.val();
	var password = passwordInput.val();
	var email = emailInput.val();
	
	$.post("http://localhost:8080/api/users/user/register",
			
	{
		'username': username, 
		'password': password,
		'email': email
	},
	function(response){
		console.log('odgovor');
		console.log(response);
		alert('Uspesno ste se registrovali.')
	}
	);
}