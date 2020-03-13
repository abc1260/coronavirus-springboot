package com.aikiinc.coronavirus;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class CornaVirusControllerTest {
	private static Logger LOG = LoggerFactory.getLogger(CornaVirusControllerTest.class);
	@Autowired
	private MockMvc mvc;

	@Test
	public void getHelloMessage() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		LOG.info(result.getResponse().getContentAsString());
		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		Assert.assertEquals("{\"message\":\"Greetings from Spring Boot!\"}", result.getResponse().getContentAsString());
	}

	@Test
	public void getDateLoaded() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/dateLoaded").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		LOG.info(result.getResponse().getContentAsString());
		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

//		/Assert.assertEquals("{\"message\":\"Greetings from Spring Boot!\"}", result.getResponse().getContentAsString());
	}

	@Test
	public void getCoronaVirusList() throws Exception {
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/coronaVirusList").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		LOG.info(result.getResponse().getContentAsString());
		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

//		/Assert.assertEquals("{\"message\":\"Greetings from Spring Boot!\"}", result.getResponse().getContentAsString());
	}

	@Test
	public void getRegionKeys() throws Exception {
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/regionKeys").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		LOG.info(result.getResponse().getContentAsString());
		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

//		/Assert.assertEquals("{\"message\":\"Greetings from Spring Boot!\"}", result.getResponse().getContentAsString());
	}

	@Test
	public void getCoronaVirusByRegion() throws Exception {
		MvcResult result = mvc
				.perform(
						MockMvcRequestBuilders.get("/coronaVirusByRegion?region=US").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		LOG.info(result.getResponse().getContentAsString());
		LOG.info("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

//		/Assert.assertEquals("{\"message\":\"Greetings from Spring Boot!\"}", result.getResponse().getContentAsString());
	}

}
