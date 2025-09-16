package com.nakuls.weatherapplication.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nakuls.weatherapplication.viewModel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.nakuls.weatherapplication.R
import com.nakuls.weatherapplication.data.model.Condition
import com.nakuls.weatherapplication.data.model.Current
import com.nakuls.weatherapplication.data.model.Location
import com.nakuls.weatherapplication.data.model.Weather

@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel = hiltViewModel(),
                  innerPadding: PaddingValues) {

    val weatherUIState by weatherViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth().padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .testTag("SearchField"),
                value = weatherUIState.city,
                trailingIcon = {
                    if (weatherUIState.city.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search),
                            color = Color.Gray,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                },
                onValueChange = {
                    weatherViewModel.onCityChanged(it)
                },
                label = {
                    stringResource(R.string.search)
                }
            )
            IconButton(
                modifier = Modifier.testTag("SearchButton"),
                enabled = weatherUIState.city.isNotBlank(),
                onClick = {
                    weatherViewModel.getWeatherForCity(weatherUIState.city)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            }
        }
        when{
            weatherUIState.isLoading -> CircularProgressIndicator(
                modifier = Modifier.testTag("LoadingIndicator")
            )
            weatherUIState.errorMessage.length > 1 -> Text(
                text = weatherUIState.errorMessage,
                modifier = Modifier.testTag("Error")
            )
            weatherUIState.weather != null -> WeatherDetail(
                weatherUIState.weather!!
            )
        }
    }
}

@Preview(widthDp = 800,  // Landscape width
    heightDp = 400, // Landscape height
    showBackground = true,
    name = "Landscape Preview")
@Composable
fun showWeatherDetail(){
    val weather = Weather(
        Current(
            cloud = "test",
            condition = Condition(
                code = "test",
                icon = "test",
                text = "test"
            ),
            dewpoint_c = "test",
            dewpoint_f = "test",
            diff_rad = "test",
            dni = "test",
            feelslike_c = "test",
            feelslike_f = "test",
            gti = "test",
            gust_kph = "test",
            gust_mph = "test",
            heatindex_c = "test",
            heatindex_f = "test",
            humidity = "test",
            is_day = "test",
            last_updated = "test",
            last_updated_epoch = "test",
            precip_in = "test",
            precip_mm = "test",
            pressure_in = "test",
            pressure_mb = "test",
            short_rad = "test",
            temp_c = "25",
            temp_f = "test",
            uv = "test",
            vis_km = "test",
            vis_miles = "test",
            wind_degree = "test",
            wind_dir = "test",
            wind_kph = "test",
            wind_mph = "test",
            windchill_c = "test",
            windchill_f = "test"
        ),
        Location(
            country = "test",
            lat = "test",
            localtime = "2025-09-08 12:34",
            localtime_epoch = "test",
            lon = "test",
            name = "Berlin",
            region = "test",
            tz_id = "test"
        )
    )
    WeatherDetail(weather)
}

@Composable
fun WeatherDetail(weather: Weather){

    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        // Landscape layout
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            // Left column - main info
            Column(
                modifier = Modifier
                    .weight(0.40f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.location_icon),
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    modifier = Modifier.testTag("City"),
                    text = weather.location.name,
                    fontSize = 30.sp
                )
                Text(
                    text = weather.location.country,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${weather.current.temp_c} °c",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("Temperature")
                )
                Text(
                    text = weather.current.condition.text,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                AsyncImage(
                    modifier = Modifier.size(160.dp),
                    model = "https:${weather.current.condition.icon}".replace("64x64","128x128"),
                    contentDescription = stringResource(R.string.weather_condition_icon)
                )

            }

            // Right column - details
            Card(
                modifier = Modifier
                    .weight(0.50f)
                    .padding(start = 16.dp),
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherKeyVal(stringResource(R.string.humidity),weather.current.humidity)
                        WeatherKeyVal(stringResource(R.string.wind_speed),weather.current.wind_kph+" km/h")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherKeyVal(stringResource(R.string.uv),weather.current.uv)
                        WeatherKeyVal(stringResource(R.string.precipitation),weather.current.precip_mm+" mm")
                      }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        WeatherKeyVal("Local Time",weather.location.localtime.split(" ")[1])
                        WeatherKeyVal("Local Date",weather.location.localtime.split(" ")[0])
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            Spacer(modifier = Modifier.weight(0.10f))
        }
    } else {
        // Portrait layout (your existing code)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ){
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = stringResource(R.string.location_icon),
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        modifier = Modifier.testTag("City"),
                        text = weather.location.name,
                        fontSize = 30.sp
                    )
                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )
                    Text(
                        text = weather.location.country,
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${weather.current.temp_c} °c",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.testTag("Temperature")
                )
                AsyncImage(
                    modifier = Modifier.size(160.dp),
                    model = "https:${weather.current.condition.icon}".replace("64x64","128x128"),
                    contentDescription = stringResource(R.string.weather_condition_icon)
                )
                Text(
                    text = weather.current.condition.text,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Card {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherKeyVal(stringResource(R.string.humidity),weather.current.humidity)
                            WeatherKeyVal(stringResource(R.string.wind_speed),weather.current.wind_kph+" km/h")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherKeyVal(stringResource(R.string.uv),weather.current.uv)
                            WeatherKeyVal(stringResource(R.string.precipitation),weather.current.precip_mm+" mm")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherKeyVal("Local Time",weather.location.localtime.split(" ")[1])
                            WeatherKeyVal("Local Date",weather.location.localtime.split(" ")[0])
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherKeyVal(key : String, value : String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}

