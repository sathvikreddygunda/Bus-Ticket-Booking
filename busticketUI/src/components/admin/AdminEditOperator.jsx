import { useEffect, useState } from "react";
import axios from "axios";
import {
    useNavigate,
    useParams
} from "react-router-dom";

const AdminEditOperator = () => {

    const navigate = useNavigate();
    const { operatorId } = useParams();
    const [operatorName, setOperatorName] = useState("");
    const [companyName, setCompanyName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        fetchOperator();
    }, []);

    const fetchOperator =
        async () => {

            try {

                const token =
                    localStorage.getItem(
                        "token"
                    );

                const response =
                    await axios.get(
                        `http://localhost:8080/api/operator/get-one/${operatorId}`,
                        {
                            headers: {
                                Authorization:
                                    `Bearer ${token}`
                            }
                        }
                    );

                const data =
                    response.data;

                setOperatorName(
                    data.operatorName
                );

                setCompanyName(
                    data.companyName
                );

                setEmail(
                    data.email
                );

                setPhone(
                    data.phone
                );

            }
            catch (error) {

                console.error(error);

            }
        };

    const updateOperator =
        async (e) => {

            e.preventDefault();

            try {

                const token =
                    localStorage.getItem(
                        "token"
                    );

                await axios.put(
                    `http://localhost:8080/api/operator/update/${operatorId}`,
                    {
                        operatorName,
                        companyName,
                        email,
                        phone,
                        password
                    },
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

                alert(
                    "Operator Updated Successfully"
                );

                navigate(
                    "/admin/operators"
                );

            }
            catch (error) {

                console.error(error);

            }
        };

    return (

        <div className="container mt-4">
            <div className="mb-3">

                <button
                    className="btn btn-dark"
                    onClick={() =>
                        navigate("/admin/operators")
                    }
                    type="button"
                >
                    ← Operators
                </button>

            </div>

            <h2>
                Edit Operator
            </h2>

            <form
                onSubmit={
                    updateOperator
                }
            >

                <div className="mb-3">

                    <label>
                        Operator Name
                    </label>

                    <input
                        type="text"
                        className="form-control"
                        value={operatorName}
                        onChange={(e) =>
                            setOperatorName(
                                e.target.value
                            )
                        }
                    />

                </div>

                <div className="mb-3">

                    <label>
                        Company Name
                    </label>

                    <input
                        type="text"
                        className="form-control"
                        value={companyName}
                        onChange={(e) =>
                            setCompanyName(
                                e.target.value
                            )
                        }
                    />

                </div>

                <div className="mb-3">

                    <label>
                        Email
                    </label>

                    <input
                        type="email"
                        className="form-control"
                        value={email}
                        onChange={(e) =>
                            setEmail(
                                e.target.value
                            )
                        }
                    />

                </div>

                <div className="mb-3">

                    <label>
                        Phone
                    </label>

                    <input
                        type="text"
                        className="form-control"
                        value={phone}
                        onChange={(e) =>
                            setPhone(
                                e.target.value
                            )
                        }
                    />

                </div>

                <div className="mb-3">

                    <label>
                        New Password
                    </label>

                    <input
                        type="password"
                        className="form-control"
                        value={password}
                        onChange={(e) =>
                            setPassword(
                                e.target.value
                            )
                        }
                    />

                </div>

                <button
                    className="btn btn-primary"
                >
                    Update Operator
                </button>

            </form>

        </div>
    );
};

export default AdminEditOperator;