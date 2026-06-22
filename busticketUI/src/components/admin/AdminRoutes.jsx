import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AdminRoutes = () => {

    const navigate = useNavigate();
    const [routes, setRoutes] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [search, setSearch] = useState("");
    const [source, setSource] = useState("ALL");
    const [destination, setDestination] = useState("ALL");

    useEffect(() => {

        fetchRoutes();

    }, [page]);

    const fetchRoutes = async () => {

        try {

            const token =
                localStorage.getItem("token");

            const response =
                await axios.get(
                    `http://localhost:8080/api/route/all-page?page=${page}&size=5`,
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

            setRoutes(
                response.data.content
            );

            setTotalPages(
                response.data.totalPages
            );

        }
        catch (error) {

            console.error(error);

        }

    };

    const uniqueSources =
        [...new Set(
            routes.map(
                route => route.source
            )
        )];

    const uniqueDestinations =
        [...new Set(
            routes.map(
                route => route.destination
            )
        )];

    const filteredRoutes =
        routes.filter(route => {

            const matchesSearch =

                route.source
                    .toLowerCase()
                    .includes(
                        search.toLowerCase()
                    )

                ||

                route.destination
                    .toLowerCase()
                    .includes(
                        search.toLowerCase()
                    )

                ||

                route.busName
                    .toLowerCase()
                    .includes(
                        search.toLowerCase()
                    );

            const matchesSource =

                source === "ALL"

                ||

                route.source === source;

            const matchesDestination =

                destination === "ALL"

                ||

                route.destination === destination;

            return (

                matchesSearch

                &&

                matchesSource

                &&

                matchesDestination

            );

        });

    return (

        <div className="container mt-4">

            <div className="d-flex justify-content-between align-items-center mb-4">

                <div>

                    <button
                        className="btn btn-outline-secondary mb-2"
                        onClick={() =>
                            navigate("/admin")
                        }
                    >
                        ← Back
                    </button>

                    <h2>
                        Admin Routes
                    </h2>

                </div>

                <div className="d-flex gap-2">

                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search Route..."
                        value={search}
                        onChange={(e) =>
                            setSearch(
                                e.target.value
                            )
                        }
                    />

                    <select
                        className="form-select"
                        value={source}
                        onChange={(e) =>
                            setSource(
                                e.target.value
                            )
                        }
                    >
                        <option value="ALL">
                            All Sources
                        </option>

                        {
                            uniqueSources.map(
                                src => (

                                    <option
                                        key={src}
                                        value={src}
                                    >
                                        {src}
                                    </option>

                                )
                            )
                        }

                    </select>

                    <select
                        className="form-select"
                        value={destination}
                        onChange={(e) =>
                            setDestination(
                                e.target.value
                            )
                        }
                    >
                        <option value="ALL">
                            All Destinations
                        </option>

                        {
                            uniqueDestinations.map(
                                dest => (

                                    <option
                                        key={dest}
                                        value={dest}
                                    >
                                        {dest}
                                    </option>

                                )
                            )
                        }

                    </select>

                </div>

            </div>

            {
                filteredRoutes.map(
                    route => (

                        <div
                            key={route.routeId}
                            className="card mb-2 shadow-sm"
                        >

                            <div
                                className="card-body py-2 px-3"
                            >

                                <div className="row align-items-center">

                                    <div className="col-md-8">

                                        <h6 className="fw-bold mb-1">
                                            🚌 {route.busName}
                                        </h6>

                                        <p className="mb-1 small">

                                            <strong>
                                                Route:
                                            </strong>

                                            {" "}

                                            {route.source}

                                            {" → "}

                                            {route.destination}

                                        </p>

                                        <p className="mb-1 small">

                                            <strong>
                                                Pickup:
                                            </strong>

                                            {" "}

                                            {route.pickupPoint}

                                            {" | "}

                                            <strong>
                                                Drop:
                                            </strong>

                                            {" "}

                                            {route.dropPoint}

                                        </p>

                                        <p className="mb-0 small">

                                            <strong>
                                                Date:
                                            </strong>

                                            {" "}

                                            {route.journeyDate}

                                        </p>

                                    </div>

                                    <div className="col-md-4 text-end">

                                        <span
                                            className="badge bg-primary"
                                        >
                                            Route ID:
                                            {" "}
                                            {route.routeId}
                                        </span>

                                    </div>

                                </div>

                            </div>

                        </div>

                    ))
            }

            <div className="d-flex justify-content-center mt-4">

                <button
                    className="btn btn-outline-primary me-2"
                    disabled={page === 0}
                    onClick={() =>
                        setPage(
                            page - 1
                        )
                    }
                >
                    Previous
                </button>

                <span className="align-self-center">

                    Page

                    {" "}

                    {page + 1}

                    {" / "}

                    {totalPages}

                </span>

                <button
                    className="btn btn-outline-primary ms-2"
                    disabled={
                        page >= totalPages - 1
                    }
                    onClick={() =>
                        setPage(
                            page + 1
                        )
                    }
                >
                    Next
                </button>

            </div>

        </div>

    );
};

export default AdminRoutes;