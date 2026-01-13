package com.springboot.testing;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

class SpringbootTestingApplicationTests {

	@BeforeAll
	public static void beforeAllTest(){
		System.out.println("Before all");
	}

	@AfterAll
	public static void afterAllTests(){
		System.out.println("After all");
	}

	@BeforeEach
	public void beforeEach(){
		System.out.println("Before Each");
	}

	@AfterEach
	public void afterEach(){
		System.out.println("After Each");
	}



	@Test
	public void justKidding(){
		System.out.println("hello world");
	}


	@Test
	public void addTwoNumbers(){
		Assertions.assertThat(add(3,5)).isEqualTo(8).isLessThan(10);
		System.out.println("success");
	}

	private int add(int a,int b){
		return a+b;
	}

}
