import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import NavbarAdmin from "../NavbarAdmin";

const AddOperator = () => {

    const navigate = useNavigate();
    const [operatorName, setOperatorName] = useState("");
    const [companyName, setCompanyName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [password, setPassword] = useState("");
    const [successMsg, setSuccessMsg] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const addOperator = async (e) => {
        e.preventDefault();

        try {

            const token =
                localStorage.getItem(
                    "token"
                );

            await axios.post(
                "http://localhost:8080/api/operator/register",
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

            setSuccessMsg(
                "Operator Added Successfully"
            );

            setErrMsg("");

            setOperatorName("");
            setCompanyName("");
            setEmail("");
            setPhone("");
            setPassword("");

        }
        catch (error) {
            console.error(error);
            setSuccessMsg("");
            setErrMsg(
                "Failed To Add Operator"
            );

        }
    };

    return (

        <>
            <NavbarAdmin />

            <div className="container mt-4">

                <div className="d-flex justify-content-between align-items-center mb-4">

                    <h2>
                        Add Operator
                    </h2>

                    <button
                        className="btn btn-dark"
                        onClick={() =>
                            navigate("/admin")
                        }
                    >
                        ← Dashboard
                    </button>

                </div>

                <div className="card shadow">

                    <div className="card-body">

                        <form onSubmit={addOperator}>

                            <div className="mb-3">

                                <label className="form-label">
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
                                    required
                                />

                            </div>

                            <div className="mb-3">

                                <label className="form-label">
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
                                    required
                                />

                            </div>

                            <div className="mb-3">

                                <label className="form-label">
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
                                    required
                                />

                            </div>

                            <div className="mb-3">

                                <label className="form-label">
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
                                    required
                                />

                            </div>

                            <div className="mb-3">

                                <label className="form-label">
                                    Password
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
                                    required
                                />

                            </div>

                            {
                                successMsg && (

                                    <div className="alert alert-success">
                                        {successMsg}
                                    </div>

                                )
                            }

                            {
                                errMsg && (

                                    <div className="alert alert-danger">
                                        {errMsg}
                                    </div>

                                )
                            }

                            <button
                                type="submit"
                                className="btn btn-success"
                            >
                                Add Operator
                            </button>

                        </form>

                    </div>

                </div>

            </div>

        </>
    );
};

export default AddOperator;