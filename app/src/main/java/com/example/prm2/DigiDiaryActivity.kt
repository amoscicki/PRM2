package com.example.prm2


import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.prm2.ui.NavigationHost
import com.example.prm2.ui.theme.DigiDiaryTheme
import com.example.prm2.viewmodel.DigiDiaryViewModel
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalAudioRecorder
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalCurrentLocation
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalDeleteEntry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalEntries
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalGetCountryName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalGetLocationName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalKeyword
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalPermissions
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSaveEntry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetKeyword
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempAudioFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempAudioSeconds
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalSetTempImageFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempAudioFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempAudioSeconds
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalTempImageFile
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.LocalUploadFile
import com.example.prm2.viewmodel.viewModelFactory


class DigiDiaryActivity : ComponentActivity() {



    // function for adding picture
    // screen for editing picture
    // function for adding audio

    // function to edit entry -> change component to edit mode
    // function to delete entry
    // function to play audio
    // function to show map -> screen with map
    // search functionality
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DigiDiaryTheme {
                val digiDiaryViewModel = viewModel<DigiDiaryViewModel>(factory = viewModelFactory {
                    DigiDiaryViewModel(
                        DigiDiaryApp.instance.firebaseDB,
                        DigiDiaryApp.instance.permissionsManager,
                        DigiDiaryApp.instance.audioRecorder,
                        DigiDiaryApp.instance.locationServiceProvider
                    )
                })

                CompositionLocalProvider(
                    LocalEntries provides digiDiaryViewModel.entries,
                    LocalSaveEntry provides digiDiaryViewModel.saveEntry,
                    LocalDeleteEntry provides digiDiaryViewModel.deleteEntry,
                    LocalKeyword provides digiDiaryViewModel.keyword.value,
                    LocalSetKeyword provides digiDiaryViewModel.setKeyword,
                    LocalPermissions provides digiDiaryViewModel.permissions.value,
                    LocalCurrentLocation provides digiDiaryViewModel.currentLocation.value,
                    LocalGetLocationName provides digiDiaryViewModel.getLocationName,
                    LocalGetCountryName provides digiDiaryViewModel.getCountryName,
                    LocalAudioRecorder provides digiDiaryViewModel.audioRecorderObject,
                    LocalUploadFile provides digiDiaryViewModel.uploadFile,
                    LocalTempAudioFile provides digiDiaryViewModel.tempAudioFile.value,
                    LocalSetTempAudioFile provides digiDiaryViewModel.setTempAudioFile,
                    LocalTempAudioSeconds provides digiDiaryViewModel.tempAudioSeconds.value,
                    LocalSetTempAudioSeconds provides digiDiaryViewModel.setTempAudioSeconds,
                    LocalTempImageFile provides digiDiaryViewModel.tempImageFile.value,
                    LocalSetTempImageFile provides digiDiaryViewModel.setTempImageFile
                ) {
                    println("Keyword: ${digiDiaryViewModel.keyword.value}")
                    println("Entries: ${digiDiaryViewModel.entries}")
                    val navController = rememberNavController()
                    NavigationHost(
                        navController = navController
                    )
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                it.windowToken, 0
            )
        }
        return super.dispatchTouchEvent(ev)
    }


}
