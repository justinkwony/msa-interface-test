package com.nonghyupit.cloud.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ConverterRestController {
    @GetMapping("/rest/toUpper/{input}")
    public ResponseEntity<?> toUpper(HttpServletRequest request, @PathVariable String input) {
        String output = input.toUpperCase();
        log.debug("{}", output);
        return ResponseEntity.ok(output);
    }
}
