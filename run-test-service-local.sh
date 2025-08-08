target_ip=`hostname -I | cut -d' ' -f1`
java -Dserver.port=8082 -Dgrpc.server.port=9082 -Dlogging.level.com.nonghyupit=info \
	-Drest.client.converterRest.address=http://$target_ip:8081 \
	-Dgrpc.client.converterGrpcImpl.address=static://$target_ip:9081 \
	-jar ./msa-interface-test-1.0.jar
