package com.recipeapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.recipeapp.R
import com.recipeapp.navigation.Screen
import com.recipeapp.ui.theme.BackgroundColorPrimary
import com.recipeapp.ui.theme.BackgroundColorSecondary
import com.recipeapp.ui.theme.ButtonColorPrimary
import com.recipeapp.ui_component.TextMedium


@Composable
fun LoginScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier
    ) {
        Image(
            painter = painterResource(R.drawable.ic_login_bg),
            contentScale = ContentScale.Crop,
            contentDescription = "image",
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.align(Alignment.BottomStart)
        ){
            StyledWelcomeText()
            LoginButton(
                modifier = Modifier
            ) {
                navController.navigate(Screen.Main.route){
                    popUpTo(Screen.Login.route){
                       inclusive = true
                    }
                }
            }
        }


    }
}

@Composable
fun StyledWelcomeText() {
    Text(

        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White, fontSize = 48.sp)) {
                append("Welcome to ")
            }
            withStyle(
                style = SpanStyle(
                    color = Color.White,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic
                )
            ) {
                append("\n\n\nReciipiie")
            }
            withStyle(
                style = SpanStyle(
                    color = BackgroundColorSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                )
            ) {
                append("\n\nPlease signing to continue")
            }
        },
        modifier = Modifier.padding(start = 16.dp)
    )
}


@Composable
private fun LoginButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier

            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColorPrimary
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = "arrow",
                colorFilter = ColorFilter.tint(BackgroundColorPrimary),
                modifier = Modifier.padding(end = 8.dp)
            )
            TextMedium(
                text = "Continue with Google",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )


        }
    }
}