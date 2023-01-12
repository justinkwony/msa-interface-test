package com.nonghyupit.with.smart.office.mobile.grpc;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import com.nonghyupit.rnd.support.Utils;
import com.nonghyupit.with.smart.office.mobile.service.TestGrpcService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class TestServiceGrpcImpl extends TestServiceGrpc.TestServiceImplBase {
	@Autowired
	private TestGrpcService testGrpcService;

	@Override
	public void call(TestRequest request, StreamObserver<TestResponse> responseObserver) {
		String result = "";

		log.debug("{} {}", Utils.millisecFormat.format(new Date()), request.getUrl() + "/" + request.getStart() + "/" + request.getEnd());
		result = testGrpcService.call(request.getUrl(), request.getStart(), request.getEnd());

		TestResponse.Builder responseBuilder = TestResponse.newBuilder();
		responseBuilder.setResult("(" + request.getUrl() + "/" + request.getStart() + "/" + request.getEnd() + ")" + result);

		TestResponse response = responseBuilder.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
		log.debug("{} {}", Utils.millisecFormat.format(new Date()), request.getUrl() + "/" + request.getStart() + "/" + request.getEnd());
	}
}
