function unzip(){
	$.get("http://localhost:8080/api/users/prikaz_slika", 
	
	{},
	
	function(response){
		console.log('unzip, dekript...');
		console.log(response);
	}
	);
}