package com.manueldidonna.puppyfinder.puppieslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Cake
import androidx.compose.material.icons.twotone.Pets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.manueldidonna.puppyfinder.entities.PuppyAge
import com.manueldidonna.puppyfinder.entities.PuppyPicture
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PuppyListEntry(
    modifier: Modifier,
    puppyName: String,
    puppyAge: PuppyAge,
    puppyPicture: PuppyPicture,
    onClick: () -> Unit
) {
    Card(modifier = modifier, shape = MaterialTheme.shapes.medium, elevation = 2.dp) {
        Column(
            modifier = Modifier.clickable(
                onClick = onClick,
                onClickLabel = "Puppy info",
            )
        ) {
            CoilImage(
                data = puppyPicture.resource,
                contentDescription = "Puppy picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(puppyPicture.aspectRatio),
                contentScale = ContentScale.FillWidth
            )
            PuppyNameAndAge(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                name = puppyName,
                age = puppyAge
            )
        }
    }
}

@Composable
private fun PuppyNameAndAge(modifier: Modifier, name: String, age: PuppyAge) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
            Icon(imageVector = Icons.TwoTone.Pets, contentDescription = "Puppy breed icon")
            Spacer(Modifier.width(8.dp))
            Text(text = name, style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.width(16.dp))
            Icon(imageVector = Icons.TwoTone.Cake, contentDescription = "Puppy age icon")
            Spacer(Modifier.width(8.dp))
            Text(
                text = remember(age) { age.toString() },
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}
