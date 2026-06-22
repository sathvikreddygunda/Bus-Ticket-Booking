import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getMyBuses } from "../../store/action/busAction";
import NavbarOperator from "../NavbarOperator";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const MyBuses = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate()

    const { buses } = useSelector(
        state => state.buses
    );

    const [currentPage, setCurrentPage] =
        useState(1);

    const recordsPerPage = 5;

    const lastIndex =
        currentPage * recordsPerPage;

    const firstIndex =
        lastIndex - recordsPerPage;

    const currentBuses =
        buses.slice(
            firstIndex,
            lastIndex
        );

    const totalPages =
        buses.length === 0
            ? 1
            : Math.ceil(
                buses.length /
                recordsPerPage
            );


    useEffect(() => {

        if (
            currentPage > totalPages
        ) {

            setCurrentPage(1);

        }

    }, [buses, totalPages]);

    /*
          state = {
            buses : {
                buses : []
            }
        }
    */
    console.log("Redux Buses:", buses);

    const deleteBus = async (busId) => {

        if (
            !window.confirm(
                "Are you sure you want to delete this bus?"
            )
        ) {
            return;
        }

        try {

            await axios.delete(
                `http://localhost:8080/api/bus/delete/${busId}`,
                {
                    headers: {
                        Authorization:
                            "Bearer " +
                            localStorage.getItem(
                                "token"
                            )
                    }
                }
            );

            window.location.reload();

        }
        catch (err) {

            console.log(err);

        }
    };

    return (
        <>
            <NavbarOperator />

            <div className="container mt-4">

                <div className="card shadow">

                    <div className="card-header">
                        My Buses
                    </div>

                    <div className="card-body">

                        <table className="table table-bordered table-striped">

                            <thead>

                                <tr>

                                    <th>ID</th>

                                    <th>Bus Name</th>

                                    <th>Bus Number</th>

                                    <th>Type</th>

                                    <th>Seats</th>

                                    <th>Fare</th>

                                    <th>Actions</th>

                                </tr>

                            </thead>

                            <tbody>

                                {
                                    currentBuses.map((bus) => (

                                        <tr key={bus.busId}>

                                            <td>{bus.busId}</td>
                                            <td>{bus.busName}</td>
                                            <td>{bus.busNumber}</td>
                                            <td>{bus.busType}</td>
                                            <td>{bus.totalSeats}</td>
                                            <td>₹{bus.fareAmount}</td>

                                            <td>

                                                <button
                                                    className="btn btn-warning btn-sm me-2"
                                                    onClick={() =>
                                                        navigate(
                                                            `/operator/edit-bus/${bus.busId}`
                                                        )
                                                    }
                                                >
                                                    <i className="bi bi-pencil-square"></i>
                                                </button>

                                                <button
                                                    className="btn btn-danger btn-sm"
                                                    onClick={() =>
                                                        deleteBus(bus.busId)
                                                    }
                                                >
                                                    <i className="bi bi-trash"></i>
                                                </button>

                                            </td>

                                        </tr>

                                    ))
                                }

                            </tbody>

                        </table>
                        <div className="d-flex justify-content-center mt-3">

                            <button
                                className="btn btn-success me-2"
                                disabled={
                                    currentPage === 1 ||
                                    buses.length === 0
                                }
                                onClick={() =>
                                    setCurrentPage(
                                        currentPage - 1
                                    )
                                }
                            >
                                Previous
                            </button>

                            <span className="mt-2">
                                Page {buses.length === 0 ? 0 : currentPage} / {totalPages}
                            </span>

                            <button
                                className="btn btn-success ms-2"
                                disabled={
                                    currentPage >= totalPages ||
                                    buses.length === 0
                                }
                                onClick={() =>
                                    setCurrentPage(
                                        currentPage + 1
                                    )
                                }
                            >
                                Next
                            </button>

                        </div>

                    </div>

                </div>

            </div>
        </>
    );
};


export default MyBuses;