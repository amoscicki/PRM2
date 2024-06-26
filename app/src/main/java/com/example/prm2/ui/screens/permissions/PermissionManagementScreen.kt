package com.example.prm2.ui.screens.permissions

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prm2.R
import com.example.prm2.ui.PinRoute
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalPermissions


@Composable
fun PermissionManagementScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
            Icon(Icons.Outlined.Shield, contentDescription = null)

        Text(
            text = stringResource(R.string.manage_permissions),
            style = MaterialTheme.typography.headlineMedium
        )}

        Text(
            stringResource(R.string.in_order_to_use_the_app_you_need_to_grant_the_following_permissions),
            style = MaterialTheme.typography.labelSmall
        )

        LocalPermissions.current.map { (key, permission) ->
            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {
                permission.value.value = it
            }

            PermissionSwitch(
                title = key,
                description = permission.description,
                isChecked = permission.value,
                onCheckedChange = {
                    permission.requestPermission(launcher)
                }
            )
        }

        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                context.startActivity(intent)

            }) {
            Text(stringResource(R.string.manage_permissions_btn))
        }

        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(PinRoute)
            }, enabled = LocalPermissions.current.checkAllPermissionsGranted()
        ) {
            Text(stringResource(R.string.continue_btn))
        }


    }
}

