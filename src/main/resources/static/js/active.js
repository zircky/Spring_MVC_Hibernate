document.addEventListener("DOMContentLoaded", () => {
  let adminlink = document.getElementById("admin-link");
  const userLink = document.getElementById("user-link")
  let currentUrl = window.location.pathname.split("/").pop();


  console.log(currentUrl)
  if (currentUrl === "user") {
    if (adminlink !== null) {
      adminlink.classList.remove("active", "bg-primary");
    }
    userLink.classList.add("active", "bg-primary");
    console.log(currentUrl)
  }


})

document.addEventListener("DOMContentLoaded", () => {
  const navUserTable = document.getElementById('nav-user-table')
  const navNewUser = document.getElementById('nav-new-user')
  const newUserButton = document.getElementById('new-user-tab')
  const userTableButton = document.getElementById('user-table-tab')

  if (navUserTable !== null || navNewUser !== null) {
    newUserButton.addEventListener('click', () => {
      navUserTable.classList.add('position-absolute', 'd-none')
      navNewUser.classList.remove('d-none')
    })
    userTableButton.addEventListener('click', () => {
      navUserTable.classList.remove('position-absolute', 'd-none')
      navNewUser.classList.add('d-none')
    })
  }
})


