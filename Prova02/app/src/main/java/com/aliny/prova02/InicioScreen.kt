package com.aliny.prova02

import android.content.Intent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aliny.prova02.model.FileUtils
import com.aliny.prova02.ui.theme.Orange
import com.aliny.prova02.ui.theme.Prova02Theme

@Composable
fun InicioScreen() {

    var context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box (
            modifier = Modifier
                .padding(top = 120.dp, bottom = 90.dp, start = 10.dp, end = 15.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Bem-vindo ao aplicativo da Jeff Bike!",
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Orange, shape = RoundedCornerShape(17.dp))
                .padding(5.dp)
                .clickable {
                    FileUtils.saveAllData(context)
                }
        ) {
            Text(
                text = "Salvar todos os dados em um TXT",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(30.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Orange, shape = RoundedCornerShape(17.dp))
                    .padding(5.dp)
                    .clickable {
                        context.startActivity(Intent(context, AllClientes::class.java))
                    }
            ) {
                Text(
                    text = "Mostrar todos os Clientes",
                    color = Color.White,
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
                        context.startActivity(Intent(context, TodasBicicletas::class.java))
                    }
            ) {
                Text(
                    text = "Mostrar todas as Bicicletas",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInicio(){
    Prova02Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            InicioScreen()
        }
    }
}