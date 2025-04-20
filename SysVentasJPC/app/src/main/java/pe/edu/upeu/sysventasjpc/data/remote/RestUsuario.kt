package pe.edu.upeu.sysventasjpc.data.remote

import pe.edu.upeu.sysventasjpc.modelo.UsuarioDto
import pe.edu.upeu.sysventasjpc.modelo.UsuarioRegisterDto
import pe.edu.upeu.sysventasjpc.modelo.UsuarioResp
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RestUsuario {
    @POST("/users/login")
    suspend fun login(@Body user: UsuarioDto): Response<UsuarioResp>

    @POST("/users/register")
    suspend fun register(@Body userRegister: UsuarioRegisterDto): Response<UsuarioResp>
}