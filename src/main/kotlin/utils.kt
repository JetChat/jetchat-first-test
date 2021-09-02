import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import java.net.HttpURLConnection
import java.net.URL

fun loadNetworkImage(link: String): ImageBitmap {
	val url = URL(link)
	val connection = url.openConnection() as HttpURLConnection
	connection.connect()
	
	val inputStream = connection.inputStream
	return loadImageBitmap(inputStream)
}
