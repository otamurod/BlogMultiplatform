package uz.otamurod.blogkmp.pages.admin

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.dom.Text
import uz.otamurod.blogkmp.util.isUserLoggedIn

@Page
@Composable
fun HomePage() {
    isUserLoggedIn { HomeScreen() }
}

@Composable
fun HomeScreen() {
    Column {
        Text("Admin Home Page")
    }
}