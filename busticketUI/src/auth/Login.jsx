import axios from "axios";
import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { Link } from "react-router-dom";

const Login = () => {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errMsg, setErrMsg] = useState("");

    const navigate = useNavigate();
    const location = useLocation();

    const loginApi =
        "http://localhost:8080/api/auth/login";

    const onLogin = async (e) => {

        e.preventDefault();

        try {

            const response = await axios.post(
                loginApi,
                {
                    email: email,
                    password: password
                }
            );

            console.log(response.data);

            const token = response.data.token;
            const role = response.data.role;

            localStorage.setItem("token", token);
            localStorage.setItem("email", email);
            localStorage.setItem("role", role);

            console.log("Token Stored:", token);
            console.log("Role:", role);

            if (role === "ADMIN") {

                navigate("/admin");

            }
            else if (role === "OPERATOR") {

                navigate("/operator");

            }
            else {

                if (
                    location.state?.selectedRouteId
                ) {

                    navigate(
                        "/search-results",
                        {
                            state: {
                                selectedRouteId:
                                    location.state.selectedRouteId
                            }
                        }
                    );

                }
                else {

                    navigate("/customer");

                }

            }

        }
        catch (err) {

            console.log(err);

            setErrMsg(
                err.response?.data
            );
        }
    };

    return (
        <div className="container">
            <div className="row mt-4">

                <div className="col-sm-3"></div>

                <div className="col-md-6">

                    <div className="card">

                        <div className="card-header">
                            Login to FastX
                        </div>

                        <div className="card-body">

                            <form onSubmit={onLogin}>

                                {
                                    errMsg &&
                                    <div className="alert alert-danger">
                                        {errMsg}
                                    </div>
                                }

                                <div className="mb-4">
                                    <label className="form-label">
                                        Email <span className="text-danger">*</span>
                                    </label>

                                    <input
                                        type="email"
                                        className="form-control"
                                        onChange={(e) => setEmail(e.target.value)}
                                        required
                                    />
                                </div>

                                <div className="mb-4">
                                    <label className="form-label">
                                        Password <span className="text-danger">*</span>
                                    </label>

                                    <input
                                        type="password"
                                        className="form-control"
                                        onChange={(e) => setPassword(e.target.value)}
                                        minLength={6}
                                        required
                                    />
                                </div>

                                <div className="mb-4">
                                    <input
                                        type="submit"
                                        value="Login"
                                        className="btn btn-primary"
                                    />
                                </div>

                            </form>

                        </div>

                    </div>

                </div>

                <div className="col-sm-3"></div>

            </div>
        </div>
    );
}

export default Login;