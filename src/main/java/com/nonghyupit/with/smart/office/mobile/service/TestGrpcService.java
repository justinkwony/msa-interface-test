package com.nonghyupit.with.smart.office.mobile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.nonghyupit.with.smart.office.mobile.grpc.TestRequest;
import com.nonghyupit.with.smart.office.mobile.grpc.TestResponse;
import com.nonghyupit.with.smart.office.mobile.grpc.TestServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class TestGrpcService {
	@Value("${grpc.client.test1ServiceGrpcImpl.address}")
	private String test1Service;
	@Value("${grpc.client.test2ServiceGrpcImpl.address}")
	private String test2Service;
	@GrpcClient("test1ServiceGrpcImpl")
	private TestServiceGrpc.TestServiceBlockingStub test1ServiceGrpc;
	@GrpcClient("test2ServiceGrpcImpl")
	private TestServiceGrpc.TestServiceBlockingStub test2ServiceGrpc;

	public String call(String url, Long start, Long end) {
		String result = "";
		if(start < end) {
			result = _call(url, start + 1, end);
		}
		return result;
	}

	private String _call(String url, Long start, Long end) {
		TestResponse response;
		if(url.contains("test2-service")) {
			response = test1ServiceGrpc.call(TestRequest.newBuilder().setUrl(test1Service).setStart(start).setEnd(end).build());
		} else {
			response = test2ServiceGrpc.call(TestRequest.newBuilder().setUrl(test2Service).setStart(start).setEnd(end).build());
		}
		FieldDescriptor fieldDescriptor = response.getDescriptorForType().findFieldByName("result");
		String result = (String) response.getField(fieldDescriptor);
		return result;
	}
}
