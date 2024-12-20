<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3Q0gpJLlm9maoEYz1zctqTWfpsyD65VohpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <div class="container mt-5">
        <h2>User Management</h2>
        <a href="addUser.jsp" class="btn btn-success mb-3">Add New User</a>
        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Dynamically populate rows with data from the backend -->
                <tr>
                    <td>1</td>
                    <td>John Doe</td>
                    <td>john.doe@example.com</td>
                    <td>
                        <a href="editUser.jsp?id=1" class="btn btn-primary btn-sm">Edit</a>
                        <a href="deleteUser?id=1" class="btn btn-danger btn-sm">Delete</a>
                    </td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Jane Smith</td>
                    <td>jane.smith@example.com</td>
                    <td>
                        <a href="editUser.jsp?id=2" class="btn btn-primary btn-sm">Edit</a>
                        <a href="deleteUser?id=2" class="btn btn-danger btn-sm">Delete</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
