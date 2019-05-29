package ib.project.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.apache.xml.security.keys.storage.StorageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {

	private static String DATA_DIR_PATH;

	@Autowired
	ServletContext context;

	static {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		DATA_DIR_PATH = rb.getString("dataDir");
	}

/*	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> createAFileInResources() throws IOException {
		byte[] content = "Content".getBytes();

		String directoryPath = getResourceFilePath(DATA_DIR_PATH).getAbsolutePath();

		Path path = Paths.get(directoryPath + File.separator + "demo.txt");

		Files.write(path, content);
		System.out.println("Upload slike");
		return new ResponseEntity<String>(path.toString(), HttpStatus.OK);
	}*/
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes
			redirectAttributes) {
		Path uploadLocation = Paths.get("uploads");
		try {
			Files.createDirectories(uploadLocation);
		}catch(IOException ex) {
			throw new RuntimeException("Coud not initialize storage", ex);
		}
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if(file.isEmpty()) {
				throw new RuntimeException("Failed to store empty file " + filename);
			}
			
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, uploadLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Pokrecemo upload");
		} catch(IOException e) {
			throw new RuntimeException("Failed to store file " + filename, e);
		}
		
		return "{result: 'true'}";
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		URL urlPath = classloader.getResource(DATA_DIR_PATH + File.separator + "demo.txt");
		File file = null;
		try {
			file = new File(urlPath.getFile());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("filename", "demo.txt");

		byte[] bFile = readBytesFromFile(file.toString());

		return ResponseEntity.ok().headers(headers).body(bFile);
	}

	private static byte[] readBytesFromFile(String filePath) {

		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {

			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return bytesArray;
	}

	private File getResourceFilePath(String path) {
		URL url = this.getClass().getClassLoader().getResource(path);
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (Exception e) {
			file = new File(url.getPath());
		}

		System.out.println("Da li je uploadovano?");
		return file;
	}
}
