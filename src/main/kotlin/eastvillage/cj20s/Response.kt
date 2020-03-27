package eastvillage.cj20s

sealed class Response(val msg: String)

object NoResponse : Response("")

class ErrorResponse(msg: String) : Response(msg)

class TextResponse(msg: String) : Response(msg)
