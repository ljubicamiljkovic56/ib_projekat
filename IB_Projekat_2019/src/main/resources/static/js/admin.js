//$(document).ready(function() {
//	var usersTable = $('#usersTable');
//	
//	
//	$.get("http://localhost:8080/api/users/user/all" +
//			"",
//			{
//			},
//			
//			
//			
//		
//	function(response){
//		console.log(response);
//		console.log('ispis...')
//		
//		for(s of response) {
//			usersTable.append(
//				'<tr>' + 
//				'<td><a href="admin.html?id='+ s.id + '">' + s.id + '</a></td>' + 
//				'<td>' + s.username + '</td>' + '<td>' + s.password + '</td>' + 
//				'<td>' + s.certificate + '</td>' + '<td>'+ s.email + '</td>' + 
//				'<td>' + s.active + '</td>' + '<td>' + s.authority + '</td>' + 
//				'<td>' + '<form>' + '<input type="submit" onclick="odobriUsera(\'' + s.username + '\')" value="Odobri" class="acceptSubmit" id="acceptSubmit">' + '</form>' + '</td>' + 
//				'</tr>'
//			)
//		}
//	}
//	);
//	
//	
//	function popuniTabelu() {
//
//		for(it of usersTable){
//			usersTable.append(
//					'<tr>' + 
//					'<td><a href="admin.html?id=' + it.id + '">' + it.id + '</a></td>' +
//					'<td>' + it.username + '">' + it.password + '</td>' +
//					'<td>' + it.certificate + '</td>' + it.email + '<td>' + 
//					'<td>' + it.active + '</td>' + '<td>' + it.authority+ '</td>' + 
//				'</tr>'	
//			)
//		}
//	};
//	
//});
//function odobriUsera(username) {
//	
//	
//	$.post("http://localhost:8080/api/users/user/accept",
//			
//			{
//		
//				'username': username
//		
//			},
//	
//	function(response){
//		console.log('odgovor');
//		console.log(response);
//	}
//	);
//	
//}
$(document).ready(function () {
	var usersTable = $('#usersTable');

	$.ajax({
		url: 'https://localhost:443/api/users/user/all',
		type: 'get',
		data: {},
		processData: false,
		dataType: 'json',
		success:function(response) {
			console.log(response);
			console.log('ispis...');

			for (s of response) {
				usersTable.append(
						'<tr>' +
						'<td><a href="admin.html?id=' + s.id + '">' + s.id + '</a></td>' +
						'<td>' + s.username + '</td>' + '<td>' + s.password + '</td>' +
						'<td>' + s.certificate + '</td>' + '<td>' + s.email + '</td>' +
						'<td>' + s.active + '</td>' + '<td>' + s.authority + '</td>' +
						'<td>' + '<form>' + '<input type="submit" onclick="odobriUsera(\'' + s.username + '\')" value="Odobri" class="acceptSubmit" id="acceptSubmit">' + '</form>' + '</td>' +
						'</tr>'
				)
			}
		}
	}).fail(function(xhr, err) {
		console.log('Greska pri ucitavanju tabele');
		console.log(err);
	});
});

	function odobriUsera(username) {	
		jQuery.ajax({
			url : 'https://localhost:443/api/users/user/accept',
			type : 'post',
			data : {'username' : username},
			success : function(response) {
				console.log('odgovor');
				console.log(response);
			}
		}).fail(function(xhr, err) {
			console.log('Greska pri odobravanju');
			console.log(err);
		});
}
