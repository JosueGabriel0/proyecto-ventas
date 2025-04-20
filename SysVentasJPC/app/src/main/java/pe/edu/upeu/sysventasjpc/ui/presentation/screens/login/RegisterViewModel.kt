package pe.edu.upeu.sysventasjpc.ui.presentation.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.edu.upeu.sysventasjpc.modelo.UsuarioRegisterDto
import pe.edu.upeu.sysventasjpc.modelo.UsuarioResp
import pe.edu.upeu.sysventasjpc.repository.UsuarioRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepo: UsuarioRepository): ViewModel(){
    val isError = MutableLiveData<Boolean>(false)
    val isSuccess = MutableLiveData(false)
    val registerResponse = MutableLiveData<UsuarioResp?>()

    fun registerSys(toData: UsuarioRegisterDto){
        viewModelScope.launch {
            try {
                val response = userRepo.registerUsuario(toData)
                if(response.isSuccessful){
                    registerResponse.value = response.body()
                    isSuccess.value = true
                }else {
                    isError.value = true
                }
            } catch (e: Exception){
                isError.value = true
            }
        }
    }
}