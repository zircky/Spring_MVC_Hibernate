const userDelete = document.getElementById('userDelete');

userDelete.addEventListener('show.bs.modal', event => {
  const button = event.relatedTarget
  const deleteUserId = button.getAttribute('data-id')
  userDelete.querySelector('.modal-body form').action = `/admin/delete/${deleteUserId}`
  userDelete.querySelector('.modal-body input[id="deleteUserId"]').value = button.getAttribute('data-id')
  userDelete.querySelector('.modal-body input[id="deleteFirstName"]').value = button.getAttribute('data-firstName')
  userDelete.querySelector('.modal-body input[id="deleteLastName"]').value = button.getAttribute('data-lastName')
  userDelete.querySelector('.modal-body input[id="deleteAge"]').value = button.getAttribute('data-age')
  userDelete.querySelector('.modal-body input[id="deleteEmail"]').value = button.getAttribute('data-email')

})
