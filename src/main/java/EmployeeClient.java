

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.employee.proto.EmployeeGrpc;
import com.employee.proto.EmployeeGrpc.EmployeeBlockingStub;
import com.employee.proto.EmployeeGrpc.EmployeeFutureStub;
import com.employee.proto.EmployeeGrpc.EmployeeStub;
import com.employee.proto.EmployeeOuterClass.EmployeeDepartment;
import com.employee.proto.EmployeeOuterClass.EmployeeRequest;
import com.employee.proto.EmployeeOuterClass.EmployeeResponse;
import com.employee.proto.EmployeeOuterClass.EmployeesList;
import com.google.common.util.concurrent.ListenableFuture;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class EmployeeClient {
	
	public static void main1(String args[]) {
		
		
		ManagedChannel channel  =  ManagedChannelBuilder.forAddress("localhost", 9094).usePlaintext().build();
		
		EmployeeBlockingStub blockingStub = EmployeeGrpc.newBlockingStub(channel);
		
		List<String> languages = new ArrayList<>();
		languages.add("JAVA");
		languages.add("GO");
		languages.add("JS");
		
		EmployeeRequest request = EmployeeRequest.newBuilder()
				.setEmployeeId(1)
				.setEmployeeDepartment(EmployeeDepartment.ADMIN)
				.setEmployeeName("Ramveer")
				.addAllLanguages(languages)
				.setAddress("ttyyhbbhjhjh")
				.build();
		
		
		
		
		EmployeeFutureStub futureStub = EmployeeGrpc.newFutureStub(channel);
		ListenableFuture<EmployeeResponse> futureResponse = futureStub.addEmployee(request);
		try {
			EmployeeResponse resp = futureResponse.get();
			System.out.println("response in case of future............"+resp);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("before async");
		
		EmployeeStub asynStub = EmployeeGrpc.newStub(channel);
		asynStub.addEmployee(request, new StreamObserver<EmployeeResponse>() {

			@Override
			public void onNext(EmployeeResponse value) {
				System.out.println("OnNext:------------------"+value);
				
			}

			@Override
			public void onError(Throwable t) {
				Status status = Status.fromThrowable(t);
				System.out.println("OnError......."+status);
				
			}

			@Override
			public void onCompleted() {
				System.out.println("OnComplete...........................");
				
			}
		});
		
		System.out.println("before the blocking after the asyn");
		
		
		EmployeeResponse response = blockingStub.addEmployee(request);
		System.out.println("response from employeeService........"+response);
		EmployeeBlockingStub getAllEmployees = EmployeeGrpc.newBlockingStub(channel);
		EmployeesList list = getAllEmployees.getEmployees(request);
		System.out.println("list.................."+list);
	}

	
	public static void main(String args[]) {
		ManagedChannel channel  =  ManagedChannelBuilder.forAddress("localhost", 9094).usePlaintext().build();
		EmployeeBlockingStub blockingStub = EmployeeGrpc.newBlockingStub(channel);
		List<String> languages = new ArrayList<>();
		languages.add("JAVA");
		languages.add("GO");
		languages.add("JS");
		EmployeeRequest request = EmployeeRequest.newBuilder()
				.setEmployeeId(1)
				.setEmployeeDepartment(EmployeeDepartment.ADMIN)
				.setEmployeeName("Ramveer")
				.addAllLanguages(languages)
				.setAddress("Test Address123")
				.build();
		EmployeeResponse response = blockingStub.addEmployee(request);
		System.out.println("Response...."+response);
	}
}
