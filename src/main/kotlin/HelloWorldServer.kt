import io.grpc.Server
import io.grpc.ServerBuilder

class HelloWorldServer {
    val PORT = 50051
    lateinit var server: Server

    fun start()  {
        server = ServerBuilder.forPort(PORT)
            .addService(HelloWorldServiceImpl())
            .build()
            .start();
    }

    fun blockUntilShutdown() {
        server.awaitTermination();
    }
}

fun main() {
    val server = HelloWorldServer()
    server.start()
    server.blockUntilShutdown()
}