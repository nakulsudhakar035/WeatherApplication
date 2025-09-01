package com.nakuls.weatherapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WeatherScreen(innerPadding: PaddingValues) {

    var city by remember {
        mutableStateOf("")
    }
    Column {
        Row {
            OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                },
                label = {
                    stringResource(R.string.search)
                }
            )
        }
    }
}

@Preview
@Composable
fun ShowScreen(){
    val paddingValues: PaddingValues = PaddingValues()
    WeatherScreen(paddingValues)
}