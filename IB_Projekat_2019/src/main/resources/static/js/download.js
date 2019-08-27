function download() {
	
	$.get("http://localhost:8080/api/users/user/prikaz_slika", {
		
		
		
	}
	);
	function(response){
		console.log('download....');
		console.log(response);
		alert("Download uspesan");
	}
	
}