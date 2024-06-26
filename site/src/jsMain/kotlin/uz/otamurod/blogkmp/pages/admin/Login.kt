package uz.otamurod.blogkmp.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Input
import uz.otamurod.blogkmp.models.Theme
import uz.otamurod.blogkmp.styles.LoginInputStyle
import uz.otamurod.blogkmp.util.Constants.FONT_FAMILY
import uz.otamurod.blogkmp.util.Res

@Page
@Composable
fun LoginScreen() {
    var errorMessage by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(
                leftRight = 50.px,
                top = 80.px,
                bottom = 24.px
            ).backgroundColor(Theme.LightGray.rgb),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.margin(bottom = 50.px)
                    .width(100.px),
                src = Res.Image.logo,
                description = "Logo Image"
            )

            Input(
                type = InputType.Text,
                attrs = LoginInputStyle.toModifier()
                    .margin(bottom = 12.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .borderRadius(r = 4.px)
                    .outline(
                        width = 0.px,
                        color = Colors.Transparent,
                        style = LineStyle.None
                    )
                    .toAttrs {
                        attr("placeholder", "Username")
                    }
            )

            Input(
                type = InputType.Password,
                attrs = LoginInputStyle.toModifier()
                    .margin(bottom = 20.px)
                    .width(350.px)
                    .height(54.px)
                    .padding(leftRight = 20.px)
                    .backgroundColor(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .borderRadius(r = 4.px)
                    .outline(
                        width = 0.px,
                        color = Colors.Transparent,
                        style = LineStyle.None
                    )
                    .toAttrs {
                        attr("placeholder", "Password")
                    }
            )

            Button(
                attrs = Modifier
                    .width(350.px)
                    .height(54.px)
                    .margin(bottom = 24.px)
                    .backgroundColor(Theme.Primary.rgb)
                    .color(Colors.White)
                    .fontFamily(FONT_FAMILY)
                    .fontSize(14.px)
                    .fontWeight(FontWeight.Medium)
                    .borderRadius(r = 4.px)
                    .border(
                        width = 0.px,
                        color = Colors.Transparent,
                        style = LineStyle.None
                    )
                    .outline(
                        width = 0.px,
                        color = Colors.Transparent,
                        style = LineStyle.None
                    )
                    .toAttrs()
            ) {
                SpanText(text = "Sign In")
            }

            SpanText(
                modifier = Modifier
                    .width(350.px)
                    .color(Colors.Red)
                    .textAlign(TextAlign.Center),
                text = errorMessage
            )
        }
    }
}