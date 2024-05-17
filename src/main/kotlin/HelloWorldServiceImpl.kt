import Hello.HelloResponse
import HelloWorldServiceGrpc.HelloWorldServiceImplBase
import io.grpc.stub.StreamObserver


class HelloWorldServiceImpl : HelloWorldServiceImplBase() {
    override fun hello(
        request: Hello.HelloRequest,
        responseObserver: StreamObserver<HelloResponse>
    ) {
        println(
            "Handling hello endpoint: $request"
        )
        val text = request.text + " World"
        val response = HelloResponse.newBuilder()
            .setText(text).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}