import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.ListenableFuture;
import com.grpc.User.APIResponse;
import com.grpc.User.LoginRequest;
import com.grpc.userGrpc;
import com.grpc.userGrpc.userBlockingStub;
import com.grpc.userGrpc.userFutureStub;
import com.grpc.userGrpc.userStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class GRPCClient {

	public static void main(String[] args) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9094).usePlaintext().build();
		
		//stubs - generate from proto file 
		
        /**
         * blocking call 
         */
		userBlockingStub userStub = userGrpc.newBlockingStub(channel);
		
    	
		LoginRequest loginRequest = LoginRequest.newBuilder().setUsername("Ramveer").setPassword("Ramveer1").build();
		
		
		
		/**
		 * future call
		 */
		userFutureStub futureStub = userGrpc.newFutureStub(channel);
    	ListenableFuture<APIResponse> futureResponse = futureStub.login(loginRequest);
    	try {
			APIResponse futureApiResponse = futureResponse.get();
			System.out.println("futureApiResponse:----->"+futureApiResponse);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
    	System.out.println("should print after future -------");
    	
    	/**
    	 * async call 
    	 */
    	userStub asynUserStub = userGrpc.newStub(channel);
    	asynUserStub.login(loginRequest, new StreamObserver<APIResponse>() {
			
			@Override
			public void onNext(APIResponse value) {
			   System.out.println("This is when the server returns the value: "+value);
				
			}
			
			@Override
			public void onError(Throwable t) {
				Status status = Status.fromThrowable(t);
				System.out.println("On error status is  : "+ status);
			}
			
			@Override
			public void onCompleted() {
				System.out.println("when the call from server gets completed : ");
			}
		});
    	
    	System.out.println("should print before async also  -------");
		APIResponse response  =  userStub.login(loginRequest);
		System.out.println("client side response....."+response.getResponsemessage()+" code ...."+response.getResponseCode());
		
		
		

	}

}
