import { Link, useNavigate } from "react-router-dom";


const NavbarAdmin = () => {

    const navigate = useNavigate();

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("role");

        navigate("/");
    };

    return (

        <nav className="navbar navbar-dark bg-dark navbar-expand-lg">

            <div className="container-fluid">

                <Link
                    className="navbar-brand ms-3"
                    to="/admin-dashboard"
                >
                    FastX Admin Portal
                </Link>

                <button
                    className="btn btn-outline-danger me-3"
                    onClick={handleLogout}
                >
                    Logout
                </button>

            </div>

        </nav>

    );
};

export default NavbarAdmin;