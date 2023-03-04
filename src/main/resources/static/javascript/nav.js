const toggleButton = document.getElementsByClassName("toggle-button")[0];
const navbarLinks = document.getElementsByClassName("navbar-links")[0];
//order page address section
const addressPanel = document.getElementById("addressPanel");
const addressInputs = document.getElementsByClassName("addressPanelInput");

toggleButton.addEventListener("click", () => {
  navbarLinks.classList.toggle("active");
});

const showAddressPanel = ()=>{
  addressPanel.style.display = 'block';
  for (let i = 0; i < addressInputs.length; i++){
    addressInputs[i].setAttribute("required", "");
  }
};

const hideAddressPanel = ()=>{
  addressPanel.style.display = 'none';
  for (let i = 0; i < addressInputs.length; i++){
    addressInputs[i].removeAttribute("required");
  }
};




