const orderButton = document.getElementById("order-button");
const burgerek = document.getElementsByClassName("burgerek");
const vegosszeg = document.getElementById("vegosszeg");
const osszesito = document.getElementById("rendelosszesito");
const checkbox = document.getElementById("szallitIgen");
const price = 1200;

orderButton.addEventListener("click", (event) => {
  event.preventDefault();
  burgerekrendeles();
});

function burgerekrendeles() {
  let burgerNameList = [];
  let burgerdarabList = [];
  let rendeles = [];

  for (i = 0; i < burgerek.length; i++) {
    let darab = burgerek[i].options[burgerek[i].selectedIndex].value;
    let nev = burgerek[i].name;
    burgerNameList.push(nev);
    db = parseInt(darab);
    burgerdarabList.push(db);
    rendel = `${nev} : ${db}`;
    rendeles.push(rendel);
  }

  const sum = burgerdarabList.reduce((total, value, index, array) => {
    return total + value;
  });

  vegosszeg.innerHTML =
    checkbox.checked && sum > 0 ? sum * price + 500 : sum * price;

  osszesito.innerHTML = rendeles;
}
