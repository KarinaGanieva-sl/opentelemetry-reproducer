import hello.Hello.HelloRequest
import hello.HelloServiceGrpcKt
import io.grpc.Context
import io.grpc.ManagedChannelBuilder
import io.vertx.core.Vertx
import io.vertx.kotlin.coroutines.dispatcher
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.*

class GrpcClient {

    companion object {
        val vertx = Vertx.vertx()

        @JvmStatic
        fun main(): Unit = runBlocking(vertx.dispatcher()) {
            val channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build()
            val count = AtomicInteger()

            (1..600).map { n ->
                val stub = HelloServiceGrpcKt.HelloServiceCoroutineStub(channel)
                println(n)
                stub.sayHello(HelloRequest.getDefaultInstance())
                    .collect {
                        count.incrementAndGet()
                    }
                val context = Context.current()
                val generationField = Context::class.java.getDeclaredField("generation")
                generationField.isAccessible = true
                println("generation after: ${generationField.get(context) as Int}")
            }
            println("after request, got ${count.get()} items")
            channel.shutdown()
        }
    }
}

fun main() {
    GrpcClient.main()
}