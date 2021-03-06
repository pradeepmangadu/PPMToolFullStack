import React from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import AddProject from "./components/project/AddProject";
import { Provider } from "react-redux";
import store from "./store";
import UpdateProject from "./components/project/UpdateProject";
import ProjectBoard from "./components/ProjectBoard/ProjectBoard";
import AddProjectTask from "./components/ProjectBoard/ProjectTasks/AddProjectTask";
import UpdateProjectTask from "./components/ProjectBoard/ProjectTasks/UpdateProjectTask";
import Landing from "./components/Layout/Landing";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/securityActions";
import SecureRoute from "./securityUtils/SecureRoute";

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJWTToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwtToken
  });

  const currentTime = Date.now() / 1000;
  if (decoded_jwtToken.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = "/";
  }
}

function App() {
  return (
    <Provider store={store}>
      <Router>
        <div className="App">
          <Header></Header>
          {
            //public Routes
          }
          <Route exact path="/" component={Landing}></Route>
          <Route exact path="/register" component={Register}></Route>
          <Route exact path="/login" component={Login}></Route>
          {
            //private Routes
          }
          <Switch>
            <SecureRoute
              exact
              path="/dashboard"
              component={Dashboard}
            ></SecureRoute>
            <SecureRoute
              exact
              path="/addProject"
              component={AddProject}
            ></SecureRoute>
            <SecureRoute
              exact
              path="/updateProject/:id"
              component={UpdateProject}
            ></SecureRoute>
            <SecureRoute
              exact
              path="/projectBoard/:id"
              component={ProjectBoard}
            ></SecureRoute>
            <SecureRoute
              exact
              path="/addProjectTask/:id"
              component={AddProjectTask}
            ></SecureRoute>
            <SecureRoute
              exact
              path="/updateProjectTask/:backlog_id/:pt_id"
              component={UpdateProjectTask}
            ></SecureRoute>
          </Switch>
        </div>
      </Router>
    </Provider>
  );
}

export default App;
