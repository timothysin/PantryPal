import { useState } from "react";
import { Link } from "react-router-dom";
import { register } from "../services/authAPI.js";
import './SignUpForm.css';

import ValidationSummary from "./ValidationSummary.js";

function SignUpForm() {
  const [errors, setErrors] = useState([]);
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [success, setSuccess] = useState(false);

  const handleChange = (evt) => {
    const nextCredentials = { ...credentials };
    nextCredentials[evt.target.name] = evt.target.value;
    setCredentials(nextCredentials);
  };

  const handleSubmit = (evt) => {
    evt.preventDefault();
    setErrors([]);
    if (!validateForm()) {
      setErrors(["Passwords do not match!"]);
      return;
    }

    register(credentials).then((data) => {
      if (data && data.errors) {
        setErrors(data.errors);
      } else {
        setSuccess(true);
      }
    });
  };

  const validateForm = () => {
    return credentials.password === credentials.confirmPassword;
  };

  return (
    <div className="signup-form-container">
      <ValidationSummary errors={errors} />
      {success ? (
        <div className="alert alert-success">
          Congratulations {credentials.username}, you have been registered.
          Login <Link to="/login">here</Link>.
        </div>
      ) : (
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
          <div className="form-group">
            <label htmlFor="confirmPassword">Confirm password</label>
            <input
              type="password"
              className="form-control"
              id="confirmPassword"
              name="confirmPassword"
              value={credentials.confirmPassword}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-actions">
            <Link to="/" className="btn btn-secondary">
              Cancel
            </Link>
            <button type="submit" className="btn btn-primary">
              Sign up
            </button>
          </div>
        </form>
      )}
    </div>
  );
}

export default SignUpForm;
