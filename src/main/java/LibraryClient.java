import java.util.concurrent.ExecutionException;

import com.google.common.util.concurrent.ListenableFuture;
import com.library.proto.LibraryGrpc;
import com.library.proto.LibraryGrpc.LibraryBlockingStub;
import com.library.proto.LibraryGrpc.LibraryFutureStub;
import com.library.proto.LibraryGrpc.LibraryStub;
import com.library.proto.LibraryOuterClass.BookRequest;
import com.library.proto.LibraryOuterClass.BookResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class LibraryClient {

	public static void main(String args[]) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9094).usePlaintext().build();
		LibraryBlockingStub blocking = LibraryGrpc.newBlockingStub(channel);
		BookRequest request = BookRequest.newBuilder().setId(12).setName("Book1").build();
		
		
		BookResponse response = blocking.addBook(request);
		System.out.println("response==============="+response);
		
		
		LibraryFutureStub future = LibraryGrpc.newFutureStub(channel);
		
		ListenableFuture<BookResponse> futureRes =   future.addBook(request);
		
		try {
			BookResponse responseFuture = futureRes.get();
			System.out.println("response fropm future.========"+responseFuture);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		LibraryStub asyn = LibraryGrpc.newStub(channel);
		
		asyn.addBook(request, new StreamObserver<BookResponse>() {
			
			@Override
			public void onNext(BookResponse value) {
				System.out.println("============))))))))))))_____________OnNext====value=========="+value);
			}
			
			@Override
			public void onError(Throwable t) {
				System.out.println("============Throwable====value==========");
			}
			
			@Override
			public void onCompleted() {
				System.out.println("============completed====================");
			}
		});
		
		System.out.println("==========================async===============================");
	}
}
