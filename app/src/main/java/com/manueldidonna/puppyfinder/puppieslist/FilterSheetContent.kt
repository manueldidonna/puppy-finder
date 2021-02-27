package com.manueldidonna.puppyfinder.puppieslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Remove
import androidx.compose.material.icons.twotone.RemoveCircle
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun FilterSheetContent(
    swipeProgress: State<Float>,
    dogBreeds: List<String>,
    selectedBreeds: Set<String>,
    toggleBreed: (String) -> Unit
) {
    val progress = swipeProgress.value
    val selectedBreedsAsList = remember(selectedBreeds) { selectedBreeds.toList() }
    Box(Modifier.fillMaxSize()) {
        if (progress < 0.95f) CollapsedBottomSheetContent(
            modifier = Modifier
                .height(120.dp)
                .graphicsLayer {
                    this.clip = true
                    this.alpha = 1f - progress
                },
            selectedBreeds = selectedBreedsAsList,
            removeBreed = toggleBreed
        )

        if (progress > 0.05f) ExpandedBottomSheetContent(
            modifier = Modifier.graphicsLayer {
                this.clip = true
                this.alpha = progress
            },
            toggleBreed = toggleBreed,
            selectedBreeds = selectedBreeds,
            maxDistanceInKilometers = 5,
            onDistanceChange = {},
            dogBreeds = dogBreeds
        )
    }
}

@Composable
private fun CollapsedBottomSheetContent(
    modifier: Modifier,
    selectedBreeds: List<String>,
    removeBreed: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        val colors = MaterialTheme.colors
        BottomSheetDragHandler(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = if (colors.isLight) colors.onPrimary else colors.onSurface
        )
        if (selectedBreeds.isNotEmpty())
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(24.dp)
            ) {
                items(selectedBreeds) { breed ->
                    SelectedDogBreed(breedName = breed, removeBreed = { removeBreed(breed) })
                }
            }
    }
}

@Composable
private fun SelectedDogBreed(breedName: String, removeBreed: () -> Unit) {
    val colors = MaterialTheme.colors
    val backgroundColor = if (colors.isLight) colors.surface else colors.primary
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = 2.dp,
        backgroundColor = backgroundColor,
        contentColor = contentColorFor(backgroundColor),
        modifier = Modifier.height(48.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = breedName,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(end = 8.dp, start = 16.dp)
            )
            IconButton(onClick = removeBreed) {
                Icon(
                    imageVector = Icons.TwoTone.RemoveCircle,
                    contentDescription = "Remove breed"
                )
            }
        }
    }
}

@Composable
private fun BottomSheetDragHandler(modifier: Modifier, color: Color) {
    Box(
        modifier = modifier
            .padding(top = 12.dp, bottom = 8.dp)
            .size(width = 60.dp, height = 4.dp)
            .background(
                color = color.copy(alpha = ContentAlpha.medium),
                shape = CircleShape
            )
    )
}

@Composable
private fun ExpandedBottomSheetContent(
    modifier: Modifier,
    dogBreeds: List<String>,
    selectedBreeds: Set<String>,
    toggleBreed: (String) -> Unit,
    maxDistanceInKilometers: Int,
    onDistanceChange: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Spacer(Modifier.statusBarsHeight())
        MaxDistanceCounter(
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 8.dp),
            distance = maxDistanceInKilometers,
            onChange = { onDistanceChange(it) }
        )
        Divider()
        DogBreedsList(
            allBreeds = dogBreeds,
            selectedBreeds = selectedBreeds,
            onBreedClick = toggleBreed
        )
    }
}

@Composable
private fun MaxDistanceCounter(
    modifier: Modifier,
    distance: Int,
    onChange: (Int) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Max allowed distance",
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$distance kilometers",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onChange(distance - 5) }) {
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Increment distance"
                )
            }
            IconButton(onClick = { onChange(distance + 5) }) {
                Icon(
                    imageVector = Icons.TwoTone.Remove,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = "Decrease distance"
                )
            }
        }
    }
}

@Composable
private fun DogBreedsList(
    allBreeds: List<String>,
    selectedBreeds: Set<String>,
    onBreedClick: (String) -> Unit
) {
    LazyColumn {
        items(allBreeds) { breed ->
            DogBreedFilterRow(
                dogBreed = breed,
                checked = selectedBreeds.contains(breed),
                onClick = { onBreedClick(breed) }
            )
        }
        item { Spacer(Modifier.navigationBarsHeight(additional = 16.dp)) }
    }
}

@Composable
private fun DogBreedFilterRow(dogBreed: String, checked: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = MaterialTheme.colors.secondary),
                onClickLabel = "Dog Breed Filter Checkbox",
                onClick = onClick
            )
            .padding(horizontal = 24.dp)
    ) {
        Checkbox(checked = checked, onCheckedChange = null)
        Spacer(Modifier.width(20.dp))
        Text(text = dogBreed, style = MaterialTheme.typography.subtitle1)
    }
}
