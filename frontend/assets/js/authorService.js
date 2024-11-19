import { configs } from "./configs.js";

const page = configs.inPage;
const pageSize = configs.inPageSize;
const alert = configs.alertComponent;
const description = configs.authorDescriptionContent;

const selectSearchType = configs.authorSelectType;
const inputsComponent = configs.authorInputsContent;
const btnSearch = configs.btnSearchAuthors;

let searchType = '';

const SearchType = {
  DEFAULT: 'default',
  FIND_ALL: 'find-all',
  FIND_BY_ALIVE_AFTER: 'find-by-alive-after',
  FIND_BY_NAME: 'find-by-name',
}

selectSearchType.addEventListener('change', (e) => {
  searchType = e.target.value;
  switch (searchType) {
    case SearchType.DEFAULT: clearAlert(); break;
    case SearchType.FIND_ALL: build_FindAllAuthors(); break;
    case SearchType.FIND_BY_ALIVE_AFTER: build_FindByAliveAfter(); break;
    case SearchType.FIND_BY_NAME: build_FindByName(); break;
    default: alertMessage(`Tipo de pesquisa para autores é inválida`); break;
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

function build_FindAllAuthors() {
  inputsComponent.innerHTML = ``;
  clearAlert();
}

function build_FindByAliveAfter() {
  inputsComponent.innerHTML = `
    <input 
      type="number" 
      placeholder="Informe o ano dos autores vivos a partir dele" 
      class="input size-250x" 
      id="in-author-alive"
    >
  `;
  clearAlert();
}

function build_FindByName() {
  inputsComponent.innerHTML = `
    <input 
      type="text" 
      placeholder="Digite o nome autor" 
      class="input size-250x" 
      id="in-author-name"
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
      <p class="author-description">
        <span class="author-name">${e.name}<span> | 
        <span class="author-birth-death">${e.birthYear}-${e.deathYear}</span>
      </p>
    `;
    description.appendChild(item);
  });
}

btnSearch.addEventListener('click', (e) => {
  switch (searchType) {
    case SearchType.FIND_ALL: findAll(); break;
    case SearchType.FIND_BY_ALIVE_AFTER: findByAliveAfter(); break;
    case SearchType.FIND_BY_NAME: findByName(); break;
  }
});

async function findAll() {
  const _page = page.value === '' ? 0 : page.value -1;
  const _size = pageSize.value === '' ? 5 : pageSize.value;

  const response = await fetch(`${configs.baseURL}/v1/authors?page=${_page}&size=${_size}`)
    .then(response => response)
    .catch(error => console.error(error));
  
  const json = await response.json();
  showAlert(response, json);
  showDescription(json);
}

async function findByAliveAfter() {
  const _page = page.value === '' ? 0 : page.value -1;
  const _size = pageSize.value === '' ? 5 : pageSize.value;
  const after = document.querySelector('#in-author-alive').value;

  const response = await fetch(`${configs.baseURL}/v1/authors/alive?after=${after}&page=${_page}&size=${_size}`)
    .then(response => response)
    .catch(error => console.error(error));
  
  const json = await response.json();
  showAlert(response, json);
  showDescription(json);
}

async function findByName() {
  const name = document.querySelector('#in-author-name').value;
  
  const response = await fetch(`${configs.baseURL}/v1/authors/name/${name}`)
  .then(response => response)
  .catch(error => console.error(error));
  
  const json = await response.json();
  showAlert(response, json);
  description.innerHTML = `
    <p class="author-description">
      <span class="author-name">${json.name}<span> | 
      <span class="author-birth-death">${json.birthYear}-${json.deathYear}</span>
    </p>
  `;
}