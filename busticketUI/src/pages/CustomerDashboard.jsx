import "../styles/CustomerDashboard.css";
import NavbarCustomer from "../components/NavbarCustomer";
import { useDispatch } from "react-redux";
import { searchRoutes } from "../store/action/routeAction";
import SearchResults from "../components/customer/SearchResults";
import { useState, useEffect } from "react";
import axios from "axios";

const CustomerDashboard = () => {

    const [source, setSource] = useState("");
    const [destination, setDestination] = useState("");
    const [journeyDate, setJourneyDate] = useState("");
    const [error, setError] = useState("");
    const [searched, setSearched] = useState(false);
    const [routesData, setRoutesData] = useState([]);

    const dispatch = useDispatch();
    useEffect(() => {

        axios
            .get(
                "http://localhost:8080/api/route/all"
            )
            .then(response => {

                setRoutesData(
                    response.data
                );

            })
            .catch(error => {

                console.log(error);

            });

    }, []);

    const handleSearch = () => {

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
        setSearched(true);

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

            routesData.map(
                route => route.source
            )

        )

    ];

    const destinations = [

        ...new Set(

            routesData.map(
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

        <div>

            <NavbarCustomer />

            <div className="container mt-4">

                {/* Header */}

                <div className="dashboard-banner">

                    <h1>
                        Search Your Journey
                    </h1>

                    <p>
                        Find buses, select seats and travel comfortably with FastX.
                    </p>

                </div>

                {/* Search Card */}

                <div className="card customer-search-card">
                    {
                        error && (

                            <div
                                className="alert alert-danger mt-3"
                                role="alert"
                            >
                                {error}
                            </div>

                        )
                    }


                    <div className="row g-3 align-items-end">

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

                            <label className="form-label fw-bold">
                                Journey Date
                            </label>

                            <input
                                type="date"
                                className="form-control form-control-lg"
                                value={journeyDate}
                                onChange={(e) =>
                                    setJourneyDate(e.target.value)
                                }
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

                <div className="mt-4">

                    <SearchResults
                        isGuestFlow={false}
                        searched={searched}
                    />

                </div>

            </div>

        </div>

    );
};

export default CustomerDashboard;