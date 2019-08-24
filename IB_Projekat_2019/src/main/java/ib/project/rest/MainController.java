package ib.project.rest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;





@Controller
public class MainController {
	
	@GetMapping(value="/get-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)

	public @ResponseBody byte[] getImageWithMediaType() throws IOException {
		InputStream in = Files.newInputStream(Paths.get("C:\\Users\\Ljubica\\Desktop"
				+ "\\ib_projekat\\ib_projekat\\IB_Projekat_2019\\resources\\static\\user_content\\cat.jpg"));
		return IOUtils.toByteArray(in);
	}
}
