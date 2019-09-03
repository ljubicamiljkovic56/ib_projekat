function download() {

	
	var xhr = new XMLHttpRequest();
	xhr.open('GET', "/api/users/user/download", true);
	xhr.responseType = 'blob';
	xhr.onload = function(e) {
		if (this.status == 200) {
			var blob = this.response;
			console.log(blob);
			var a = document.createElement('a');
			var url = window.URL.createObjectURL(blob);
			a.href = url;
			a.download = xhr.getResponseHeader('filename');
			a.click();
			window.URL.revokeObjectURL(url);
		}

	};

	xhr.send();
};

//window.onload = function() {
//	var link_za_cert = document.createElement('a');
//	link_za_cert.setAttribute('href', window.location.href + 'api/users/get-cert');
//	link_za_cert.innerText = 'Download certificates';
//	
//	document.body.appendChild(link_za_cert);
};