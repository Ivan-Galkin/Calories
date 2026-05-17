package com.colorilens.app.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Today
import androidx.compose.ui.graphics.vector.ImageVector
import com.colorilens.app.R

enum class ColoriLensDestination(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
) {
    Today(R.string.today_title, Icons.Outlined.Today),
    History(R.string.history_title, Icons.AutoMirrored.Outlined.List),
    Stats(R.string.stats_title, Icons.Outlined.BarChart),
    Activity(R.string.activity_title, Icons.Outlined.DirectionsRun),
    Profile(R.string.profile_title, Icons.Outlined.Person),
}
