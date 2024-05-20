package helloworld.usingjavalib.client

import Hello.HelloRequest
import Hello.HelloResponse
import HelloWorldServiceGrpc
import HelloWorldServiceGrpc.HelloWorldServiceBlockingStub
import io.grpc.Channel
import io.grpc.Grpc
import io.grpc.InsecureChannelCredentials
import java.util.concurrent.TimeUnit


class HelloWorldClient(val channel: Channel) {
    // Creating a stub for the server
    var grpc: HelloWorldServiceBlockingStub = HelloWorldServiceGrpc.newBlockingStub(channel)
}

fun main() {
    val target = "localhost:50051"

    val channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build()
    try {
        val client = HelloWorldClient(channel)
        val request = HelloRequest.newBuilder().setText("Kaustuv").build()
        val response : HelloResponse = client.grpc.hello(request)
        println(response.text)
    } finally {
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS)
    }
}