# grpc_java_client

# About the project
This is java implementation of the grpc client of a specific proto files

## Steps to build the project 

 - clone the project and import as maven project in eclipse or intelij
 ```mvn clean install```
 - You have to add the proto file inside ```src/main/resources/``` which should be same as in your server
 -  Now you have to generate the stubds, Right click on ```pom.xml``` and then run the default profile, that will generate the stubs if not already generated 
 - In next section we will seee how to write a client using these stubs in Java

## Implementing a grpc client in JAVA
``` JAVA

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
  
```
## Defining a proto file
``` proto

syntax = "proto3";

option java_package = "patientProto";

service PatientService{
	rpc getPatient(PatientRequest) returns (PatientResponse);
	rpc addPatient(Patient) returns (AddDeletePatientResponse);
	rpc deletePatient(Patient) returns (AddDeletePatientResponse);
	rpc getAllPatients(EmptyResponse) returns (PatientResponse);
}

message PatientRequest{
	int32 identifier = 1;
	string name = 2;
}


message PatientResponse{
 repeated Patient patient = 1;

}
enum Gender{
	MALE = 0;
	FEMALE = 1;
}
enum MaritalStatus{
	MARRIED = 0;
	UNMERRIED = 1;
}

message Patient{
	int32 identifier = 1;
	string name = 2;
	Gender gender = 3;
	bool active = 4;
	string address = 5;
	MaritalStatus maritalStatus = 6;
    string link = 7;
    repeated string language = 8;
    repeated Provider careProvider = 9;
    string managingOrganization = 10;
    repeated Contact guardian = 11;
}

message Provider{
	string name = 1;
	int32 identifier = 2;
	int32 tin = 3;

}

message Contact{
	string name = 1;
	string relationWithPatient = 2;
	string cotactDetails = 3;
}

message AddDeletePatientResponse{
	string successMessage = 1;
	string errorMessage = 2;
	int32 statusCode = 3;

}

message EmptyResponse{

}


```



Now you can run your client to request the server.

## Contributing
Please raise an issue if you find anything which is not working or want unimplemented functionality.

## License
[MIT](https://choosealicense.com/licenses/mit/)
