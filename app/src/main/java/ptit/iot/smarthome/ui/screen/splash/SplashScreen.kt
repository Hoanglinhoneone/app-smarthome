package ptit.iot.smarthome.ui.screen.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compose.AppTheme
import ptit.iot.smarthome.R

@Composable
fun SplashScreen(navController: NavController) {
    // Hiệu ứng fade-in
    val alpha = remember { Animatable(0f) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // Hiệu ứng mờ dần
        alpha.animateTo(1f, animationSpec = tween(durationMillis = 1000))
//        delay(2000) // Chờ 2 giây
//        navController.navigate("home") {
//            popUpTo("splash") { inclusive = true } // Xóa màn Splash khỏi stack
//        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.logo_ptit),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer(alpha = alpha.value)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.app_name_real),
                style = MaterialTheme.typography.titleLarge,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.DarkGray,
                modifier = Modifier.graphicsLayer(alpha = alpha.value)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    AppTheme {
        SplashScreen(navController = NavController(LocalContext.current))
    }
}