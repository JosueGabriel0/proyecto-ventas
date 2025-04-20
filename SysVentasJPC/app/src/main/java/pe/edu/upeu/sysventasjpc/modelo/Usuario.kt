package pe.edu.upeu.sysventasjpc.modelo

data class UsuarioDto(
    var user: String,
    var clave: String,
)
data class UsuarioResp(
    val idUsuario: Long,
    val user: String,
    val estado: String,
    val token: String,
)

data class UsuarioRegisterDto(
    var user: String,
    var clave: String,
    var rol: String,
    var estado: String
)