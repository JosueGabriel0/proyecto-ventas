package pe.edu.upeu.sysventasjpc.ui.presentation.screens.login

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.github.k0shk0sh.compose.easyforms.BuildEasyForms
import com.github.k0shk0sh.compose.easyforms.EasyFormsResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import pe.edu.upeu.sysventasjpc.ui.presentation.components.ErrorImageAuth
import pe.edu.upeu.sysventasjpc.ui.presentation.components.ImageLogin
import pe.edu.upeu.sysventasjpc.ui.presentation.components.ProgressBarLoading

import pe.edu.upeu.sysventasjpc.utils.ComposeReal
import pe.edu.upeu.sysventasjpc.utils.TokenUtils

import pe.edu.upeu.sysventasjpc.modelo.UsuarioDto
import pe.edu.upeu.sysventasjpc.modelo.UsuarioRegisterDto
import pe.edu.upeu.sysventasjpc.ui.navigation.Destinations
import pe.edu.upeu.sysventasjpc.ui.presentation.components.Spacer
import pe.edu.upeu.sysventasjpc.ui.presentation.components.form.EmailTextField
import pe.edu.upeu.sysventasjpc.ui.presentation.components.form.LoginButton
import pe.edu.upeu.sysventasjpc.ui.presentation.components.form.PasswordTextField

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    viewModelRegister: RegisterViewModel = hiltViewModel()
) {
    val showRegisterDialog = remember { mutableStateOf(false) }
    val isErrorRegister by viewModelRegister.isError.observeAsState(false)
    val isSuccess by viewModelRegister.isSuccess.observeAsState(false)

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }


    val isLoading by viewModel.isLoading.observeAsState(false)
    val isLogin by viewModel.islogin.observeAsState(false)
    val isError by viewModel.isError.observeAsState(false)
    val loginResul by viewModel.listUser.observeAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        ImageLogin()
        Text("Login Screen", fontSize = 40.sp)
        BuildEasyForms { easyForm ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                EmailTextField(easyForms = easyForm, text ="","E-Mail:", "U")
                PasswordTextField(easyForms = easyForm, text ="", label ="Password:" )
                LoginButton(easyForms=easyForm, onClick = {
                    val dataForm=easyForm.formData()
                    val user=UsuarioDto(
                        (dataForm.get(0) as EasyFormsResult.StringResult).value,
                        (dataForm.get(1) as EasyFormsResult.StringResult).value)

                    Log.i("LOGIN_DEBUG", "Usuario enviado: ${user.user}, password: ${user.clave}")

                    viewModel.loginSys(user)
                    scope.launch {
                        delay(3600)
                        if(isLogin && loginResul!=null){
                            navigateToHome()
                            Log.i("TOKENV", TokenUtils.TOKEN_CONTENT)
                            Log.i("DATA", loginResul!!.user)
                            navigateToHome.invoke()
                        }else if(isError){
                            Log.v("ERRORX", "Error logeo")
                            Toast.makeText(context,"Error al conectar",Toast.LENGTH_LONG).show()
                        }
                    }
                },

                label = "Log In"
                )
                Spacer(size = 90)
                Button(onClick = { showRegisterDialog.value = true }) {
                    Text("Registrar cuenta", fontSize = 24.sp)
                }

                if (showRegisterDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showRegisterDialog.value = false },
                        title = { Text("Registro de Usuario") },
                        text = {
                            Column {
                                TextField(
                                    value = email.value,
                                    onValueChange = { email.value = it },
                                    label = { Text("Correo electrónico") }
                                )
                                TextField(
                                    value = password.value,
                                    onValueChange = { password.value = it },
                                    label = { Text("Contraseña") },
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                TextField(
                                    value = confirmPassword.value,
                                    onValueChange = { confirmPassword.value = it },
                                    label = { Text("Confirmar Contraseña") },
                                    visualTransformation = PasswordVisualTransformation()
                                )
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    if (password.value == confirmPassword.value) {
                                        val userRegister = UsuarioRegisterDto(email.value, password.value, "ADMIN", "Activo")
                                        viewModelRegister.registerSys(userRegister)

                                        scope.launch {
                                            delay(3500)
                                            if(isSuccess){
                                                navController.navigate(Destinations.Login.route)
                                            } else if(isErrorRegister){
                                                Toast.makeText(context,"Error al conectar",Toast.LENGTH_LONG).show()
                                            }
                                        }

                                        showRegisterDialog.value = false
                                        Toast.makeText(context, "Cuenta registrada", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                                    }

                                }
                            ) {
                                Text("Registrar")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { showRegisterDialog.value = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                /*Button(onClick = {
                    navigateToHome.invoke()
                }) {
                    Text("Ir a Detalle")
                }*/
                ComposeReal.COMPOSE_TOP.invoke()
            }
        }
    }
    ErrorImageAuth(isImageValidate = isError)
    ProgressBarLoading(isLoading = isLoading)
}

/*
@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val colors = LightRedColors
    val darkTheme = isSystemInDarkTheme()
    SysVentasJPCTheme(colorScheme = colors) {
        LoginScreen(
            navigateToHome = {}
        )
    }
}*/