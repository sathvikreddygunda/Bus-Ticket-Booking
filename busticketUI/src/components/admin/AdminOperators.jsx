import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AdminOperators = () => {

    const navigate = useNavigate();
    const [operators, setOperators] = useState([]);
    const [search, setSearch] = useState("");

    useEffect(() => {

        fetchOperators();

    }, []);

    const fetchOperators = async () => {

        try {

            const token =
                localStorage.getItem("token");

            const response =
                await axios.get(
                    "http://localhost:8080/api/operator/all",
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

            setOperators(
                response.data
            );

        }
        catch (error) {

            console.error(error);

        }

    };

    const filteredOperators =
        operators.filter(operator =>

            operator.operatorName
                ?.toLowerCase()
                .includes(
                    search.toLowerCase()
                )

            ||

            operator.email
                ?.toLowerCase()
                .includes(
                    search.toLowerCase()
                )

            ||

            operator.companyName
                ?.toLowerCase()
                .includes(
                    search.toLowerCase()
                )

        );

    const deleteOperator =
        async (operatorId) => {

            if (
                !window.confirm(
                    "Are you sure you want to delete this operator?"
                )
            ) {
                return;
            }

            try {

                const token =
                    localStorage.getItem(
                        "token"
                    );

                await axios.delete(
                    `http://localhost:8080/api/operator/delete/${operatorId}`,
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

                setOperators(
                    operators.filter(
                        operator =>
                            operator.operatorId !== operatorId
                    )
                );

            }
            catch (error) {

                console.error(error);

            }
        };

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
                        Operators
                    </h2>

                </div>

                <input
                    type="text"
                    className="form-control w-25"
                    placeholder="Search Operator..."
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
                                <th>Operator</th>
                                <th>Company</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Action</th>


                            </tr>

                        </thead>

                        <tbody>

                            {
                                filteredOperators.map(
                                    operator => (

                                        <tr
                                            key={
                                                operator.operatorId
                                            }
                                        >

                                            <td>
                                                {
                                                    operator.operatorId
                                                }
                                            </td>

                                            <td>
                                                {
                                                    operator.operatorName
                                                }
                                            </td>

                                            <td>
                                                {
                                                    operator.companyName
                                                }
                                            </td>

                                            <td>
                                                {
                                                    operator.email
                                                }
                                            </td>

                                            <td>
                                                {
                                                    operator.phone
                                                }
                                            </td>

                                            <td>

                                                <button
                                                    className="btn btn-warning btn-sm me-2"
                                                    onClick={() =>
                                                        navigate(
                                                            `/admin/edit-operator/${operator.operatorId}`
                                                        )
                                                    }
                                                >
                                                    <i className="bi bi-pencil-square"></i>
                                                </button>

                                                <button
                                                    className="btn btn-danger btn-sm"
                                                    onClick={() =>
                                                        deleteOperator(
                                                            operator.operatorId
                                                        )
                                                    }
                                                >
                                                    <i className="bi bi-trash"></i>
                                                </button>

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

export default AdminOperators;