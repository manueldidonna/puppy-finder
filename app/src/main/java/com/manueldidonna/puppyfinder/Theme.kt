/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.manueldidonna.puppyfinder

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

private val DarkColorPalette = darkColors(
    primary = Color(0xff9bbed3),
    primaryVariant = Color(0xff9bbed3),
    secondary = Color(0xff86e6a9),
    surface = Color(0xff9bbed3).copy(alpha = 0.08f).compositeOver(Color(0xff121212)),
    background = Color(0xff9bbed3).copy(alpha = 0.08f).compositeOver(Color(0xff121212))
)

private val LightColorPalette = lightColors(
    primary = Color(0xff073042),
    primaryVariant = Color(0xff073042),
    secondary = Color(0xff3ddc84)
)

private val Shapes = Shapes(
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun floatingActionButtonDefaultElevation() =
    FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp, pressedElevation = 6.dp)

@Composable
fun PuppyFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun EdgeToEdgeContent(content: @Composable () -> Unit) {
    val view = LocalView.current
    val window = (LocalContext.current as Activity).window
    // window.statusBarColor = android.graphics.Color.TRANSPARENT
    // window.navigationBarColor = android.graphics.Color.TRANSPARENT
    val insetsController = remember(view, window) {
        WindowCompat.getInsetsController(window, view)
    }
    val isLightTheme = MaterialTheme.colors.isLight
    insetsController?.run {
        isAppearanceLightNavigationBars = isLightTheme
        isAppearanceLightStatusBars = isLightTheme
    }
    ProvideWindowInsets(content = content)
}
