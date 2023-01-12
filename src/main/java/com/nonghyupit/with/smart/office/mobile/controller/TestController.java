package com.nonghyupit.with.smart.office.mobile.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nonghyupit.rnd.support.Utils;
import com.nonghyupit.with.smart.office.mobile.service.TestGrpcService;
import com.nonghyupit.with.smart.office.mobile.service.TestRestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {
	@Autowired
	private TestRestService testRestService;
	@Autowired
	private TestGrpcService testGrpcService;

	@GetMapping("/rest/{start}/{end}")
	public ResponseEntity<?> restCall(HttpServletRequest request, @PathVariable Long start, @PathVariable Long end) {
		String result = "";
		String scheme, serverName;
		int serverPort;
		scheme = request.getScheme();
		serverName = request.getServerName();
		serverPort = request.getServerPort();

		log.debug("{} {}", Utils.millisecFormat.format(new Date()), request.getRequestURL());
		result = testRestService.call(scheme + "://" + serverName + ":" + serverPort, start, end);
		log.debug("{} {}", Utils.millisecFormat.format(new Date()), request.getRequestURL());
		return ResponseEntity.ok(result);
	}

	@GetMapping("/grpc/{start}/{end}")
	public ResponseEntity<?> grpcCall(HttpServletRequest request, @PathVariable Long start, @PathVariable Long end) {
		String result = "";
		String serverName;
		int serverPort;
		serverName = request.getServerName();
		serverPort = request.getServerPort();

		log.debug("{} {}", Utils.millisecFormat.format(new Date()), request.getRequestURL());
		result = testGrpcService.call("static://" + serverName + ":" + (serverPort + 1000), start, end);
		log.debug("{} {}", Utils.millisecFormat.format(new Date()), request.getRequestURL());
		return ResponseEntity.ok(result);
	}
}
