import "../../styles/SearchResults.css";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import SeatSelection from "./SeatSelection";
import { useState } from "react";
import { useEffect } from "react";

const SearchResults = ({ isGuestFlow, searched
}) => {

    const { routes } = useSelector(state => state.routes);
    const [selectedRouteId, setSelectedRouteId] = useState(null);


    if (
        searched &&
        routes &&
        routes.length === 0
    ) {

        return (

            <div
                className="alert alert-warning mt-4 text-center"
                role="alert"
            >
                No buses available for the selected route and journey date.
            </div>

        );

    }



    const handleSeatSelection = (route) => {

        setSelectedRouteId(
            selectedRouteId === route.routeId
                ? null
                : route.routeId
        );

    };

    return (
        <div className="container mt-4">

            {
                routes.map((route) => (

                    <div
                        key={route.routeId}
                        className="card search-card mb-4"
                    >
                        <div className="card-body">

                            <div className="row align-items-center">

                                <div className="col-md-3">

                                    <h4 className="fw-bold mb-1">
                                        {route.busName}
                                    </h4>

                                    <p className="text-muted">
                                        AC Sleeper (2+1)
                                    </p>

                                </div>

                                <div className="col-md-6 text-center">

                                    <div className="row align-items-center">

                                        <div className="col">
                                            <h3>
                                                {route.departureTime.substring(11, 16)}
                                            </h3>

                                            <small>
                                                {route.source}
                                            </small>
                                        </div>

                                        <div className="col">
                                            <p className="text-muted">
                                                ━━━━━━━━
                                            </p>
                                        </div>

                                        <div className="col">
                                            <h3>
                                                {route.arrivalTime.substring(11, 16)}
                                            </h3>

                                            <small>
                                                {route.destination}
                                            </small>
                                        </div>

                                    </div>

                                </div>

                                <div className="col-md-3 text-end">

                                    <div className="fare-box mb-3">

                                        <div className="fare-label">
                                            Starting From
                                        </div>

                                        <div className="fare-amount">
                                            ₹{route?.fareAmount}
                                        </div>

                                    </div>

                                    <button
                                        className="btn btn-primary"
                                        onClick={() =>
                                            handleSeatSelection(route)
                                        }
                                    >
                                        {
                                            selectedRouteId === route.routeId
                                                ? "HIDE SEATS"
                                                : "SELECT SEATS"
                                        }
                                    </button>

                                </div>

                            </div>

                        </div>
                        {
                            selectedRouteId === route.routeId &&
                            <SeatSelection
                                route={route}
                                isGuestFlow={isGuestFlow}
                            />
                        }
                    </div>


                ))
            }

        </div>
    );
};

export default SearchResults;