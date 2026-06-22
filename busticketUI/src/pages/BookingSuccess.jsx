import NavbarCustomer from "../components/NavbarCustomer";
import { useNavigate } from "react-router-dom";

const BookingSuccess = () => {

    const navigate = useNavigate();

    return (

        <div>

            <NavbarCustomer />

            <div
                className="container mt-5"
            >

                <div
                    className="text-center"
                    style={{
                        background: "white",
                        borderRadius: "25px",
                        padding: "60px 40px",
                        boxShadow: "0 10px 30px rgba(0,0,0,0.1)",
                        maxWidth: "800px",
                        margin: "auto"
                    }}
                >

                    <div
                        style={{
                            fontSize: "80px",
                            marginBottom: "20px"
                        }}
                    >
                        ✅
                    </div>

                    <h1
                        className="fw-bold text-success"
                    >
                        Booking Confirmed
                    </h1>

                    <p
                        className="lead text-muted mt-3"
                    >
                        Your ticket has been booked successfully.
                    </p>

                    <div
                        className="alert alert-success mt-4"
                    >
                        Thank you for choosing FastX.
                        <br />
                        Have a safe and pleasant journey.
                    </div>

                    <button
                        style={{
                            background: "#198754",
                            color: "white",
                            border: "none",
                            borderRadius: "12px",
                            padding: "12px 30px",
                            fontSize: "18px",
                            fontWeight: "600"
                        }}
                        className="mt-3"
                        onClick={() =>
                            navigate("/my-bookings")
                        }
                    >
                        View My Bookings
                    </button>

                </div>

            </div>

        </div>

    );
};

export default BookingSuccess;