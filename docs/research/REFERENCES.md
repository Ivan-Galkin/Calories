# ColoriLens Research References

Last updated: 2026-05-17

## AI image analysis

- OpenAI Images and Vision guide: https://developers.openai.com/api/docs/guides/images-vision
  - Used for the requirement that food photo analysis should use an image-capable OpenAI API flow.

## Physical activity MET values

- 2024 Adult Compendium of Physical Activities tracking guide: https://pacompendium.com/wp-content/uploads/2024/03/4_2024_adult-compendium-tracking-guide-1-2024.pdf
  - Used as the source family for the MVP activity catalog and MET-based calorie calculation.

## Market and design references

- Cal AI on Google Play: https://play.google.com/store/apps/details?id=com.viraldevelopment.calai
  - Useful reference for fast photo logging, macro tracking, and user complaints about inaccurate estimates and profile/history reliability.
- Calchi AI: https://www.calchi.ai/
  - Useful reference for clean daily dashboard patterns, macro bars, editable AI estimates, and photo-first meal cards.

## Formula references to add before implementation

Create `docs/FORMULAS.md` before coding formula logic. It should include:

- Mifflin-St Jeor equation source and examples.
- Activity factor definitions.
- MET calorie formula source and examples.
- Unit conversion examples.
- Test cases for male/female/other formula choices and edge cases.
