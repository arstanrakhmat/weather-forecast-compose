package com.example.weatherapp.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.model.WeatherItem
import com.example.weatherapp.utils.formatDateTime


@Composable
fun SunsetAndSunriseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(25.dp)
            )

            Text(
                text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.labelMedium
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Text(
                text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.labelMedium
            )

            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset icon",
                modifier = Modifier.size(25.dp)
            )


        }

    }
}

@Composable
fun HumidityWeatherPressure(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.labelMedium)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.labelMedium)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )

            Text(text = "${weather.humidity} mph", style = MaterialTheme.typography.labelMedium)
        }
    }
}


@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(data = imageUrl), contentDescription = "icon image",
        modifier = Modifier.size(50.dp)
    )
}