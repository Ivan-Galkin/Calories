# ColoriLens

ColoriLens is an Android-first calorie tracker that estimates meals from food photos with OpenAI vision models, lets the user correct every estimate manually, tracks daily activities, and shows calorie balance analytics over time.

## Current repository state

This repository now contains the product/workflow foundation and the first Android app shell:

- Android scaffold: [`app/`](app/)
- Product requirements: [`docs/PRODUCT_REQUIREMENTS.md`](docs/PRODUCT_REQUIREMENTS.md)
- Technical blueprint: [`docs/TECHNICAL_BLUEPRINT.md`](docs/TECHNICAL_BLUEPRINT.md)
- Workflow and documentation map: [`docs/workflow/WORKFLOW.md`](docs/workflow/WORKFLOW.md)
- Agent role prompts: [`docs/agents/`](docs/agents/)
- Initial physical activity seed catalog: [`data/activities_seed.json`](data/activities_seed.json)

## Planned app stack

- Android app: Kotlin + Jetpack Compose + Material 3
- Local data: Room + encrypted local storage where needed
- AI analysis: OpenAI Responses API with image inputs through a secure backend proxy before production
- Backend: small API service for OpenAI key protection, usage limits, subscriptions, and future sync
- Monetization later: ads and/or subscription, after the self-use MVP is stable

## MVP goal

Build a private Android MVP for daily use:

1. Take or import a meal photo.
2. Ask AI to estimate calories, macros, sugars, fast sugars, portion size, and confidence.
3. Save the meal to today's log.
4. Allow manual editing and deletion.
5. Add daily activities from a MET-based catalog.
6. Show daily/weekly/monthly/yearly calorie intake, burn, deficit, and surplus.
7. Support Russian and English UI.

## Android shell

The current app shell includes:

- `MainActivity` with a Compose entry point.
- Material 3 light/dark theme.
- Bottom navigation for Today, History, Stats, Activity, and Profile.
- Russian and English string resources for all visible placeholder copy.
- A Today floating action button reserved for the future meal photo scan flow.
- A Profile calorie-target draft card backed by the first BMR/TDEE/MET formula utilities.

## Local build notes

The project expects an Android SDK with API 36 and Build Tools 35.0.0+ available through `ANDROID_HOME` or `local.properties`. Use a JDK 17 runtime for Gradle/Android builds.
