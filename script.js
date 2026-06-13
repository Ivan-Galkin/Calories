const header = document.querySelector('[data-header]');
const menuToggle = document.querySelector('[data-menu-toggle]');
const nav = document.querySelector('[data-nav]');
const filterButtons = document.querySelectorAll('[data-filter]');
const productCards = document.querySelectorAll('.product-card');
const contactForm = document.querySelector('[data-contact-form]');
const formNote = document.querySelector('[data-form-note]');

window.addEventListener('scroll', () => {
  header?.classList.toggle('is-scrolled', window.scrollY > 12);
});

menuToggle?.addEventListener('click', () => {
  const isOpen = nav.classList.toggle('is-open');
  menuToggle.setAttribute('aria-expanded', String(isOpen));
});

nav?.addEventListener('click', (event) => {
  if (event.target instanceof HTMLAnchorElement) {
    nav.classList.remove('is-open');
    menuToggle?.setAttribute('aria-expanded', 'false');
  }
});

filterButtons.forEach((button) => {
  button.addEventListener('click', () => {
    const filter = button.dataset.filter;
    filterButtons.forEach((item) => item.classList.toggle('active', item === button));
    productCards.forEach((card) => {
      const shouldShow = filter === 'all' || card.dataset.category === filter;
      card.hidden = !shouldShow;
    });
  });
});

document.querySelectorAll('[data-expand]').forEach((button) => {
  button.addEventListener('click', () => {
    const card = button.closest('.product-card');
    const isExpanded = card.classList.toggle('is-expanded');
    button.textContent = isExpanded ? 'Свернуть' : 'Подробнее';
  });
});

contactForm?.addEventListener('submit', (event) => {
  event.preventDefault();
  const formData = new FormData(contactForm);
  const subject = encodeURIComponent('Заявка по продуктам Кошелёк.ру');
  const body = encodeURIComponent(`Имя: ${formData.get('name')}\nКонтакт: ${formData.get('contact')}\nЗадача: ${formData.get('message') || ''}`);
  window.location.href = `mailto:support@cashelec.ru?subject=${subject}&body=${body}`;
  if (formNote) formNote.textContent = 'Откройте почтовый клиент и отправьте сформированное письмо.';
});
