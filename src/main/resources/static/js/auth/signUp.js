document.addEventListener("DOMContentLoaded", async (e) => {
  const response = await fetch('/api/auth/roles'); // Получаем роли с сервера
  const roles = await response.json();
  console.log(roles)
  const roleSelect = document.getElementById('roles');

  roles.forEach(role => {
    const option = document.createElement('option');
    option.value = role.id;
    option.text = role.name;
    roleSelect.appendChild(option);
  });
})


document.getElementById('signUpForm').onsubmit = async function (event) {

  event.preventDefault();

  const formData = {
    firstName: document.getElementById('firstName').value,
    lastName: document.getElementById('lastName').value,
    email: document.getElementById('email').value,
    password: document.getElementById('password').value,
    roles: parseInt(document.getElementById('roles').value)
  };

  try {
    const response = await fetch('http://localhost:8080/api/auth/sign-up', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    });

    if (response.ok) {
      window.location.href = "/login"
      alert('Пользователь успешно зарегистрирован');
    } else {
      alert('Ошибка регистрации');
    }
  } catch (error) {
    alert('Произошла ошибка');
  }
};
