package uz.otamurod.blogkmp.styles

import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.focus
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px
import uz.otamurod.blogkmp.models.Theme

val LoginInputStyle by ComponentStyle {
    base {
        Modifier
            .border(
                width = 2.px,
                color = Colors.Transparent,
                style = LineStyle.Solid
            )
            .transition(CSSTransition(property = "border", duration = 300.ms))
    }

    focus {
        Modifier
            .border(
                width = 2.px,
                color = Theme.Primary.rgb,
                style = LineStyle.Solid
            )
    }
}