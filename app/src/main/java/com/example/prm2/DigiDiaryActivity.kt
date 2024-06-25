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
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.currentLocation
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.deleteEntry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.entries
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.getLocationName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.getCountryName
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.keyword
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.permissions
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.saveEntry
import com.example.prm2.viewmodel.ProvidableCompositionLocalValues.Companion.setKeyword
import com.example.prm2.viewmodel.viewModelFactory


class DigiDiaryActivity : ComponentActivity() {


    // get location
    // get date
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
                        DigiDiaryApp.instance.imageCanvas,
                        DigiDiaryApp.instance.audioRecorder,
                        DigiDiaryApp.instance.locationServiceProvider
                    )
                })

                CompositionLocalProvider(
                    entries provides digiDiaryViewModel.entries,
                    saveEntry provides digiDiaryViewModel.saveEntry,
                    deleteEntry provides digiDiaryViewModel.deleteEntry,
                    keyword provides digiDiaryViewModel.keyword.value,
                    setKeyword provides digiDiaryViewModel.setKeyword,
                    permissions provides digiDiaryViewModel.permissions.value,
                    currentLocation provides digiDiaryViewModel.location.value,
                    getLocationName provides digiDiaryViewModel.getLocationName,
                    getCountryName provides digiDiaryViewModel.getCountryName
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
