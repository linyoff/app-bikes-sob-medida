package com.aliny.prova02

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.composable
import com.aliny.prova02.model.Cliente
import com.aliny.prova02.model.DAOCliente
import com.aliny.prova02.ui.theme.Orange
import com.aliny.prova02.ui.theme.Prova02Theme

@Composable
fun ClientsScreen() {
    var context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Clientes",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 50.dp, bottom = 25.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Orange, shape = RoundedCornerShape(17.dp))
                    .padding(5.dp)
                    .clickable {
                        context.startActivity(Intent(context, AdicionarCliente::class.java))
                    }
            ) {
                Text(
                    text = "Adicionar novo Cliente",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Orange, shape = RoundedCornerShape(17.dp))
                    .padding(5.dp)
                    .clickable {
                        context.startActivity(Intent(context, AdicionarCliente::class.java))
                    }
            ) {
                Text(
                    text = "Alterar dados de um Cliente",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )
            }
        }

        var inputState by remember { mutableStateOf(TextFieldValue()) }

        Text(
            text = "Digite o CPF ou o nome do cliente que deseja buscar ou excluir",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 30.dp, start = 28.dp, end = 28.dp, bottom = 5.dp)
        )

        OutlinedTextField(
            value = inputState,
            onValueChange = { inputState = it },
            label = { Text("Nome ou CPF") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "CPF Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        var cliente by remember { mutableStateOf<Cliente?>(null) }
        Button(
            onClick = {

                DAOCliente.buscarCliente(inputState.text, context) { c, erro ->
                    if (c != null) {
                        cliente = c
                    } else {
                        Toast.makeText(context, erro, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Text(
                text = "Buscar",
                fontSize = 15.sp
            )
        }
        Button(
            onClick = {
                DAOCliente.excluirCliente(
                    inputState.text,
                    context
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 4.dp)
        ) {
            Text(
                text = "Excluir",
                fontSize = 15.sp
            )
        }

        cliente?.let {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "${cliente.toString()}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClients() {
    Prova02Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ClientsScreen()
        }
    }
}
