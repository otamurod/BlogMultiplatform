package uz.otamurod.blogkmp.pages.admin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.file.loadDataUrlFromDisk
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.Resize
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.attrsModifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.disabled
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxHeight
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.outline
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.resize
import com.varabyte.kobweb.compose.ui.modifiers.scrollBehavior
import com.varabyte.kobweb.compose.ui.modifiers.visibility
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Switch
import com.varabyte.kobweb.silk.components.forms.SwitchSize
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.dom.Ul
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import uz.otamurod.blogkmp.components.AdminPageLayout
import uz.otamurod.blogkmp.models.Category
import uz.otamurod.blogkmp.models.EditorControl
import uz.otamurod.blogkmp.models.Post
import uz.otamurod.blogkmp.models.Theme
import uz.otamurod.blogkmp.styles.EditorControlStyle
import uz.otamurod.blogkmp.util.Constants.FONT_FAMILY
import uz.otamurod.blogkmp.util.Constants.SIDE_PANEL_WIDTH
import uz.otamurod.blogkmp.util.Id
import uz.otamurod.blogkmp.util.addPost
import uz.otamurod.blogkmp.util.isUserLoggedIn
import uz.otamurod.blogkmp.util.noBorder

data class CreatePageUiState(
    var id: String = "",
    var title: String = "",
    var subtitle: String = "",
    var thumbnailFilename: String = "",
    var isThumbnailInputDisabled: Boolean = true,
    var content: String = "",
    var selectedCategory: Category = Category.Programming,
    var buttonText: String = "Create",
    var popularSwitch: Boolean = false,
    var mainSwitch: Boolean = false,
    var sponsoredSwitch: Boolean = false,
    var editorVisibility: Boolean = true,
    var messagePopup: Boolean = false,
    var linkPopup: Boolean = false,
    var imagePopup: Boolean = false
) {
    fun reset() = this.copy(
        id = "",
        title = "",
        subtitle = "",
        thumbnailFilename = "",
        content = "",
        selectedCategory = Category.Programming,
        buttonText = "Create",
        mainSwitch = false,
        popularSwitch = false,
        sponsoredSwitch = false,
        editorVisibility = true,
        messagePopup = false,
        linkPopup = false,
        imagePopup = false
    )
}

@Page
@Composable
fun CreatePage() {
    isUserLoggedIn { CreateScreen() }
}

@Composable
fun CreateScreen() {
    val breakpoint = rememberBreakpoint()
    var uiEvent by remember { mutableStateOf(CreatePageUiState()) }
    val scope = rememberCoroutineScope()

    AdminPageLayout {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .margin(topBottom = 50.px)
                .padding(
                    left = if (breakpoint > Breakpoint.MD) {
                        SIDE_PANEL_WIDTH.px
                    } else {
                        0.px
                    }
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .maxWidth(700.px),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleGrid(
                    numColumns = numColumns(base = 1, sm = 3)
                ) {
                    Row(
                        modifier = Modifier
                            .margin(
                                right = if (breakpoint < Breakpoint.SM) 0.px else 24.px,
                                bottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = uiEvent.popularSwitch,
                            onCheckedChange = { uiEvent = uiEvent.copy(popularSwitch = it) },
                            size = SwitchSize.LG
                        )

                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Theme.HalfBlack.rgb),
                            text = "Popular"
                        )
                    }

                    Row(
                        modifier = Modifier
                            .margin(
                                right = if (breakpoint < Breakpoint.SM) 0.px else 24.px,
                                bottom = if (breakpoint < Breakpoint.SM) 12.px else 0.px
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = uiEvent.mainSwitch,
                            onCheckedChange = { uiEvent = uiEvent.copy(mainSwitch = it) },
                            size = SwitchSize.LG
                        )

                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Theme.HalfBlack.rgb),
                            text = "Main"
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            modifier = Modifier.margin(right = 8.px),
                            checked = uiEvent.sponsoredSwitch,
                            onCheckedChange = { uiEvent = uiEvent.copy(sponsoredSwitch = it) },
                            size = SwitchSize.LG
                        )

                        SpanText(
                            modifier = Modifier
                                .fontSize(14.px)
                                .fontFamily(FONT_FAMILY)
                                .color(Theme.HalfBlack.rgb),
                            text = "Sponsored"
                        )
                    }
                }

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(Id.titleInput)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(topBottom = 12.px)
                        .padding(leftRight = 20.px)
                        .backgroundColor(Theme.LightGray.rgb)
                        .borderRadius(r = 4.px)
                        .noBorder()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .toAttrs {
                            attr("placeholder", "Title")
                        }
                )

                Input(
                    type = InputType.Text,
                    attrs = Modifier
                        .id(Id.subtitleInput)
                        .fillMaxWidth()
                        .height(54.px)
                        .margin(bottom = 12.px)
                        .padding(leftRight = 20.px)
                        .backgroundColor(Theme.LightGray.rgb)
                        .borderRadius(r = 4.px)
                        .noBorder()
                        .fontFamily(FONT_FAMILY)
                        .fontSize(16.px)
                        .toAttrs {
                            attr("placeholder", "Subtitle")
                        }
                )

                CategoryDropDown(
                    selectedCategory = uiEvent.selectedCategory,
                    onCategorySelect = { uiEvent = uiEvent.copy(selectedCategory = it) }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .margin(topBottom = 12.px),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Switch(
                        modifier = Modifier.margin(right = 8.px),
                        checked = !uiEvent.isThumbnailInputDisabled,
                        onCheckedChange = {
                            uiEvent = uiEvent.copy(isThumbnailInputDisabled = !it)
                            if (it) {
                                uiEvent = uiEvent.copy(thumbnailFilename = "")
                            }
                        },
                        size = SwitchSize.MD
                    )

                    SpanText(
                        modifier = Modifier
                            .fontSize(14.px)
                            .fontFamily(FONT_FAMILY)
                            .color(Theme.HalfBlack.rgb),
                        text = "Paste an Image URL instead"
                    )
                }

                ThumbnailUploader(
                    thumbnail = uiEvent.thumbnailFilename,
                    isThumbnailInputDisabled = uiEvent.isThumbnailInputDisabled,
                    onThumbnailSelect = { filename, _ ->
                        (document.getElementById(Id.thumbnailInput) as HTMLInputElement).value =
                            filename

                        uiEvent = uiEvent.copy(thumbnailFilename = filename)
                    }
                )

                EditorControls(
                    breakpoint = breakpoint,
                    editorVisibility = uiEvent.editorVisibility,
                    onEditorVisibilityChange = {
                        uiEvent = uiEvent.copy(editorVisibility = !uiEvent.editorVisibility)
                    }
                )

                Editor(isVisible = uiEvent.editorVisibility)

                CreateButton(
                    onClick = {
                        uiEvent =
                            uiEvent.copy(title = (document.getElementById(Id.titleInput) as HTMLInputElement).value)
                        uiEvent =
                            uiEvent.copy(subtitle = (document.getElementById(Id.subtitleInput) as HTMLInputElement).value)
                        uiEvent =
                            uiEvent.copy(content = (document.getElementById(Id.editor) as HTMLTextAreaElement).value)

                        if (!uiEvent.isThumbnailInputDisabled) {
                            uiEvent =
                                uiEvent.copy(thumbnailFilename = (document.getElementById(Id.thumbnailInput) as HTMLInputElement).value)
                        }

                        if (
                            uiEvent.title.isNotEmpty() &&
                            uiEvent.subtitle.isNotEmpty() &&
                            uiEvent.thumbnailFilename.isNotEmpty() &&
                            uiEvent.content.isNotEmpty()
                        ) {
                            scope.launch {
                                val result = addPost(
                                    post = Post(
                                        author = localStorage.getItem("username").toString(),
                                        title = uiEvent.title,
                                        subtitle = uiEvent.subtitle,
                                        category = uiEvent.selectedCategory,
                                        main = uiEvent.mainSwitch,
                                        popular = uiEvent.popularSwitch,
                                        sponsored = uiEvent.sponsoredSwitch,
                                        thumbnail = uiEvent.thumbnailFilename,
                                        content = uiEvent.content
                                    )
                                )

                                if (result) {
                                    println("Successful!")
                                }
                            }
                        } else {
                            println("Please fill in all fields")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryDropDown(
    selectedCategory: Category,
    onCategorySelect: (Category) -> Unit
) {
    Box(
        modifier = Modifier
            .classNames("dropdown")
            .margin(topBottom = 12.px)
            .fillMaxWidth()
            .height(54.px)
            .backgroundColor(Theme.LightGray.rgb)
            .borderRadius(r = 4.px)
            .noBorder()
            .cursor(Cursor.Pointer)
            .attrsModifier {
                attr("data-bs-toggle", "dropdown")
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(leftRight = 20.px),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SpanText(
                modifier = Modifier
                    .fillMaxWidth()
                    .fontSize(16.px)
                    .fontFamily(FONT_FAMILY),
                text = selectedCategory.name
            )

            Box(
                modifier = Modifier.classNames("dropdown-toggle")
            )
        }

        Ul(
            attrs = Modifier
                .fillMaxWidth()
                .classNames("dropdown-menu")
                .toAttrs()
        ) {
            Category.entries.forEach { category ->
                Li {
                    A(
                        attrs = Modifier
                            .classNames("dropdown-item")
                            .fontSize(16.px)
                            .fontFamily(FONT_FAMILY)
                            .color(Theme.Black.rgb)
                            .onClick {
                                onCategorySelect(category)
                            }
                            .toAttrs()
                    ) {
                        Text(value = category.name)
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbnailUploader(
    thumbnail: String,
    isThumbnailInputDisabled: Boolean,
    onThumbnailSelect: (String, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .margin(bottom = 20.px)
            .height(54.px),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Input(
            type = InputType.Text,
            attrs = Modifier
                .id(Id.thumbnailInput)
                .fillMaxWidth()
                .height(54.px)
                .margin(right = 12.px)
                .padding(leftRight = 20.px)
                .backgroundColor(Theme.LightGray.rgb)
                .borderRadius(r = 4.px)
                .noBorder()
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .thenIf(
                    condition = isThumbnailInputDisabled,
                    other = Modifier.disabled()
                )
                .toAttrs {
                    attr("placeholder", "Thumbnail")
                    attr("value", thumbnail)
                }
        )

        Button(
            attrs = Modifier
                .onClick {
                    document.loadDataUrlFromDisk(
                        accept = "image/png, image/jpeg",
                        onLoad = {
                            onThumbnailSelect(filename, it)
                        }
                    )
                }
                .fillMaxHeight()
                .padding(leftRight = 24.px)
                .backgroundColor(
                    if (!isThumbnailInputDisabled) {
                        Theme.Gray.rgb
                    } else {
                        Theme.Primary.rgb
                    }
                )
                .color(
                    if (!isThumbnailInputDisabled) {
                        Theme.DarkGray.rgb
                    } else {
                        Theme.White.rgb
                    }
                )
                .borderRadius(r = 4.px)
                .noBorder()
                .fontFamily(FONT_FAMILY)
                .fontSize(14.px)
                .fontWeight(FontWeight.Medium)
                .thenIf(
                    condition = !isThumbnailInputDisabled,
                    other = Modifier.disabled()
                )
                .toAttrs()
        ) {
            SpanText(text = "Upload")
        }
    }
}

@Composable
fun EditorControls(
    breakpoint: Breakpoint,
    editorVisibility: Boolean,
    onEditorVisibilityChange: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        SimpleGrid(
            modifier = Modifier.fillMaxWidth(),
            numColumns = numColumns(base = 1, sm = 2)
        ) {
            Row(
                modifier = Modifier
                    .height(54.px)
                    .backgroundColor(Theme.LightGray.rgb)
                    .borderRadius(r = 4.px)
            ) {
                EditorControl.entries.forEach {
                    EditorControlView(editorControl = it)
                }
            }

            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    attrs = Modifier
                        .height(54.px)
                        .margin(
                            topBottom = if (breakpoint < Breakpoint.SM) {
                                12.px
                            } else {
                                0.px
                            }
                        )
                        .padding(leftRight = 24.px)
                        .backgroundColor(if (editorVisibility) Theme.LightGray.rgb else Theme.Primary.rgb)
                        .color(if (editorVisibility) Theme.DarkGray.rgb else Colors.White)
                        .borderRadius(r = 4.px)
                        .noBorder()
                        .thenIf(
                            condition = breakpoint < Breakpoint.SM,
                            other = Modifier.fillMaxWidth()
                        )
                        .onClick {
                            onEditorVisibilityChange()
                        }
                        .toAttrs()
                ) {
                    SpanText(
                        modifier = Modifier
                            .fontFamily(FONT_FAMILY)
                            .fontWeight(FontWeight.Medium)
                            .fontSize(14.px),
                        text = "Preview"
                    )
                }
            }
        }
    }
}

@Composable
fun EditorControlView(editorControl: EditorControl) {
    Box(
        modifier = EditorControlStyle.toModifier()
            .fillMaxHeight()
            .padding(leftRight = 12.px)
            .borderRadius(r = 4.px)
            .cursor(Cursor.Pointer)
            .onClick { },
        contentAlignment = Alignment.Center
    ) {
        Image(
            src = editorControl.icon,
            description = "${editorControl.name} Icon"
        )
    }
}

@Composable
fun Editor(isVisible: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextArea(
            attrs = Modifier
                .id(Id.editor)
                .visibility(if (isVisible) Visibility.Visible else Visibility.Hidden)
                .fillMaxWidth()
                .height(400.px)
                .maxHeight(400.px)
                .resize(Resize.None)
                .margin(top = 8.px)
                .padding(all = 20.px)
                .backgroundColor(Theme.LightGray.rgb)
                .borderRadius(r = 4.px)
                .noBorder()
                .fontFamily(FONT_FAMILY)
                .fontSize(16.px)
                .toAttrs {
                    attr("placeholder", "Write your article here... ")
                }
        )

        Div(
            attrs = Modifier
                .id(Id.editorPreview)
                .fillMaxWidth()
                .height(400.px)
                .maxHeight(400.px)
                .margin(top = 8.px)
                .padding(all = 20.px)
                .backgroundColor(Theme.LightGray.rgb)
                .borderRadius(r = 4.px)
                .border(
                    width = 0.px,
                    style = LineStyle.None,
                    color = Colors.Transparent
                )
                .outline(
                    width = 0.px,
                    style = LineStyle.None,
                    color = Colors.Transparent
                )
                .visibility(if (isVisible) Visibility.Hidden else Visibility.Visible)
                .overflow(Overflow.Auto)
                .scrollBehavior(ScrollBehavior.Smooth)
                .toAttrs()
        )
    }
}

@Composable
fun CreateButton(
    onClick: () -> Unit
) {
    Button(
        attrs = Modifier
            .onClick {
                onClick()
            }
            .fillMaxWidth()
            .height(54.px)
            .margin(top = 24.px)
            .backgroundColor(Theme.Primary.rgb)
            .color(Colors.White)
            .borderRadius(r = 4.px)
            .noBorder()
            .fontFamily(FONT_FAMILY)
            .fontSize(16.px)
            .toAttrs()
    ) {
        SpanText(text = "Create")
    }
}