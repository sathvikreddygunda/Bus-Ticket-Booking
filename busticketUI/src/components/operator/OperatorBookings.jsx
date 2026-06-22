import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const OperatorBookings = () => {
    const navigate = useNavigate();

    const [bookings, setBookings] =
        useState([]);

    const [status, setStatus] =
        useState("ALL");

    useEffect(() => {

        fetchBookings();

    }, []);

    const fetchBookings = async () => {

        try {

            const token =
                localStorage.getItem("token");

            const response =
                await axios.get(
                    "http://localhost:8080/api/booking/operator-bookings",
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

            setBookings(
                response.data
            );

        } catch (error) {

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
    const [currentPage, setCurrentPage] =
        useState(1);

    const recordsPerPage = 3;

    const lastIndex =
        currentPage * recordsPerPage;

    const firstIndex =
        lastIndex - recordsPerPage;

    const currentBookings =
        filteredBookings.slice(
            firstIndex,
            lastIndex
        );

    const totalPages =
        Math.ceil(
            filteredBookings.length /
            recordsPerPage
        );
    console.log("Bookings:", filteredBookings.length);
    console.log("Pages:", totalPages);

    return (

        <div className="container mt-4">

            <div className="d-flex justify-content-between align-items-center mb-4">

                <div>

                    <button
                        className="btn btn-outline-secondary mb-3"
                        onClick={() =>
                            navigate("/operator")
                        }
                    >
                        ← Back to Dashboard
                    </button>

                    <h2 className="fw-bold mb-0">
                        Operator Bookings
                    </h2>

                </div>

                <select
                    className="form-select w-auto"
                    value={status}
                    onChange={(e) => {

                        setStatus(
                            e.target.value
                        );

                        setCurrentPage(1);

                    }}
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

                    currentBookings.map(
                        booking => (

                            <div
                                key={booking.bookingId}
                                className="card shadow-sm mb-3"
                            >

                                <div className="card-body">

                                    <div className="row">

                                        <div className="col-md-8">

                                            <h5>
                                                🚌 {booking.busName}
                                            </h5>

                                            <p className="mb-1">
                                                <strong>
                                                    Passengers:
                                                </strong>
                                                {" "}
                                                {booking.passengerNames.join(", ")}
                                            </p>

                                            <p className="mb-1">
                                                <strong>
                                                    Route:
                                                </strong>
                                                {" "}
                                                {booking.source}
                                                {" → "}
                                                {booking.destination}
                                            </p>

                                            <p className="mb-1">
                                                <strong>
                                                    Seats:
                                                </strong>
                                                {" "}
                                                {booking.seatNumbers.join(", ")}
                                            </p>

                                        </div>

                                        <div className="col-md-4 text-end">

                                            <h5 className="text-success">
                                                ₹{booking.totalAmount}
                                            </h5>

                                            <span
                                                className={
                                                    booking.bookingStatus === "BOOKED"
                                                        ? "badge bg-success"
                                                        : "badge bg-danger"
                                                }
                                            >
                                                {booking.bookingStatus}
                                            </span>

                                        </div>

                                    </div>

                                </div>

                            </div>

                        )
                    )
            }

            {
                totalPages > 1 && (

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

                )
            }
        </div>
    );
};

export default OperatorBookings;