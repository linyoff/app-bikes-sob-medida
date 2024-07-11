package com.aliny.prova02

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliny.prova02.model.DAOBicicleta
import com.aliny.prova02.model.DAOCliente
import com.aliny.prova02.ui.theme.Black2
import com.aliny.prova02.ui.theme.Orange
import com.aliny.prova02.ui.theme.Prova02Theme

class AdicionarBicicleta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prova02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelaAdicionarBicicleta()
                }
            }
        }
    }
}

@Composable
fun TelaAdicionarBicicleta() {

    var cpfClienteState by remember { mutableStateOf(TextFieldValue()) }
    var codigoState by remember { mutableStateOf(TextFieldValue()) }
    var modeloState by remember { mutableStateOf(TextFieldValue()) }
    var materialDoChassiState by remember { mutableStateOf("Material do chassi") }
    var aroState by remember { mutableStateOf("Aro") }
    var precoState by remember { mutableStateOf(TextFieldValue()) }
    var qtdDeMarchasState by remember { mutableStateOf("Quantidade de marchas") }

    val materiais = listOf("Aço", "Alumínio", "Fibra de Carbono", "Titânio")
    val aros = listOf("Aro 26", "Aro 27.5", "Aro 29", "Aro 700c", "Aro 20 (BMX)", "Aro 16 (infantil)")
    val marchas = listOf("1 marcha", "3 marchas", "7 marchas", "12 marchas", "18 marchas", "24 marchas", "27 marchas", "30 marchas")

    var estadoExpMat by remember { mutableStateOf(false) }
    var estadoExpAro by remember { mutableStateOf(false) }
    var estadoExpMarcha by remember { mutableStateOf(false) }

    var context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Button(
            onClick = {
                (context as? ComponentActivity)?.finish()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 30.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Voltar"
            )
        }
        Text(
            text = "Fazer pedido de Bicicleta",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 25.dp)
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = cpfClienteState,
            onValueChange = { cpfClienteState = it },
            label = { Text("CPF do Cliente") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "CPF Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = codigoState,
            onValueChange = { codigoState = it },
            label = { Text("Código") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Código Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = modeloState,
            onValueChange = { modeloState = it },
            label = { Text("Modelo") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Modelo Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 5.dp)
        )

        Column(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.78f)
                .padding(vertical = 3.dp)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Black2, shape = RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable { estadoExpMat = !estadoExpMat }
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { estadoExpMat = !estadoExpMat }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Build,
                    tint = Black2,
                    contentDescription = "Modelo Icon",
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = materialDoChassiState,
                    color = Black2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { estadoExpMat = !estadoExpMat }
                        .padding(start = 5.dp, top = 5.dp)
                )
            }

            DropdownMenu(
                expanded = estadoExpMat,
                onDismissRequest = { estadoExpMat = false }
            ) {
                materiais.forEach{material ->
                    DropdownMenuItem(
                        onClick = {
                            materialDoChassiState = material
                            estadoExpMat = false
                            Log.i("Teste", "Clicado: $materialDoChassiState")
                            Toast.makeText(context, "Clicado: $materialDoChassiState", Toast.LENGTH_SHORT).show()
                        },
                        text = { Text(text = material) }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.78f)
                .padding(vertical = 3.dp)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Black2, shape = RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable { estadoExpAro = !estadoExpAro }
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { estadoExpAro = !estadoExpAro }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    tint = Black2,
                    contentDescription = "Modelo Icon",
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = aroState,
                    color = Black2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { estadoExpAro = !estadoExpAro }
                        .padding(start = 5.dp, top = 5.dp)
                )
            }

            DropdownMenu(
                expanded = estadoExpAro,
                onDismissRequest = { estadoExpAro = false }
            ) {
                aros.forEach{aro ->
                    DropdownMenuItem(
                        onClick = {
                            aroState = aro
                            estadoExpAro = false
                            Log.i("Teste", "Clicado: $aroState")
                            Toast.makeText(context, "Clicado: $aroState", Toast.LENGTH_SHORT).show()
                        },
                        text = { Text(text = aro) }
                    )
                }
            }
        }

        OutlinedTextField(
            value = precoState,
            onValueChange = { precoState = it },
            label = { Text("Preço") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Preço Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 5.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Column(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth(0.78f)
                .padding(vertical = 3.dp)
                .align(Alignment.CenterHorizontally)
                .border(1.dp, Black2, shape = RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable { estadoExpMarcha = !estadoExpMarcha }
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { estadoExpMarcha = !estadoExpMarcha }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    tint = Black2,
                    contentDescription = "Modelo Icon",
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = qtdDeMarchasState,
                    color = Black2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { estadoExpMarcha = !estadoExpMarcha }
                        .padding(start = 5.dp, top = 5.dp)
                )
            }

            DropdownMenu(
                expanded = estadoExpMarcha,
                onDismissRequest = { estadoExpMarcha = false }
            ) {
                marchas.forEach{marcha ->
                    DropdownMenuItem(
                        onClick = {
                            qtdDeMarchasState = marcha
                            estadoExpMarcha = false
                            Log.i("Teste", "Clicado: $qtdDeMarchasState")
                            Toast.makeText(context, "Clicado: $qtdDeMarchasState", Toast.LENGTH_SHORT).show()
                        },
                        text = { Text(text = marcha) }
                    )
                }
            }
        }

        Button(
            onClick = {
                DAOBicicleta.addBicicleta(
                    cpfClienteState.text,
                    codigoState.text,
                    modeloState.text,
                    materialDoChassiState,
                    aroState,
                    precoState.text,
                    qtdDeMarchasState,
                    context
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(18.dp)
        ) {
            Text(
                text = "Fazer Pedido",
                fontSize = 15.sp
            )
        }
        Button(
            onClick = {
                DAOBicicleta.altBicicleta(
                    cpfClienteState.text,
                    codigoState.text,
                    modeloState.text,
                    materialDoChassiState,
                    aroState,
                    precoState.text,
                    qtdDeMarchasState,
                    context
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp)
        ) {
            Text(
                text = "Alterar pedido de Bicicleta",
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdicionarBike() {
    Prova02Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TelaAdicionarBicicleta()
        }
    }
}