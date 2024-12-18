package uz.otamurod.blogkmp.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import org.jetbrains.compose.web.css.px
import uz.otamurod.blogkmp.util.Constants.PAGE_WIDTH


@Composable
fun AdminPageLayout(
    content: @Composable () -> Unit
) {
    var isOverflowSidePanelOpened by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .maxWidth(PAGE_WIDTH.px )
        ) {
            SidePanel(onMenuClick = {
                isOverflowSidePanelOpened = true
            })

            if (isOverflowSidePanelOpened){
                OverflowSidePanel(
                    onMenuClose = {
                        isOverflowSidePanelOpened = false
                    },
                    content = {})
            }

            content()
        }
    }
}