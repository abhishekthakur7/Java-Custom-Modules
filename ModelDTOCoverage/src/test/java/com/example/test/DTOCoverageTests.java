package com.example.test;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class DTOCoverageTests {

	GetterSetterTester util = new GetterSetterTester();
	
	@Test
	public void testDTO() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		TestDTO testDTO = new TestDTO();
		testDTO= (TestDTO) util.getSetDefaultValues(testDTO);
		System.out.println(testDTO);
	}
	
	@Test
	public void secondTestDTO() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		SecondTestDTO secondTestDTO = new SecondTestDTO();
		secondTestDTO= (SecondTestDTO) util.getSetDefaultValues(secondTestDTO, "SetGet");
		System.out.println(secondTestDTO);
	}
}
