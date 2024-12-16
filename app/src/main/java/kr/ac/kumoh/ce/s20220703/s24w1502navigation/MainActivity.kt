package kr.ac.kumoh.ce.s20220703.s24w1502navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            DrawerSheet(drawerState) {
                navController.navigate(it) {
                    launchSingleTop = true
                    popUpTo(it) { inclusive = true }
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
            bottomBar = {
//                BottomBar {
//                    navController.navigate(it) {
//                        launchSingleTop = true
//                        popUpTo(it) { inclusive = true }
//                    }
//                }
                BottomNavigationBar {
                    navController.navigate(it) {
                        launchSingleTop = true
                        popUpTo(it) { inclusive = true }
                    }
                }
            },
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "screen1",
                modifier = Modifier.padding(innerPadding),
            ) {
                composable("screen1") {
                    FirstScreen()
                }
                composable("screen2") {
                    SecondScreen {
                        navController.navigate("screen_detail/${it}") {
                            launchSingleTop = true
                            popUpTo("screen_detail") { inclusive = true }
                        }
                    }
                }
                composable(
                    route = "screen_detail/{songId}",
                    arguments = listOf(
                        navArgument("songId") {
                            type = NavType.IntType
                        },
                    )
                ) {
                    DetailScreen(it.arguments?.getInt("songId"))
                }
                }
            }
        }
    }


@Composable
fun DetailScreen(songId: Int?) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = "노래 $songId",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 100.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun BottomNavigationBar(onNavigate: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            label = {
                Text("가수")
            },
            icon = {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = "screen1 icon"
                )
            },
            selected = false,
            onClick = {
                onNavigate("screen1")
            }
        )
        NavigationBarItem(
            label = {
                Text("노래")
            },
            icon = {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "screen2 icon"
                )
            },
            selected = false,
            onClick = {
                onNavigate("screen2")
            }
        )
    }
}

//@Composable
//fun BottomBar(onNavigate: (String) -> Unit) {
//    BottomAppBar(
//        actions = {
//            IconButton(
//                onClick = {
//                    onNavigate("screen1")
//                }
//            ) {
//                Icon(
//                    Icons.Filled.Face,
//                    contentDescription = "screen1 icon"
//                )
//            }
//            IconButton(
//                onClick = {
//                    onNavigate("screen2")
//                }
//            ) {
//                Icon(
//                    Icons.Filled.Star,
//                    contentDescription = "screen2 icon"
//                )
//            }
//        }
//    )
//}

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
            label = { Text("가수") },
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
            label = { Text("노래") },
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
fun FirstScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text("가수")
    }
}

@Composable
fun SecondScreen(onNavigate: (Int) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inversePrimary)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(30) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            onNavigate(it)
                        },
                    colors = CardDefaults.cardColors(
                        MaterialTheme.colorScheme
                            .surfaceVariant,
                    ),
                    elevation = CardDefaults
                        .cardElevation(16.dp),
                ) {
                    Text(
                        text = "노래 $it",
                        modifier = Modifier
                            .padding(16.dp),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}