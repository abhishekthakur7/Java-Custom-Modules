package com.example.test;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class CoverDTO {

	GetterSetterTester util = new GetterSetterTester();
	
	@Test
	public void testDTO() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		TestDTO testDTO = new TestDTO();
		testDTO= (TestDTO) util.getSetDefaultValues(testDTO, "DTO");
		System.out.println(testDTO);
	}
}
