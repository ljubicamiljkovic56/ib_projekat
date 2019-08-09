function accept(){
	
	function popuniTabelu() {
		usersTable.find('tr:gt(1)').remove();

		for(it of usersTable){
			usersTable.append(
					'<tr>' + 
					'<td><a href="admin.html?id=' + it.id + '">' + it.id + '</a></td>' +
					'<td>' + it.username + '">' + it.password + '</td>' +
					'<td>' + it.certificate + '</td>' + it.email + '<td>' + 
					'<td>' + it.active + '</td>' + '<td>' + it.authority+ '</td>' + 
				'</tr>'	
			)
		}
	};
	
	
	var usernameInput = $('#usernameInput');
	var passwordInput = $('#passwordInput');
	var emailInput = $('#emailInput');
	
	var username = usernameInput.val();
	var password = passwordInput.val();
	var email = emailInput.val();
	
	$.post("http://localhost:8080/api/users/user/accept",
			
	{
		'username': username, 
		'password': password,
		'email': email
	},
	function(response){
		console.log('odgovor');
		console.log(response);
	}
	);
	
}