import { useNavigate } from "react-router-dom";

const NavbarCustomer = () => {

    const navigate = useNavigate();

    const logout = () => {
        localStorage.clear();
        navigate("/login");
    };

    const email = localStorage.getItem("email");

    return (
        <nav className="navbar navbar-expand-lg bg-body-tertiary">

            <div className="container-fluid">

                <span
                    className="navbar-brand fw-bold"
                    style={{ cursor: "pointer" }}
                    onClick={() =>
                        navigate("/customer")
                    }
                >
                    FastX
                </span>

                <div className="navbar-nav ms-4">

                    <button
                        className="btn btn-outline-primary me-2"
                        onClick={() =>
                            navigate("/customer")
                        }
                    >
                        Dashboard
                    </button>

                    <button
                        className="btn btn-outline-success me-2"
                        onClick={() =>
                            navigate("/my-bookings")
                        }
                    >
                        My Bookings
                    </button>

                </div>

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

export default NavbarCustomer;