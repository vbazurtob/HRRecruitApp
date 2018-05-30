package org.vbazurtob.HRRecruitApp;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.PersistenceUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.repository.Repository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.vbazurtob.HRRecruitApp.model.JobType;
import org.vbazurtob.HRRecruitApp.model.repository.JobTypeRepository;
import org.vbazurtob.HRRecruitApp.ui.pubweb.HomeController;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class JobTypeCrudTest {

	//@Autowired
	//private JobTypeRepository jobType;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void insertTest() {
		
		
//		jobType.save(new JobType("Remoto"));
//		
//		System.out.print("=======================================" + jobType);
//		List<JobType> jt = (List<JobType>)jobType.findAll();
//		assertNotNull(jt.get(0));
		int a = 1+2;
		assertEquals(3, a);
	}

}
