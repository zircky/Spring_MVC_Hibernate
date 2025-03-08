document.addEventListener("DOMContentLoaded", function () {
  const editModal = new bootstrap.Modal(document.getElementById("userEdit"))
  const url = "http://localhost:8080/api/admin/editUser"

  document.getElementById("editForm").onsubmit = async (e) => {
    e.preventDefault()

    const id = document.getElementById("editId").value
    const updatedUser = {
      firstName: document.getElementById("editFirstName").value,
      lastName: document.getElementById("editLastName").value,
      age: document.getElementById("editAge").value,
      email: document.getElementById("editEmail").value,
      roles: parseInt(document.getElementById("editRoles").value)
    }

    const response = await fetch(url + `/${id}`, {
      method: "PUT",
      headers: {
        'Content-Type': 'application/json',
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      },
      body: JSON.stringify(updatedUser)
    })

    if (response.ok) {
      editModal.hide()
      location.reload()
    }
  }

})