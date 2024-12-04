package uz.otamurod.blogkmp.styles

import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.hover
import org.jetbrains.compose.web.css.ms
import uz.otamurod.blogkmp.models.Theme

val EditorControlStyle by ComponentStyle {
    base {
        Modifier.backgroundColor(Colors.Transparent)
            .transition(CSSTransition(property = "background", duration = 300.ms))
    }

    hover {
        Modifier.backgroundColor(Theme.Primary.rgb)
    }
}