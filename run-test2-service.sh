java -Dserver.port=8082 -Dgrpc.server.port=9082 -Dlogging.level.com.nonghyupit=info \
	-Drest.client.converterRest.address=http://192.168.100.24:8081 \
	-Dgrpc.client.converterGrpcImpl.address=static://192.168.100.24:9081 \
	-jar ./msa-interface-test-1.0.jar
