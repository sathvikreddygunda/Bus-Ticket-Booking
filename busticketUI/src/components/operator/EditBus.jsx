import axios from "axios";
import { useEffect, useState } from "react";
import {
    useNavigate,
    useParams
} from "react-router-dom";

const EditBus = () => {

    const navigate = useNavigate();

    const { busId } = useParams();
    const [busName, setBusName] = useState("");
    const [busNumber, setBusNumber] = useState("");
    const [busType, setBusType] = useState("");
    const [totalSeats, setTotalSeats] = useState("");
    const [fareAmount, setFareAmount] = useState("");

    useEffect(() => {
        fetchBus();

    }, []);

    const fetchBus =
        async () => {

            try {

                const token =
                    localStorage.getItem(
                        "token"
                    );

                const response =
                    await axios.get(
                        `http://localhost:8080/api/bus/get-one/${busId}`,
                        {
                            headers: {
                                Authorization:
                                    `Bearer ${token}`
                            }
                        }
                    );

                const bus = response.data;
                setBusName(bus.busName);
                setBusNumber(bus.busNumber);
                setBusType(bus.busType);
                setTotalSeats(bus.totalSeats);
                setFareAmount(bus.fareAmount);

            }
            catch (error) {
                console.error(error);
            }
        };

    const updateBus =
        async (e) => {

            e.preventDefault();

            try {

                const token =
                    localStorage.getItem(
                        "token"
                    );

                await axios.put(
                    `http://localhost:8080/api/bus/update/${busId}`,
                    {
                        busName,
                        busNumber,
                        busType,
                        totalSeats,
                        fareAmount
                    },
                    {
                        headers: {
                            Authorization:
                                `Bearer ${token}`
                        }
                    }
                );

                alert(
                    "Bus Updated Successfully"
                );

                navigate(
                    "/my-buses"
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
                        "/my-buses"
                    )
                }
            >
                ← My Buses
            </button>

            <div className="card shadow">

                <div className="card-header">
                    Edit Bus
                </div>

                <div className="card-body">

                    <form
                        onSubmit={
                            updateBus
                        }
                    >

                        <div className="mb-3">

                            <label>
                                Bus Name
                            </label>

                            <input
                                type="text"
                                className="form-control"
                                value={busName}
                                onChange={(e) =>
                                    setBusName(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Bus Number
                            </label>

                            <input
                                type="text"
                                className="form-control"
                                value={busNumber}
                                onChange={(e) =>
                                    setBusNumber(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Bus Type
                            </label>

                            <select
                                className="form-select"
                                value={busType}
                                onChange={(e) =>
                                    setBusType(
                                        e.target.value
                                    )
                                }
                            >
                                <option value="AC">
                                    AC
                                </option>

                                <option value="NON_AC">
                                    NON AC
                                </option>

                            </select>

                        </div>

                        <div className="mb-3">

                            <label>
                                Total Seats
                            </label>

                            <input
                                type="number"
                                className="form-control"
                                value={totalSeats}
                                onChange={(e) =>
                                    setTotalSeats(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <div className="mb-3">

                            <label>
                                Fare Amount
                            </label>

                            <input
                                type="number"
                                className="form-control"
                                value={fareAmount}
                                onChange={(e) =>
                                    setFareAmount(
                                        e.target.value
                                    )
                                }
                            />

                        </div>

                        <button
                            className="btn btn-primary"
                        >
                            Update Bus
                        </button>

                    </form>

                </div>

            </div>

        </div>
    );
};

export default EditBus;