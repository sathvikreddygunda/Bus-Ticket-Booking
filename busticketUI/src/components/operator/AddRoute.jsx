import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import NavbarOperator from "../NavbarOperator";

const AddRoute = () => {

    const navigate = useNavigate();
    const [source, setSource] = useState("");
    const [destination, setDestination] = useState("");
    const [pickupPoint, setPickupPoint] = useState("");
    const [dropPoint, setDropPoint] = useState("");
    const [journeyDate, setJourneyDate] = useState("");
    const [departureTime, setDepartureTime] = useState("");
    const [arrivalTime, setArrivalTime] = useState("");

    const [busId, setBusId] = useState(0);
    const [buses, setBuses] = useState([]);
    const [successMsg, setSuccessMsg] = useState();
    const [errMsg, setErrMsg] = useState();

    const getMyBusesApi =
        "http://localhost:8080/api/bus/my-buses";

    const addRouteApi =
        "http://localhost:8080/api/route/add/";


    const config_details = {
        headers: {
            Authorization:
                "Bearer " + localStorage.getItem("token")
        }
    };

    useEffect(() => {

        const getMyBuses = async () => {

            try {

                const response =
                    await axios.get(
                        getMyBusesApi,
                        config_details
                    );

                setBuses(response.data);

            } catch (err) {

                console.log(err?.response);

            }
        };

        getMyBuses();

    }, []);

    const addRoute = async (e) => {

        e.preventDefault();

        try {

            const body = {
                source,
                destination,
                pickupPoint,
                dropPoint,
                departureTime,
                arrivalTime,
                journeyDate
            };

            await axios.post(
                addRouteApi + busId,
                body,
                config_details
            );

            setSuccessMsg(
                "Route Added Successfully"
            );

            setErrMsg(undefined);

            setSource("");
            setDestination("");
            setPickupPoint("");
            setDropPoint("");
            setJourneyDate("");
            setDepartureTime("");
            setArrivalTime("");
            setBusId(0);

        } catch (err) {

            console.log(err);

            setErrMsg(
                err.response?.data?.message ||
                "Failed To Add Route"
            );

            setSuccessMsg(undefined);
        }
    };

    return (
        <>
            <NavbarOperator />

            <div className="container mt-4">

                <div className="row justify-content-center">

                    <div className="col-md-8">

                        <div className="card shadow">

                            <div className="card-header d-flex justify-content-between align-items-center">

                                <span>
                                    Add Route
                                </span>


                            </div>

                            <div className="card-body">

                                {
                                    successMsg &&
                                    <div className="alert alert-success">
                                        {successMsg}
                                    </div>
                                }

                                {
                                    errMsg &&
                                    <div className="alert alert-danger">
                                        {errMsg}
                                    </div>
                                }

                                <form
                                    onSubmit={(e) => addRoute(e)}
                                >

                                    <div className="row">

                                        <div className="col-md-6 mb-3">

                                            <label>
                                                Source
                                                <span className="text-danger">*</span>
                                            </label>

                                            <input
                                                type="text"
                                                className="form-control"
                                                value={source}
                                                onChange={(e) =>
                                                    setSource(e.target.value)
                                                }
                                                required
                                            />

                                        </div>

                                        <div className="col-md-6 mb-3">

                                            <label>
                                                Destination
                                                <span className="text-danger">*</span>
                                            </label>

                                            <input
                                                type="text"
                                                className="form-control"
                                                value={destination}
                                                onChange={(e) =>
                                                    setDestination(e.target.value)
                                                }
                                                required
                                            />

                                        </div>

                                    </div>

                                    <div className="row">

                                        <div className="col-md-6 mb-3">

                                            <label>
                                                Pickup Point
                                            </label>

                                            <input
                                                type="text"
                                                className="form-control"
                                                value={pickupPoint}
                                                onChange={(e) =>
                                                    setPickupPoint(e.target.value)
                                                }
                                            />

                                        </div>

                                        <div className="col-md-6 mb-3">

                                            <label>
                                                Drop Point
                                            </label>

                                            <input
                                                type="text"
                                                className="form-control"
                                                value={dropPoint}
                                                onChange={(e) =>
                                                    setDropPoint(e.target.value)
                                                }
                                            />

                                        </div>

                                    </div>

                                    <div className="mb-3">

                                        <label>
                                            Journey Date
                                            <span className="text-danger">*</span>
                                        </label>

                                        <input
                                            type="date"
                                            className="form-control"
                                            value={journeyDate}
                                            onChange={(e) =>
                                                setJourneyDate(e.target.value)
                                            }
                                            required
                                        />

                                    </div>

                                    <div className="row">

                                        <div className="col-md-6 mb-3">

                                            <label>
                                                Departure Time
                                                <span className="text-danger">*</span>
                                            </label>

                                            <input
                                                type="datetime-local"
                                                className="form-control"
                                                value={departureTime}
                                                onChange={(e) =>
                                                    setDepartureTime(e.target.value)
                                                }
                                                required
                                            />

                                        </div>

                                        <div className="col-md-6 mb-3">

                                            <label>
                                                Arrival Time
                                                <span className="text-danger">*</span>
                                            </label>

                                            <input
                                                type="datetime-local"
                                                className="form-control"
                                                value={arrivalTime}
                                                onChange={(e) =>
                                                    setArrivalTime(e.target.value)
                                                }
                                                required
                                            />

                                        </div>

                                    </div>

                                    <div className="mb-4">

                                        <label>
                                            Select Bus
                                            <span className="text-danger">*</span>
                                        </label>

                                        <select
                                            className="form-control"
                                            value={busId}
                                            onChange={(e) =>
                                                setBusId(e.target.value)
                                            }
                                            required
                                        >

                                            <option value={0}>
                                                -- Select Bus --
                                            </option>

                                            {
                                                buses.map((bus) => (

                                                    <option
                                                        key={bus.busId}
                                                        value={bus.busId}
                                                    >
                                                        {bus.busName}
                                                        {" "}
                                                        ({bus.busNumber})
                                                    </option>

                                                ))
                                            }

                                        </select>

                                    </div>

                                    <button
                                        type="submit"
                                        className="btn btn-success w-100"
                                    >
                                        Add Route
                                    </button>

                                </form>

                            </div>

                        </div>

                    </div>

                </div>

            </div>
        </>
    );
};

export default AddRoute;