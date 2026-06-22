import NavbarAdmin from "../components/NavbarAdmin";
import { useNavigate } from "react-router-dom";
import "../styles/AdminDashboard.css";
import { useEffect, useState } from "react";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    // ArcElement,
    Title,
    Tooltip,
    Legend
} from "chart.js";

import { Bar } from "react-chartjs-2";
// import { Bar, Pie } from "react-chartjs-2";

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    // ArcElement,
    Title,
    Tooltip,
    Legend
);
import axios from "axios";

const AdminDashboard = () => {
    const navigate = useNavigate();
    const [stats, setStats] = useState({
        customerCount: 0,
        operatorCount: 0,
        busCount: 0,
        routeCount: 0,
        bookingCount: 0,
        pendingOperatorCount: 0
    });
    // const [bookedCount,
    //     setBookedCount] =
    //     useState(0);

    // const [cancelledCount,
    //     setCancelledCount] =
    //     useState(0);

    useEffect(() => {

        fetchStats();

    }, []);

    const fetchStats = async () => {

        try {

            const token =
                localStorage.getItem("token");

            const response =
                await axios.get(
                    "http://localhost:8080/api/admin/stats",
                    {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    }
                );

            setStats(response.data);

            // const bookedResponse =
            //     await axios.get(
            //         "http://localhost:8080/api/booking/count/booked",
            //         {
            //             headers: {
            //                 Authorization:
            //                     `Bearer ${token}`
            //             }
            //         }
            //     );

            // const cancelledResponse =
            //     await axios.get(
            //         "http://localhost:8080/api/booking/count/cancelled",
            //         {
            //             headers: {
            //                 Authorization:
            //                     `Bearer ${token}`
            //             }
            //         }
            //     );

            // setBookedCount(
            //     bookedResponse.data
            // );

            // setCancelledCount(
            //     cancelledResponse.data
            // );

        }
        catch (error) {

            console.log(error);

        }
    };

    const chartData = {
        labels: [
            "Customers",
            "Operators",
            "Buses",
            "Routes",
            "Bookings"

        ],

        datasets: [
            {
                label: "Count",

                data: [
                    stats.customerCount,
                    stats.operatorCount,
                    stats.busCount,
                    stats.routeCount,
                    stats.bookingCount
                ],

                backgroundColor: [
                    "#0d6efd",
                    "#198754",
                    "#fd7e14",
                    "#6f42c1",
                    "#dc3545"

                ],

                borderRadius: 8
            }
        ]
    };
    // const pieData = {

    //     labels: [
    //         "Booked",
    //         "Cancelled"
    //     ],

    //     datasets: [
    //         {
    //             data: [
    //                 bookedCount,
    //                 cancelledCount
    //             ],

    //             backgroundColor: [
    //                 "#010c0d",
    //                 "#f5f3f3"
    //             ],

    //         }
    //     ]
    // };

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("role");

        navigate("/");

    };

    return (

        <div>

            <NavbarAdmin />

            <div className="container mt-4">

                <div className="admin-hero text-center">

                    <h1 className="display-4 fw-bold">
                        FastX Admin Portal
                    </h1>

                    <p className="lead mb-0">
                        Monitor and manage the platform
                    </p>

                </div>

                <div className="row g-3 mt-2">

                    <div className="col-md-3">

                        <div className="card stats-card">

                            <div className="dashboard-icon">
                                👥
                            </div>

                            <h2>{stats.customerCount}</h2>

                            <p>
                                Customers
                            </p>

                        </div>

                    </div>

                    <div className="col-md-3">

                        <div className="card stats-card">

                            <div className="dashboard-icon">
                                🏢
                            </div>

                            <h2>{stats.operatorCount}</h2>

                            <p>
                                Operators
                            </p>

                        </div>

                    </div>

                    <div className="col-md-3">

                        <div className="card stats-card">

                            <div className="dashboard-icon">
                                🚌
                            </div>

                            <h2>{stats.busCount}</h2>

                            <p>
                                Buses
                            </p>

                        </div>

                    </div>

                    <div className="col-md-3">

                        <div className="card stats-card">

                            <div className="dashboard-icon">
                                🛣️
                            </div>

                            <h2>{stats.routeCount}</h2>

                            <p>
                                Routes
                            </p>

                        </div>

                    </div>

                </div>
                <div className="card mt-3 p-3">

                    <h5 className="text-center fw-bold mb-3">
                        FastX Platform Statistics
                    </h5>

                    <div
                        style={{
                            height: "350px"
                        }}
                    >
                        <Bar
                            data={chartData}
                            options={{
                                responsive: true,

                                maintainAspectRatio: false,

                                plugins: {

                                    legend: {
                                        display: false
                                    },
                                },

                                scales: {

                                    y: {
                                        beginAtZero: true
                                    }

                                }

                            }}
                        />
                    </div>

                    {/* <div className="card mt-4 p-3">

                        <h5 className="text-center fw-bold mb-3">
                            Booking Status
                        </h5>

                        <div
                            style={{
                                height: "350px"
                            }}
                        >
                            <Pie
                                data={pieData}
                                options={{
                                    maintainAspectRatio: false
                                }}
                            />
                        </div>

                    </div> */}

                </div>


                <div className="row mt-3 g-2">

                    <div className="col">

                        <button
                            className="btn admin-btn customers-btn"
                            onClick={() =>
                                navigate("/admin/customers")
                            }
                        >
                            Customers
                        </button>

                    </div>

                    <div className="col">

                        <button
                            className="btn admin-btn operators-btn"
                            onClick={() =>
                                navigate("/admin/operators")
                            }
                        >
                            Operators
                        </button>

                    </div>

                    <div className="col">

                        <button
                            className="btn admin-btn buses-btn"
                            onClick={() =>
                                navigate("/admin/buses")
                            }
                        >
                            Buses
                        </button>

                    </div>

                    <div className="col">

                        <button
                            className="btn admin-btn routes-btn"
                            onClick={() =>
                                navigate("/admin/routes")
                            }
                        >
                            Routes
                        </button>

                    </div>

                    <div className="col-md-2">

                        <button
                            className="btn admin-btn bookings-btn"
                            onClick={() =>
                                navigate("/admin/bookings")
                            }
                        >
                            Bookings
                        </button>

                    </div>
                    <div className="col">

                        <button
                            className="btn admin-btn pending-operators-btn"
                            onClick={() =>
                                navigate("/admin/add-operator")
                            }
                        >
                            Add Operator
                        </button>

                    </div>

                </div>

            </div>

        </div>

    );
};

export default AdminDashboard;