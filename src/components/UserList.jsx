import { useEffect, useState } from "react";
import axios from "axios";

function UserList() {

    const [users, setUsers] = useState([]);

    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {

        try {

            const response = await axios.get(
                "https://jsonplaceholder.typicode.com/users"
            );

            setUsers(response.data);

        } catch (error) {

            console.log(error);

        }

    };

    const deleteUser = async (id) => {

        try {

            await axios.delete(
                `https://jsonplaceholder.typicode.com/users/${id}`
            );

            setUsers(
                users.filter((user) => user.id !== id)
            );

            alert("User Deleted Successfully");

        } catch (error) {

            console.log(error);

        }

    };

    return (
        <div className="card shadow">

            <div className="card-header bg-primary text-white">
                <h4>User List</h4>
            </div>

            <div className="card-body">

                <table className="table table-striped table-bordered table-hover">

                    <thead className="table-dark">

                        <tr>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Company</th>
                            <th>Action</th>
                        </tr>

                    </thead>

                    <tbody>

                        {
                            users.map((user) => (

                                <tr key={user.id}>

                                    <td>{user.name}</td>

                                    <td>{user.email}</td>

                                    <td>{user.phone}</td>

                                    <td>{user.company.name}</td>

                                    <td>

                                        <button
                                            className="btn btn-danger btn-sm"
                                            onClick={() => deleteUser(user.id)}
                                        >
                                            Delete
                                        </button>

                                    </td>

                                </tr>

                            ))
                        }

                    </tbody>

                </table>

            </div>

        </div>
    );
}

export default UserList;