package eastvillage.cj20s

import net.dv8tion.jda.core.events.message.MessageReceivedEvent

sealed class Response(val msg: String)

object NoResponse : Response("")

class ErrorResponse(msg: String) : Response(msg)

class TextResponse(msg: String) : Response(msg)

class DungeonResponse(msg: String) : Response(msg)

class ChainedResponse(val response1: Response, val response2: Response) : Response("")

class ActionResponse(val action: (MessageReceivedEvent) -> Unit) : Response("")