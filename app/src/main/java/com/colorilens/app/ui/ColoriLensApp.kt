package com.colorilens.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.colorilens.app.R
import com.colorilens.app.ui.theme.ColoriLensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColoriLensApp() {
    var selectedDestination by rememberSaveable { mutableStateOf(ColoriLensDestination.Today) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(selectedDestination.titleRes)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                ),
            )
        },
        bottomBar = {
            ColoriLensNavigationBar(
                selectedDestination = selectedDestination,
                onDestinationSelected = { selectedDestination = it },
            )
        },
        floatingActionButton = {
            if (selectedDestination == ColoriLensDestination.Today) {
                FloatingActionButton(onClick = { /* Camera flow will be added in the AI photo milestone. */ }) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = stringResource(R.string.camera_action),
                    )
                }
            }
        },
    ) { innerPadding ->
        ColoriLensScreen(
            destination = selectedDestination,
            contentPadding = innerPadding,
        )
    }
}

@Composable
private fun ColoriLensNavigationBar(
    selectedDestination: ColoriLensDestination,
    onDestinationSelected: (ColoriLensDestination) -> Unit,
) {
    NavigationBar {
        ColoriLensDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = destination == selectedDestination,
                onClick = { onDestinationSelected(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.titleRes),
                    )
                },
                label = { Text(text = stringResource(destination.titleRes)) },
            )
        }
    }
}

@Composable
private fun ColoriLensScreen(
    destination: ColoriLensDestination,
    contentPadding: PaddingValues,
) {
    val copy = when (destination) {
        ColoriLensDestination.Today -> ScreenCopy(
            headlineRes = R.string.today_headline,
            bodyRes = R.string.today_body,
            metricRes = R.string.calories_remaining_label,
        )
        ColoriLensDestination.History -> ScreenCopy(
            headlineRes = R.string.history_headline,
            bodyRes = R.string.history_body,
        )
        ColoriLensDestination.Stats -> ScreenCopy(
            headlineRes = R.string.stats_headline,
            bodyRes = R.string.stats_body,
        )
        ColoriLensDestination.Activity -> ScreenCopy(
            headlineRes = R.string.activity_headline,
            bodyRes = R.string.activity_body,
        )
        ColoriLensDestination.Profile -> ScreenCopy(
            headlineRes = R.string.profile_headline,
            bodyRes = R.string.profile_body,
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (destination == ColoriLensDestination.Profile) {
                ProfileSetupCard()
            } else {
                PlaceholderCard(copy = copy)
            }
        }
    }
}

@Composable
private fun PlaceholderCard(copy: ScreenCopy) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            copy.metricRes?.let { metricRes ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = stringResource(R.string.placeholder_kcal),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = stringResource(metricRes),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Outlined.PhotoCamera,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(copy.headlineRes),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = stringResource(copy.bodyRes),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

private data class ScreenCopy(
    val headlineRes: Int,
    val bodyRes: Int,
    val metricRes: Int? = null,
)

@Preview(showBackground = true)
@Composable
private fun ColoriLensAppPreview() {
    ColoriLensTheme {
        ColoriLensApp()
    }
}
