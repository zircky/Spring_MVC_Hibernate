document.getElementById('loginForm').addEventListener('submit', async (e) => {
  const url = 'http://localhost:8080/api/auth/login'
  e.preventDefault();

  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  const errorMessage = document.getElementById('errorMessage');

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify({email, password})
    });

    if (!response.ok) {
      const error = await response.json();
      throw new Error(error.error || 'Login failed');
    }
    const data = await response.json();
    console.log(data);

  } catch (error) {
    errorMessage.textContent = error.message;
    errorMessage.classList.remove('d-none');
  }
});