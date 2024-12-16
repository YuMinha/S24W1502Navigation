package kr.ac.kumoh.ce.s20220703.s24w1502navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kr.ac.kumoh.ce.s20220703.s24w1502navigation.ui.theme.S24W1502NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S24W1502NavigationTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "screen1",
            modifier = Modifier.padding(innerPadding),
        ) {
            composable("screen1") {
                FirstScreen(navController)
            }
            composable("screen2") {
                SecondScreen(navController)
            }
        }
    }
}

@Composable
fun FirstScreen(navController: NavController) {
    Column {
        Text("화면 1")
        Button(
            onClick = {
                navController.navigate("screen2"){
                    launchSingleTop = true
                    popUpTo("screen2") { inclusive = true }
                }
            }
        ) {
            Text("화면 2로 이동")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    Column {
        Text("화면 2")
        Button(
            onClick = {
                navController.navigate("screen1"){
                    launchSingleTop = true
                    popUpTo("screen2") { inclusive = true }
                }
            }
        ) {
            Text("화면 1로 이동")
        }
    }
}
