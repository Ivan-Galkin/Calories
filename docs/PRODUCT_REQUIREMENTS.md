# ColoriLens Product Requirements

Last updated: 2026-05-17

## 1. Product vision

ColoriLens is a mobile calorie tracker for Android-first personal use. The app helps the user photograph meals throughout the day, receive an AI nutrition estimate, correct the result manually, add activities, and understand daily calorie deficit or surplus through clear statistics.

The first version is optimized for one owner/user. Later versions should be ready for Google Play distribution, monetization through ads or subscriptions, and a privacy-safe production backend.

## 2. Target users

### Primary user for MVP

- A Russian-speaking individual who wants a fast personal calorie diary.
- Uses Android as the main platform.
- Wants to take photos instead of manually searching for every food item.
- Accepts that AI estimates are approximate and must be editable.

### Future users

- English-speaking Android users.
- Users who want goal-based calorie deficit/surplus tracking.
- Users willing to pay for higher scan limits, history, cloud backup, or advanced analytics.

## 3. Languages and localization

The app must support:

- Russian (`ru`) as the first/default language for the owner.
- English (`en`) as a selectable language.

All user-facing strings must be stored in Android string resources and must not be hard-coded in Compose screens.

## 4. Core user journeys

### 4.1 First launch and profile setup

1. User opens the app.
2. User chooses language or accepts device/default language.
3. User enters profile data:
   - sex/gender for metabolic formula selection,
   - age,
   - height,
   - current weight,
   - target weight,
   - goal: lose weight, maintain, gain weight,
   - preferred deficit/surplus target if applicable.
4. App calculates an estimated daily baseline using a documented formula.
5. App shows today's dashboard.

### 4.2 Meal photo logging

1. User taps the primary camera action.
2. User takes a photo or imports from gallery.
3. App sends image to the AI analysis flow.
4. AI returns structured nutrition data:
   - meal title,
   - detected food items,
   - estimated portion sizes in grams,
   - calories,
   - proteins,
   - fats,
   - carbohydrates,
   - total sugars,
   - fast sugars/simple sugars,
   - fiber when available,
   - confidence score,
   - assumptions and warning notes.
5. User reviews the estimate before saving.
6. User can edit any value manually.
7. User saves the meal into the selected date/day.

### 4.3 Meal history management

The user must be able to:

- View today's meals as a chronological photo timeline.
- View all previous days in calendar/list form.
- Open a meal details screen.
- Edit meal title, time, food items, grams, calories, macros, sugars, photo, and notes.
- Delete a meal.
- Duplicate or re-log a previous meal in a future version.

### 4.4 Activity logging

1. User opens the Activity section.
2. User selects an activity from a searchable catalog.
3. User enters duration in minutes.
4. App estimates calories burned using MET value, body weight, and duration.
5. User can edit calories burned manually.
6. Activity is added to the selected day.

The user must be able to view, edit, and delete activity history.

### 4.5 Statistics and insights

The Statistics section must show:

- Daily calorie intake.
- Daily activity calories.
- Estimated baseline calories.
- Total estimated expenditure.
- Deficit or surplus per day.
- Macro totals and macro ratio.
- Period views: day, week, month, year, all time.
- Trends and averages for selected period.
- Clear visual charts, not only tables.

### 4.6 Profile management

The Profile section must allow editing:

- language,
- sex/gender for formulas,
- age/date of birth,
- height,
- current weight,
- target weight,
- goal,
- preferred units,
- activity baseline level,
- OpenAI/API configuration only in local developer builds, not in production UI.

Changing profile data should recalculate future targets and optionally preserve historical calculations as recorded snapshots.

## 5. Functional requirements

### 5.1 Meal entity

Each meal record must include:

- unique ID,
- date and time,
- local photo URI or stored image reference,
- AI model/version used,
- title,
- list of detected/entered food items,
- total calories,
- protein grams,
- fat grams,
- carbohydrate grams,
- sugar grams,
- fast/simple sugar grams,
- fiber grams when available,
- portion estimate in grams,
- confidence score,
- notes,
- source: AI, manual, edited AI,
- created/updated timestamps.

### 5.2 Activity entity

Each activity record must include:

- unique ID,
- date and time,
- activity catalog ID,
- localized name,
- MET value,
- duration minutes,
- body weight used for calculation,
- estimated calories burned,
- manually overridden calories burned if edited,
- notes,
- created/updated timestamps.

### 5.3 Profile entity

Profile must include:

- language,
- sex/gender option used for calorie formula,
- age or date of birth,
- height,
- weight history,
- current weight,
- target weight,
- goal type,
- daily target calories,
- preferred units,
- created/updated timestamps.

### 5.4 AI nutrition analysis

The AI output must be strict JSON matching the app schema. The prompt must tell the model to:

- identify all visible food items,
- estimate portions and calories,
- explain assumptions briefly,
- return confidence and uncertainty warnings,
- never present estimates as medical certainty,
- recommend manual correction when portion size is ambiguous.

OpenAI image input should be implemented through the official image/vision-capable API flow. OpenAI documents that recent language models can process image inputs and that the Responses API can analyze images as input. Source: https://developers.openai.com/api/docs/guides/images-vision

### 5.5 Calorie target calculation

MVP formula choice:

- Use Mifflin-St Jeor for estimated resting energy expenditure/BMR.
- Add selected baseline activity factor.
- Add logged activity calories for the day.
- Calculate deficit/surplus as: `caloriesConsumed - (baselineTarget + loggedActivityCalories)`.

Formula details must be implemented in code with tests and references in `docs/FORMULAS.md` before development starts.

### 5.6 Activity calorie calculation

Use MET-based calculation:

`caloriesBurned = MET * 3.5 * bodyWeightKg / 200 * durationMinutes`

The initial catalog should be derived from the Adult Compendium of Physical Activities. The repository includes a small starter seed file, and production should use a curated, localized subset. Source: https://pacompendium.com/wp-content/uploads/2024/03/4_2024_adult-compendium-tracking-guide-1-2024.pdf

## 6. Non-functional requirements

### 6.1 Privacy and safety

- Do not expose an OpenAI API key in a production Android client.
- Store private health and food data securely.
- Provide export and delete-my-data flows before public release.
- Clearly tell users that calorie estimates from photos are approximate.
- Avoid medical claims.

### 6.2 Offline behavior

- Existing diary, profile, and statistics must work offline.
- AI analysis requires network unless a future local model is introduced.
- Failed analysis requests should be retryable.

### 6.3 Performance

- Today's dashboard should load quickly from local database.
- Photos should be resized/compressed before upload.
- Charts should handle at least several years of daily data.

### 6.4 Accessibility

- Minimum touch target: 48dp.
- Support dynamic font size where possible.
- Good color contrast in light and dark themes.
- Important actions must have text labels, not only icons.

## 7. Design direction

Reference patterns from current calorie/photo trackers:

- Cal AI emphasizes a fast snap-and-track flow and macro breakdowns, but user reviews highlight the need for manual correction and reliable profile/history persistence. Source: https://play.google.com/store/apps/details?id=com.viraldevelopment.calai
- Calchi highlights a clean dashboard with calories left, macro bars, meal list, and a visible fix/edit action. Source: https://www.calchi.ai/

ColoriLens should use these lessons but avoid copying branding or layouts directly.

Recommended UX style:

- Modern Material 3 Android UI.
- Bottom navigation: Today, History, Stats, Activity, Profile.
- Prominent floating camera action on Today.
- Friendly glass/lens visual metaphor.
- Clear photo cards with editable nutrition chips.
- Light and dark mode.
- Charts with calm colors, not aggressive diet-shaming language.

## 8. MVP scope

### Included in MVP

- Android app only.
- Russian and English strings.
- Local profile.
- Meal photo capture/import.
- OpenAI-based analysis.
- Review-before-save screen.
- Meal edit/delete/history.
- Activity catalog and activity edit/delete/history.
- Basic statistics for day/week/month/year.
- Local database.

### Explicitly out of MVP

- iOS app.
- Public accounts.
- Social features.
- Barcode scanning.
- Wearable integrations.
- Payment/subscription implementation.
- Ads implementation.
- Medical or clinical recommendations.

## 9. Future monetization requirements

Before Google Play release, add:

- backend proxy for AI calls and API key protection,
- auth or anonymous install identity,
- usage quotas,
- subscription/paywall experiments,
- ad consent flows where required,
- privacy policy and data deletion flow,
- Google Play closed testing checklist.

## 10. Open questions

- Which Android minimum SDK should be targeted?
- Should MVP store all photos locally only or support cloud backup later?
- Should sugar and fast sugar be separate user-editable fields or should fast sugar be an AI-estimated subset with warning?
- Should the app use metric-only units first or support imperial units in English from day one?
- Which backend stack should be chosen for production: Firebase, Supabase, Ktor, or another lightweight service?
