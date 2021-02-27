/*
 * Copyright (C) 2021 Manuel Di Donna
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  he Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.manueldidonna.puppyfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.manueldidonna.puppyfinder.details.PuppyDetailsScreen
import com.manueldidonna.puppyfinder.entities.FakePuppies
import com.manueldidonna.puppyfinder.entities.FakePuppyBreeds
import com.manueldidonna.puppyfinder.entities.Puppy
import com.manueldidonna.puppyfinder.puppieslist.PuppiesList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            PuppyFinderTheme {
                EdgeToEdgeContent {
                    PuppyFinder()
                }
            }
        }
    }
}

@Composable
fun PuppyFinder() {
    Surface(modifier = Modifier.fillMaxSize()) {
        var puppyIndexInList by rememberSaveable { mutableStateOf(-1) }
        val puppy: Puppy? = remember(puppyIndexInList) {
            FakePuppies.getOrNull(puppyIndexInList)
        }
        if (puppy == null) {
            PuppiesList(
                puppies = FakePuppies,
                dogBreeds = FakePuppyBreeds,
                onPuppyClick = { puppyIndexInList = FakePuppies.indexOf(it) }
            )
        } else {
            PuppyDetailsScreen(
                puppy = puppy,
                closeScreen = { puppyIndexInList = -1 }
            )
        }
    }
}
