import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import { useDispatch } from "react-redux";
import { searchRoutes } from "../store/action/routeAction";
import SearchResults from "../components/customer/SearchResults";


const Home = () => {

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [searched, setSearched] = useState(false);
  const [source, setSource] = useState("");
  const [destination, setDestination] = useState("");
  const [journeyDate, setJourneyDate] = useState("");
  const [error, setError] = useState("");
  const [routes, setRoutes] = useState([]);

  useEffect(() => {

    axios
      .get("http://localhost:8080/api/route/all")
      .then(response =>
        setRoutes(response.data)
      )
      .catch(error =>
        console.log(error)
      );

  }, []);

  const handleSearch = () => {
    console.log("Source:", source);
    console.log("Destination:", destination);
    console.log("Journey Date:", journeyDate);
    setSearched(true);

    if (
      source.trim().toLowerCase() ===
      destination.trim().toLowerCase()
    ) {

      setError(
        "Both Origin and Destination cannot be same"
      );

      return;
    }

    setError("");

    dispatch(
      searchRoutes(
        source,
        destination,
        journeyDate
      )
    );
  };
  const sources = [
    ...new Set(
      routes.map(
        route => route.source
      )
    )
  ];

  const destinations = [
    ...new Set(
      routes.map(
        route => route.destination
      )
    )
  ];
  const swapLocations = () => {

    const temp = source;

    setSource(destination);

    setDestination(temp);

  };

  return (
    <>
      <Navbar />

      {/* Hero Section */}

      <div className="hero-section">

        <div className="container text-center">

          <h1 className="display-2 fw-bold">
            FastX
          </h1>

          <p className="fs-4">
            Your Journey Starts Here
          </p>

          <p className="lead">
            Book bus tickets across India with ease,
            comfort and reliability.
          </p>

        </div>

      </div>

      {/* Search Card */}

      <div className="container">

        <div
          className="card search-box p-4"
          style={{
            marginTop: "-100px",
            borderRadius: "20px"
          }}
        >
          {
            error && (

              <div
                className="alert alert-danger mb-3"
                role="alert"
              >
                {error}
              </div>

            )
          }

          <div className="row align-items-end">

            <div className="col-md-3">

              <label className="fw-bold">
                From
              </label>

              <select
                className="form-control form-control-lg"
                value={source}
                onChange={(e) => {

                  setSource(e.target.value);
                  setError("");

                }}
              >

                <option value="">
                  Select Source
                </option>

                {
                  sources.map(city => (
                    <option
                      key={city}
                      value={city}
                    >
                      {city}
                    </option>
                  ))
                }

              </select>

            </div>

            <div
              className="col-md-1 d-flex justify-content-center align-items-end"
            >

              <button
                type="button"
                className="btn btn-outline-primary rounded-circle"
                onClick={swapLocations}
                style={{
                  width: "45px",
                  height: "45px"
                }}
              >
                ⇄
              </button>

            </div>

            <div className="col-md-3">

              <label className="fw-bold">
                To
              </label>

              <select
                className="form-control form-control-lg"
                value={destination}
                onChange={(e) => {

                  setDestination(e.target.value);
                  setError("");

                }}
              >

                <option value="">
                  Select Destination
                </option>

                {
                  destinations.map(city => (
                    <option
                      key={city}
                      value={city}
                    >
                      {city}
                    </option>
                  ))
                }

              </select>

            </div>

            <div className="col-md-2">

              <label className="fw-bold">
                Journey Date
              </label>

              <input
                type="date"
                className="form-control form-control-lg"
                value={journeyDate}
                onChange={(e) => setJourneyDate(e.target.value)}
              />

            </div>

            <div className="col-md-3">

              <button
                className="btn btn-success btn-lg w-100"
                onClick={handleSearch}
              >
                Search Buses
              </button>

            </div>

          </div>

        </div>
        {/* Search Results */}

        <div className="mt-5">
          <SearchResults
            isGuestFlow={true}
            searched={searched}
          />
        </div>

        {/* Popular Routes */}

        <div className="mt-5">

          <h2 className="text-center fw-bold mb-4">
            Popular Routes
          </h2>

          <div className="row g-4">

            <div className="col-md-4">

              <div className="card route-card p-4 text-center">

                <h4>Chennai</h4>

                <h3 className="text-success fw-bold">
                  →
                </h3>

                <h4>Hyderabad</h4>

              </div>

            </div>

            <div className="col-md-4">

              <div className="card route-card p-4 text-center">

                <h4>Bangalore</h4>

                <h3 className="text-success fw-bold">
                  →
                </h3>

                <h4>Vijayawada</h4>

              </div>

            </div>

            <div className="col-md-4">

              <div className="card route-card p-4 text-center">

                <h4>Hyderabad</h4>

                <h3 className="text-success fw-bold">
                  →
                </h3>

                <h4>Chennai</h4>

              </div>

            </div>

          </div>

        </div>

        {/* Why Choose Us */}

        <div className="mt-5 mb-5">

          <h2 className="text-center fw-bold mb-4">
            Why Choose FastX?
          </h2>

          <div className="row g-4">

            <div className="col-md-4">

              <div className="card feature-card">

                <div>

                  <div className="feature-icon">
                    🚌
                  </div>

                  <h3>
                    Wide Network
                  </h3>

                  <p>
                    Multiple routes across cities.
                  </p>

                </div>

              </div>

            </div>

            <div className="col-md-4">

              <div className="card feature-card">

                <div>

                  <div className="feature-icon">
                    💳
                  </div>

                  <h3>
                    Secure Payments
                  </h3>

                  <p>
                    Fast and secure payment options.
                  </p>

                </div>

              </div>

            </div>

            <div className="col-md-4">

              <div className="card feature-card">

                <div>

                  <div className="feature-icon">
                    🎫
                  </div>

                  <h3>
                    Instant Booking
                  </h3>

                  <p>
                    Book tickets in just a few clicks.
                  </p>

                </div>

              </div>

            </div>

          </div>

        </div>

      </div>
    </>
  );
};

export default Home;