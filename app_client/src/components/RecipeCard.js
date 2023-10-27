import { useContext } from "react";
import { Link } from "react-router-dom";
import RecipePage from "./RecipePage.js";
import "./RecipeCard.css"

export default function RecipeCard({ recipe }) {
    return (
        <div className="col mb-4">
            <div className="card">
                <p className="recipe-name">{recipe.name}</p>
                <Link to={`/recipe/${recipe.id}`}>
                    <button className="info-button">Info</button>
                </Link>
            </div>
        </div>
    );
}