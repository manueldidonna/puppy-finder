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
package com.manueldidonna.puppyfinder.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.manueldidonna.puppyfinder.entities.Puppy
import com.manueldidonna.puppyfinder.floatingActionButtonDefaultElevation
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun PuppyDetailsScreen(puppy: Puppy, closeScreen: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = closeScreen,
            modifier = Modifier
                .zIndex(8f)
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(start = 4.dp)
        ) {
            Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = "Arrow back icon")
        }

        CoilImage(
            data = puppy.picture.resource,
            contentDescription = "Puppy picture",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight(fraction = 0.45f)
                .fillMaxWidth()
        )

        Card(
            shape = MaterialTheme.shapes.large.copy(
                bottomStart = ZeroCornerSize,
                bottomEnd = ZeroCornerSize
            ),
            elevation = if (MaterialTheme.colors.isLight) 8.dp else 0.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxHeight(fraction = 0.60f)
        ) {
            PuppyInfo(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .padding(bottom = 80.dp),
                puppy = puppy
            )
        }

        AdoptPuppyButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(),
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
private fun AdoptPuppyButton(modifier: Modifier, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.TwoTone.Favorite, contentDescription = "Adopt button icon") },
        text = { Text(text = "ADOPTION") },
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        elevation = floatingActionButtonDefaultElevation()
    )
}
