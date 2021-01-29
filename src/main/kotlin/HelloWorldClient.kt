import org.zeromq.SocketType
import org.zeromq.ZContext
import org.zeromq.ZMQ

fun main(args: Array<String>) {
    ZContext().use { context ->
        //  Socket to talk to server
        println("Connecting to hello world server")
        val socket: ZMQ.Socket = context.createSocket(SocketType.REQ)
        val serverHost = System.getenv().getOrDefault("ZMQ_SERVER_HOST", "localhost")
        socket.connect("tcp://${serverHost}:5555")
        for (requestNbr in 0..9) {
            val request = "Hello message with nr: $requestNbr"
            println("Sending Hello $requestNbr")
            socket.send(request.toByteArray(ZMQ.CHARSET), 0)
            val reply: ByteArray = socket.recv(0)
            println(
                "Received message: " + String(reply, ZMQ.CHARSET) + " " +
                        requestNbr
            )
        }
    }
}
