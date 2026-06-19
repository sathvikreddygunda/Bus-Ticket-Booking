import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function AddUser() {
    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [company, setCompany] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        const userData = {
            name,
            email,
            phone,
            company: {
                name: company
            }
        };

        try {
            const response = await axios.post("https://jsonplaceholder.typicode.com/users", userData);

            console.log(response.data);

            alert("User Added Successfully");

            navigate("/users");

        } catch (error) {
            console.log(error);
        }

    };

    return (
        <div className="card shadow">

            <div className="card-header bg-success text-white">
                <h4>Add User</h4>
            </div>

            <div className="card-body">

                <form onSubmit={handleSubmit}>

                    <div className="mb-3">
                        <label className="form-label">Name</label>

                        <input
                            type="text"
                            className="form-control"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Email</label>

                        <input
                            type="email"
                            className="form-control"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Phone</label>

                        <input
                            type="text"
                            className="form-control"
                            value={phone}
                            onChange={(e) => setPhone(e.target.value)}
                            required
                        />
                    </div>

                    <div className="mb-3">
                        <label className="form-label">Company Name</label>

                        <input
                            type="text"
                            className="form-control"
                            value={company}
                            onChange={(e) => setCompany(e.target.value)}
                            required
                        />
                    </div>

                    <button
                        type="submit"
                        className="btn btn-success"
                    >
                        Save User
                    </button>

                </form>

            </div>

        </div>
    );

}

export default AddUser;