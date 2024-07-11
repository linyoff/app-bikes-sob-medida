package com.aliny.prova02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aliny.prova02.ui.theme.Gray
import com.aliny.prova02.ui.theme.Prova02Theme
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.vector.path

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prova02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) }
                    ) {
                        NavigationHost(navController = navController, modifier = Modifier.padding(it))
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        modifier = Modifier.height(80.dp),
        backgroundColor = Gray,
        contentColor = Color.White
    ) {
        val items = listOf(
            Screen.Main,
            Screen.Clients,
            Screen.Clients,
            Screen.Bikes
        )
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.route,
                        modifier = Modifier
                            .padding(top = 9.dp, bottom = 3.dp)
                            .size(30.dp)
                    )
                },
                label = { Text(screen.route) },
                selected = false,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


sealed class Screen(val route: String, val icon: ImageVector) {
    object Main : Screen("Início", Icons.Filled.Home)
    object Clients : Screen("Clientes", Icons.Filled.Person)
    object Bikes : Screen("Bikes",  IconBike)

}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Screen.Main.route, modifier = modifier) {
        composable(Screen.Main.route) { InicioScreen() }
        composable(Screen.Clients.route) { ClientsScreen() }
        composable(Screen.Bikes.route) { BikesScreen() }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaPreview() {
    Prova02Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavigationBar(navController) }
            ) {
                NavigationHost(navController = navController, modifier = Modifier.padding(it))
            }
        }
    }
}

//variavel do icone bike em formato de imagevector
private var _IconBike: ImageVector? = null

val IconBike: ImageVector
    get() {
        if (_IconBike != null) {
            return _IconBike!!
        }
        _IconBike = ImageVector.Builder(
            name = "IconBike",
            defaultWidth = 500.dp,
            defaultHeight = 500.dp,
            viewportWidth = 375f,
            viewportHeight = 375f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(297.98f, 177.04f)
                    curveTo(264.14f, 177.04f, 236.62f, 204.57f, 236.62f, 238.4f)
                    curveTo(236.62f, 272.23f, 264.14f, 299.76f, 297.98f, 299.76f)
                    curveTo(331.81f, 299.76f, 359.34f, 272.23f, 359.34f, 238.4f)
                    curveTo(359.34f, 204.57f, 331.81f, 177.04f, 297.98f, 177.04f)
                    close()
                    moveTo(297.98f, 315.29f)
                    curveTo(255.58f, 315.29f, 221.09f, 280.8f, 221.09f, 238.4f)
                    curveTo(221.09f, 196.01f, 255.58f, 161.52f, 297.98f, 161.52f)
                    curveTo(340.37f, 161.52f, 374.86f, 196.01f, 374.86f, 238.4f)
                    curveTo(374.86f, 280.8f, 340.37f, 315.29f, 297.98f, 315.29f)
                }
            }
            group {
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(77.02f, 177.04f)
                    curveTo(43.19f, 177.04f, 15.66f, 204.57f, 15.66f, 238.4f)
                    curveTo(15.66f, 272.23f, 43.19f, 299.76f, 77.02f, 299.76f)
                    curveTo(110.85f, 299.76f, 138.38f, 272.23f, 138.38f, 238.4f)
                    curveTo(138.38f, 204.57f, 110.85f, 177.04f, 77.02f, 177.04f)
                    close()
                    moveTo(77.02f, 315.29f)
                    curveTo(34.63f, 315.29f, 0.14f, 280.79f, 0.14f, 238.4f)
                    curveTo(0.14f, 196.01f, 34.63f, 161.52f, 77.02f, 161.52f)
                    curveTo(119.41f, 161.52f, 153.9f, 196.01f, 153.9f, 238.4f)
                    curveTo(153.9f, 280.79f, 119.41f, 315.29f, 77.02f, 315.29f)
                }
            }
            group {
                path(
                    fill = SolidColor(Color(0xFFFFFFFF)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(297.98f, 246.17f)
                    curveTo(294.83f, 246.17f, 291.87f, 244.24f, 290.71f, 241.12f)
                    lineTo(234.44f, 90.35f)
                    lineTo(204.54f, 90.35f)
                    curveTo(200.25f, 90.35f, 196.78f, 86.87f, 196.78f, 82.59f)
                    curveTo(196.78f, 78.3f, 200.25f, 74.82f, 204.54f, 74.82f)
                    lineTo(239.82f, 74.82f)
                    curveTo(243.07f, 74.82f, 245.96f, 76.84f, 247.1f, 79.87f)
                    lineTo(305.25f, 235.69f)
                    curveTo(306.75f, 239.7f, 304.71f, 244.18f, 300.7f, 245.68f)
                    curveTo(299.8f, 246.01f, 298.88f, 246.17f, 297.98f, 246.17f)
                }
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(89.8f, 230.64f)
                lineTo(179.5f, 230.64f)
                lineTo(133.23f, 146.97f)
                close()
                moveTo(192.66f, 246.16f)
                lineTo(77.02f, 246.16f)
                curveTo(74.31f, 246.16f, 71.79f, 244.75f, 70.39f, 242.43f)
                curveTo(68.98f, 240.12f, 68.88f, 237.23f, 70.13f, 234.82f)
                lineTo(126.11f, 126.99f)
                curveTo(126.12f, 126.97f, 126.13f, 126.96f, 126.14f, 126.95f)
                lineTo(131.73f, 116.17f)
                lineTo(116.88f, 116.17f)
                curveTo(112.59f, 116.17f, 109.11f, 112.7f, 109.11f, 108.41f)
                curveTo(109.11f, 104.12f, 112.59f, 100.64f, 116.88f, 100.64f)
                lineTo(144.51f, 100.64f)
                curveTo(147.22f, 100.64f, 149.73f, 102.06f, 151.14f, 104.38f)
                curveTo(152.55f, 106.7f, 152.64f, 109.58f, 151.4f, 111.98f)
                lineTo(145.79f, 122.78f)
                lineTo(252.48f, 122.78f)
                curveTo(256.77f, 122.78f, 260.24f, 126.26f, 260.24f, 130.54f)
                curveTo(260.24f, 134.83f, 256.77f, 138.3f, 252.48f, 138.3f)
                lineTo(146.18f, 138.3f)
                lineTo(199.45f, 234.64f)
                curveTo(200.79f, 237.05f, 200.74f, 239.98f, 199.35f, 242.34f)
                curveTo(197.95f, 244.71f, 195.41f, 246.16f, 192.66f, 246.16f)
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(192.65f, 246.16f)
                curveTo(191.28f, 246.16f, 189.88f, 245.8f, 188.62f, 245.03f)
                curveTo(184.96f, 242.8f, 183.8f, 238.02f, 186.03f, 234.36f)
                lineTo(251.35f, 127.19f)
                curveTo(253.58f, 123.53f, 258.36f, 122.37f, 262.02f, 124.6f)
                curveTo(265.68f, 126.83f, 266.84f, 131.61f, 264.61f, 135.27f)
                lineTo(199.29f, 242.44f)
                curveTo(197.82f, 244.84f, 195.27f, 246.16f, 192.65f, 246.16f)
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(210.95f, 238.4f)
                curveTo(210.95f, 248.5f, 202.76f, 256.69f, 192.66f, 256.69f)
                curveTo(182.56f, 256.69f, 174.38f, 248.5f, 174.38f, 238.4f)
                curveTo(174.38f, 228.3f, 182.56f, 220.12f, 192.66f, 220.12f)
                curveTo(202.76f, 220.12f, 210.95f, 228.3f, 210.95f, 238.4f)
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(201.07f, 277.41f)
                lineTo(187.01f, 277.41f)
                curveTo(184.36f, 277.41f, 182.21f, 275.26f, 182.21f, 272.61f)
                curveTo(182.21f, 269.96f, 184.36f, 267.82f, 187.01f, 267.82f)
                lineTo(187.86f, 267.82f)
                lineTo(187.86f, 238.4f)
                curveTo(187.86f, 235.75f, 190.01f, 233.6f, 192.66f, 233.6f)
                curveTo(195.31f, 233.6f, 197.46f, 235.75f, 197.46f, 238.4f)
                lineTo(197.46f, 267.82f)
                lineTo(201.07f, 267.82f)
                curveTo(203.71f, 267.82f, 205.86f, 269.96f, 205.86f, 272.61f)
                curveTo(205.86f, 275.26f, 203.71f, 277.41f, 201.07f, 277.41f)
            }
        }.build()
        return _IconBike!!
    }

