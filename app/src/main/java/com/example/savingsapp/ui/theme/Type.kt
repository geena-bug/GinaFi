package com.example.savingsapp.ui.theme

// Import necessary classes and functions
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    // Define the style for bodyLarge text
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, // Default font family
        fontWeight = FontWeight.Normal, // Normal font weight
        fontSize = 16.sp, // Font size of 16sp
        lineHeight = 24.sp, // Line height of 24sp
        letterSpacing = 0.5.sp // Letter spacing of 0.5sp
    )
    /* Other default text styles to override
    // Define the style for titleLarge text
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default, // Default font family
        fontWeight = FontWeight.Normal, // Normal font weight
        fontSize = 22.sp, // Font size of 22sp
        lineHeight = 28.sp, // Line height of 28sp
        letterSpacing = 0.sp // No letter spacing
    ),
    // Define the style for labelSmall text
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default, // Default font family
        fontWeight = FontWeight.Medium, // Medium font weight
        fontSize = 11.sp, // Font size of 11sp
        lineHeight = 16.sp, // Line height of 16sp
        letterSpacing = 0.5.sp // Letter spacing of 0.5sp
    )
    */
)
