//package com.controller;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse.BodyHandlers;
//
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.bean.ResponseBean;
//import com.bean.StaffBean;
//
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//public class StaffControllerTest {
//
//	@LocalServerPort
//	int randomPort;
//
//	@Test
//	public void abc() {
//		// 2+2 = 4
//		HttpClient client = HttpClient.newBuilder().build();
//		HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:" + randomPort + "/staff")).build();
//
//		try {
//			client.send(request, BodyHandlers.ofString());
//			System.out.println("done");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	@org.junit.jupiter.api.Test
//	public void addStaff() throws Exception {
//		RestTemplate rest = new RestTemplate();
//		String url = "http://localhost:9996/staff";
//		URI uri = new URI(url);
//		StaffBean staff = new StaffBean();
////		staff.setStaffFirstName("rock");
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("authToken", "345u398lkxdf9834igj8965");
//
//		HttpEntity<StaffBean> req = new HttpEntity<StaffBean>(staff, headers);
//
//		ResponseEntity<ResponseBean> res = rest.postForEntity(uri, req, ResponseBean.class);
//		Assert.assertEquals(200, res.getStatusCodeValue());
//
//		StaffBean dbStaffBean = (StaffBean) res.getBody().getData();
//		Assert.assertEquals(1, res.getBody().getData());
////		Assert.assertEquals("rock", dbStaffBean.getStaffFirstName());
//		Assert.assertEquals("staff added...", res.getBody().getMsg());
//
//	}
//
//	@Test
//	public void addStaffBadRequestTest() {
//		StaffController sc = new StaffController();
//		sc.addStaff(null, null);
//
//	}
//
//	@Test
//	public void addStaffValidTest() {
//		StaffController sc = new StaffController();
//		sc.addStaff(null, null);
//	}
//}
