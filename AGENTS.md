# Repository Instructions for AI Coding Agents

Scope: entire repository.

## Product context

This repository is for ColoriLens, an Android-first calorie tracker that estimates meal nutrition from food photos using OpenAI, supports manual correction, logs physical activities, and shows calorie deficit/surplus statistics.

Before making product or code changes, read:

1. `docs/PRODUCT_REQUIREMENTS.md`
2. `docs/TECHNICAL_BLUEPRINT.md`
3. Relevant role instructions in `docs/agents/`

## Working rules

- Keep the app Android-first unless the task explicitly says otherwise.
- Preserve Russian and English localization for user-facing strings.
- Never hard-code production OpenAI API keys in Android source code.
- Treat photo calorie estimates as approximate and editable.
- Prefer small, testable increments.
- Update documentation when requirements, architecture, formulas, or user-visible flows change.
- Do not copy competitor branding, screenshots, or proprietary layouts.

## Quality expectations

- Add tests for formulas, data mapping, and business logic.
- Keep UI accessible: readable contrast, scalable text, and clear touch targets.
- Make destructive actions confirmable or undoable.
- Keep meal and activity records editable and deletable.
