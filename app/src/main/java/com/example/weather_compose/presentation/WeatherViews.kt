package com.example.weather_compose.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.weather_compose.data.WeatherModel
import com.example.weather_compose.response.ErrorType
import com.example.weather_compose.response.NetworkResponse
import com.example.weather_compose.util.Const.TAG
import com.example.weather_compose.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(viewModel: WeatherViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current

    var city by remember {
        mutableStateOf("")
    }

    val weatherState by viewModel.weatherResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(modifier = Modifier.weight(1f), value = city, onValueChange = {
                city = it
            }, label = {
                Text(text = "Search for any location")
            })
            IconButton(onClick = {
                viewModel.getData(city)
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search for any location"
                )
            }

        }



        when (weatherState) {
            is NetworkResponse.Error -> {
                val errorState = weatherState as NetworkResponse.Error
                val errorMessage = when (errorState.errorType) {
                    ErrorType.NETWORK -> "Network error: ${errorState.message}"
                    ErrorType.TIMEOUT -> "Timeout error: ${errorState.message}"
                    ErrorType.UNKNOWN -> "Unknown error: ${errorState.message}"
                }
                // Show error message
                Text(text = errorMessage, color = Color.Red)
            }

            is NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Success -> {
                val weatherData = (weatherState as NetworkResponse.Success<WeatherModel>).data
                // Display weather data
                Text(text = "Weather: ${weatherData.forecast}")
            }

            is NetworkResponse.DoNone -> {

            }
        }
    }


}