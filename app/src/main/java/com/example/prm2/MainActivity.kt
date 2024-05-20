package com.example.prm2

import android.media.MediaDrm.PlaybackComponent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.ModeEditOutline
import androidx.compose.material.icons.outlined.PlayCircleOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.prm2.ui.theme.PRM2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PRM2Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopMenu() },
                    bottomBar = { AddNewEntry() }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp),

                            ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth().padding(16.dp, 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                TextField(
                                    modifier = Modifier,
                                    value = "", onValueChange = { /* doSomething() */ },
                                    placeholder = { Text("Search...") })
                                IconButton(
                                    onClick = { /* doSomething() */ }
                                ) {
                                    Icon(Icons.Outlined.Search, contentDescription = null)
                                }
                            }
                        }
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding( 0.dp, 8.dp)
                        ) {
                            items(10) {
                                Entry()
                            }
                        }
                    }
                }.apply {

                }
            }
        }
    }


}

@Composable
fun Entry() {
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    IconButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Icon(Icons.Outlined.LocationOn, contentDescription = null)
                    }
                    Text(text = "Warsaw, \nPoland")
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(text = "01/01/2024")
                    IconButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Icon(Icons.Outlined.DateRange, contentDescription = null)
                    }
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Card(
                    modifier = Modifier
                        .padding(16.dp, 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.outlinedCardColors()

                )
                {
                    Text(
                        modifier = Modifier.padding(16.dp, 4.dp),
                        text = "Write your thoughts here..."
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.ModeEditOutline, contentDescription = null)
                }


            }
            Card(
                modifier = Modifier
                    .padding(16.dp, 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.outlinedCardColors(),

            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    IconButton(onClick = { /*TODO*/ }) {

                        Icon(Icons.Outlined.PlayCircleOutline, contentDescription = null)
                    }
                    Text(text = "2:30")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null)
                }
       

            }



        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopMenu() {
    TopAppBar(
        modifier = Modifier,
        title = { Text(text = stringResource(R.string.app_title)) },
        navigationIcon = {
            IconButton(
                onClick = { /* doSomething() */ }
            ) {
                Icon(Icons.Outlined.DateRange, contentDescription = null)
            }
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Outlined.Map, contentDescription = null)
            }
        }
    )
}

@Composable
fun AddNewEntry() {
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    IconButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Icon(Icons.Outlined.EditLocationAlt, contentDescription = null)
                    }
                    Text(text = "Warsaw, \nPoland")
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(text = "01/01/2024")
                    IconButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Icon(Icons.Outlined.EditCalendar, contentDescription = null)
                    }

                }

            }
            Row(
                modifier = Modifier

            )
            {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "", onValueChange = { /* doSomething() */ },
                    placeholder = { Text("Write your thoughts here...") })
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = { /* doSomething() */ }
                    ) {
                        Icon(Icons.Outlined.Image, contentDescription = null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Mic, contentDescription = null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.LocationOn, contentDescription = null)
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.AutoMirrored.Outlined.Backspace, contentDescription = null)
                    }
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.AddCircleOutline, contentDescription = null)
                }
            }


        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
