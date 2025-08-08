package com.nonghyupit.cloud.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class ConverterGrpcImpl extends ConverterGrpc.ConverterImplBase {
    @Override
    public void toUpper(GrpcRequest request, StreamObserver<GrpcResponse> responseObserver) {
        String output = request.getInput().toUpperCase();
        log.debug("{}", output);
        GrpcResponse.Builder responseBuilder = GrpcResponse.newBuilder();
        responseBuilder.setOutput(output);
        GrpcResponse response = responseBuilder.build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
