package com.recipeapp.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.recipeapp.ui.theme.BackgroundColorPrimary
import com.recipeapp.ui.theme.ButtonColorPrimary
import com.recipeapp.ui_component.TextHeadline
import com.recipeapp.ui_component.TextMedium
import com.recipeapp.ui_component.TextSmall
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    buttonText: String? = null,
    content: @Composable (() -> Unit),
    onButtonClick: () -> Unit = {},
    onIconClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    icon: ImageVector = Icons.Default.Close,
    iconArrangement: Arrangement.Horizontal = Arrangement.Start
) {

    val coroutineScope = rememberCoroutineScope()
    val closeSheet: () -> Unit = {
        coroutineScope.launch {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismissRequest,
        containerColor = Color.Transparent,
        sheetState = sheetState,
        shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp),
        scrimColor = Color(0xD9000000),
        dragHandle = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = iconArrangement
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = "backArrow",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .background(Color.Black, CircleShape)
                        .padding(8.dp)
                        .clickable {
                            closeSheet()
                            onIconClick()
                        },
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .background(BackgroundColorPrimary, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
        ) {

            content()

            if (buttonText != null) {
                ButtonWithIcon(modifier = Modifier,
                    buttonText,
                    onButtonClick = {
                    closeSheet()
                    onButtonClick()
                })
            }
        }


    }


}


@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    buttonText: String,
    onButtonClick: () -> Unit,

    ) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColorPrimary
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onButtonClick()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextMedium(
                text = buttonText,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            Image(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "arrow",
                colorFilter = ColorFilter.tint(BackgroundColorPrimary),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

