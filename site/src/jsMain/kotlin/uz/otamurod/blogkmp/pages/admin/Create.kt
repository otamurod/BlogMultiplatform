package uz.otamurod.blogkmp.pages.admin

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.dom.Text
import uz.otamurod.blogkmp.components.AdminPageLayout
import uz.otamurod.blogkmp.util.isUserLoggedIn

@Page
@Composable
fun CreatePage() {
    isUserLoggedIn { CreateScreen() }
}

@Composable
fun CreateScreen() {
    AdminPageLayout {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(value = "Create")
        }
    }
}