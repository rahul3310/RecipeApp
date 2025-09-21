package recipeassignment.ui.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.recipeapp.ui.theme.BackgroundColorPrimary

@Composable
fun CustomImageButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    tint: Color = Color.Unspecified,
    border : BorderStroke? = null,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable {
            onClick()
        },
        color = BackgroundColorPrimary,
        shape = RoundedCornerShape(8.dp),
        border = border
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "button",
            tint = tint,
            modifier = Modifier.padding(8.dp)
        )
    }
}