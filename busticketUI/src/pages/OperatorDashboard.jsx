import { useEffect } from "react";
import NavbarOperator from "../components/NavbarOperator";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { getMyBuses } from "../store/action/busAction";
import "../styles/OperatorDashboard.css";

const OperatorDashboard = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {

        dispatch(getMyBuses());

    }, []);

    return (

        <div>

            <NavbarOperator />

            <div className="container mt-4">

                {/* Hero Section */}

                <div className="operator-hero text-center">

                    <h1 className="display-4 fw-bold">
                        FastX Operator Portal
                    </h1>

                    <p className="lead mb-0">
                        Manage buses and routes efficiently
                    </p>

                </div>

                {/* Stats Cards */}

                <div className="row g-2 mt-1">

                    <div className="col-md-3">

                        <div className="card stats-card">

                            <h2>🚌</h2>

                            <h3>
                                Buses
                            </h3>

                            <p>
                                Manage your fleet
                            </p>

                        </div>

                    </div>

                    <div className="col-md-3">

                        <div className="card stats-card">

                            <h2>🛣️</h2>

                            <h3>
                                Routes
                            </h3>

                            <p>
                                View all routes
                            </p>

                        </div>

                    </div>

                    <div className="col-md-3">

                        <div className="card stats-card">

                            <h2>📋</h2>

                            <h3>
                                Bookings
                            </h3>

                            <p>
                                Track reservations
                            </p>

                        </div>

                    </div>

                    <div className="col-md-3">

                        <div className="card stats-card">

                            <h2>🏢</h2>

                            <h3>
                                Operator
                            </h3>

                            <p>
                                FastX Partner
                            </p>

                        </div>

                    </div>

                </div>

                {/* Action Buttons */}

                <div className="row mt-2 g-3">

                    <div className="col">
                        <button
                            className="btn dashboard-btn add-bus-btn w-100"
                            onClick={() => navigate("/add-bus")}
                        >
                            Add Bus
                        </button>
                    </div>

                    <div className="col">
                        <button
                            className="btn dashboard-btn add-route-btn w-100"
                            onClick={() => navigate("/add-route")}
                        >
                            Add Route
                        </button>
                    </div>

                    <div className="col">
                        <button
                            className="btn dashboard-btn my-buses-btn w-100"
                            onClick={() => navigate("/my-buses")}
                        >
                            My Buses
                        </button>
                    </div>

                    <div className="col">
                        <button
                            className="btn dashboard-btn my-routes-btn w-100"
                            onClick={() => navigate("/my-routes")}
                        >
                            My Routes
                        </button>
                    </div>

                    <div className="col">
                        <button
                            className="btn dashboard-btn my-bookings-btn w-100"
                            onClick={() =>
                                navigate("/operator/bookings")
                            }
                        >
                            My Bookings
                        </button>
                    </div>

                </div>

                {/* Bottom Card */}

                <div className="card dashboard-info-card mt-5">

                    <div className="card-body text-center">

                        <h4>
                            Operator Workspace
                        </h4>

                        <p className="text-muted mb-0">
                            Use the options above to manage buses,
                            routes and bookings.
                        </p>

                    </div>

                </div>

            </div>

        </div>

    );
};

export default OperatorDashboard;