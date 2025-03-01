document.addEventListener("DOMContentLoaded", async (e) => {
  const response = await fetch('/api/auth/roles');
  const roles = await response.json();
  console.log(roles);

  const table = document.getElementById('dataTable');

  data.forEach(item => {
    const row = document.createElement('tr');

    Object.values(item).forEach(value => {
      const cell = document.createElement('td');
      cell.textContent = value;
      row.appendChild(cell);
    });

    table.appendChild(row);
  });
})