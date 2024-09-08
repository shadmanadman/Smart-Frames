package com.sam.adams.taskmanagerwidget.presentation.view.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val glanceBodyText21 = androidx.glance.text.TextStyle(
    fontSize = 21.sp,
    fontWeight = androidx.glance.text.FontWeight.Bold
)
val glanceBodyText18 = androidx.glance.text.TextStyle(
    fontSize = 18.sp,
    fontWeight = androidx.glance.text.FontWeight.Bold
)

val glanceBodyText14 = androidx.glance.text.TextStyle(
    fontSize = 14.sp,
    fontWeight = androidx.glance.text.FontWeight.Bold
)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 10.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)