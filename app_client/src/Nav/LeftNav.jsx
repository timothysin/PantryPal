import React from 'react';
import styled from 'styled-components';
import { useContext } from "react";
import { NavLink, Link } from "react-router-dom";

import AuthContext from "../contexts/AuthContext";

const Ul = styled.ul`
  list-style: none;
  display: flex;
  flex-flow: column nowrap;
  background-color: tan;
  position: fixed;
  transform: ${({ open }) => open ? 'translateX(0)' : 'translateX(0%)'};
  top: 0;
  left: 0;
  height: 100vh;
  width: 300px;
  padding-top: 7rem;
  transition: transform 0.3s ease-in-out;

  li {
    padding: 18px 10px;
    color: #fff;
    margin-left: 30px;
  }

  .auth-links {
    color: #fff;
    text-decoration: none;
  }

  .badge {
    background-color: #311b07; /* Background color */
    color: #fff; /* Text color */
    padding: 0.2rem 0.5rem; /* Padding */
    border-radius: 1rem; /* Rounded corners */
    font-size: 0.9rem; /* Font size */
    margin-left: 30px;
  }
  
  /* Style for the logout button */
  button.btn {
    background-color: #fff; /* Background color */
    color: #542f0d; /* Text color */
    border: 1px solid #542f0d; /* Border style */
    padding: 0.2rem 0.5rem; /* Padding */
    border-radius: 0.25rem; /* Slightly rounded corners */
    font-size: 0.9rem; /* Font size */
    cursor: pointer; /* Pointer cursor on hover */
  }
  
  button.btn:hover {
    background-color: #311b07; /* Background color on hover */
    color: #fff; /* Text color on hover */


  @media (max-width: 768px) {
    flex-flow: column nowrap;
    background-color: #0D2538;
    position: fixed;
    transform: ${({ open }) => open ? 'translateX(0)' : 'translateX(0%)'};
    top: 0;
    left: 0;
    height: 100vh;
    width: 300px;
    padding-top: 3.5rem;
    transition: transform 0.3s ease-in-out;

    li {
      color: #fff;
    }
  }
`;

const LeftNav = ({ open }) => {
  const { user, logout } = useContext(AuthContext);
  return (
    <Ul open={open}>
      <li><Link to="/" className="auth-links">Home</Link></li>
      <li><Link to="/generate" className="auth-links">Generate Recipe</Link></li>
      <li><Link to="/recipes" className="auth-links">Your Saved Recipes</Link></li>
      <li>About Us</li>
      <li>Contact Us</li>
      {!user && <div style={{ marginLeft: '40px', marginTop: '20px'}}>
             <Link to="/login" className="auth-links">Login</Link>
             { '  |  ' }
             <Link to="/signup" className="auth-links">Sign Up?</Link>
            </div>}
          {user && (
            <div>
              <span className="badge rounded-pill text-bg-info">
                {user.username}
              </span>
              {}
              <button
                onClick={logout}
                className="btn btn-outline-secondary btn-sm"
              >
                Log out
              </button>
            </div>
          )}
    </Ul>
  )
}

export default LeftNav