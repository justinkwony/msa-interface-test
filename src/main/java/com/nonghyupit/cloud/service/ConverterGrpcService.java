package com.nonghyupit.cloud.service;

import com.nonghyupit.cloud.grpc.GrpcRequest;
import com.nonghyupit.cloud.grpc.GrpcResponse;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConverterGrpcService {
    @Value("${grpc.client.converterGrpcImpl.address}")
    private String address;
    @GrpcClient("converterGrpcImpl")
    private com.nonghyupit.cloud.grpc.ConverterGrpc.ConverterBlockingStub converterGrpc;

    public String toUpper(String input) {
        GrpcResponse response = converterGrpc.toUpper(GrpcRequest.newBuilder().setInput(input).build());
//        String output = response.getOutput();
//        log.debug("{}", output);
//        return output;
        return response.getOutput();
    }

    public String getServer() {
        return address;
    }
}
