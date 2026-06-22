import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg bg-white shadow-sm">

      <div className="container">

        <Link
          className="navbar-brand fw-bold fs-2 text"
          to="/"
        >
          🚌 FastX
        </Link>

        <div className="ms-auto">

          <Link
            to="/login"
            className="btn btn-outline-primary me-2"
          >
            Login
          </Link>

          <Link
            to="/register"
            className="btn btn-primary"
          >
            Register
          </Link>

        </div>

      </div>

    </nav>
  );
};

export default Navbar;