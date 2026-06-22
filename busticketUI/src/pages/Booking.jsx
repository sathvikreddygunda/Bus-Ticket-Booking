import NavbarCustomer from "../components/NavbarCustomer";
import MyBookings from "../components/customer/MyBookings";

const Booking = () => {

    return (
        <div>

            <NavbarCustomer />

            <div className="container mt-4">

                <h2>
                    My Bookings
                </h2>

                <MyBookings />

            </div>

        </div>
    );
};

export default Booking;