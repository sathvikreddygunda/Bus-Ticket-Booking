import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AdminBuses = () => {

    const navigate = useNavigate();
    const [buses, setBuses] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [busType, setBusType] =
        useState("ALL");

    const [search, setSearch] = useState("");
    useEffect(() => {
        fetchBuses();
    }, [page]);

    const fetchBuses = async () => {

        try {

            const token =
                localStorage.getItem("token");

            const response =
                await axios.get(
                    `http://localhost:8080/api/bus/all-page?page=${page}&size=5`,
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

            setBuses(
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

    const filteredBuses =
        buses.filter(bus => {

            const matchesType =
                busType === "ALL"
                ||
                bus.busType === busType;

            const matchesSearch =
                bus.busName
                    .toLowerCase()
                    .includes(
                        search.toLowerCase()
                    );

            return (
                matchesType
                &&
                matchesSearch
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
                        Buses List
                    </h2>

                </div>

                <div className="d-flex gap-2">

                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search Bus..."
                        value={search}
                        onChange={(e) =>
                            setSearch(
                                e.target.value
                            )
                        }
                    />

                    <select
                        className="form-select"
                        value={busType}
                        onChange={(e) =>
                            setBusType(
                                e.target.value
                            )
                        }
                    >
                        <option value="ALL">
                            All
                        </option>

                        <option value="AC">
                            AC
                        </option>

                        <option value="NON_AC">
                            NON_AC
                        </option>

                    </select>

                </div>

            </div>

            {
                filteredBuses.map(bus => (

                    <div
                        key={bus.busId}
                        className="card mb-2 shadow-sm"
                    >

                        <div className="card-body py-2 px-3">

                            <div className="row align-items-center">

                                <div className="col-md-8">

                                    <h6 className="fw-bold mb-1">
                                        🚌 {bus.busName}
                                    </h6>

                                    <p className="mb-1 small">

                                        <strong>
                                            Number:
                                        </strong>

                                        {" "}

                                        {bus.busNumber}

                                        {" | "}

                                        <strong>
                                            Type:
                                        </strong>

                                        {" "}

                                        {bus.busType}

                                    </p>

                                    <p className="mb-0 small">

                                        <strong>
                                            Operator:
                                        </strong>

                                        {" "}

                                        {
                                            bus.operator
                                                ?.operatorName
                                        }

                                    </p>

                                </div>

                                <div className="col-md-4 text-end">

                                    <h6
                                        className="text-success fw-bold"
                                    >
                                        ₹
                                        {
                                            bus.fareAmount
                                        }
                                    </h6>

                                    <span
                                        className="badge bg-primary"
                                    >
                                        {
                                            bus.totalSeats
                                        }
                                        {" "}
                                        Seats
                                    </span>

                                </div>

                            </div>

                        </div>

                    </div>

                ))
            }

            <div
                className="d-flex justify-content-center mt-4"
            >

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

                <span
                    className="align-self-center"
                >
                    Page
                    {" "}
                    {page + 1}
                    {" / "}
                    {totalPages}
                </span>

                <button
                    className="btn btn-outline-primary ms-2"
                    disabled={
                        page >=
                        totalPages - 1
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

export default AdminBuses;