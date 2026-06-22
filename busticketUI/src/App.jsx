import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Auth from "./pages/Auth";
import Register from "./auth/Register";

import PageNotFound from "./pages/PageNotFound";
import EditRoute
  from "./components/operator/EditRoute";
import CustomerDashboard from "./pages/CustomerDashboard";
import OperatorDashboard from "./pages/OperatorDashboard";
import AdminDashboard from "./pages/AdminDashboard";
import AddBus from "./components/operator/AddBus";
import AddRoute from "./components/operator/AddRoute";
import MyBuses from "./components/operator/MyBuses";
import MyRoutes from "./components/operator/MyRoutes";
import MyBookings from "./components/customer/MyBookings";
import Booking from "./pages/Booking";
import Payment from "./pages/Payment";
import BookingSuccess from "./pages/BookingSuccess";
import AdminCustomers from "./components/admin/AdminCustomers";
import AdminOperators from "./components/admin/AdminOperators";
import SearchResults from "./components/customer/SearchResults";
import OperatorBookings from "./components/operator/OperatorBookings";
import AdminBookings from "./components/admin/AdminBookings";
import AdminBuses from "./components/admin/AdminBuses";
import AdminRoutes from "./components/admin/AdminRoutes";
import AddOperator from "./components/admin/AddOperator";
import AdminEditOperator
  from "./components/admin/AdminEditOperator";
import EditBus from "./components/operator/EditBus";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Auth />} />
      <Route path="/register" element={<Register />} />

      <Route path="/customer" element={<CustomerDashboard />} />
      <Route path="/operator" element={<OperatorDashboard />} />
      <Route path="/add-bus" element={<AddBus />} />
      <Route path="/add-route" element={<AddRoute />} />
      <Route path="/my-buses" element={<MyBuses />} />
      <Route path="/my-routes" element={<MyRoutes />} />
      <Route path="/admin" element={<AdminDashboard />} />
      <Route
        path="/booking/:routeId"
        element={<Booking />} />
      <Route path="/my-bookings" element={<MyBookings />} />
      <Route path="/payment" element={<Payment />} />
      <Route path="/operator/edit-bus/:busId" element={<EditBus />} />
      <Route path="/booking-success" element={<BookingSuccess />} />
      <Route path="*" element={<PageNotFound />} />
      <Route path="/admin/customers" element={<AdminCustomers />} />
      <Route path="/admin/operators" element={<AdminOperators />} />
      <Route
        path="/admin/bookings"
        element={<AdminBookings />} />
      <Route path="/search-results" element={<SearchResults />} />
      <Route
        path="/operator/bookings"
        element={<OperatorBookings />} />
      <Route
        path="/admin/buses"
        element={<AdminBuses />} />
      <Route
        path="/admin/routes"
        element={<AdminRoutes />} />
      <Route
        path="/admin/add-operator"
        element={<AddOperator />} />
      <Route
        path="/admin/edit-operator/:operatorId"
        element={<AdminEditOperator />} />
      <Route
        path="/operator/edit-route/:routeId"
        element={<EditRoute />} />
    </Routes>
  );
}

export default App;