import { useState, useEffect } from "react";
import './App.css';

export default function App() {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [k, setK] = useState(10);
    const [fn, setFn] = useState('min');
    const [energy, setEnergy] = useState(false);
    const [protein, setProtein] = useState(false);
    const [carbohydrate, setCarbohydrate] = useState(false);
    const [fat, setFat] = useState(false);

    useEffect(() => {
        fetchFagin();
    }, []);

    function fetchFagin() {
        fetch(`http://localhost:8080/fagin?k=${k}&function=${fn}&energy=${energy ? 1 : 0}&protein=${protein ? 1 : 0}&carbohydrate=${carbohydrate ? 1 : 0}&fat=${fat ? 1 : 0}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(
                        `This is an HTTP error: The status is ${response.status}`
                    );
                }
                return response.json();
            })
            .then((actualData) => {
                setData(actualData);
                setError(null);
            })
            .catch((err) => {
                console.log(err.message)
            })
            .finally(() => {
                setLoading(false);
            });
    }

    function handleSubmit(e) {
        e.preventDefault();
        // Any checkobx checked
        if (!energy && !protein && !carbohydrate && !fat) {
            alert('At least one attribute has to be checked.')
            return;
        }

        fetchFagin();
    }

    return (
        <div className="App">
            <h1>Fagin's top-k algorithm</h1>
            <form onSubmit={handleSubmit}>
                <label>k </label>
                <input
                    type="number"
                    value={k}
                    onChange={(e) => setK(e.target.value)}
                />
                <br/>
                <label>function </label>
                <select
                    value={fn}
                    onChange={(e) => setFn(e.target.value)}
                >
                    <option value="min">min</option>
                    <option value="max">max</option>
                    <option value="avg">avg</option>
                </select>
                <br/>
                <input
                    id="energy"
                    type="checkbox"
                    checked={energy}
                    onChange={() => setEnergy(!energy)}
                />
                <label for="energy"> energy</label>
                <input
                    id="protein"
                    type="checkbox"
                    checked={protein}
                    onChange={() => setProtein(!protein)}
                />
                <label for="protein"> protein</label>
                <input
                    id="carbohydrate"
                    type="checkbox"
                    checked={carbohydrate}
                    onChange={() => setCarbohydrate(!carbohydrate)}
                />
                <label for="carbohydrate"> carbohydrate</label>
                <input
                    id="fat"
                    type="checkbox"
                    checked={fat}
                    onChange={() => setFat(!fat)}
                />
                <label for="fat"> fat</label>
                <br/>
                <button>
                    Submit
                </button>
            </form>
            {loading && <div>Hit the submit button!</div>}
            {error && (
                <div>{`There is a problem fetching the post data - ${error}`}</div>
            )}
            {data && <div>Processed rows: {data.processedRows}</div>}
            {data && <div>Fagin computation time (in miliseconds): {data.durationFagin / 1000000}</div>}
            {data && <div>Sequential computation time (in miliseconds): {data.durationSequential / 1000000}</div>}
            <table data-table-theme="default zebra">
                <thead>
                    <tr><td>Id</td><td>Name</td><td>Energy</td><td>Protein</td><td>Carbohydrate</td><td>Fat</td></tr>
                </thead>
                <tbody>
                    {data &&
                    data.topRows.map(({ id, name, energy, protein, carbohydrate, fat }) => (
                        <tr key={id}>
                            <td>{id}</td>
                            <td>{name}</td>
                            <td>{energy}</td>
                            <td>{protein}</td>
                            <td>{carbohydrate}</td>
                            <td>{fat}</td>
                        </tr>
                    ))}
                </tbody>
          </table>
        </div>
      );
}