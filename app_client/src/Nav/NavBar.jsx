import { useContext } from "react";
import { NavLink, Link } from "react-router-dom";

import AuthContext from "../contexts/AuthContext";

import React from 'react';
import styled from 'styled-components';
import Burger from './Burger';

const Nav = styled.nav`
  width: 100%;
  height: 140px;
  padding: 0 20px;
  display: flex;
  justify-content: left;

  .logo {
    padding: 15px 0;
    font-family: Oswald;
    font-size: 100px;
    color: #FFFFFF;
    position: absolute;
    left: 160px;
  }
`

const Navbar = () => {
  return (
    <Nav>
      <div className="logo">
        PantryPal
      </div>
      <Burger />
    </Nav>
  )
}

export default Navbar