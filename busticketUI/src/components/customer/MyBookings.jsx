import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const MyBookings = () => {
    const navigate = useNavigate();
    const [bookings, setBookings] = useState([]);
    const [page, setPage] = useState(0);
    const [status, setStatus] = useState("ALL");


    useEffect(() => {
        fetchBookings();
    }, [page]);

    const [totalPages,
        setTotalPages] =
        useState(0);

    const fetchBookings = async () => {

        try {
            const token = localStorage.getItem("token");
            const response =
                await axios.get(
                    `http://localhost:8080/api/booking/my-bookings?page=${page}&size=5`,
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

        } catch (error) {

            console.error(error);

        }
    };

    const cancelBooking = async (bookingId) => {

        try {

            const token =
                localStorage.getItem("token");

            await axios.put(
                `http://localhost:8080/api/booking/cancel/${bookingId}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            fetchBookings();

        } catch (error) {

            console.error(error);

            alert("Unable to cancel booking");

        }
    };
    // filters All, Booked, Cancelled
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

            {/* Header */}

            <div className="d-flex justify-content-between align-items-center mb-4">

                <div>

                    <button
                        className="btn btn-outline-secondary mb-2"
                        onClick={() => navigate(-1)}
                    >
                        ← Back
                    </button>

                    <h2 className="fw-bold mb-0">
                        My Bookings
                    </h2>

                    <small className="text-muted">
                        View and manage your bookings
                    </small>

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
                filteredBookings.length === 0 ?

                    (
                        <div className="alert alert-info">
                            No Bookings Found
                        </div>
                    )
                    :
                    filteredBookings.map((booking) => (

                        <div
                            key={booking.bookingId}
                            className="card shadow-sm mb-3"
                            style={{
                                borderRadius: "14px"
                            }}
                        >

                            <div className="card-body p-3">

                                <div className="row align-items-center">

                                    {/* Left Side */}

                                    <div className="col-md-8">

                                        <h5 className="fw-bold mb-2">
                                            🚌 {booking.busName}
                                        </h5>

                                        <p className="mb-1">
                                            <strong>Route:</strong>
                                            {" "}
                                            {booking.source}
                                            {" → "}
                                            {booking.destination}
                                        </p>

                                        <p className="mb-1">
                                            <strong>Passenger:</strong>
                                            {" "}
                                            {
                                                booking.passengerNames.join(", ")
                                            }
                                        </p>

                                        <p className="mb-1">
                                            <strong>Seats:</strong>
                                            {" "}
                                            {
                                                booking.seatNumbers.join(", ")
                                            }
                                        </p>

                                        <p className="mb-0">
                                            <strong>Booking Date:</strong>
                                            {" "}
                                            {
                                                new Date(
                                                    booking.bookingDate
                                                ).toLocaleDateString()
                                            }
                                        </p>

                                    </div>

                                    {/* Right Side */}

                                    <div className="col-md-4 text-md-end mt-3 mt-md-0">

                                        <h4 className="text-success fw-bold mb-2">
                                            ₹{booking.totalAmount}
                                        </h4>

                                        <span
                                            className={
                                                booking.bookingStatus === "BOOKED"
                                                    ? "badge bg-success"
                                                    : "badge bg-danger"
                                            }
                                        >
                                            {booking.bookingStatus}
                                        </span>

                                        {
                                            booking.bookingStatus === "BOOKED" &&

                                            <div>

                                                <button
                                                    className="btn btn-danger btn-sm mt-3"
                                                    onClick={() => {

                                                        const confirmCancel =
                                                            window.confirm(
                                                                "Are you sure you want to cancel this booking?"
                                                            );

                                                        if (confirmCancel) {

                                                            cancelBooking(
                                                                booking.bookingId
                                                            );

                                                        }

                                                    }}
                                                >
                                                    Cancel Booking
                                                </button>

                                            </div>
                                        }

                                    </div>

                                </div>

                            </div>

                        </div>

                    ))
            }

            {/* Pagination */}

            {
                totalPages > 0 &&

                <div className="d-flex justify-content-center align-items-center mt-4">

                    <button
                        className="btn btn-outline-primary me-2"
                        disabled={page === 0}
                        onClick={() =>
                            setPage(page - 1)
                        }
                    >
                        Previous
                    </button>

                    <span className="fw-semibold">
                        Page {page + 1}
                        {" / "}
                        {totalPages}
                    </span>

                    <button
                        className="btn btn-outline-primary ms-2"
                        disabled={
                            page >= totalPages - 1
                        }
                        onClick={() =>
                            setPage(page + 1)
                        }
                    >
                        Next
                    </button>

                </div>
            }

        </div>

    );
};

export default MyBookings;