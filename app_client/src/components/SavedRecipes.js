import { useEffect, useState, useCallback } from "react";
import { NavLink, Link, useNavigate } from 'react-router-dom';
import AuthContext from "../contexts/AuthContext.js";
import RecipeCard from "./RecipeCard.js";
import "./SavedRecipes.css";

export default function SavedRecipes() {
    const [recipes, setRecipes] = useState([]);
    const navigate = useNavigate();

    async function findUsersRecipes() {
        const jwtToken = localStorage.getItem('jwt_token');
        const init = {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Authorization': 'Bearer ' + jwtToken
            },
        };
        try{
            const response = await fetch("http://localhost:8080/api/recipes/saved", init);
            if (response.status === 200) {
                const result = await response.json();
                const addMessage = document.getElementById("addMessage");
                addMessage.style.display = "flex";
                const savedMessage = document.getElementById("savedRecipes");
                savedMessage.style.display = "flex";
                return result;
            }
            if (response.status === 403) {
                const loginMessage = document.getElementById("loginMessage");
                loginMessage.style.display = "flex";
                return;
            }
            else {
                return Promise.reject("Unexpected error, oops");
            }
        } catch (error) {
            console.error('Error:', error);
        }
        
    }
    useEffect(() => {
        findUsersRecipes()
          .then((data) => setRecipes(data))
          .catch((err) =>
            navigate("/error", {
              state: { message: err },
            })
          );
      }, [navigate]);

    return (
        <div>
            <div id="loginMessage" className="loginMessage" style={{ display: "none", justifyContent: "center", fontFamily: "Oswald", fontSize: "54px", color: "#FFFFFF"}}> Please Login to View Saved Recipes </div>
            <div className="d-flex justify-content-between" >
                <div id="savedRecipes" className="me-auto p-2 bd-highlight" style={{ display: "none", justifyContent: "center", fontFamily:"Oswald", color: "#FFFFFF" }}>
                    <h2>Saved Recipes</h2>
                </div>
            </div>
            <div className="row-container">
                <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4">
            {recipes && recipes.length > 0 ? (
                recipes.map((recipe) => <RecipeCard key={recipe.id} recipe={recipe} />)
            ) : (
                <div id="addMessage" className="alert alert-info" style={{ display: "none", justifyContent: "center", fontFamily: "Oswald", fontSize: "54px", color: "#FFFFFF"}}>
                Nothing here yet, add a recipe!
                </div>
            )}
            </div>
            </div>
            
        </div>
        
    )
}