package org.einnfeigr.iot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.einnfeigr.iot.pojo.AccessToken;
import org.einnfeigr.iot.pojo.Gyro;
import org.einnfeigr.iot.pojo.Record;
import org.einnfeigr.iot.pojo.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class BikeStatApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	private static Session session;
	private static AccessToken token;
	
	private MvcResult result;
	
	private final static ObjectMapper mapper = new ObjectMapper();
	private final static Logger log = LoggerFactory.getLogger(BikeStatApplicationTests.class);
	
	private final static Integer hours = 1;
	private final static Float distance = 10F;
	private final static Random random = new Random();
	
	static {
		mapper.registerModule(new JavaTimeModule());
	}
	
	@BeforeAll
	static void init() {
		session = createSession(hours, distance);
	}
	
	@Order(0)
	@Test
	void sessionCreate() throws Exception {
		String content = mapper.writeValueAsString(session);
		String address = "/api/session/add";
		mvc.perform(post(address).with(user("user").roles("user")))
			.andExpect(status().isForbidden());
		result = mvc.perform(post(address)
				.content(content)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.with(user("admin").roles("admin")))
			.andExpect(status().isOk())
			.andReturn();
		session = mapper.readValue(result.getResponse().getContentAsString(), Session.class);
	}
	
	@Order(1)
	@Test
	void sessionRead() throws Exception {
		String address = "/api/session/"+session.getId();
		mvc.perform(get(address).with(user("user").roles("user")))
			.andExpect(status().isForbidden());
		result = mvc.perform(get(address).with(user("admin").roles("admin")))
			.andExpect(status().isOk())
			.andReturn(); 
		Session response = mapper.readValue(result.getResponse().getContentAsString(),
				Session.class);
		assertEquals(response, session);
	}
	
	@Order(2)
	@Test
	void sessionUpdate() throws Exception {
		session.setDistance(distance+10F);
		String content = mapper.writeValueAsString(session);
		String response = mvc.perform(post("/api/session/"+session.getId())
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
				.with(user("admin").roles("admin")))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		Session responseSession = mapper.readValue(response, Session.class);
		assertThat(!responseSession.equals(session));
	}
	
	@Order(3)
	@Test
	void sessionDelete() throws Exception {
		mvc.perform(delete("/api/session/"+session.getId()).with(user("admin").roles("admin")))
			.andExpect(status().isOk());
	}
	
	@Order(4)
	@Test
	void createToken() throws Exception {
		mvc.perform(post("/api/token/generate").with(user("user").roles("user")))
			.andExpect(status().isForbidden());
		result = mvc.perform(post("/api/token/generate").with(user("admin").roles("admin")))
			.andExpect(status().isOk())
			.andReturn();
		token = mapper.readValue(result.getResponse().getContentAsString(), AccessToken.class);
	}
	
	@Order(5)
	@Test
	void checkToken() throws Exception {
		String address = "/api/token/check"; 
		mvc.perform(get(address).param("token", "12345abc"))
			.andExpect(status().isForbidden());
		mvc.perform(get(address).with(user("user").roles("user")).param("token", "12345abc"))
			.andExpect(status().isNotFound());
		mvc.perform(get(address).with(user("user").roles("user")).param("token", token.getToken()))
			.andExpect(status().isFound());
	}
	
	@Order(6)
	void uploadData() throws Exception {
		Map<String, Object> data = generateData();
	}
	
	@Order(7)
	@Test
	void deleteToken() throws Exception {
		String address = "/api/token/"+token.getId();
		mvc.perform(delete(address).with(user("user").roles("user")))
			.andExpect(status().isForbidden());
		mvc.perform(delete(address).with(user("admin").roles("admin")))
			.andExpect(status().isOk());
	}
	
	static Map<String, Object> generateData() {
		Map<String, Object> data = new HashMap<>();
		data.put("start_date", LocalDateTime.now().minusHours(5).toString());
		data.put("end_date", LocalDateTime.now().toString());
		return data;
	}
	
	static Session createSession(int hours, float distance) {
		Session session = new Session();
		session.setDistance(distance);
		LocalDateTime start = LocalDateTime.now().minusHours(hours);
		LocalDateTime end = LocalDateTime.now();
		session.setStart(start);
		session.setEnd(end);
		session.setId(0);
		for(int x = 0; x < hours*60*60; x++) {
			Record record = new Record();
			record.setImpulsesRear((byte)random.nextInt(5));
			record.setImpulsesFront((byte)random.nextInt(5));
			record.addGyro(generateGyro(record));
			record.addGyro(generateGyro(record));
			record.setSession(session);
			session.addRecord(record);
		}
		return session;
	}
	
	private static Gyro generateGyro(Record record) {
		Gyro gyro = new Gyro();
		gyro.setAccX(random.nextFloat());
		gyro.setAccY(random.nextFloat());
		gyro.setAccZ(random.nextFloat());
		gyro.setPosX(random.nextFloat());
		gyro.setPosY(random.nextFloat());
		gyro.setPosZ(random.nextFloat());
		gyro.setRecord(record);
		return gyro;
	}

}
