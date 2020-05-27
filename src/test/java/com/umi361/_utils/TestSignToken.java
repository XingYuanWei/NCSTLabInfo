package com.umi361._utils;


import org.testng.annotations.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import java.util.Arrays;
import static org.testng.Assert.assertTrue;

@ContextConfiguration("classpath:spring.xml")
public class TestSignToken extends AbstractTestNGSpringContextTests {
	static final String digested = "bb2b1b118023158a82396a3dd7e9a806f16db012";
	@Test
	public void testCheckSignature()
	{
		//raw为实际接受加密的字符串
		final String raw = "123abcgoddrinksjava";
		assertTrue(SignToken.checkSignature(digested, "123", "abc"));
	}

	@Test
	public void testSignToBytes() {
		System.out.println(Arrays.toString(SignToken.signToBytes(digested)));
	}
}
