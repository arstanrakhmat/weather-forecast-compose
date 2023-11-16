package com.example.weatherapp.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapp.model.Favorite
import com.example.weatherapp.navigation.WeatherScreens
import com.example.weatherapp.screens.favorites.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

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

                    IconButton(onClick = {
                        showDialog.value = true
                    }) {
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
                if (isMainScreen) {
                    val isAlreadyInFavList =
                        favoriteViewModel.favList.collectAsState().value.filter { item ->
                            (item.city == title.split(",")[0])
                        }

                    if (isAlreadyInFavList.isEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Icon",
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp)
                                .scale(0.9f)
                                .clickable {
                                    val dataList = title.split(",")
                                    favoriteViewModel
                                        .insertFavorite(
                                            Favorite(
                                                city = dataList[0],
                                                country = dataList[1]
                                            )
                                        )
                                        .run { showIt.value = true }
                                }, tint = Color.Red.copy(alpha = 0.5f)
                        )
                    } else {
                        showIt.value = false
                        Box {}
                    }

                    ShowToast(context = context, showIt)

                }
            }
        )
    }

}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, "Добавленно в избранное", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf("О приложении", "Избранное", "Настройки")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = when (text) {
                                "О приложении" -> Icons.Default.Info
                                "Избранное" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            }, contentDescription = null,
                            tint = Color.LightGray
                        )
                        Text(
                            text = text,
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    when (text) {
                                        "О приложении" -> WeatherScreens.AboutScreen.name
                                        "Избранное" -> WeatherScreens.FavoritesScreen.name
                                        else -> WeatherScreens.SettingScreen.name
                                    }
                                )
                            },
                            fontWeight = FontWeight.W300
                        )
                    }
                }, onClick = {
                    expanded = false
                    showDialog.value = false
                })
            }
        }
    }
}


