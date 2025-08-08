package com.nonghyupit.cloud.controller;

import com.nonghyupit.cloud.entity.Result;
import com.nonghyupit.cloud.service.ConverterGrpcService;
import com.nonghyupit.cloud.service.ConverterRestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class TestController {
    @Autowired
    private ConverterRestService converterRestService;
    @Autowired
    private ConverterGrpcService converterGrpcService;

    @GetMapping("/{type}/{count}")
    public ResponseEntity<?> call(HttpServletRequest request, @PathVariable String type, @PathVariable int count) {
        Result result = new Result();
        result.type = type;
        result.count = count;
        result.from = request.getServerName() + ":" + request.getServerPort();
        result.times.add(new Date()); // start time

        String res = null;
        if(type.equals("rest")) { // "rest" or "grpc"
            result.to = converterRestService.getServer();
            for(int i = 0; i < count; i++) {
                res = converterRestService.toUpper(type);
            }
        } else {
            result.to = converterGrpcService.getServer();
            for(int i = 0; i < count; i++) {
                res = converterGrpcService.toUpper(type);
            }
        }
        result.times.add(new Date()); // end time
        result.diff = result.times.get(1).getTime() - result.times.get(0).getTime();
        log.info("{} x {} = {}", res, count, result.diff);
        return ResponseEntity.ok(result);
    }
}
