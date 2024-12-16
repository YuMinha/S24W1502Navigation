package kr.ac.kumoh.ce.s20220703.s24w1502navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
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
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerSheet(drawerState){
                navController.navigate(it){
                    launchSingleTop = true
                    popUpTo("screen2") { inclusive = true }
                }
            }
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(drawerState)
            },
            )
            { innerPadding ->
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = { Text("네비게이션") },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "menu icon"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}
@Composable
fun DrawerSheet(
    drawerState: DrawerState,
    onNavigate: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    ModalDrawerSheet {
        NavigationDrawerItem(
            label = { Text("화면 1") },
            selected = false,
            onClick = {
                onNavigate("screen1")
                scope.launch {
                    drawerState.close()
                }
            },
            icon = {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = "screen1 icon"
                )
            }
        )
        NavigationDrawerItem(
            label = { Text("화면 2") },
            selected = false,
            onClick = {
                onNavigate("screen2")
                scope.launch {
                    drawerState.close()
                }
            },
            icon = {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "screen2 icon"
                )
            }
        )
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
