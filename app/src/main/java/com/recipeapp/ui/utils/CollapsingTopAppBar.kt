package recipeassignment.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CollapsingToolbar() {
    val scroll: ScrollState = rememberScrollState(0)

    Box(modifier = Modifier.fillMaxSize()) {
        Header()
        Body(scroll)
        Toolbar()
        Title()
    }
}
private val headerHeight = 275.dp
@Composable
private fun Header() {
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    Box(modifier = Modifier.fillMaxWidth().height(headerHeight)) {
        Image(
            imageVector = Icons.Default.Search,
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                        startY = 3 * headerHeightPx / 4 // to wrap the title only
                    )
                )
        )
    }
}

@Composable
private fun Body(scroll: ScrollState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scroll)) {
        Spacer(Modifier.height(headerHeight))

        repeat(100) {
            Text(
                text = "Rahul Jangra",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .background(Color(0XFF161616))
                    .padding(16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar() {
    TopAppBar(
        modifier = Modifier.background(
            brush = Brush.horizontalGradient(
                listOf(Color(0xff026586), Color(0xff032C45))
            )
        ),
        navigationIcon = {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        title = {},
    )
}


@Composable
private fun Title() {
    Text(
        text = "New York",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
}