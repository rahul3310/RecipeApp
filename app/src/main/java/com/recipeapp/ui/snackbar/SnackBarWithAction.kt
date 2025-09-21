package com.recipeapp.ui.snackbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.recipeapp.ui.theme.ButtonColorSecondary
import com.recipeapp.ui.theme.TextColorPrimary
import com.recipeapp.ui_component.TextMedium

@Composable
fun SnackBarWithActon(
    snackbarData: SnackbarData,
    dismissIcon: ImageVector? = null,
    onDismiss: () -> Unit
) {

    Snackbar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = TextColorPrimary,
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextMedium(
                text = snackbarData.visuals.message,
                color = Color.White
            )
            Row(
                modifier = Modifier.clickable {
                    onDismiss()
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextMedium(
                    text = snackbarData.visuals.actionLabel ?: "",
                    color = ButtonColorSecondary
                )
                IconButton(
                    onClick = {
                        onDismiss()
                    }) {
                    if (dismissIcon != null && snackbarData.visuals.withDismissAction) {
                        Icon(
                            imageVector = dismissIcon,
                            contentDescription = "add",
                            tint = ButtonColorSecondary
                        )
                    }
                }
            }

        }

    }
}