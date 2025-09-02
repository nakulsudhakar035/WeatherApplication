package com.nakuls.weatherapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nakuls.weatherapplication.viewModel.WeatherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel = viewModel(),
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
                modifier = Modifier.weight(1f),
                value = weatherUIState.city,
                onValueChange = {
                    weatherViewModel.onCityChanged(it)
                },
                label = {
                    stringResource(R.string.search)
                }
            )
            IconButton(onClick = {
                weatherViewModel.getWeatherforCity(weatherUIState.city)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon)
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowScreen(){
    val paddingValues: PaddingValues = PaddingValues()
    WeatherScreen(viewModel(),paddingValues)
}