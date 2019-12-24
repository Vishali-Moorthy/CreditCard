package com.bank.credit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class MegaCreditCardApplicationTests {

	@Test
	public void applicationTest() {
		MegaCreditCardApplication.main(new String[] {});
		assertTrue(true);
	}

}
