package org.vbazurtob.HRRecruitApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.vbazurtob.HRRecruitApp.conf.WebMvcConfig;
import org.vbazurtob.HRRecruitApp.model.Applicant;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;
import org.vbazurtob.HRRecruitApp.ui.pubweb.HomeController;

@SpringBootApplication
public class MainApp{

	
	public static void main(String[] args) throws Exception{
		SpringApplication.run(MainApp.class, args);
	}

	

}
