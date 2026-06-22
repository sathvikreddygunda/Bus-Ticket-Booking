import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const AdminCustomers = () => {

    const navigate = useNavigate();
    const [customers, setCustomers] = useState([]);
    const [search, setSearch] = useState("");

    useEffect(() => {
        fetchCustomers();
    }, []);

    const fetchCustomers = async () => {

        try {

            const token =
                localStorage.getItem("token");

            const response =
                await axios.get(
                    "http://localhost:8080/api/customer/all",
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

            setCustomers(
                response.data
            );

        }
        catch (error) {

            console.error(error);

        }

    };

    const filteredCustomers =
        customers.filter(customer =>

            customer.customerName
                ?.toLowerCase()
                .includes(
                    search.toLowerCase()
                )

            ||

            customer.email
                ?.toLowerCase()
                .includes(
                    search.toLowerCase()
                )

        );

    return (

        <div className="container mt-4">

            <div className="d-flex justify-content-between align-items-center mb-4">

                <div>

                    <button
                        className="btn btn-dark mb-2"
                        onClick={() =>
                            navigate("/admin")
                        }
                    >
                        ← Dashboard
                    </button>

                    <h2 className="fw-bold">
                        Customers
                    </h2>

                </div>

                <input
                    type="text"
                    className="form-control w-25"
                    placeholder="Search Customer..."
                    value={search}
                    onChange={(e) =>
                        setSearch(
                            e.target.value
                        )
                    }
                />

            </div>

            <div className="card shadow">

                <div className="card-body">

                    <table className="table table-hover">

                        <thead>

                            <tr>

                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>

                            </tr>

                        </thead>

                        <tbody>

                            {
                                filteredCustomers.map(
                                    customer => (

                                        <tr
                                            key={
                                                customer.customerId
                                            }
                                        >

                                            <td>
                                                {
                                                    customer.customerId
                                                }
                                            </td>

                                            <td>
                                                {
                                                    customer.customerName
                                                }
                                            </td>

                                            <td>
                                                {
                                                    customer.email
                                                }
                                            </td>

                                            <td>
                                                {
                                                    customer.phone
                                                }
                                            </td>

                                        </tr>

                                    ))
                            }

                        </tbody>

                    </table>

                </div>

            </div>

        </div>

    );

};

export default AdminCustomers;