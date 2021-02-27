package com.manueldidonna.puppyfinder.puppieslist

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.manueldidonna.puppyfinder.entities.Puppy
import com.manueldidonna.puppyfinder.floatingActionButtonDefaultElevation
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsHeight
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PuppiesList(puppies: List<Puppy>, dogBreeds: List<String>, onPuppyClick: (Puppy) -> Unit) {
    var selectedBreeds by remember { mutableStateOf(setOf<String>()) }

    val scaffoldState = rememberBottomSheetScaffoldState()

    var layoutHeight by remember { mutableStateOf(-1) }

    val sheetPeekHeightDp = systemNavigationBarHeightDp() + 120.dp

    val swipeProgress = scaffoldState.bottomSheetState.swipeProgressAsState(
        totalDragDistance = layoutHeight - with(LocalDensity.current) { sheetPeekHeightDp.toPx() }
    )

    BottomSheetScaffold(
        modifier = Modifier.onSizeChanged { layoutHeight = it.height },
        topBar = { PuppyFinderAppBar() },
        sheetContent = {
            FilterSheetContent(
                swipeProgress = swipeProgress,
                dogBreeds = dogBreeds,
                selectedBreeds = selectedBreeds,
                toggleBreed = { breed ->
                    selectedBreeds =
                        if (breed in selectedBreeds) selectedBreeds - breed
                        else selectedBreeds + breed
                }
            )
        },
        sheetPeekHeight = if (selectedBreeds.isNotEmpty()) sheetPeekHeightDp else 0.dp,
        sheetShape = sheetShape(),
        sheetBackgroundColor = sheetBackgroundColorAsState(swipeProgress).value,
        scaffoldState = scaffoldState,
        content = { paddingValues ->
            Box {
                val lazyListState = rememberLazyListState()
                val isScrollingForward by lazyListState.isScrollingForwardAsState()

                LazyColumn(contentPadding = paddingValues, state = lazyListState) {
                    item { Spacer(Modifier.height(16.dp)) }
                    items(puppies) { puppy ->
                        PuppyListEntry(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 8.dp),
                            puppyName = puppy.name,
                            puppyAge = puppy.age,
                            puppyPicture = puppy.picture,
                            onClick = { onPuppyClick(puppy) }
                        )
                    }
                    item { Spacer(Modifier.navigationBarsHeight(additional = 16.dp)) }
                }

                val scope = rememberCoroutineScope()

                FilterPuppiesButton(
                    onClick = { scope.launch { scaffoldState.bottomSheetState.expandSlowly() } },
                    visible = selectedBreeds.isEmpty() && !isScrollingForward,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding(),
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
private suspend fun BottomSheetState.expandSlowly() {
    animateTo(BottomSheetValue.Expanded, tween(durationMillis = 500))
}

@Composable
private fun systemNavigationBarHeightDp(): Dp {
    val density = LocalDensity.current
    val insets = LocalWindowInsets.current.navigationBars
    return remember(density, insets) { with(density) { insets.bottom.toDp() } }
}

@Composable
private fun sheetShape(): Shape {
    val shapes = MaterialTheme.shapes
    return remember {
        shapes.large.copy(bottomStart = ZeroCornerSize, bottomEnd = ZeroCornerSize)
    }
}

@Stable
@Composable
private fun sheetBackgroundColorAsState(swipeProgress: State<Float>): State<Color> {
    val colors = MaterialTheme.colors
    if (!colors.isLight) return remember { mutableStateOf(colors.surface) }
    val primary = colors.primary
    val surface = colors.surface
    val progress by swipeProgress
    return remember(swipeProgress) {
        derivedStateOf { lerp(start = primary, stop = surface, fraction = progress) }
    }
}

@Composable
@Stable
@OptIn(ExperimentalMaterialApi::class)
private fun BottomSheetState.swipeProgressAsState(totalDragDistance: Float): State<Float> {
    val bottomSheetOffset by this.offset
    return remember(this, totalDragDistance) {
        derivedStateOf {
            if (bottomSheetOffset.isNaN() || totalDragDistance <= 0) 0f
            else 1f - ((bottomSheetOffset) / totalDragDistance).coerceIn(0f, 1f)
        }
    }
}

@Composable
private fun PuppyFinderAppBar() {
    Surface(
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        TopAppBar(
            title = { Text("Puppy Finder") },
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.onSurface,
            elevation = 0.dp,
            modifier = Modifier.statusBarsPadding()
        )
    }
}

@Composable
private fun FilterPuppiesButton(modifier: Modifier, visible: Boolean, onClick: () -> Unit) {
    val transition = updateTransition(targetState = visible)

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 280, easing = FastOutSlowInEasing) },
        targetValueByState = { state -> if (state) 1f else 0f }
    )

    val translationY by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 280, easing = FastOutSlowInEasing) },
        targetValueByState = { state ->
            if (state) 0f else with(LocalDensity.current) { 40.dp.toPx() }
        }
    )

    Box(modifier = modifier.graphicsLayer {
        this.clip = true
        this.alpha = alpha
        this.translationY = translationY
    }) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp, vertical = 24.dp),
            text = { Text(text = "FILTER PUPPIES") },
            icon = { Icon(Icons.TwoTone.FilterAlt, contentDescription = "Filter icon") },
            elevation = floatingActionButtonDefaultElevation(),
            onClick = onClick
        )
    }
}

private class ScrollInfoHolder(var index: Int = 0, var scrollOffset: Int = 0)

@Composable
private fun LazyListState.isScrollingForwardAsState(): State<Boolean> {
    val holder = remember(this) { ScrollInfoHolder() }
    val scrollOffset by rememberUpdatedState(firstVisibleItemScrollOffset)
    val index by rememberUpdatedState(firstVisibleItemIndex)
    return remember(this) {
        derivedStateOf {
            val isForward =
                if (holder.index != index) holder.index < index
                else holder.scrollOffset < scrollOffset
            holder.scrollOffset = scrollOffset
            holder.index = index
            return@derivedStateOf isForward
        }
    }
}
