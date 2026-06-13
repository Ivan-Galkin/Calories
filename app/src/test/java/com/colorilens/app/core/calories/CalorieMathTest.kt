package com.colorilens.app.core.calories

import org.junit.Assert.assertEquals
import org.junit.Test

class CalorieMathTest {
    @Test
    fun `male mifflin st jeor bmr uses documented constant`() {
        val bmr = CalorieMath.mifflinStJeorBmr(
            weightKg = 80.0,
            heightCm = 180.0,
            ageYears = 30,
            sexForFormula = SexForFormula.Male,
        )

        assertEquals(1780.0, bmr, 0.001)
    }

    @Test
    fun `female mifflin st jeor bmr uses documented constant`() {
        val bmr = CalorieMath.mifflinStJeorBmr(
            weightKg = 65.0,
            heightCm = 170.0,
            ageYears = 35,
            sexForFormula = SexForFormula.Female,
        )

        assertEquals(1376.5, bmr, 0.001)
    }

    @Test
    fun `activity calories use met formula`() {
        val burned = CalorieMath.activityCaloriesBurned(
            met = 6.0,
            bodyWeightKg = 75.0,
            durationMinutes = 60,
        )

        assertEquals(472.5, burned, 0.001)
    }

    @Test
    fun `daily balance is consumed minus target plus activity`() {
        val balance = CalorieMath.dailyCalorieBalance(
            caloriesConsumed = 2200.0,
            baselineTargetKcal = 2000.0,
            loggedActivityKcal = 400.0,
        )

        assertEquals(-200.0, balance, 0.001)
    }

    @Test
    fun `profile target applies activity factor and goal adjustment`() {
        val targets = CalorieMath.calculateTargets(
            ProfileDraft(
                sexForFormula = SexForFormula.Male,
                ageYears = 30,
                heightCm = 180.0,
                weightKg = 80.0,
                baselineActivityLevel = BaselineActivityLevel.Light,
                goal = WeightGoal.Lose,
            )
        )

        assertEquals(1780, targets.bmrKcal)
        assertEquals(2448, targets.maintenanceKcal)
        assertEquals(1948, targets.dailyTargetKcal)
    }
}
