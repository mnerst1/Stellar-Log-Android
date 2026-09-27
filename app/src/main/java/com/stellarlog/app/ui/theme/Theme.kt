package com.stellarlog.app.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val Night = darkColorScheme(primary=Color(0xFFB9A7FF),secondary=Color(0xFF91D7D0),tertiary=Color(0xFFFFC978),background=Color(0xFF10131C),surface=Color(0xFF171B26),primaryContainer=Color(0xFF292441),secondaryContainer=Color(0xFF202B34))
private val Dawn = lightColorScheme(primary=Color(0xFF5946A8),secondary=Color(0xFF176B67),tertiary=Color(0xFF815500),background=Color(0xFFF7F6FC),surface=Color(0xFFF7F6FC),primaryContainer=Color(0xFFE9E2FF),secondaryContainer=Color(0xFFE0F1F0))
@Composable fun StellarTheme(dark: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) { MaterialTheme(colorScheme=if(dark) Night else Dawn, content=content) }