# ColoriLens Technical Blueprint

Last updated: 2026-05-17

## 1. Recommended architecture

ColoriLens should be built as an Android-first Kotlin app with a clean architecture split:

- `app`: Android UI, navigation, dependency wiring.
- `core:designsystem`: theme, reusable UI components, typography, icons.
- `core:model`: shared domain models.
- `core:database`: Room entities, DAOs, migrations.
- `core:network`: API clients and DTOs.
- `feature:today`: daily dashboard and quick logging.
- `feature:meal`: camera/import, AI review, meal detail/edit.
- `feature:activity`: activity catalog, add/edit activity, history.
- `feature:stats`: charts and period analytics.
- `feature:profile`: profile, goals, language, units.

If the project starts as a smaller single-module app, keep package boundaries matching these modules so the code can be split later.

## 2. Android stack

- Language: Kotlin.
- UI: Jetpack Compose + Material 3.
- Architecture: MVVM or MVI with unidirectional UI state.
- Navigation: Navigation Compose.
- Async: Kotlin Coroutines + Flow.
- Local database: Room.
- Local key-value settings: DataStore.
- Image loading: Coil.
- Charts: Vico, Compose Charts, or another maintained Compose-compatible chart library after evaluation.
- Dependency injection: Hilt or Koin.
- Testing: JUnit, Turbine, MockK, Compose UI tests.

## 3. Backend recommendation

For a personal prototype, AI requests can be tested with a local developer configuration. For any public APK or Google Play build, the OpenAI key must not be embedded in the Android app.

Production-ready approach:

1. Android sends compressed image plus locale and user context to the backend.
2. Backend authenticates the request or checks anonymous install quota.
3. Backend calls OpenAI.
4. Backend validates JSON response against schema.
5. Backend returns normalized nutrition estimate to Android.
6. Android stores the reviewed/saved result locally.

Backend can start as one of:

- Firebase Cloud Functions for fastest Google Play path.
- Supabase Edge Functions if Postgres/sync is desired.
- Kotlin Ktor service if a Kotlin-only stack is preferred.

## 4. Data model draft

### Profile

- `id`
- `locale`
- `sexForFormula`
- `dateOfBirth`
- `heightCm`
- `currentWeightKg`
- `targetWeightKg`
- `goalType`
- `baselineActivityFactor`
- `dailyTargetCalories`
- `createdAt`
- `updatedAt`

### Meal

- `id`
- `loggedAt`
- `photoUri`
- `title`
- `portionGrams`
- `caloriesKcal`
- `proteinG`
- `fatG`
- `carbsG`
- `sugarG`
- `fastSugarG`
- `fiberG`
- `confidence`
- `source`
- `aiModel`
- `notes`
- `createdAt`
- `updatedAt`

### Meal item

- `id`
- `mealId`
- `name`
- `portionGrams`
- `caloriesKcal`
- `proteinG`
- `fatG`
- `carbsG`
- `sugarG`
- `fastSugarG`
- `confidence`

### Activity log

- `id`
- `loggedAt`
- `activityCatalogId`
- `nameRu`
- `nameEn`
- `met`
- `durationMinutes`
- `bodyWeightKg`
- `caloriesBurnedKcal`
- `manualOverrideKcal`
- `notes`
- `createdAt`
- `updatedAt`

## 5. AI response schema draft

The backend should request and validate JSON with this shape:

```json
{
  "meal_title": "string",
  "language": "ru|en",
  "items": [
    {
      "name": "string",
      "portion_grams": 0,
      "calories_kcal": 0,
      "protein_g": 0,
      "fat_g": 0,
      "carbs_g": 0,
      "sugar_g": 0,
      "fast_sugar_g": 0,
      "fiber_g": 0,
      "confidence": 0.0,
      "assumptions": ["string"]
    }
  ],
  "totals": {
    "portion_grams": 0,
    "calories_kcal": 0,
    "protein_g": 0,
    "fat_g": 0,
    "carbs_g": 0,
    "sugar_g": 0,
    "fast_sugar_g": 0,
    "fiber_g": 0
  },
  "confidence": 0.0,
  "warnings": ["string"]
}
```

## 6. Prompt requirements

The AI prompt must include:

- user locale,
- optional user country/food context,
- request for structured JSON only,
- explicit instruction to estimate visible food and portion uncertainty,
- instruction to flag hidden ingredients, sauces, oil, sugar, and drinks,
- instruction to avoid medical advice,
- reminder that user can edit every number.

## 7. Statistics calculations

For each day:

- `consumed = sum(meal.caloriesKcal)`
- `activityBurn = sum(activity.effectiveCaloriesBurned)`
- `baseline = profile.dailyTargetCalories or calculated BMR/TDEE baseline`
- `availableCalories = baseline + activityBurn`
- `balance = consumed - availableCalories`
- negative balance = deficit,
- positive balance = surplus.

Charts should aggregate by local date using the user's selected timezone.

## 8. Formula implementation status

The first calorie formula utilities are implemented in `app/src/main/java/com/colorilens/app/core/calories/CalorieMath.kt` and documented in `docs/FORMULAS.md`. Tests for BMR, MET activity calories, daily balance, and goal-adjusted targets live in `app/src/test/java/com/colorilens/app/core/calories/CalorieMathTest.kt`.

## 9. Repository structure target

```text
.
├── app/
├── core/
│   ├── designsystem/
│   ├── model/
│   ├── database/
│   └── network/
├── feature/
│   ├── today/
│   ├── meal/
│   ├── activity/
│   ├── stats/
│   └── profile/
├── backend/
├── data/
│   └── activities_seed.json
└── docs/
```

The repository now contains the first single-module Android app scaffold in `app/`. Future work can either keep feature packages in the app module for speed or split them into the target `core:*` and `feature:*` modules when the codebase grows.
