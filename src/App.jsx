import { Routes, Route, Navigate } from "react-router-dom";
// import UserList from "./components/UserList";
// import AddUser from "./components/AddUser";
import CharacterList from "./components/CharacterList";

function App() {
  return (
    <div>

      <CharacterList />
    </div>
  );
}

export default App;

// return (
//   <div>
//     <nav className="navbar navbar-dark bg-dark">
//       <div className="container">
//         <a className="navbar-brand" href="/users">User Dashboard</a>
//         <a className="btn btn-success" href="/add-user">
//           Add User
//         </a>
//       </div>
//     </nav>
//     <Routes>
//       <Route path="/" element={<Navigate to="/users" />} />
//       <Route path="/users" element={<UserList />} />
//       <Route path="/add-user" element={<AddUser />} />
//     </Routes>
//   </div>


// );
// }

// export default App;