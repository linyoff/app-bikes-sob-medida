package com.aliny.prova02

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliny.prova02.model.Bicicleta
import com.aliny.prova02.model.DAOBicicleta
import com.aliny.prova02.ui.theme.Orange
import com.aliny.prova02.ui.theme.Prova02Theme

class TodasBicicletas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prova02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    ShowAllBikes()
                }
            }
        }
    }
}

@Composable
fun ShowAllBikes() {
    val context = LocalContext.current
    var bicicletas: List<Bicicleta> by remember { mutableStateOf(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
            text = "Todas as Bicicletas",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, bottom = 25.dp)
        )

        LazyColumn (
            modifier = Modifier.padding(horizontal = 16.dp)
        ){
            items(bicicletas){ bike ->
                Text(
                    text = bike.toString(),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        DAOBicicleta.getAllBikes(context = context){ listaBikes, msg ->
            if(listaBikes!=null){
                bicicletas = listaBikes
            } else {
                Toast.makeText(context, msg ?: "Erro ao obter bicicletas", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TodasBicicletasPreview() {
    Prova02Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            ShowAllBikes()
        }
    }
}