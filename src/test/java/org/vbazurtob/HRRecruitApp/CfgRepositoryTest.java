package org.vbazurtob.HRRecruitApp;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.vbazurtob.HRRecruitApp.model.JobType;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;

@Configuration
@Profile("test")
public class CfgRepositoryTest {


	@Bean
	public JobTypeRepository getJobTypeRepository() {
		return Mockito.mock(JobTypeRepository.class);
	}
	
}
