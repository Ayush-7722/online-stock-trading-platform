<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3Q0gpJLlm9maoEYz1zctqTWfpsyD65VohpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <div class="container mt-5">
        <h2>Edit User</h2>
        <form action="editUserServlet" method="post">
            <!-- Assume we receive the user ID, name, and email dynamically -->
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="<%= request.getParameter("name") %>" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="<%= request.getParameter("email") %>" required>
            </div>
            <button type="submit" class="btn btn-primary">Save Changes</button>
            <a href="userList.jsp" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>
