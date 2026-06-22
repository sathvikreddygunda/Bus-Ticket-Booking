import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AdminBookings = () => {

    const navigate = useNavigate();
    const [bookings, setBookings] = useState([]);
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [status, setStatus] = useState("ALL");

    useEffect(() => {
        fetchBookings();
    }, [page]);

    const fetchBookings =
        async () => {

            try {

                const token =
                    localStorage.getItem(
                        "token"
                    );

                const response =
                    await axios.get(
                        `http://localhost:8080/api/booking/all-page?page=${page}&size=5`,
                        {
                            headers: {
                                Authorization:
                                    `Bearer ${token}`
                            }
                        }
                    );

                setBookings(
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

    const filteredBookings =
        status === "ALL"

            ? bookings

            : bookings.filter(
                booking =>
                    booking.bookingStatus
                    === status
            );

    return (

        <div className="container mt-4">

            <div className="d-flex justify-content-between align-items-center mb-4">

                <div>

                    <button
                        className="btn btn-outline-secondary mb-2"
                        onClick={() => navigate("/admin")}
                    >
                        ← Back
                    </button>

                    <h2 className="mb-0">
                        Admin Bookings
                    </h2>

                </div>

                <select
                    className="form-select w-auto"
                    value={status}
                    onChange={(e) =>
                        setStatus(e.target.value)
                    }
                >
                    <option value="ALL">All</option>
                    <option value="BOOKED">Booked</option>
                    <option value="CANCELLED">Cancelled</option>
                </select>

            </div>

            {
                filteredBookings.map(
                    booking => (

                        <div
                            key={booking.bookingId}
                            className="card mb-2 shadow-sm"
                            style={{
                                borderRadius: "12px"
                            }}
                        >

                            <div className="card-body py-2 px-3">

                                <div className="row align-items-center">

                                    <div className="col-md-8">

                                        <h6 className="fw-bold mb-1">
                                            🚌 {booking.busName}
                                        </h6>

                                        <p className="mb-1 small">

                                            <strong>
                                                Customer:
                                            </strong>

                                            {" "}

                                            {booking.customerName}

                                            {" | "}

                                            <strong>
                                                Route:
                                            </strong>

                                            {" "}

                                            {booking.source}

                                            {" → "}

                                            {booking.destination}

                                        </p>

                                        <p className="mb-0 small">

                                            <strong>
                                                Seats:
                                            </strong>

                                            {" "}

                                            {
                                                booking.seatNumbers?.join(", ")
                                            }

                                        </p>

                                    </div>

                                    <div className="col-md-4 text-end">

                                        <h6
                                            className="text-success fw-bold mb-1"
                                        >
                                            ₹{booking.totalAmount}
                                        </h6>

                                        <span
                                            className={
                                                booking.bookingStatus === "BOOKED"

                                                    ?

                                                    "badge bg-success"

                                                    :

                                                    "badge bg-danger"
                                            }
                                            style={{
                                                fontSize: "0.75rem"
                                            }}
                                        >
                                            {booking.bookingStatus}
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

export default AdminBookings;