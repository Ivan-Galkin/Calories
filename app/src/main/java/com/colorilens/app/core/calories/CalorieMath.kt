package com.colorilens.app.core.calories

import kotlin.math.roundToInt

enum class SexForFormula {
    Male,
    Female,
    Unspecified,
}

enum class BaselineActivityLevel(val factor: Double) {
    Sedentary(1.2),
    Light(1.375),
    Moderate(1.55),
    VeryActive(1.725),
}

enum class WeightGoal(val calorieAdjustmentKcal: Int) {
    Lose(-500),
    Maintain(0),
    Gain(300),
}

data class ProfileDraft(
    val sexForFormula: SexForFormula = SexForFormula.Unspecified,
    val ageYears: Int = 30,
    val heightCm: Double = 175.0,
    val weightKg: Double = 75.0,
    val targetWeightKg: Double = 72.0,
    val baselineActivityLevel: BaselineActivityLevel = BaselineActivityLevel.Light,
    val goal: WeightGoal = WeightGoal.Maintain,
)

data class ProfileCalorieTargets(
    val bmrKcal: Int,
    val maintenanceKcal: Int,
    val dailyTargetKcal: Int,
)

object CalorieMath {
    fun calculateTargets(profile: ProfileDraft): ProfileCalorieTargets {
        val bmr = mifflinStJeorBmr(
            weightKg = profile.weightKg,
            heightCm = profile.heightCm,
            ageYears = profile.ageYears,
            sexForFormula = profile.sexForFormula,
        )
        val maintenance = maintenanceCalories(
            bmrKcal = bmr,
            baselineActivityLevel = profile.baselineActivityLevel,
        )
        val dailyTarget = dailyTargetCalories(
            maintenanceKcal = maintenance,
            goal = profile.goal,
        )

        return ProfileCalorieTargets(
            bmrKcal = roundKcal(bmr),
            maintenanceKcal = roundKcal(maintenance),
            dailyTargetKcal = roundKcal(dailyTarget),
        )
    }

    fun mifflinStJeorBmr(
        weightKg: Double,
        heightCm: Double,
        ageYears: Int,
        sexForFormula: SexForFormula,
    ): Double {
        require(weightKg > 0.0) { "Weight must be greater than zero." }
        require(heightCm > 0.0) { "Height must be greater than zero." }
        require(ageYears > 0) { "Age must be greater than zero." }

        val sexConstant = when (sexForFormula) {
            SexForFormula.Male -> 5.0
            SexForFormula.Female -> -161.0
            SexForFormula.Unspecified -> -78.0
        }

        return 10.0 * weightKg + 6.25 * heightCm - 5.0 * ageYears + sexConstant
    }

    fun maintenanceCalories(
        bmrKcal: Double,
        baselineActivityLevel: BaselineActivityLevel,
    ): Double {
        require(bmrKcal > 0.0) { "BMR must be greater than zero." }
        return bmrKcal * baselineActivityLevel.factor
    }

    fun dailyTargetCalories(
        maintenanceKcal: Double,
        goal: WeightGoal,
    ): Double {
        require(maintenanceKcal > 0.0) { "Maintenance calories must be greater than zero." }
        return maintenanceKcal + goal.calorieAdjustmentKcal
    }

    fun activityCaloriesBurned(
        met: Double,
        bodyWeightKg: Double,
        durationMinutes: Int,
    ): Double {
        require(met > 0.0) { "MET must be greater than zero." }
        require(bodyWeightKg > 0.0) { "Body weight must be greater than zero." }
        require(durationMinutes > 0) { "Duration must be greater than zero." }

        return met * 3.5 * bodyWeightKg / 200.0 * durationMinutes
    }

    fun dailyCalorieBalance(
        caloriesConsumed: Double,
        baselineTargetKcal: Double,
        loggedActivityKcal: Double,
    ): Double {
        require(caloriesConsumed >= 0.0) { "Consumed calories cannot be negative." }
        require(baselineTargetKcal > 0.0) { "Baseline target must be greater than zero." }
        require(loggedActivityKcal >= 0.0) { "Logged activity calories cannot be negative." }

        return caloriesConsumed - (baselineTargetKcal + loggedActivityKcal)
    }

    fun roundKcal(value: Double): Int = value.roundToInt()
}
