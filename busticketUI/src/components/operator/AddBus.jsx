import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import NavbarOperator from "../NavbarOperator";

const AddBus = () => {
    const navigate = useNavigate();

    const [busName, setBusName] = useState();
    const [busNumber, setBusNumber] = useState();
    const [busType, setBusType] = useState();
    const [totalSeats, setTotalSeats] = useState();
    const [fareAmount, setFareAmount] = useState();

    const [successMsg, setSuccessMsg] = useState();
    const [errMsg, setErrMsg] = useState();

    const postApi = "http://localhost:8080/api/bus/add";

    const addBus = async (e) => {

        e.preventDefault();

        let body = {
            busName,
            busNumber,
            busType,
            totalSeats,
            fareAmount
        };

        const config_details = {
            headers: {
                Authorization:
                    "Bearer " + localStorage.getItem("token")
            }
        };

        try {

            await axios.post(
                postApi,
                body,
                config_details
            );

            setSuccessMsg("Bus Added Successfully");
            {
            successMsg &&
            <div className="alert alert-success">
                {successMsg}
            </div>
            }

            {
                successMsg &&
                <div className="mb-3">
                    <button
                        className="btn btn-secondary"
                        onClick={() => navigate("/operator")}
                    >
                        Back to Dashboard
                    </button>
                </div>
            }

            setBusName("");
            setBusNumber("");
            setBusType("");
            setTotalSeats("");
            setFareAmount("");

            setErrMsg(undefined);

        }
        catch(err){

        console.log("ERROR DATA => ", err.response?.data);
        console.log(err);

            setErrMsg(
                err.response?.data?.message ||
                "Failed To Add Bus"
            );

            setSuccessMsg(undefined);
        }
    };

    return (

        <div>

            <NavbarOperator/>

            <div className="container mt-4">

                <div className="row">

                    <div className="col-md-3"></div>

                    <div className="col-md-6">

                        <div className="card">

                            <div className="card-header">
                                Add Bus
                            </div>

                            <div className="card-body">

                                <form onSubmit={(e)=>addBus(e)}>

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

                                    <div className="mb-3">
                                        <label>Bus Name</label>

                                        <input
                                            type="text"
                                            className="form-control"
                                            value={busName}
                                            onChange={(e)=>
                                                setBusName(e.target.value)
                                            }
                                            required
                                        />
                                    </div>

                                    <div className="mb-3">
                                        <label>Bus Number</label>

                                        <input
                                            type="text"
                                            className="form-control"
                                            value={busNumber}
                                            onChange={(e)=>
                                                setBusNumber(e.target.value)
                                            }
                                            required
                                        />
                                    </div>

                                    <div className="mb-3">
                                        <label>Bus Type</label>

                                        <select
                                            className="form-control"
                                            value={busType}
                                            onChange={(e)=>
                                                setBusType(e.target.value)
                                            }
                                            required
                                        >
                                            <option value="">
                                                Select Bus Type
                                            </option>

                                            <option value="AC">
                                                AC
                                            </option>

                                            <option value="NON_AC">
                                                NON AC
                                            </option>

                                            <option value="SLEEPER">
                                                SLEEPER
                                            </option>

                                        </select>

                                    </div>

                                    <div className="mb-3">
                                        <label>Total Seats</label>

                                        <input
                                            type="number"
                                            className="form-control"
                                            value={totalSeats}
                                            onChange={(e)=>
                                                setTotalSeats(e.target.value)
                                            }
                                            required
                                        />
                                    </div>

                                    <div className="mb-4">
                                        <label>Fare Amount</label>

                                        <input
                                            type="number"
                                            className="form-control"
                                            value={fareAmount}
                                            onChange={(e)=>
                                                setFareAmount(e.target.value)
                                            }
                                            required
                                        />
                                    </div>

                                    <input
                                        type="submit"
                                        value="Add Bus"
                                        className="btn btn-primary w-100"
                                    />

                                </form>

                            </div>

                        </div>

                    </div>

                    <div className="col-md-3"></div>

                </div>

            </div>

        </div>
    );
};

export default AddBus;