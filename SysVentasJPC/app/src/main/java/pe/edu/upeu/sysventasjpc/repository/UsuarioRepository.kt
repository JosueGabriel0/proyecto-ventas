package pe.edu.upeu.sysventasjpc.repository

import pe.edu.upeu.sysventasjpc.modelo.UsuarioDto
import pe.edu.upeu.sysventasjpc.modelo.UsuarioResp
import pe.edu.upeu.sysventasjpc.data.remote.RestUsuario
import pe.edu.upeu.sysventasjpc.modelo.UsuarioRegisterDto
import retrofit2.Response
import javax.inject.Inject

interface UsuarioRepository {
    suspend fun loginUsuario(user: UsuarioDto): Response<UsuarioResp>
    suspend fun registerUsuario(userRegister: UsuarioRegisterDto): Response<UsuarioResp>
}
class UsuarioRepositoryImp @Inject constructor(private val restUsuario: RestUsuario):UsuarioRepository{
    override suspend fun loginUsuario(user:UsuarioDto): Response<UsuarioResp>{
        return restUsuario.login(user)
    }

    override suspend fun registerUsuario(userRegister: UsuarioRegisterDto): Response<UsuarioResp> {
        return restUsuario.register(userRegister)
    }
}