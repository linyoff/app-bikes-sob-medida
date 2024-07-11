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
import androidx.compose.material.icons.outlined.Lock
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
import com.aliny.prova02.model.Bicicleta
import com.aliny.prova02.model.Cliente
import com.aliny.prova02.model.DAOBicicleta
import com.aliny.prova02.model.DAOCliente
import com.aliny.prova02.ui.theme.Orange
import com.aliny.prova02.ui.theme.Prova02Theme

@Composable
fun BikesScreen() {

    var context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bicicletas",
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
                        context.startActivity(Intent(context, AdicionarBicicleta::class.java))
                    }
            ) {
                Text(
                    text = "Fazer novo pedido de Bicicleta",
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
                        context.startActivity(Intent(context, AdicionarBicicleta::class.java))
                    }
            ) {
                Text(
                    text = "Alterar dados de um pedido de Bicicleta",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )
            }
        }
        var codigoState by remember { mutableStateOf(TextFieldValue()) }

        Text(
            text = "Digite o código ou o modelo da bicicleta que deseja excluir ou buscar",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 30.dp, start = 30.dp, end = 25.dp, bottom = 5.dp)
        )

        OutlinedTextField(
            value = codigoState,
            onValueChange = { codigoState = it },
            label = { Text("Código ou modelo") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Código Icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        var bike by remember { mutableStateOf<Bicicleta?>(null) }
        Button(
            onClick = {

                DAOBicicleta.buscarBicicleta(codigoState.text, context) { b, erro ->
                    if (b != null) {
                        bike = b
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
                DAOBicicleta.excluirBicicleta(
                    codigoState.text,
                    context
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Excluir",
                fontSize = 15.sp
            )
        }

        bike?.let {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "${bike.toString()}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBikes(){
    Prova02Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BikesScreen()
        }
    }
}