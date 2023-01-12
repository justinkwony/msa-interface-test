package com.nonghyupit.with.smart.office.mobile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestRestService {
	@Value("${rest.client.test1ServiceRest.address}")
	private String test1Service;
	@Value("${rest.client.test2ServiceRest.address}")
	private String test2Service;
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		restTemplate = new RestTemplateBuilder().build();
		return restTemplate;
	}

	public String call(String url, Long start, Long end) {
		log.debug("{} {} {}", url, start, end);
		String result = "";
		if(start < end) {
			result = _call(url, start + 1, end);
		}
		log.debug("{}", result);
		return result;
	}

	private String _call(String url, Long start, Long end) {
		UriComponentsBuilder builder;
		if(url.contains("test2-service")) {
			log.debug("{} {} {} {}", url, start, end, test1Service);
			builder = UriComponentsBuilder.fromHttpUrl(test1Service + "/rest/" + start + "/" + end);
		} else {
			log.debug("{} {} {} {}", url, start, end, test2Service);
			builder = UriComponentsBuilder.fromHttpUrl(test2Service + "/rest/" + start + "/" + end);
		}
		log.debug("{} {} {} {}", url, start, end, builder.build().toUriString());
		UriComponents uri = builder.build();
		ResponseEntity<String> s = restTemplate.exchange(uri.toUri(), HttpMethod.GET, null, String.class);
		return "(" + url + "/" + start + "/" + end + ")" + s.getBody();
	}
}
