package com.nonghyupit.cloud.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class ConverterRestService {
	@Value("${rest.client.converterRest.address}")
	private String address;
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		restTemplate = new RestTemplateBuilder().build();
		return restTemplate;
	}

	public String toUpper(String input) {
		UriComponentsBuilder builder;
		builder = UriComponentsBuilder.fromUriString(address + "/rest/toUpper/" + input);
		UriComponents uri = builder.build();
		ResponseEntity<String> response = restTemplate.exchange(uri.toUri(), HttpMethod.GET, null, String.class);
//		return s.getBody();
        String output = response.getBody();
        log.debug("{}", output);
        return output;
	}

	public String getServer() {
		return address;
	}
}
