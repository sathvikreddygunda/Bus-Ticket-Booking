import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";

const Register = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [errMsg, setErrMsg] = useState("");
    const [successMsg, setSuccessMsg] = useState("");

    const registerUser = async (e) => {

        console.log("REGISTER BUTTON CLICKED");

        e.preventDefault();

        setErrMsg("");
        setSuccessMsg("");

        if (password.length < 6) {

            setErrMsg(
                "Password must be at least 6 characters"
            );

            return;
        }

        if (password !== confirmPassword) {

            setErrMsg(
                "Passwords do not match"
            );

            return;
        }

        try {

            const response =
                await axios.post(
                    "http://localhost:8080/api/auth/register",
                    {
                        email,
                        password
                    }
                );

            console.log(response.data);

            setSuccessMsg(
                "Registration Successful"
            );

            setEmail("");
            setPassword("");
            setConfirmPassword("");

        }
        catch (error) {

            console.error(error);

            setErrMsg(
                "Registration Failed"
            );

        }
    };

    return (

        <div className="container mt-5">

            <div className="row justify-content-center">

                <div className="col-md-5">

                    <div className="card shadow">

                        <div className="card-header text-center">
                            <h3>Register</h3>
                        </div>

                        <div className="card-body">

                            {
                                errMsg &&
                                <div className="alert alert-danger">
                                    {errMsg}
                                </div>
                            }

                            {
                                successMsg &&
                                <div className="alert alert-success">
                                    {successMsg}
                                </div>
                            }

                            <form onSubmit={registerUser}>

                                <div className="mb-3">

                                    <label className="form-label">
                                        Email <span className="text-danger">*</span>
                                    </label>

                                    <input
                                        type="email"
                                        className="form-control"
                                        placeholder="Enter your email"
                                        value={email}
                                        onChange={(e) =>
                                            setEmail(e.target.value)
                                        }
                                        required
                                    />

                                </div>

                                <div className="mb-3">

                                    <label className="form-label">
                                        Password <span className="text-danger">*</span>
                                    </label>

                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Enter password"
                                        value={password}
                                        onChange={(e) =>
                                            setPassword(e.target.value)
                                        }
                                        required
                                    />

                                </div>

                                <div className="mb-3">

                                    <label className="form-label">
                                        Confirm Password <span className="text-danger">*</span>
                                    </label>

                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Confirm password"
                                        value={confirmPassword}
                                        onChange={(e) =>
                                            setConfirmPassword(e.target.value)
                                        }
                                        required
                                    />

                                </div>

                                <button
                                    type="submit"
                                    className="btn btn-danger w-100"
                                >
                                    Register
                                </button>

                            </form>

                            <div className="text-center mt-3">

                                Already have an account?

                                <Link
                                    to="/login"
                                    className="ms-2 text-decoration-none"
                                >
                                    Login
                                </Link>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    );
};

export default Register;