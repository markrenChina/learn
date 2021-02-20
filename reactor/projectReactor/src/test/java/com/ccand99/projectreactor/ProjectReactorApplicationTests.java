package com.ccand99.projectreactor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;

@SpringBootTest
class ProjectReactorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test() throws IOException {
		var a = new DefaultResourceLoader().getResource(".");
		System.out.println(a.getURI());
	}
}
