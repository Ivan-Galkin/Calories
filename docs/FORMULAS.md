# ColoriLens Formulas

Last updated: 2026-05-17

## 1. Purpose

This document defines the first MVP formulas for calorie targets, activity calories, and daily deficit/surplus calculations. These formulas are consumer wellness estimates, not medical guidance.

## 2. Resting energy: Mifflin-St Jeor BMR

ColoriLens uses the Mifflin-St Jeor equation for estimated resting energy expenditure / basal metabolic rate.

### Male formula

```text
BMR = 10 × weightKg + 6.25 × heightCm - 5 × ageYears + 5
```

### Female formula

```text
BMR = 10 × weightKg + 6.25 × heightCm - 5 × ageYears - 161
```

### Unspecified sex/gender handling

For MVP, when the user does not choose a male/female formula option, ColoriLens uses the midpoint between the male and female constants:

```text
unspecifiedConstant = (5 + -161) / 2 = -78
BMR = 10 × weightKg + 6.25 × heightCm - 5 × ageYears - 78
```

This is intentionally documented so the app avoids silently assuming a sex-specific formula. A later version may let the user manually set the daily calorie target instead.

Reference source to verify before public release:

- Mifflin MD, St Jeor ST, Hill LA, Scott BJ, Daugherty SA, Koh YO. A new predictive equation for resting energy expenditure in healthy individuals. The American Journal of Clinical Nutrition. 1990.

## 3. Baseline activity factors

BMR is multiplied by a baseline activity factor to estimate maintenance calories before separately logged activities are added.

| App level | Factor | MVP meaning |
| --- | ---: | --- |
| Sedentary | 1.2 | Mostly sitting, little routine movement |
| Light | 1.375 | Light routine movement or light exercise |
| Moderate | 1.55 | Moderate routine activity or several workouts weekly |
| Very active | 1.725 | High routine activity or frequent hard training |

```text
maintenanceCalories = BMR × baselineActivityFactor
```

The first MVP should keep this simple and editable. If the user logs explicit activities, those activity calories are added separately to the daily available calories.

## 4. Goal adjustment

ColoriLens uses conservative default goal adjustments:

| Goal | Adjustment |
| --- | ---: |
| Lose weight | -500 kcal/day |
| Maintain weight | 0 kcal/day |
| Gain weight | +300 kcal/day |

```text
dailyTargetCalories = maintenanceCalories + goalAdjustment
```

Future versions should allow the user to customize this value.

## 5. MET activity calories

ColoriLens estimates calories burned for logged activities with the standard MET formula:

```text
caloriesBurned = MET × 3.5 × bodyWeightKg / 200 × durationMinutes
```

The seed activity catalog is stored in `data/activities_seed.json` and is based on a curated MVP subset of activities from the Adult Compendium of Physical Activities family of references.

Reference source to verify before public release:

- 2024 Adult Compendium of Physical Activities tracking guide: https://pacompendium.com/wp-content/uploads/2024/03/4_2024_adult-compendium-tracking-guide-1-2024.pdf

## 6. Daily calorie balance

ColoriLens calculates deficit/surplus as consumed calories minus available calories:

```text
availableCalories = dailyTargetCalories + loggedActivityCalories
dailyBalance = caloriesConsumed - availableCalories
```

Interpretation:

- Negative balance = deficit.
- Positive balance = surplus.
- Zero balance = at target.

## 7. Initial test examples

These examples are mirrored in `CalorieMathTest`.

| Case | Input | Expected result |
| --- | --- | ---: |
| Male BMR | 80 kg, 180 cm, 30 years | 1780 kcal |
| Female BMR | 65 kg, 170 cm, 35 years | 1376.5 kcal |
| Activity burn | MET 6.0, 75 kg, 60 min | 472.5 kcal |
| Daily balance | 2200 consumed, 2000 target, 400 activity | -200 kcal |
| Weight loss target | male BMR 1780 × light factor 1.375 - 500 | 1948 kcal rounded |
