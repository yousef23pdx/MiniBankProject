package com.example.Mini_Bank_Project

import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@CucumberContextConfiguration
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = ["src/test/resources/application-test.properties"]
)
@ActiveProfiles("test")
class MiniBankProjectApplicationTests {

	@Test
	fun contextLoads() {
	}

}
