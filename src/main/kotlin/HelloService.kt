import hello.Hello
import hello.HelloServiceGrpcKt
import kotlinx.coroutines.flow.flow

class HelloService : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {
    override fun sayHello(request: Hello.HelloRequest) = flow {
        emit(Hello.HelloReply.newBuilder().setMessage("Hello, ${request.name}").build())
    }
}