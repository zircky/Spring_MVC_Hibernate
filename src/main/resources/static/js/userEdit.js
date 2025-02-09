const userEdit = document.getElementById('userEdit');

userEdit.addEventListener('shown.bs.modal', event => {
    const button = event.relatedTarget
    const editUserId = button.getAttribute('data-bs-id')
    userEdit.querySelector('.modal-body form').action = '/admin/update/' + editUserId
    userEdit.querySelector('.modal-body input[id="editUserId"]').setAttribute('data-id')
    userEdit.querySelector('.modal-body input[id="editFirstName"]').setAttribute('data-firstName')
    userEdit.querySelector('.modal-body input[id="editLastName"]').setAttribute('data-lastName')
    userEdit.querySelector('.modal-body input[id="editAge"]').value = button.getAttribute('data-age')
    userEdit.querySelector('.modal-body input[id="editEmail"]').setAttribute('data-email')

})

