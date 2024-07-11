package com.aliny.prova02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliny.prova02.model.DAOCliente
import com.aliny.prova02.ui.theme.Orange
import com.aliny.prova02.ui.theme.Prova02Theme

class AdicionarCliente : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prova02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelaAdicionarCliente()
                }
            }
        }
    }
}

@Composable
fun TelaAdicionarCliente() {

    var cpfState by remember { mutableStateOf(TextFieldValue()) }
    var nomeState by remember { mutableStateOf(TextFieldValue()) }
    var emailState by remember { mutableStateOf(TextFieldValue()) }
    var instaState by remember { mutableStateOf(TextFieldValue()) }

    var context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
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
                .padding(top = 30.dp, start = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Voltar"
            )
        }
        Text(
            text = "Adicionar Cliente",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 25.dp)
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = cpfState,
            onValueChange = { cpfState = it },
            label = { Text("CPF") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = "CPF Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
        OutlinedTextField(
            value = nomeState,
            onValueChange = { nomeState = it },
            label = { Text("Nome") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Face,
                    contentDescription = "CPF Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
        OutlinedTextField(
            value = emailState,
            onValueChange = { emailState = it },
            label = { Text("E-mail") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "CPF Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
        OutlinedTextField(
            value = instaState,
            onValueChange = { instaState = it },
            label = { Text("Instagram") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.AccountBox,
                    contentDescription = "CPF Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
        )
        Button(
            onClick = {
                DAOCliente.addCliente(
                    cpfState.text,
                    nomeState.text,
                    emailState.text,
                    instaState.text,
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
                text = "Adicionar",
                fontSize = 15.sp
            )
        }
        Button(
            onClick = {
                DAOCliente.altCliente(
                    cpfState.text,
                    nomeState.text,
                    emailState.text,
                    instaState.text,
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
                text = "Alterar",
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAdicionarCliente() {
    Prova02Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TelaAdicionarCliente()
        }
    }
}