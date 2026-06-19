import axios from "axios";
import { useEffect, useState } from "react";

import { useDispatch, useSelector } from "react-redux";
import { getAll } from "../redux/actions/CharacterAction";

function CharacterList() {

    const dispatch = useDispatch();

    // const [characters, setCharacters] = useState([]);
    const [page, setPage] = useState(1);

    // reads data from store
    const characters =
        useSelector(
            state =>
                state.characterReducer.characters
        );

    useEffect(() => {
        // dispatches page
        dispatch(
            getAll(page)
        );
    }, [page]);

    // const fetchCharacters = async () => {
    //     try {
    //         const response = await axios.get(`https://rickandmortyapi.com/api/character/?page=${page}`);

    //         console.log(response.data);

    //         setCharacters(response.data.results);
    //     } catch (error) {
    //         console.log(error);
    //     }
    // };
    return (
        <div className="container mt-4">

            <div className="card shadow">

                <div className="card-header bg-primary text-white">
                    <h4>Rick & Morty Characters</h4>
                </div>

                <div className="card-body">

                    <table className="table table-bordered table-striped table-hover">

                        <thead className="table-dark">

                            <tr>
                                <th>Name</th>
                                <th>Status</th>
                                <th>Species</th>
                                <th>Origin</th>
                                <th>Location</th>
                            </tr>

                        </thead>

                        <tbody>

                            {
                                characters.map((character) => (

                                    <tr key={character.id}>

                                        <td>{character.name}</td>

                                        <td>{character.status}</td>

                                        <td>{character.species}</td>

                                        <td>{character.origin.name}</td>

                                        <td>{character.location.name}</td>

                                    </tr>

                                ))
                            }

                        </tbody>

                    </table>

                    <div className="d-flex justify-content-center">

                        <button
                            className="btn btn-secondary me-2"
                            disabled={page === 1}
                            onClick={() => setPage(page - 1)}
                        >
                            Previous
                        </button>

                        <span className="align-self-center fw-bold">
                            Page {page}
                        </span>

                        <button
                            className="btn btn-primary ms-2"
                            onClick={() => setPage(page + 1)}
                        >
                            Next
                        </button>

                    </div>

                </div>

            </div>

        </div>
    );

}

export default CharacterList;