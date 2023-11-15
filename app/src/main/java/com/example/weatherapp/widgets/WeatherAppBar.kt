package com.example.weatherapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    Surface(shadowElevation = elevation) {
        TopAppBar(
            title = {
                Text(
                    text = title
                )
            },
            actions = {
                if (isMainScreen) {
                    IconButton(onClick = { onAddActionClicked.invoke() }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "more icon"
                        )
                    }
                } else Box {}
            },
            navigationIcon = {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Go back",
                        modifier = Modifier.clickable {
                            onButtonClicked.invoke()
                        })
                }
            }
        )
    }

}


