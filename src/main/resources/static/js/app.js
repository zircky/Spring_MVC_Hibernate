document.addEventListener("DOMContentLoaded", async (e) => {
  const response = await fetch('/api/auth/roles'); // Получаем роли с сервера
  const roles = await response.json();
  console.log(roles)
  const roleSelect = document.getElementById('editRoles');

  roles.forEach(role => {
    const option = document.createElement('option');
    option.value = role.id;
    option.text = role.name;
    roleSelect.appendChild(option);
  });
})

document.addEventListener("DOMContentLoaded", async () => {
  const header = document.getElementById("header")
  const tbody = document.getElementById("tbody")
  const editModal = new bootstrap.Modal(document.getElementById("userEdit"))
  const currentUrl = window.location.pathname
  const url = "http://localhost:8080/api/admin"

  const response = await fetch(url, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${localStorage.getItem("token")}`
    }
  })
  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.error || "Failed")
  }
  const data = await response.json()
  console.log(data)

  tbody.innerHTML = ""

  data.forEach(user => {
    header.innerHTML = `
      <div class="d-inline h5 fw-bold">
      <span>${user.email}</span>
    </div>
    <div class="d-inline h5 fw-light">
      <span>with roles:</span>
    </div>
    <div class="d-inline h5 fw-bold">
      ${user.roles.map(role => role.name)}
      <span class="fw-light">Role</span>
    </div>
  </div>
    `

    const isAdmin = user.roles.some(role => role.name === "ADMIN")

    isAdmin && currentUrl.includes("/admin") ? tbody.innerHTML += `
    <tr>
     <td>${user.id}</td>
     <td>${user.firstName}</td>
     <td>${user.lastName}</td>
     <td>${user.age}</td>
     <td>${user.email}</td>
     <td>${user.roles.map(role => role.name).join(", ")}</td>
     <td>
         <button class="btn btn-outline-primary btn-sm edit-btn
         " data-id="${user.id}" data-bs-target="#userEdit" data-bs-toggle="modal" type="button">
           <img alt="Edit" class="img-fluid" src="/img/edit-2-line.svg" style="width: 1.6rem; height: 1.6rem">
           <span>Edit</span>
         </button>
       </td>
       <td>
         <button class="btn btn-outline-danger btn-sm" data-id="${user.id}" data-bs-target="#userDelete" data-bs-toggle="modal" type="button">
           <img alt="Delete" class="img-fluid" src="/img/delete-row.svg" style="width: 1.65rem; height: 1.65rem">
           <span>Delete</span>
         </button>
       </td>
    </tr>
    ` : tbody.innerHTML = `
      <tr>
      <td>${user.id}</td>
      <td>${user.firstName}</td>
      <td>${user.lastName}</td>
      <td>${user.age}</td>
      <td>${user.email}</td>
      <td>${user.roles.map(role => role.name).join(", ")}</td>
    </tr>
    `
  })

  document.querySelectorAll(".edit-btn").forEach(button => {
    button.addEventListener("click", async (e) => {
      const userId = e.target.closest("button").dataset.id
      const user = data.find(u => u.id == userId)

      document.getElementById("editUserId").value = user.id
      document.getElementById("editFirstName").value = user.firstName
      document.getElementById("editLastName").value = user.lastName
      document.getElementById("editAge").value = user.age
      document.getElementById("editEmail").value = user.email


      editModal.show()
    })
  })
})
