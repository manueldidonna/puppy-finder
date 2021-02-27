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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manueldidonna.puppyfinder.PuppyFinderTheme
import com.manueldidonna.puppyfinder.entities.FakePuppies
import com.manueldidonna.puppyfinder.entities.Puppy
import com.manueldidonna.puppyfinder.entities.PuppyAge

@Composable
fun PuppyInfo(modifier: Modifier, puppy: Puppy) {
    Column(modifier = modifier) {
        PuppyNameAndBreed(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
            puppyName = puppy.name,
            breed = puppy.breedName,
            isFemale = puppy.isFemale
        )
        Divider()
        PuppyCharacteristicsCarousel(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
            weightInKilos = puppy.weightInKilos,
            age = puppy.age,
            location = puppy.location
        )
        Divider()
        PuppyStory(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
            story = puppy.description
        )
    }
}

@Composable
private fun PuppyNameAndBreed(
    modifier: Modifier,
    puppyName: String,
    breed: String,
    isFemale: Boolean
) {
    Column(modifier = modifier) {
        Row {
            Text(text = puppyName, style = MaterialTheme.typography.h6)
            Spacer(Modifier.weight(1f))
            Icon(
                imageVector = if (isFemale) Icons.TwoTone.Female else Icons.TwoTone.Male,
                contentDescription = "Puppy gender icon",
                tint = MaterialTheme.colors.primary
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = breed,
            style = MaterialTheme.typography.subtitle2,
            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
        )
    }
}

@Composable
private fun PuppyStory(modifier: Modifier, story: String) {
    Column(modifier = modifier) {
        Text(
            text = "Puppy story",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = story,
            style = MaterialTheme.typography.body2,
            color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
        )
    }
}

@Composable
private fun PuppyCharacteristicsCarousel(
    contentPadding: PaddingValues,
    weightInKilos: Double,
    age: PuppyAge,
    location: String
) {
    LazyRow(contentPadding = contentPadding, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        item { PuppyCharacteristic(icon = Icons.TwoTone.Place, text = location) }
        item { PuppyCharacteristic(icon = Icons.TwoTone.Cake, text = age.toString()) }
        item {
            PuppyCharacteristic(icon = Icons.TwoTone.MonitorWeight, text = "$weightInKilos kilos")
        }
    }
}

@Composable
private fun PuppyCharacteristic(icon: ImageVector, text: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 2.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(48.dp)
        ) {
            Icon(imageVector = icon, contentDescription = text)
            Spacer(Modifier.width(12.dp))
            Text(text = text, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
@Preview
private fun PreviewInfo() {
    PuppyFinderTheme {
        Surface {
            PuppyInfo(modifier = Modifier, puppy = FakePuppies.first())
        }
    }
}
