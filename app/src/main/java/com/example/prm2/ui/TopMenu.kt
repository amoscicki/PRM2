package com.example.prm2.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalCurrentLocation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopMenu(navController: NavHostController) {
    val location = LocalCurrentLocation.current
    TopAppBar(modifier = Modifier,
        title = {
//            Text(text = stringResource(R.string.app_title))
            Text(text=location.toString())
                },
        navigationIcon = {
            IconButton(onClick = { navController.navigate(EntryListRoute) }) {
                Icon(Icons.Outlined.DateRange, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(
                    MapRoute                )
            }) {
                Icon(Icons.Outlined.Map, contentDescription = null)
            }
        })
}