function download() {
	
	$.get("http://localhost:8080/api/users/user/download", {
		
	}
	);
	function(response){
		console.log('download....');
		console.log(response);
		alert("Download uspesan");
	}
	
}