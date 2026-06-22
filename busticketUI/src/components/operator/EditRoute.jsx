import axios from "axios";
import { useEffect, useState } from "react";
import {
    useNavigate,
    useParams
} from "react-router-dom";

const EditRoute = () => {

    const navigate =
        useNavigate();

    const { routeId } =
        useParams();

    const [source,
        setSource] =
        useState("");

    const [destination,
        setDestination] =
        useState("");

    const [pickupPoint,
        setPickupPoint] =
        useState("");

    const [dropPoint,
        setDropPoint] =
        useState("");

    const [journeyDate,
        setJourneyDate] =
        useState("");

    const [departureTime,
        setDepartureTime] =
        useState("");

    const [arrivalTime,
        setArrivalTime] =
        useState("");

    useEffect(() => {

        fetchRoute();

    }, []);

    const fetchRoute =
        async () => {

            try {

                const token =
                    localStorage.getItem(
                        "token"
                    );

                const response =
                    await axios.get(
                        `http://localhost:8080/api/route/get-one/${routeId}`,
                        {
                            headers: {
                                Authorization:
                                    `Bearer ${token}`
                            }
                        }
                    );

                const route =
                    response.data;

                setSource(
                    route.source
                );

                setDestination(
                    route.destination
                );

                setPickupPoint(
                    route.pickupPoint
                );

                setDropPoint(
                    route.dropPoint
                );

                setJourneyDate(
                    route.journeyDate
                );

                setDepartureTime(
                    route.departureTime?.slice(
                        0,
                        16
                    )
                );

                setArrivalTime(
                    route.arrivalTime?.slice(
                        0,
                        16
                    )
                );

            }
            catch (error) {

                console.error(error);

            }
        };

    const updateRoute =
        async (e) => {

            e.preventDefault();

            try {

                const token =
                    localStorage.getItem(
                        "token"
                    );

                await axios.put(
                    `http://localhost:8080/api/route/update/${routeId}`,
                    {
                        source,
                        destination,
                        pickupPoint,
                        dropPoint,
                        journeyDate,
                        departureTime,
                        arrivalTime
                    },
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

                alert(
                    "Route Updated Successfully"
                );

                navigate(
                    "/my-routes"
                );

            }
            catch (error) {

                console.error(error);

            }
        };

    return (

        <div className="container mt-4">

            <button
                className="btn btn-dark mb-3"
                type="button"
                onClick={() =>
                    navigate(
                        "/my-routes"
                    )
                }
            >
                ← My Routes
            </button>

            <div className="card shadow">

                <div className="card-header">
                    Edit Route
                </div>

                <div className="card-body">

                    <form
                        onSubmit={
                            updateRoute
                        }
                    >

                        <div className="mb-3">

                            <label>
                                Source
                            </label>

                            <input
                                type="text"
                                className="form-control"
                                value={source}
                                onChange={(e) =>
                                    setSource(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Destination
                            </label>

                            <input
                                type="text"
                                className="form-control"
                                value={destination}
                                onChange={(e) =>
                                    setDestination(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Pickup Point
                            </label>

                            <input
                                type="text"
                                className="form-control"
                                value={pickupPoint}
                                onChange={(e) =>
                                    setPickupPoint(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Drop Point
                            </label>

                            <input
                                type="text"
                                className="form-control"
                                value={dropPoint}
                                onChange={(e) =>
                                    setDropPoint(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Journey Date
                            </label>

                            <input
                                type="date"
                                className="form-control"
                                value={journeyDate}
                                onChange={(e) =>
                                    setJourneyDate(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Departure Time
                            </label>

                            <input
                                type="datetime-local"
                                className="form-control"
                                value={departureTime}
                                onChange={(e) =>
                                    setDepartureTime(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Arrival Time
                            </label>

                            <input
                                type="datetime-local"
                                className="form-control"
                                value={arrivalTime}
                                onChange={(e) =>
                                    setArrivalTime(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <button
                            className="btn btn-primary"
                        >
                            Update Route
                        </button>

                    </form>

                </div>

            </div>

        </div>
    );
};

export default EditRoute;