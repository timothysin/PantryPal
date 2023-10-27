import { useEffect, useState, useCallback } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";

import Error from "./components/Error.js";
import NavBar from "./Nav/NavBar.jsx";
import Generate from "./components/Generate.js";
import AuthContext from "./contexts/AuthContext.js";
import LoginForm from "./components/LoginForm.js";
import SignUpForm from "./components/SignUpForm.js";
import SavedRecipes from "./components/SavedRecipes.js";
import RecipePage from "./components/RecipePage.js";
import Home from "./components/Home.js";


import { refreshToken, logout } from "./services/authAPI.js";

const TIMEOUT_MILLISECONDS = 14 * 60 * 1000;

function App() {

  const [user, setUser] = useState();
  const [initialized, setInitialized] = useState(false);

  const resetUser = useCallback(() => {
    refreshToken()
      .then((user) => {
        setUser(user);
        setTimeout(resetUser, TIMEOUT_MILLISECONDS);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => setInitialized(true));
  }, []);

  useEffect(() => {
    resetUser();
  }, [resetUser]);

  const auth = {
    user: user,
    handleLoggedIn(user) {
      setUser(user);
      setTimeout(resetUser, TIMEOUT_MILLISECONDS);
    },
    hasAuthority(authority) {
      return user?.authorities.includes(authority);
    },
    logout() {
      logout();
      setUser(null);
    },
  };

  if (!initialized) {
    return null;
  }

  const renderWithAuthority = (Component, ...authorities) => {
    for (let authority of authorities) {
      if (auth.hasAuthority(authority)) {
        return <Component />;
      }
    }
    return <Error />;
  };


  return (
      <AuthContext.Provider value={auth}>
        <Router>
          <NavBar></NavBar>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/generate" element={<Generate />} />
            <Route path="/login" element={<LoginForm />} />
            <Route path="/signup" element={<SignUpForm />} />
            <Route path="/recipes" element={<SavedRecipes />} />
            <Route path="/recipe/:id" element={<RecipePage />} />
            <Route path="/error" element={<Error />} />
            <Route path="*" element={<Error />} />
          </Routes>
        </Router>
      </AuthContext.Provider>
  );
}

export default App;
