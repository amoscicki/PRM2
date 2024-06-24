package com.example.prm2.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.prm2.ui.screens.entrylist.EntryListScreen
import com.example.prm2.ui.screens.map.MapScreen
import com.example.prm2.ui.screens.permissions.PermissionManagementScreen
import com.example.prm2.ui.screens.picture.PictureCanvasScreen
import com.example.prm2.ui.screens.pin.PinScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationHost(navController: NavHostController) {



    val currentDestination =
        mutableStateOf(navController.currentDestination?.route?.substringAfterLast("."))
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentDestination.value = destination.route?.substringAfterLast(".")
        }
    }

    @Composable
    fun getTopBar() {
        when (currentDestination.value) {
            "EntryListRoute" -> {
                TopMenu(navController)
            }

            "MapRoute" -> {
                TopMenu(navController)
            }

            else -> {}
        }
    }

    Scaffold(
        topBar = { getTopBar() }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        NavHost(navController, startDestination = PictureCanvasRoute) {
            composable<PermissionsManagementRoute> { PermissionManagementScreen(modifier, navController) }
            composable<EntryListRoute> { EntryListScreen(modifier, navController) }
            composable<MapRoute> { MapScreen(modifier, navController) }
            composable<PinRoute> { PinScreen(modifier, navController) }
            composable<PictureCanvasRoute> { PictureCanvasScreen(modifier, navController) }
        }
    }
}


@Serializable
object PermissionsManagementRoute

@Serializable
object EntryListRoute

@Serializable
object MapRoute

@Serializable
object PinRoute

@Serializable
object PictureCanvasRoute
