package io.writeopia.editor.configuration.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.writeopia.appresourcers.R

@Preview
@Composable
internal fun NoteGlobalActionsMenu(onShareJson: () -> Unit = {}, onShareMd: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.export),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            ShareButton(text = stringResource(id = R.string.export_json), onClick = onShareJson)

            Spacer(modifier = Modifier.width(8.dp))

            ShareButton(text = stringResource(id = R.string.export_md), onClick = onShareMd)
        }
    }
}

@Composable
private fun ShareButton(text: String, onClick: () -> Unit) {
    Text(
        modifier = Modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(8.dp),
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold
        )
    )
}
