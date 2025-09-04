package com.nakuls.weatherapplication

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.unit.dp
import com.nakuls.weatherapplication.ui.screen.WeatherScreen
import com.nakuls.weatherapplication.ui.theme.WeatherApplicationTheme
import com.nakuls.weatherapplication.viewModel.WeatherUIState
import com.nakuls.weatherapplication.viewModel.WeatherViewModel
import org.junit.Rule
import org.junit.Test

class WeatherScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchField_updatesTextCorrectly() {
        val fakeApi = FakeWeatherApi()
        val viewModel = WeatherViewModel(fakeApi)
        composeTestRule.setContent {
            WeatherApplicationTheme {
                WeatherScreen(
                    viewModel,
                    innerPadding = PaddingValues(8.dp)
                )
            }
        }

        composeTestRule.onNodeWithTag("SearchField")
            .performTextInput("Berlin")
        composeTestRule.onNodeWithTag("SearchField")
            .assertTextEquals("Berlin")
    }

    @Test
    fun showsLoadingIndicator_whenLoading() {
        val fakeApi = FakeWeatherApi()
        val viewModel = WeatherViewModel(fakeApi)
        composeTestRule.setContent {
            WeatherApplicationTheme {
                WeatherScreen(
                    viewModel,
                    innerPadding = PaddingValues(8.dp)
                )
            }
        }
        composeTestRule.onNodeWithTag("SearchField")
            .performTextInput("Berlin")
        composeTestRule.onNodeWithTag("SearchButton")
            .performClick()
        composeTestRule.onNodeWithTag("LoadingIndicator").assertExists()
    }

    @Test
    fun weatherScreen_displaysFakeWeather() {
        val fakeApi = FakeWeatherApi()
        val viewModel = WeatherViewModel(fakeApi)

        composeTestRule.setContent {
            WeatherApplicationTheme {
                WeatherScreen(
                    viewModel,
                    innerPadding = PaddingValues(0.dp)
                )
            }
        }

        composeTestRule.onNodeWithTag("SearchField")
            .performTextInput("Berlin")
        composeTestRule.onNodeWithTag("SearchButton")
            .performClick()
        composeTestRule.waitUntil(
            condition = {
                composeTestRule.onAllNodesWithTag("City")
                    .fetchSemanticsNodes()
                    .isNotEmpty()
            },
            timeoutMillis = 3000
        )
        composeTestRule.onNodeWithTag("City")
            .assertTextContains("Berlin")
        composeTestRule.onNodeWithTag("Temperature")
            .assertTextContains("25")

    }
}