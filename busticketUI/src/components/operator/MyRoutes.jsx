import axios from "axios";
import { useEffect, useState } from "react";
import NavbarOperator from "../NavbarOperator";
import { useNavigate } from "react-router-dom";

const MyRoutes = () => {
    const navigate = useNavigate();

    const [routes, setRoutes] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);

    const recordsPerPage = 5;

    const lastIndex =
        currentPage * recordsPerPage;

    const firstIndex =
        lastIndex - recordsPerPage;

    const currentRoutes =
        routes.slice(
            firstIndex,
            lastIndex
        );

    const totalPages =
        Math.ceil(
            routes.length /
            recordsPerPage
        );

    const myRoutesApi =
        "http://localhost:8080/api/route/my-routes";

    const config_details = {
        headers: {
            Authorization:
                "Bearer " + localStorage.getItem("token")
        }
    };

    useEffect(() => {

        const getMyRoutes = async () => {

            try {

                const response =
                    await axios.get(
                        myRoutesApi,
                        config_details
                    );

                setRoutes(response.data);

            } catch (err) {

                console.log(err);

            }
        };

        getMyRoutes();

    }, []);
    const formatDateTime = (dateTime) => {

        return new Date(dateTime)
            .toLocaleString("en-IN", {

                day: "2-digit",
                month: "short",
                year: "numeric",

                hour: "2-digit",
                minute: "2-digit",

                hour12: true
            });
    };
    const deleteRoute =
        async (routeId) => {

            if (
                !window.confirm(
                    "Are you sure you want to delete this route?"
                )
            ) {
                return;
            }

            try {

                await axios.delete(
                    `http://localhost:8080/api/route/delete/${routeId}`,
                    config_details
                );

                setRoutes(
                    routes.filter(
                        route =>
                            route.routeId !== routeId
                    )
                );

            }
            catch (err) {

                console.log(err);

            }
        };

    return (
        <>
            <NavbarOperator />

            <div className="container mt-4">

                <div className="card shadow">

                    <div className="card-header">
                        My Routes
                    </div>

                    <div className="card-body">

                        <table className="table table-bordered table-striped">

                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Source</th>
                                    <th>Destination</th>
                                    <th>Pickup</th>
                                    <th>Drop</th>
                                    <th>Journey Date</th>
                                    <th>Departure</th>
                                    <th>Arrival</th>
                                    <th>Bus</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>

                            <tbody>

                                {
                                    currentRoutes.map((route) => (

                                        <tr key={route.routeId}>

                                            <td>{route.routeId}</td>

                                            <td>{route.source}</td>

                                            <td>{route.destination}</td>

                                            <td>{route.pickupPoint}</td>

                                            <td>{route.dropPoint}</td>

                                            <td>
                                                {new Date(route.journeyDate)
                                                    .toLocaleDateString(
                                                        "en-IN",
                                                        {
                                                            day: "2-digit",
                                                            month: "short",
                                                            year: "numeric"
                                                        }
                                                    )}
                                            </td>

                                            <td>
                                                {formatDateTime(route.departureTime)}
                                            </td>

                                            <td>
                                                {formatDateTime(route.arrivalTime)}
                                            </td>

                                            <td>{route.busName}</td>
                                            <td>

                                                <button
                                                    className="btn btn-warning btn-sm me-2"
                                                    onClick={() =>
                                                        navigate(
                                                            `/operator/edit-route/${route.routeId}`
                                                        )
                                                    }
                                                >
                                                    <i className="bi bi-pencil-square"></i>
                                                </button>

                                                <button
                                                    className="btn btn-danger btn-sm"
                                                    onClick={() =>
                                                        deleteRoute(route.routeId)
                                                    }
                                                >
                                                    <i className="bi bi-trash"></i>
                                                </button>

                                            </td>

                                        </tr>

                                    ))
                                }

                            </tbody>

                        </table>
                        {totalPages > 1 && (

                            <div className="d-flex justify-content-center mt-3">

                                <button
                                    className="btn btn-success me-2"
                                    disabled={currentPage === 1}
                                    onClick={() =>
                                        setCurrentPage(
                                            currentPage - 1
                                        )
                                    }
                                >
                                    Previous
                                </button>

                                <span className="fw-bold mx-3 mt-2">
                                    Page {currentPage} / {totalPages}
                                </span>

                                <button
                                    className="btn btn-success ms-2"
                                    disabled={currentPage === totalPages}
                                    onClick={() =>
                                        setCurrentPage(
                                            currentPage + 1
                                        )
                                    }
                                >
                                    Next
                                </button>

                            </div>

                        )}

                    </div>

                </div>

            </div>
        </>
    );
};

export default MyRoutes;