document.addEventListener("DOMContentLoaded", function () {
  const userEdit = document.getElementById('userEdit')

  userEdit.addEventListener('show.bs.modal', event => {
    const button = event.relatedTarget
    const editId = button.getAttribute('data-id')

    const form = userEdit.querySelector('#editFrom');
    if (form) {
      form.action = `/admin/update/${editId}`;
    }
    console.log(editId)

    userEdit.querySelector('.modal-body input[id="editUserId"]').value = button.getAttribute('data-id')
    userEdit.querySelector('.modal-body input[id="editFirstName"]').value = button.getAttribute('data-firstName')
    userEdit.querySelector('.modal-body input[id="editLastName"]').value = button.getAttribute('data-lastName')
    userEdit.querySelector('.modal-body input[id="editAge"]').value = button.getAttribute('data-age')
    userEdit.querySelector('.modal-body input[id="editEmail"]').value = button.getAttribute('data-email')

    console.log("User Data:", {
      id: button.getAttribute('data-id'),
      firstName: button.getAttribute('data-firstName'),
      lastName: button.getAttribute('data-lastName'),
      age: button.getAttribute('data-age'),
      email: button.getAttribute('data-email'),
    });
  })


})