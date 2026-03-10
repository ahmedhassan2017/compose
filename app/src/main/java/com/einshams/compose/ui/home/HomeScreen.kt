package com.einshams.compose.ui.home

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.einshams.domain.model.HomeItem

private val BackgroundColor = Color(0xFF0E1117)
private val CardColor       = Color(0xFF161B25)
private val TitleColor      = Color(0xFFE8EAF0)
private val SubtitleColor   = Color(0xFF7D8599)
private val AccentBlue      = Color(0xFF5B77FF)
private val ShimmerBase     = Color(0xFF1E2433)
private val ShimmerHighlight = Color(0xFF2A3347)

// ─── Public entry point ────────────────────────────────────────────────────

@Composable
fun HomeScreen(
    isLoading: Boolean,
    items: List<HomeItem>,
    errorMessage: String?,
    onItemClick: (String) -> Unit,
    onRetry: () -> Unit,
) {
    val showInitialLoading = isLoading && items.isEmpty()
    val showInitialError = errorMessage != null && items.isEmpty()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage, items.size) {
        if (!errorMessage.isNullOrBlank() && items.isNotEmpty()) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .statusBarsPadding(),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeTopBar()
            when {
                showInitialLoading -> HomeLoadingState()
                showInitialError -> HomeErrorState(message = errorMessage.orEmpty(), onRetry = onRetry)
                else -> RefreshableHomeList(
                    items = items,
                    isRefreshing = isLoading,
                    onItemClick = onItemClick,
                    onRefresh = onRetry
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )
    }
}

// ─── Top bar ───────────────────────────────────────────────────────────────

@Composable
private fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = TitleColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Text(
            text = "Home",
            color = TitleColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = TitleColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Box(
            modifier = Modifier
                .padding(end = 4.dp)
                .size(34.dp)
                .clip(CircleShape)
                .background(ShimmerBase)
        ) {
            AsyncImage(
                model = "https://picsum.photos/seed/avatar/200",
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// ─── List ──────────────────────────────────────────────────────────────────

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun RefreshableHomeList(
    items: List<HomeItem>,
    isRefreshing: Boolean,
    onItemClick: (String) -> Unit,
    onRefresh: () -> Unit,
) {
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        modifier = Modifier.fillMaxSize()
    ) {
        HomeList(
            items = items,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun HomeList(
    items: List<HomeItem>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items, key = { it.id }) { item ->
            HomeListItem(item = item, onClick = { onItemClick(item.id) })
        }
    }
}

@Composable
private fun HomeListItem(
    item: HomeItem,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(ShimmerBase)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                color = TitleColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = item.subtitle,
                color = SubtitleColor,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
    }
}

// ─── Loading (shimmer skeleton) ────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
private fun HomeLoadingState() {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue  = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    val shimmerBrush = Brush.linearGradient(
        colors = listOf(ShimmerBase, ShimmerHighlight, ShimmerBase),
        start  = Offset(translateAnim.value - 300f, 0f),
        end    = Offset(translateAnim.value,        0f)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(8) {
            ShimmerRow(brush = shimmerBrush)
        }
    }
}

@Composable
private fun ShimmerRow(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardColor)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(brush)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .height(14.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.38f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(brush)
            )
        }
    }
}

// ─── Error ─────────────────────────────────────────────────────────────────

@Composable
private fun HomeErrorState(
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            tint = SubtitleColor,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = message,
            color = SubtitleColor,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
        ) {
            Text(text = "Retry", color = Color.White)
        }
    }
}
