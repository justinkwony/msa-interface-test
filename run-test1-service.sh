java -Dserver.port=8081 -Dgrpc.server.port=9081 -Dlogging.level.com.nonghyupit=info \
	-Drest.client.converterRest.address=http://192.168.100.25:8082 \
	-Dgrpc.client.converterGrpcImpl.address=static://192.168.100.25:9082 \
	-jar ./msa-interface-test-1.0.jar
