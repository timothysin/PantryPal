import { useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../services/authAPI.js";
import './LoginForm.css';

import AuthContext from "../contexts/AuthContext.js";
import ValidationSummary from "./ValidationSummary.js";

function LoginForm() {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });
  const [errors, setErrors] = useState([]);

  const { handleLoggedIn } = useContext(AuthContext);

  const navigate = useNavigate()

  const handleSubmit = (evt) => {
    evt.preventDefault();
    setErrors([]);
    login(credentials)
      .then(user => {
        handleLoggedIn(user);
        navigate("/");
      })
      .catch(err => {
        setErrors(['Invalid username/password.']);
      });
  };

  const handleChange = (evt) => {
    const nextCredentials = {...credentials};
    nextCredentials[evt.target.name] = evt.target.value;
    setCredentials(nextCredentials);
  };

  return (
    <div className="login-form-container">
      <ValidationSummary errors={errors} />
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="username"
            className="form-control"
            id="username"
            name="username"
            value={credentials.username}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            className="form-control"
            id="password"
            name="password"
            value={credentials.password}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-actions">
          <Link to="/" className="btn btn-secondary">
            Cancel
          </Link>
          <button type="submit" className="btn btn-primary">
            Log in
          </button>
        </div>
      </form>
    </div>
  );
}

export default LoginForm;
