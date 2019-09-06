function unzip(){
	
	$.ajax({
		url: 'https://localhost:443/api/users/prikaz_slika',
		type: 'get',
		data: {},
		success:function(response){
			alert("Pokrecem unzip i decrypt");
			console.log('unzip, dekript...');
			console.log(response);
		}
	}).fail(function(xhr, err) {
		console.log('Error request');
		console.log(err);
		alert("Error");
	});

//	
//	$.get("http://localhost:8080/api/users/prikaz_slika", 
//	
//	{},
//	
//	function(response){
//		console.log('unzip, dekript...');
//		console.log(response);
//	}
//	);
}