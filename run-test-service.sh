target_ip="192.168.100.24"
java -Dserver.port=8081 -Dgrpc.server.port=9081 -Dlogging.level.com.nonghyupit=info \
	-Drest.client.converterRest.address=http://$target_ip:8082 \
	-Dgrpc.client.converterGrpcImpl.address=static://$target_ip:9082 \
	-jar ./msa-interface-test-1.0.jar
