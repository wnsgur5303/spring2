package kr.or.ddit.batch.ranger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class RangerScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(RangerScheduler.class);
	
	//@Resource(name="jobLauncher")
	private JobLauncher jobLauncher;
	
	//DI
	//@Resource(name="rangersJob")
	private Job job;
	
	public void rangerTacsk() {
		try {
			
			
			jobLauncher.run(job, new JobParameters());
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext context = 
				new ClassPathXmlApplicationContext(
						new String[]{"classpath:/kr/or/ddit/config/spring/scheduler-context.xml",
							"classpath:/kr/or/ddit/config/spring/batch-context.xml"});
		
		/*
		 * RangerScheduler rangerScheduler = new RangerScheduler();
		 * 
		 * rangerScheduler.jobLauncher =
		 * context.getBean("jabLauncher",JobLauncher.class); rangerScheduler.job =
		 * context.getBean("rangersJob",Job.class);
		 * 
		 * 어노테이션 기반이거나 세터나 생성자
		 */
	}

}
