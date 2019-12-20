import React from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route } from "react-router-dom";
import AddProject from "./components/project/AddProject";

function App() {
  return (
    <Router>
      <div className="App">
        <Header></Header>
        <Route exact path="/dashboard" component={Dashboard}></Route>
        <Route exact path="/addProject" component={AddProject}></Route>
      </div>
    </Router>
  );
}

export default App;