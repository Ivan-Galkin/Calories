package com.colorilens.app.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.colorilens.app.R
import com.colorilens.app.core.calories.BaselineActivityLevel
import com.colorilens.app.core.calories.CalorieMath
import com.colorilens.app.core.calories.ProfileDraft
import com.colorilens.app.core.calories.SexForFormula
import com.colorilens.app.core.calories.WeightGoal

@Composable
fun ProfileSetupCard(modifier: Modifier = Modifier) {
    var age by rememberSaveable { mutableStateOf("30") }
    var height by rememberSaveable { mutableStateOf("175") }
    var weight by rememberSaveable { mutableStateOf("75") }
    var targetWeight by rememberSaveable { mutableStateOf("72") }
    var sex by rememberSaveable { mutableStateOf(SexForFormula.Unspecified) }
    var goal by rememberSaveable { mutableStateOf(WeightGoal.Maintain) }
    var activityLevel by rememberSaveable { mutableStateOf(BaselineActivityLevel.Light) }

    val profile = buildProfileDraft(
        sex = sex,
        goal = goal,
        activityLevel = activityLevel,
        age = age,
        height = height,
        weight = weight,
        targetWeight = targetWeight,
    )
    val targets = profile?.let(CalorieMath::calculateTargets)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Text(
                text = stringResource(R.string.profile_setup_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = stringResource(R.string.profile_setup_body),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            ChoiceRow(
                titleRes = R.string.profile_sex_label,
                options = SexForFormula.entries,
                selected = sex,
                labelRes = SexForFormula::labelRes,
                onSelected = { sex = it },
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                NumberField(
                    modifier = Modifier.weight(1f),
                    value = age,
                    onValueChange = { age = it },
                    labelRes = R.string.profile_age_label,
                )
                NumberField(
                    modifier = Modifier.weight(1f),
                    value = height,
                    onValueChange = { height = it },
                    labelRes = R.string.profile_height_label,
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                NumberField(
                    modifier = Modifier.weight(1f),
                    value = weight,
                    onValueChange = { weight = it },
                    labelRes = R.string.profile_weight_label,
                )
                NumberField(
                    modifier = Modifier.weight(1f),
                    value = targetWeight,
                    onValueChange = { targetWeight = it },
                    labelRes = R.string.profile_target_weight_label,
                )
            }

            ChoiceRow(
                titleRes = R.string.profile_goal_label,
                options = WeightGoal.entries,
                selected = goal,
                labelRes = WeightGoal::labelRes,
                onSelected = { goal = it },
            )
            ChoiceRow(
                titleRes = R.string.profile_activity_level_label,
                options = BaselineActivityLevel.entries,
                selected = activityLevel,
                labelRes = BaselineActivityLevel::labelRes,
                onSelected = { activityLevel = it },
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                TargetMetric(
                    modifier = Modifier.weight(1f),
                    labelRes = R.string.profile_bmr_label,
                    value = targets?.bmrKcal,
                )
                TargetMetric(
                    modifier = Modifier.weight(1f),
                    labelRes = R.string.profile_maintenance_label,
                    value = targets?.maintenanceKcal,
                )
                TargetMetric(
                    modifier = Modifier.weight(1f),
                    labelRes = R.string.profile_daily_target_label,
                    value = targets?.dailyTargetKcal,
                )
            }
        }
    }
}

@Composable
private fun NumberField(
    value: String,
    onValueChange: (String) -> Unit,
    labelRes: Int,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(labelRes)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}

@Composable
private fun <T> ChoiceRow(
    titleRes: Int,
    options: List<T>,
    selected: T,
    labelRes: (T) -> Int,
    onSelected: (T) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            options.forEach { option ->
                FilterChip(
                    selected = selected == option,
                    onClick = { onSelected(option) },
                    label = { Text(text = stringResource(labelRes(option))) },
                )
            }
        }
    }
}

@Composable
private fun TargetMetric(
    labelRes: Int,
    value: Int?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = value?.let { "$it" } ?: "—",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(labelRes),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

private fun buildProfileDraft(
    sex: SexForFormula,
    goal: WeightGoal,
    activityLevel: BaselineActivityLevel,
    age: String,
    height: String,
    weight: String,
    targetWeight: String,
): ProfileDraft? {
    val parsedAge = age.toIntOrNull() ?: return null
    val parsedHeight = height.toDoubleOrNull() ?: return null
    val parsedWeight = weight.toDoubleOrNull() ?: return null
    val parsedTargetWeight = targetWeight.toDoubleOrNull() ?: return null

    if (parsedAge <= 0 || parsedHeight <= 0.0 || parsedWeight <= 0.0 || parsedTargetWeight <= 0.0) {
        return null
    }

    return ProfileDraft(
        sexForFormula = sex,
        ageYears = parsedAge,
        heightCm = parsedHeight,
        weightKg = parsedWeight,
        targetWeightKg = parsedTargetWeight,
        baselineActivityLevel = activityLevel,
        goal = goal,
    )
}

private fun SexForFormula.labelRes(): Int = when (this) {
    SexForFormula.Male -> R.string.sex_male
    SexForFormula.Female -> R.string.sex_female
    SexForFormula.Unspecified -> R.string.sex_unspecified
}

private fun WeightGoal.labelRes(): Int = when (this) {
    WeightGoal.Lose -> R.string.goal_lose
    WeightGoal.Maintain -> R.string.goal_maintain
    WeightGoal.Gain -> R.string.goal_gain
}

private fun BaselineActivityLevel.labelRes(): Int = when (this) {
    BaselineActivityLevel.Sedentary -> R.string.activity_sedentary
    BaselineActivityLevel.Light -> R.string.activity_light
    BaselineActivityLevel.Moderate -> R.string.activity_moderate
    BaselineActivityLevel.VeryActive -> R.string.activity_very_active
}
