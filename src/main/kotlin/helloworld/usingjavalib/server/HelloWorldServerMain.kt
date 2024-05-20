package helloworld.usingjavalib.server

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver

class HelloWorldServer {
    lateinit var server: Server

    fun start()  {
        server = ServerBuilder.forPort(50051)
            .addService(HelloWorldServiceImpl())
            .build()
            .start();
    }

    fun blockUntilShutdown() {
        server.awaitTermination();
    }
}

/**
 * The actual implementation of the service class
 */
class HelloWorldServiceImpl : HelloWorldServiceGrpc.HelloWorldServiceImplBase() {
    override fun hello(
        request: Hello.HelloRequest,
        responseObserver: StreamObserver<Hello.HelloResponse>
    ) {
        println(
            "Handling hello endpoint: $request"
        )
        val text = "Hello World!! ${request.text}"
        val response = Hello.HelloResponse.newBuilder()
            .setText(text).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}

fun main() {
    val server = HelloWorldServer()
    server.start()
    server.blockUntilShutdown()
}