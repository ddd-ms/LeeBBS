package com.NJU.SWI.LeeBBS;

import com.NJU.SWI.LeeBBS.dao.AlphaDao;
import com.NJU.SWI.LeeBBS.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = LeeBbsApplication.class)
class LeeBbsApplicationTests implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void test1(){
		System.out.println(this.applicationContext);
		AlphaDao ad = applicationContext.getBean(AlphaDao.class);
		System.out.println(ad.select());
		AlphaDao ad2 = (AlphaDao) applicationContext.getBean("adh1p");
		System.out.println(ad2.select());
	}
	@Test
	public void test2(){
		AlphaService ctx = applicationContext.getBean(AlphaService.class);
		System.out.println(ctx);
	}
	@Test
	public void test3(){
		// test bean config
		SimpleDateFormat bean = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(bean);
		System.out.println(bean.format(new Date()));
	}
	@Autowired
	@Qualifier("adh1p")
	private AlphaDao ad22;
	@Test
	public void test4(){
		System.out.println(ad22);
	}
}
