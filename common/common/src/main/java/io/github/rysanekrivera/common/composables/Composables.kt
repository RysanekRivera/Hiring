package io.github.rysanekrivera.common.composables

import androidx.annotation.StringRes
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun TitleText(
    text: String,
    bold: Boolean = false,
    underline: Boolean = false,
    strikeThrough: Boolean = false,
    ellipsize: Boolean = false,
    fontFamily: FontFamily? = null,
    color: Color = MaterialTheme.typography.titleMedium.color,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {

    Text(
        text = text,
        color = color,
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
        fontWeight = if (bold) FontWeight.Bold else null,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = when {
            underline && strikeThrough -> TextDecoration.Underline + TextDecoration.LineThrough
            underline -> TextDecoration.Underline
            strikeThrough -> TextDecoration.LineThrough
            else -> null
        } ,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = if (ellipsize) TextOverflow.Ellipsis else TextOverflow.Clip,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )

}

@Composable
fun TitleText(
    @StringRes text: Int,
    bold: Boolean = false,
    underline: Boolean = false,
    strikeThrough: Boolean = false,
    ellipsize: Boolean = false,
    fontFamily: FontFamily? = null,
    color: Color = MaterialTheme.typography.titleMedium.color,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {

    TitleText(
        text = stringResource(text),
        color = color,
        bold = bold,
        underline = underline,
        strikeThrough = strikeThrough,
        ellipsize = ellipsize,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textAlign = textAlign,
        lineHeight = lineHeight,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

@Composable
fun BodyText(
    text: String,
    bold: Boolean = false,
    underline: Boolean = false,
    strikeThrough: Boolean = false,
    ellipsize: Boolean = false,
    fontFamily: FontFamily? = null,
    color: Color = MaterialTheme.typography.bodyMedium.color,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {

    Text(
        text = text,
        color = color,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        fontWeight = if (bold) FontWeight.Bold else null,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = when {
            underline && strikeThrough -> TextDecoration.Underline + TextDecoration.LineThrough
            underline -> TextDecoration.Underline
            strikeThrough -> TextDecoration.LineThrough
            else -> null
        } ,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = if (ellipsize) TextOverflow.Ellipsis else TextOverflow.Clip,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )

}

@Composable
fun BodyText(
    @StringRes text: Int,
    bold: Boolean = false,
    underline: Boolean = false,
    strikeThrough: Boolean = false,
    ellipsize: Boolean = false,
    fontFamily: FontFamily? = null,
    color: Color = MaterialTheme.typography.bodyMedium.color,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {

    BodyText(
        text = stringResource(text),
        color = color,
        bold = bold,
        underline = underline,
        strikeThrough = strikeThrough,
        ellipsize = ellipsize,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textAlign = textAlign,
        lineHeight = lineHeight,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

@Composable
fun SmallText(
    text: String,
    bold: Boolean = false,
    underline: Boolean = false,
    strikeThrough: Boolean = false,
    ellipsize: Boolean = false,
    fontFamily: FontFamily? = null,
    color: Color = MaterialTheme.typography.bodySmall.color,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {

    Text(
        text = text,
        color = color,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontWeight = if (bold) FontWeight.Bold else null,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = when {
            underline && strikeThrough -> TextDecoration.Underline + TextDecoration.LineThrough
            underline -> TextDecoration.Underline
            strikeThrough -> TextDecoration.LineThrough
            else -> null
        } ,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = if (ellipsize) TextOverflow.Ellipsis else TextOverflow.Clip,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )

}

@Composable
fun SmallText(
    @StringRes text: Int,
    bold: Boolean = false,
    underline: Boolean = false,
    strikeThrough: Boolean = false,
    ellipsize: Boolean = false,
    fontFamily: FontFamily? = null,
    color: Color = MaterialTheme.typography.bodySmall.color,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null,
    style: TextStyle = LocalTextStyle.current
) {

    SmallText(
        text = stringResource(text),
        color = color,
        bold = bold,
        underline = underline,
        strikeThrough = strikeThrough,
        ellipsize = ellipsize,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textAlign = textAlign,
        lineHeight = lineHeight,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style
    )
}


