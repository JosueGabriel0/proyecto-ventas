package pe.edu.upeu.sysventasjpc.modelo

data class Message(
    val statusCode: Long,
    val datetime: String,
    val message: String,
    val details: String
)