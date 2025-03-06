document.addEventListener("DOMContentLoaded", function () {
  const userEdit = document.getElementById('userEdit')
  const url = "http://localhost:8080/api/admin/editUser"
  var modulsEdit = new bootstrap.Modal(document.getElementById(""))

  userEdit.addEventListener('show.bs.modal', async (event) => {
    const button = event.relatedTarget

    const response = await fetch(url, {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      },
      body: JSON.stringify()
    })
    const form = userEdit.querySelector('#editFrom');


    userEdit.querySelector('.modal-body input[id="editUserId"]').value = button.getAttribute('data-id')
    userEdit.querySelector('.modal-body input[id="editFirstName"]').value = button.getAttribute('data-firstName')
    userEdit.querySelector('.modal-body input[id="editLastName"]').value = button.getAttribute('data-lastName')
    userEdit.querySelector('.modal-body input[id="editAge"]').value = button.getAttribute('data-age')
    userEdit.querySelector('.modal-body input[id="editEmail"]').value = button.getAttribute('data-email')

  })


})