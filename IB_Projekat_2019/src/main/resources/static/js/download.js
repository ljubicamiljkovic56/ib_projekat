function download() {
	
	$.get("http://localhost:8080/api/users/user/pregled_slika", {},
			function(response){
				console.log('download....');
				console.log(response);
				alert("Download uspesan");
				
				var json_linkovi = JSON.decode(response);
				
				
				for (var json_link of json_linkovi){
					var img = document.createElement('img');
					img.setAttribute('src', 'localhost:8080' + json_link);
					document.body.appendChild(img);
				}
	}
	);
}