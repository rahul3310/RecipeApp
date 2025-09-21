import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun HtmlText(
    summary: String,
    textColor: Color = Color.White // default is white, but you can pass custom
) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            text = HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_LEGACY)
            movementMethod = LinkMovementMethod.getInstance()
            textSize =14f
            setTextColor(android.graphics.Color.parseColor("#%06X".format(0xFFFFFF and textColor.value.toInt())))
        }
    })
}
