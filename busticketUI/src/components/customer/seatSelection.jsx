import "../../styles/SeatSelection.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useEffect } from "react";

const SeatSelection = ({ route, isGuestFlow }) => {
    console.log(route);
    const navigate = useNavigate();
    console.log("ROUTE DATA:", route);
    console.log("BUS ID:", route.busId);


    const [selectedSeats, setSelectedSeats] = useState([]);
    const [seats, setSeats] = useState([]);
    const [passengers, setPassengers] = useState([]);
    const [pickupPoint, setPickupPoint] = useState("");
    const [dropPoint, setDropPoint] = useState("");
    const [showReview, setShowReview] = useState(false);
    const [showAuthPopup, setShowAuthPopup] = useState(false);

    const bookedSeats =
        seats
            .filter(
                seat =>
                    seat.seatStatus === "BOOKED"
            )
            .map(
                seat =>
                    seat.seatNumber
            );
    console.log("BOOKED SEATS:", bookedSeats);

    useEffect(() => {

        if (route?.busId) {

            fetchSeats();

        }

    }, [route]);

    const fetchSeats = async () => {

        console.log(
            "FETCHING SEATS FOR BUS:",
            route.busId
        );

        try {

            const response =
                await axios.get(
                    `http://localhost:8080/api/seat/by-bus/${route.busId}`
                );

            console.log(
                "SEATS API:",
                response.data
            );

            setSeats(response.data);
            console.log(
                "FIRST SEAT OBJECT =",
                response.data[0]
            );

        }
        catch (error) {

            console.error(
                "SEAT API ERROR:",
                error
            );

        }

    };
    const totalAmount =
        selectedSeats.length * (route.fareAmount || 1200);

    const seatRows = [
        ["A1", "A2", "A3", "A4"],
        ["B1", "B2", "B3", "B4"],
        ["C1", "C2", "C3", "C4"],
        ["D1", "D2", "D3", "D4"]
    ];

    const handleSeatClick = (seatObj) => {

        if (
            isGuestFlow &&
            !localStorage.getItem("token")
        ) {

            setShowAuthPopup(true);
            return;

        }

        if (!seatObj) return;

        if (
            bookedSeats.includes(
                seatObj.seatNumber
            )
        ) return;

        setSelectedSeats(prev => {

            if (
                prev.some(
                    s => s.seatId === seatObj.seatId
                )
            ) {

                return prev.filter(
                    s => s.seatId !== seatObj.seatId
                );

            }

            return [...prev, seatObj];

        });

    };

    const handlePassengerChange = (
        index,
        field,
        value
    ) => {

        const selectedSeat =
            selectedSeats[index];

        const updatedPassengers =
            [...passengers];

        updatedPassengers[index] = {

            ...updatedPassengers[index],

            [field]: value,

            seatId: selectedSeat?.seatId

        };

        setPassengers(
            updatedPassengers
        );
    };

    return (


        <div className="card p-4 seat-layout-card">

            <div className="row">
                {/* Seat Layout */}
                <div className="col-md-5">
                    <h5 className="mb-3">
                        Select Seats
                    </h5>

                    {
                        seatRows.map((row, rowIndex) => (
                            <div
                                key={rowIndex}
                                className="d-flex mb-2"
                            >
                                {
                                    row.map((seat, index) => {
                                        let btnClass =
                                            "btn btn-outline-secondary";
                                        if (
                                            bookedSeats.includes(seat)
                                        ) {
                                            btnClass =
                                                "btn btn-dark";
                                        }
                                        if (
                                            selectedSeats.some(
                                                s => s.seatNumber === seat
                                            )
                                        ) {
                                            btnClass =
                                                "btn btn-primary";
                                        } if (
                                            selectedSeats.includes(seat)
                                        ) {
                                            btnClass =
                                                "btn btn-primary";
                                        }
                                        console.log(passengers);
                                        return (
                                            <div
                                                key={seat}
                                                style={{
                                                    marginRight:
                                                        index === 1
                                                            ? "40px"
                                                            : "10px"
                                                }}
                                            >
                                                <button
                                                    className={`${btnClass} seat-btn`}
                                                    onClick={() =>
                                                        handleSeatClick(
                                                            seats.find(
                                                                s => s.seatNumber === seat
                                                            )
                                                        )
                                                    }
                                                >
                                                    {seat}
                                                </button>
                                            </div>
                                        );
                                    })
                                }
                            </div>
                        ))
                    }

                    <hr />
                    <h6>
                        Selected Seats
                    </h6>
                    <p>
                        {
                            <p>
                                {
                                    selectedSeats.length > 0
                                        ? selectedSeats
                                            .map(s => s.seatNumber)
                                            .join(", ")
                                        : "No Seats Selected"
                                }
                            </p>
                        }
                    </p>
                </div>
                {/* Boarding */}
                <h5 className="mb-3">
                    Boarding Points
                    <span className="text-danger"> *</span>
                </h5>

                <div className="list-group">

                    <label className="list-group-item">

                        <input
                            type="radio"
                            name="pickup"
                            value={route.pickupPoint}
                            onChange={(e) =>
                                setPickupPoint(e.target.value)
                            }
                        />

                        &nbsp;
                        {route.pickupPoint}
                        {" ("}
                        {new Date(route.departureTime)
                            .toLocaleTimeString([], {
                                hour: "2-digit",
                                minute: "2-digit"
                            })}
                        {")"}

                    </label>

                </div>
                {/* Dropping */}
                <h5 className="mb-3">
                    Dropping Points
                    <span className="text-danger"> *</span>
                </h5>

                <div className="list-group">

                    <label className="list-group-item">

                        <input
                            type="radio"
                            name="drop"
                            value={route.dropPoint}
                            onChange={(e) =>
                                setDropPoint(e.target.value)
                            }
                        />

                        &nbsp;
                        {route.dropPoint}
                        {" ("}
                        {new Date(route.arrivalTime)
                            .toLocaleTimeString([], {
                                hour: "2-digit",
                                minute: "2-digit"
                            })}
                        {")"}

                    </label>

                </div>
            </div>
            {
                selectedSeats.length > 0 && (
                    <>
                        <hr />
                        <h5>
                            Passenger Details
                        </h5>
                        {
                            selectedSeats.map((seat, index) => (
                                <div
                                    key={seat.seatId}
                                    className="card p-2 mb-2"
                                >

                                    <h6> Passenger {index + 1} {" "} (Seat {seat.seatNumber}) </h6>
                                    <div className="row">
                                        <div className="col-md-4">
                                            <input
                                                type="text"
                                                className="form-control"
                                                placeholder="Passenger Name *"
                                                required
                                                onChange={(e) =>
                                                    handlePassengerChange(
                                                        index,
                                                        "passengerName",
                                                        e.target.value
                                                    )
                                                }
                                            />

                                        </div>
                                        <div className="col-md-4">
                                            <input
                                                type="number"
                                                className="form-control"
                                                placeholder="Age *"
                                                min="1"
                                                max="120"
                                                required
                                                onChange={(e) =>
                                                    handlePassengerChange(
                                                        index,
                                                        "age",
                                                        e.target.value
                                                    )
                                                }
                                            />
                                        </div>
                                        <div className="col-md-4">
                                            <select
                                                className="form-control"
                                                defaultValue=""
                                                required
                                                onChange={(e) =>
                                                    handlePassengerChange(
                                                        index,
                                                        "gender",
                                                        e.target.value
                                                    )
                                                }
                                            >
                                                <option value="" disabled>
                                                    Select Gender *
                                                </option>
                                                <option value="Male">
                                                    Male
                                                </option>
                                                <option value="Female">
                                                    Female
                                                </option>
                                                <option value="Other">
                                                    Other
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            ))
                        }

                        <div className="mt-3 text-end">

                            <button
                                className="btn btn-success"
                                onClick={() => {
                                    if (!pickupPoint) {

                                        alert(
                                            "Please select Boarding Point"
                                        );

                                        return;
                                    }

                                    if (!dropPoint) {

                                        alert(
                                            "Please select Drop Point"
                                        );

                                        return;
                                    }

                                    if (
                                        selectedSeats.length === 0
                                    ) {

                                        alert(
                                            "Please select at least one seat"
                                        );

                                        return;
                                    }

                                    if (
                                        passengers.length !==
                                        selectedSeats.length
                                    ) {

                                        alert(
                                            "Please fill all passenger details"
                                        );

                                        return;
                                    }

                                    const isValid =
                                        passengers.every(
                                            passenger =>
                                                passenger &&
                                                passenger.passengerName &&
                                                passenger.age &&
                                                passenger.gender
                                        );

                                    if (!isValid) {

                                        alert(
                                            "Please fill all passenger details"
                                        );

                                        return;
                                    }
                                    console.log(
                                        "SELECTED SEATS",
                                        selectedSeats
                                    );
                                    console.log(
                                        "PASSENGERS DATA",
                                        JSON.stringify(
                                            passengers,
                                            null,
                                            2
                                        )
                                    );

                                    console.log(
                                        "PASSENGERS:",
                                        passengers
                                    );

                                    setShowReview(true);

                                }}
                            >
                                Continue
                            </button>

                        </div>
                    </>
                )
            }

            {
                showReview && (
                    <div className="mt-4 review-card container-fluid">
                        <hr />
                        <h4>
                            Booking Review
                        </h4>
                        <p>
                            <strong>Bus:</strong>
                            {" "}
                            {route.busName}
                        </p>
                        <p>
                            <strong>Route:</strong>
                            {" "}
                            {route.source}
                            {" → "}
                            {route.destination}
                        </p>

                        <p>
                            <strong>Journey Date:</strong>
                            {" "}
                            {route.journeyDate}
                        </p>

                        <div className="amount-box">
                            ₹{totalAmount}
                        </div>

                        <p>
                            <strong>
                                Seats:
                            </strong>
                            {" "}
                            {selectedSeats
                                .map(s => s.seatNumber)
                                .join(", ")}
                        </p>

                        <h6>
                            Passenger Details
                        </h6>

                        {
                            passengers.map(
                                (passenger, index) => (

                                    <div
                                        key={index}
                                        className="card p-3 mb-3 passenger-card"
                                    >

                                        <strong>
                                            Passenger {index + 1}
                                        </strong>

                                        <br />

                                        Name:
                                        {" "}
                                        {passenger.passengerName}

                                        <br />

                                        Age:
                                        {" "}
                                        {passenger.age}

                                        <br />

                                        Gender:
                                        {" "}
                                        {passenger.gender}

                                    </div>

                                )
                            )

                        }


                        <div className="text-end mt-3">

                            <button
                                className="btn proceed-btn"
                                onClick={() => {

                                    console.log(
                                        "Passengers Before Payment:",
                                        JSON.stringify(
                                            passengers,
                                            null,
                                            2
                                        )
                                    );
                                    console.log(
                                        "PASSENGERS BEFORE PAYMENT =",
                                        passengers
                                    );

                                    navigate(
                                        "/payment",
                                        {
                                            state: {
                                                route,
                                                passengers,
                                                selectedSeats,
                                                totalAmount
                                            }
                                        }
                                    );

                                }}
                            >
                                Proceed To Payment
                            </button>

                        </div>

                    </div>

                )
            }
            {
                showAuthPopup && (

                    <div
                        className="modal d-block"
                        style={{
                            backgroundColor:
                                "rgba(0,0,0,0.5)"
                        }}
                    >
                        <div className="modal-dialog">

                            <div className="modal-content">

                                <div className="modal-header">

                                    <h5>
                                        Login Required
                                    </h5>
                                </div>
                                <div className="modal-footer">
                                    <button
                                        className="btn btn-primary"
                                        onClick={() => navigate("/login")}
                                    >
                                        Login
                                    </button>

                                    <button
                                        className="btn btn-success"
                                        onClick={() => navigate("/register")}
                                    >
                                        Register
                                    </button>

                                    <button
                                        className="btn btn-secondary"
                                        onClick={() => setShowAuthPopup(false)}
                                    >
                                        Close
                                    </button>

                                </div>

                            </div>

                        </div>

                    </div>

                )
            }

        </div>

    );
};

export default SeatSelection;