import { configs } from "./configs.js";

const page = configs.inPage;
const pageSize = configs.inPageSize;
const alert = configs.alertComponent;
const description = configs.bookDescriptionContent;

const selectSearchType = configs.bookSelectType;
const inputsComponent = configs.bookInputsComponent;
const btnSearch = configs.btnSearchBooks;

let searchType = '';

const SearchType = {
  DEFAULT: 'default',
  FIND_ALL: 'find-all-books',
  FIND_BY_TITLE: 'find-by-title',
  FIND_BY_LANGUAGE: 'find-by-language',
  COUNT_BY_LANGUAGE: 'count-by-language',
}

selectSearchType.addEventListener('change', (e) => {
  searchType = e.target.value;
  switch (searchType) {
    case SearchType.DEFAULT: clearAlert(); break;
    case SearchType.FIND_ALL: build_FindAllBooks(); break;
    case SearchType.FIND_BY_TITLE: build_FindByTitle(); break;
    case SearchType.FIND_BY_LANGUAGE: build_FindByLanguage(); break;
    case SearchType.COUNT_BY_LANGUAGE: build_CountByLanguage(); break;
    default: alertMessage(`Tipo de pesquisa para livros é inválida`); break;
  }
});

btnSearch.addEventListener('click', (e) => {
  switch (searchType) {
    case SearchType.FIND_ALL: findAllBooks(); break;
    case SearchType.FIND_BY_TITLE: findByTitle(); break;
    case SearchType.FIND_BY_LANGUAGE: findByLanguage(); break;
    case SearchType.COUNT_BY_LANGUAGE: countByLanguage(); break;
  }
});

function alertMessage(message) {
  alert.innerText = message;
  alert.style.visibility = 'visible';
}

function clearAlert() {
  alert.innerText = '';
  alert.style.visibility = 'hidden';
}

function build_FindAllBooks() {
  inputsComponent.innerHTML = ``;
  clearAlert();
}

function build_FindByTitle() {
  inputsComponent.innerHTML = `
    <input 
      type="text" 
      placeholder="Digite o titulo do livro" 
      class="input size-250x" 
      id="in-book-title"
    >
  `;
  clearAlert();
}

function build_FindByLanguage() {
  inputsComponent.innerHTML = `
    <input 
      type="text" 
      placeholder="Digite língua usado no livro" 
      class="input size-250x" 
      id="in-book-language"
    >
  `;
  clearAlert();
}

function build_CountByLanguage() {
  inputsComponent.innerHTML = `
    <input 
      type="text" 
      placeholder="Digite a língua para contar os livros" 
      class="input size-250x" 
      id="in-book-language-count"
    >
  `;
  clearAlert();
}

async function showAlert(response, json) {
  if (!response.ok) {
    alert.innerHTML = `
      <p class="alert-title">${json.error} ${json.status} (${json.timestamp})</p>
      <p class="alert-message">
        ${json.message}
        <span class="material-icons">error</span>
      </p>
    `;
    alert.style.visibility = 'visible';
  }
}

function showDescription(json) {
  description.innerHTML = '';
  json.forEach(e => {
    const item = document.createElement('li');
    item.innerHTML = `
      <img src="${e.imageUrl}" alt="Book Image">
      <p class="book-title">${e.title}</p>
      <p class="book-author">${e.author.name} | ${e.author.birthYear}-${e.author.deathYear}</p>
      <p class="book-languages">
        <span class="book-languages-target">línguas:</span>&nbsp; 
        <span class="book-languages-value">${e.languages.map(l => l.language).join(', ')}</span>
      </p>
      <p class="book-downloads">
        <span class="book-downloads-target">downloads:</span>&nbsp;
        <span class="book-downloads-value">${e.downloadCount}</span>
      </p>
    `;
    description.appendChild(item);
  });
}

async function findAllBooks() {
  const _page = page.value === '' ? 0 : page.value -1;
  const _size = pageSize.value === '' ? 5 : pageSize.value;

  const response = await fetch(`${configs.baseURL}/v1/books?page=${_page}&size=${_size}`)
    .then(response => response)
    .catch(error => console.error(error));
  
  const json = await response.json();
  showAlert(response, json);
  showDescription(json);
}

async function findByTitle() {
  const title = document.querySelector('#in-book-title').value;

  const response = await fetch(`${configs.baseURL}/v1/books/title/${title}`)
    .then(response => response)
    .catch(error => console.error(error));

  const json = await response.json();
  showAlert(response, json);
  showDescription(json);
}

async function findByLanguage() {
  const language = document.querySelector('#in-book-language').value;

  const response = await fetch(`${configs.baseURL}/v1/books/language/${language}`)
    .then(response => response)
    .catch(error => console.error(error));

  const json = await response.json();
  showAlert(response, json);
  showDescription(json);
}

async function countByLanguage() {
  const language = document.querySelector('#in-book-language-count').value;

  const response = await fetch(`${configs.baseURL}/v1/books/language/count/${language}`)
    .then(response => response)
    .catch(error => console.error(error));

  const json = await response.json();
  showAlert(response, json);

  description.innerHTML = `
    <p>
      <span class="bold-field">Quantidade de livros em ${language}:</span>&nbsp;
      <span class="field">${json}</span>
    </p>
  `;
}
