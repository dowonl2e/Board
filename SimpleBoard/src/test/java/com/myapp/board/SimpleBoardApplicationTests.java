package com.myapp.board;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SimpleBoardApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private SqlSessionFactory sessionFactory;

	@Test
	void contextLoads() {}

	@Test
	public void testByApplicationContextByBeanName() {
		try {
			System.out.println("==세션팩토리 체크 By Context BeanName==");
			// -> Bean에 name을 설정하면 메서드명으로 가져올 수 없다.
			// System.out.println(context.getBean("sqlSessionFactory"));
			System.out.println(context.getBean("factory"));
			System.out.println("=========================");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBySqlSessionFactory() {
		try {
			System.out.println("=======세션팩토리 체크=======");
			System.out.println(sessionFactory.toString());
			System.out.println("=========================");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
