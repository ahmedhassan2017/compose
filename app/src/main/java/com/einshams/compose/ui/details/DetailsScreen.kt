package com.einshams.compose.ui.details

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.einshams.domain.model.ProductDetail
import com.einshams.domain.model.Review

private val BgColor        = Color(0xFF0E1117)
private val CardColor      = Color(0xFF161B25)
private val TitleColor     = Color(0xFFE8EAF0)
private val SubtitleColor  = Color(0xFF7D8599)
private val AccentBlue     = Color(0xFF5B77FF)
private val ActiveGreen    = Color(0xFF4CAF50)
private val ChipColor      = Color(0xFF1E2433)
private val ShimmerBase    = Color(0xFF1E2433)
private val ShimmerHigh    = Color(0xFF2A3347)
private val ErrorCardColor = Color(0xFF1A1F2E)

// ─── Entry point ───────────────────────────────────────────────────────────

@Composable
fun DetailsScreen(
    isLoading: Boolean,
    product: ProductDetail?,
    errorMessage: String?,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .statusBarsPadding()
    ) {
        when {
            isLoading    -> DetailsLoadingState(onBack = onBack)
            product != null -> DetailsContent(product = product, onBack = onBack)
            else         -> DetailsFullErrorState(
                message = errorMessage.orEmpty(),
                onBack = onBack,
                onRetry = onRetry
            )
        }
    }
}

// ─── Top bar ───────────────────────────────────────────────────────────────

@Composable
private fun DetailsTopBar(
    onBack: () -> Unit,
    avatarUrl: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = TitleColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

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
                model = avatarUrl ?: "https://picsum.photos/seed/avatar/200",
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// ─── Loaded content ────────────────────────────────────────────────────────

@Composable
private fun DetailsContent(
    product: ProductDetail,
    onBack: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DetailsTopBar(onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeroSection(product = product)

            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleSection(product = product)
                ProductDetailSection(product = product)
                ReviewersSection(reviews = product.reviews)
            }
        }
    }
}

// ─── Hero section ──────────────────────────────────────────────────────────

@Composable
private fun HeroSection(product: ProductDetail) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp))
                .background(ShimmerBase)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, BgColor.copy(alpha = 0.6f)),
                        startY = 100f
                    )
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(2.dp, BgColor, RoundedCornerShape(12.dp))
                    .background(ShimmerBase)
            ) {
                AsyncImage(
                    model = product.thumbnailUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = "#${product.brand} ${product.title}",
                color = TitleColor.copy(alpha = 0.9f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ─── Title section ─────────────────────────────────────────────────────────

@Composable
private fun TitleSection(product: ProductDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = product.title,
            color = TitleColor,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "#${product.category.uppercase()} community",
                color = SubtitleColor,
                fontSize = 13.sp
            )

            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(ActiveGreen)
            )

            Text(
                text = "Active now",
                color = ActiveGreen,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ─── Product detail section ────────────────────────────────────────────────

@Composable
private fun ProductDetailSection(product: ProductDetail) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "Product Details: ${product.title}",
            color = TitleColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        TagChip(label = "#${product.brand}")

        Text(
            text = product.description,
            color = SubtitleColor,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )

        TagChip(label = "Active community project")
    }
}

@Composable
private fun TagChip(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(ChipColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            color = SubtitleColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// ─── Reviewers section ─────────────────────────────────────────────────────

@Composable
private fun ReviewersSection(reviews: List<Review>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Project Contributors & Reviews",
            color = TitleColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        if (reviews.isEmpty()) {
            Text(
                text = "No reviews yet",
                color = SubtitleColor,
                fontSize = 13.sp
            )
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy((-10).dp)) {
                reviews.take(5).forEachIndexed { index, review ->
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .offset(x = (index * (-4)).dp)
                            .clip(CircleShape)
                            .border(2.dp, BgColor, CircleShape)
                            .background(ShimmerBase)
                    ) {
                        AsyncImage(
                            model = review.reviewerAvatarUrl,
                            contentDescription = review.reviewerName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

// ─── Loading (skeleton) ────────────────────────────────────────────────────

@Composable
private fun DetailsLoadingState(onBack: () -> Unit) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )
    val brush = Brush.linearGradient(
        colors = listOf(ShimmerBase, ShimmerHigh, ShimmerBase),
        start = Offset(translateAnim.value - 300f, 0f),
        end = Offset(translateAnim.value, 0f)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        DetailsTopBar(onBack = onBack)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(brush)
        )

        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ShimmerBox(width = 0.7f, height = 28.dp, brush = brush)
            ShimmerBox(width = 0.5f, height = 16.dp, brush = brush)
            Spacer(modifier = Modifier.height(4.dp))
            ShimmerBox(width = 0.9f, height = 16.dp, brush = brush)
            ShimmerBox(width = 0.8f, height = 16.dp, brush = brush)
            ShimmerBox(width = 0.6f, height = 16.dp, brush = brush)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy((-10).dp)) {
                repeat(4) { i ->
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .offset(x = (i * (-4)).dp)
                            .clip(CircleShape)
                            .background(brush)
                    )
                }
            }
        }
    }
}

@Composable
private fun ShimmerBox(width: Float, height: androidx.compose.ui.unit.Dp, brush: Brush) {
    Box(
        modifier = Modifier
            .fillMaxWidth(width)
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .background(brush)
    )
}

// ─── Full-screen error ─────────────────────────────────────────────────────

@Composable
private fun DetailsFullErrorState(
    message: String,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DetailsTopBar(onBack = onBack)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ErrorCardColor)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.WifiOff,
                    contentDescription = null,
                    tint = SubtitleColor,
                    modifier = Modifier.size(40.dp)
                )

                Text(
                    text = message,
                    color = SubtitleColor,
                    fontSize = 14.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Button(
                    onClick = onRetry,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
                ) {
                    Text(text = "Retry", color = Color.White)
                }
            }
        }
    }
}
