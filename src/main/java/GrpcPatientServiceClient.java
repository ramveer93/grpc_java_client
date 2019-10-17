

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import patientProto.PatientServiceGrpc;
import patientProto.PatientServiceGrpc.PatientServiceBlockingStub;
import patientProto.PatientServiceOuterClass.AddDeletePatientResponse;
import patientProto.PatientServiceOuterClass.PatientResponse;




public class GrpcPatientServiceClient {
	
	public static void main(String args[]) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9094).usePlaintext().build();
		PatientServiceBlockingStub blockingStub = PatientServiceGrpc.newBlockingStub(channel);
		PatientResponse allPatients = blockingStub.getAllPatients(null);
		
		
		AddDeletePatientResponse.Builder  response = AddDeletePatientResponse.newBuilder();
		response.setStatusCode(1);
		response.setSuccessMessage("Hello");
	    response.setErrorMessage("world");
	    int size = response.build().getSerializedSize();
	    System.out.println("---------------------size of the message--------------"+size);
	    System.out.println("==================response==============="+response);
	    
	}

}
