import { useEffect, useState, useCallback } from "react";
import { useParams } from 'react-router-dom';
import './RecipePage.css'; 

export default function RecipePage() {
    const [recipe, setRecipe] = useState({});
    const { id } = useParams();

    useEffect(() => {
        async function findRecipeById() {
        const jwtToken = localStorage.getItem('jwt_token');
        const init = {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
              'Accept': 'application/json',
              'Authorization': 'Bearer ' + jwtToken
            }
        };
            try {
                const response = await fetch(`http://localhost:8080/api/recipes/${id}`, init);
                
                if (response.status === 200) {
                    // Parse the response body as JSON.
                    const rec = await response.json();
                    setRecipe(rec);
                    return rec;
                } else if (response.status === 403) {
                    return "Must Sign In to Save";
                } else {
                    // Handle other error cases.
                    const errorText = await response.text(); // Get the response body text.
                    console.error('Error:', response.status, errorText);
                    return errorText;
                }
            } catch (error) {
                console.error('Error:', error);
                return Promise.reject('Error');
            }
        }
        
        findRecipeById();
    }, [id]);

    console.log(recipe.ingredients);

    
    return (
        <div className="page-container">
          <div className="details">
            <h2 className="recipe-title">{recipe.name}</h2>
            <div className="recipe-section">
              <h3>Ingredients</h3>
              {recipe.ingredients &&
  recipe.ingredients
    .split('-')
    .map((ingredient, index) => ingredient.trim())
    .filter((ingredient) => ingredient !== "")
    .map((ingredient, index) => (
      <p key={index} className="ingredient">
        {`- ${ingredient}`}
      </p>
    ))
}
            </div>
            <div className="recipe-section">
              <h3>Instructions</h3>
              {recipe.instructions &&
                recipe.instructions
                    .split(/\d+\.\s+/)
                    .filter((item) => item.trim() !== '')
                    .map((instruction, index) => (
                    <p key={index} className="instruction">
                        {`${index + 1}. ${instruction.trim()}`.replace(/,(\s*)$/, '$1')}
                    </p>
                ))}
            </div>
          </div>
        </div>
      );
      

    
}