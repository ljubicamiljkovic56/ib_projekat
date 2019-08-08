package ib.project;

import java.io.File;
import java.util.ResourceBundle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

import ib.project.rest.DemoController;

@CrossOrigin
@SpringBootApplication
//@EnableJpaRepositories
@ComponentScan(basePackages = {"ib.project"})
public class DemoApplication {
	
	

	private static String DATA_DIR_PATH;
	
	static {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		DATA_DIR_PATH = rb.getString("dataDir");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		
		// create files folder in target/classes
		// avoid spaces in name of project, workspace etc.
		new File(DemoController.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator + DATA_DIR_PATH).mkdirs();
	}
}
 