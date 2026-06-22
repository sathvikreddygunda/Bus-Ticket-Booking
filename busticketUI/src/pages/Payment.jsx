import axios from "axios";
import { useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "../styles/Payment.css";

const Payment = () => {

    const location = useLocation();
    const navigate = useNavigate();

    const {
        route,
        passengers,
        selectedSeats,
        totalAmount
    } = location.state;

    const handlePayment = async () => {

        try {

            const token =
                localStorage.getItem("token");

            const bookingData = {
                passengers: passengers
            };

            console.log(
                "Route Data:",
                route
            );

            console.log(
                "Booking Data:",
                bookingData
            );
            console.log(route);
            console.log(
                JSON.stringify(
                    bookingData,
                    null,
                    2
                )
            );
            console.log("TOKEN =", localStorage.getItem("token"));
            console.log("ROLE =", localStorage.getItem("role"));
            console.log("BOOKING DATA =", bookingData);

            await axios.post(
                `http://localhost:8080/api/booking/add/${route.routeId}`,
                bookingData,
                {
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

            navigate(
                "/booking-success"
            );

        }
        catch (err) {

            console.log("STATUS =", err.response?.status);

            console.log(
                "ERROR DATA =",
                err.response?.data
            );

            console.log(err);

        }

    };
    return (

        <div className="container mt-5 mb-5">

            <div className="row">

                {/* Payment Methods */}

                <div className="col-md-8">

                    <div className="payment-card">

                        <h3 className="mb-4">
                            Payment Options
                        </h3>

                        <div className="payment-option">
                            <input type="radio" name="payment" />
                            <span> UPI</span>
                        </div>

                        <div className="payment-option">
                            <input type="radio" name="payment" />
                            <span> Credit Card</span>
                        </div>

                        <div className="payment-option">
                            <input type="radio" name="payment" />
                            <span> Debit Card</span>
                        </div>

                        <div className="payment-option">
                            <input type="radio" name="payment" />
                            <span> Net Banking</span>
                        </div>

                        <div className="payment-option">
                            <input type="radio" name="payment" />
                            <span> Wallet</span>
                        </div>

                        <button
                            className="btn btn-success pay-btn mt-4"
                            onClick={handlePayment}
                        >
                            Pay ₹{totalAmount}
                        </button>

                    </div>

                </div>

                {/* Booking Summary */}

                <div className="col-md-4">

                    <div className="summary-card">

                        <h4>
                            Booking Summary
                        </h4>

                        <hr />

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
                            <strong>Seats:</strong>
                            {" "}
                            {selectedSeats.join(", ")}
                        </p>

                        <p>
                            <strong>Passengers:</strong>
                            {" "}
                            {passengers.length}
                        </p>

                        <div className="total-price">
                            ₹{totalAmount}
                        </div>

                    </div>

                </div>

            </div>

        </div>

    );
};

export default Payment;