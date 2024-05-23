import io.grpc.ServerBuilder
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.runBlocking

class GrpcServer {
    companion object {

        @JvmStatic
        fun main() {
            Vertx.vertx()
            runBlocking(Vertx.vertx().dispatcher()) {
                val server = ServerBuilder
                    .forPort(8080)
                    .addService(HelloService())
                    .build()
                    .start()

                println("Server started")
                Runtime.getRuntime().addShutdownHook(Thread {
                    server.shutdown()
                })
            }
        }
    }
}

fun main() {
    GrpcServer.main()
}