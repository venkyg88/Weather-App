package com.example.weatherapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

/*
    Re-usable AppBar component across the app
 */
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddAction: () -> Unit = {},
    onButtonClicked: () -> Unit = {}){

    TopAppBar(title = {
                      Text(text = title,
                      color = MaterialTheme.colors.onSecondary,
                      style = TextStyle(fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp))
    },
        actions = {
                  if(isMainScreen) {
                      IconButton(onClick = {
                          onAddAction.invoke()
                      }) {
                          Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                      }
                      IconButton(onClick = { /*TODO*/ }) {
                          Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "more icon")
                      }
                  } else Box{}
        },
        navigationIcon = {
                         if(icon != null) {
                             Icon(imageVector = icon, contentDescription = null,
                             tint = MaterialTheme.colors.onSecondary,
                             modifier = Modifier.clickable {
                                 onButtonClicked.invoke()
                             })
                         }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation

        )
}