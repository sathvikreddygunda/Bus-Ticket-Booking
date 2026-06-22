import { useNavigate } from "react-router-dom";

const NavbarOperator = () => {

    const navigate = useNavigate();

    const logout = () => {
        localStorage.clear();
        navigate("/login");
    };

    const email = localStorage.getItem("email");

    return (
        <nav className="navbar navbar-expand-lg bg-body-tertiary">

            <div className="container-fluid">

                <a className="navbar-brand">
                    FastX Operator Portal
                </a>
                <button
                    className="btn btn-primary me-2"
                    onClick={() => navigate("/operator")}
                    >
                    Dashboard
                    </button>

                <div className="ms-auto d-flex align-items-center">

                    <span className="me-3">
                        Welcome {email}
                    </span>

                    <button
                        className="btn btn-outline-danger"
                        onClick={logout}
                    >
                        Logout
                    </button>

                </div>

            </div>

        </nav>
    );
};

export default NavbarOperator;