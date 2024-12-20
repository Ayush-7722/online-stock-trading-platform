<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3Q0gpJLlm9maoEYz1zctqTWfpsyD65VohpuuCOmLASjC"
          crossorigin="anonymous">
</head>
<body>
    <div class="container">
        <h3>User Registration</h3>
        <form id="registrationForm">
            <div class="mb-3">
                <label for="exampleFormControlInput1" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" placeholder="name@example.com">
                <small id="emailError" class="text-danger"></small>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlTextarea1" class="form-label">Example textarea</label>
                <textarea class="form-control" id="textarea" rows="3"></textarea>
                <small id="textareaError" class="text-danger"></small>
            </div>
            <button type="button" class="btn btn-primary" onclick="validateForm()">Register</button>
            <button type="reset" class="btn btn-secondary">Reset</button>
        </form>
    </div>

    <script>
        function validateForm() {
            // Get form elements
            const email = document.getElementById("email").value.trim();
            const textarea = document.getElementById("textarea").value.trim();

            // Error elements
            const emailError = document.getElementById("emailError");
            const textareaError = document.getElementById("textareaError");

            let isValid = true;

            // Validate email
            if (email === "") {
                emailError.textContent = "Email is required.";
                isValid = false;
            } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
                emailError.textContent = "Please enter a valid email address.";
                isValid = false;
            } else {
                emailError.textContent = "";
            }

            // Validate textarea
            if (textarea === "") {
                textareaError.textContent = "This field cannot be empty.";
                isValid = false;
            } else {
                textareaError.textContent = "";
            }

            if (isValid) {
                alert("Form submitted successfully!");
                // Optionally, submit the form via AJAX or a similar method.
            }
        }
    </script>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
            integrity="sha384-IQsoLXl5PILfybNUbU6tzQe93jv4x2r9ZXmr60pNhCQe+ErSR3uy6iNqK4zAzO8/"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"
            integrity="sha384-cVKIPhG81rKr9fKqQ0F1C5QpebU7xJ92jCuKYQe9I/gJc5D+pPjHM+0I/Hcv+kt/2"
            crossorigin="anonymous"></script>
</body>
</html>
