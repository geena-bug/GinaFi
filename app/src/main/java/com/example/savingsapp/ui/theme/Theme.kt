package com.example.savingsapp.ui.theme

// Import necessary classes and functions
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Define the dark color scheme
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Define the light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// Composable function to set the theme for the SavingsApp
@Composable
fun SavingsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Determine if the system is in dark theme
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, // Enable dynamic color if available
    content: @Composable () -> Unit // Content to be displayed within the theme
) {
    val colorScheme = when {
        // Use dynamic color scheme if dynamic color is enabled and the Android version is S or higher
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Use dark color scheme if dark theme is enabled
        darkTheme -> DarkColorScheme
        // Use light color scheme otherwise
        else -> LightColorScheme
    }

    // Apply the MaterialTheme with the selected color scheme and typography
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
