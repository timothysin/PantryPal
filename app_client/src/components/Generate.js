import './Generate.css'; 
import '../background-image.jpg';
import { useEffect, useState, useCallback } from "react";
import { NavLink, Link, useNavigate } from 'react-router-dom';
import { Configuration, OpenAI} from "openai";

export default function Generate() {
    const openai = new OpenAI({ apiKey: process.env.REACT_APP_OPENAI_API_KEY, dangerouslyAllowBrowser: true  });
    const navigate = useNavigate();

    const [inputCount, setInputCount] = useState(1);
    const [inputValues, setInputValues] = useState([""]);

    const [isLoading, setIsLoading] = useState(false);
    const [response, setResponse] = useState('');

    const [recipeTitle, setRecipeTitle] = useState('Generated Recipe');
    const [ingred, setIngredients] = useState('');
    const [instruct, setInstructions] = useState('');
    const [category, setCategory] = useState('');
    const [recipe, setRecipe] = useState({});


    useEffect(() => {
        if (isLoading) {
            const loadingText = document.querySelector('.loading-text');
            const dots = loadingText.querySelector('.dots');

            const animateDots = () => {
                dots.innerHTML = '...';
                setTimeout(() => {
                    dots.innerHTML = '..';
                    setTimeout(() => {
                        dots.innerHTML = '.';
                        setTimeout(animateDots, 1000);
                    }, 500);
                }, 500);
            }

            animateDots();
        }
    }, [isLoading]);

    const handleSubmit = (evt) => {
        evt.preventDefault();
        let category_id;
    
        // First, find the category
        findCategory(category)
            .then(result => {
                console.log(result);
                if (result == null) {
                    category_id = undefined;
                } else if (result.id) {
                    category_id = result.id;
                }
    
                if (category_id === undefined) {
                    // If category_id is not set, add the category
                    addCategory(category)
                        .then(result => {
                            console.log(result);
                            if (result.success) {
                                category_id = result.payload.id;
                                // Now that category_id is set, add the recipe
                                const newRecipe = {
                                    "name": recipeTitle,
                                    "ingredients": ingred,
                                    "instructions": instruct,
                                    "stat": "saved",
                                    "category_id": category_id
                                };
                                addRecipe(newRecipe)
                                    .then(recipeResult => {
                                    if (recipeResult.success) {
                                        // Display the success message
                                        const successMessage = document.getElementById("successMessage");
                                        successMessage.style.display = "block";

                                        // Display the refresh button
                                        const refreshButton = document.getElementById("refreshButton");
                                        refreshButton.style.display = "block";
                                    }
                                    });
                            } else if (!result.success) {
                                console.log(result.messages);
                            }
                        })
                        .catch(error => {
                            console.error(error);
                        });
                } else {
                    // If category_id is already set, just add the recipe
                    const newRecipe = {
                        "name": recipeTitle,
                        "ingredients": ingred,
                        "instructions": instruct,
                        "stat": "saved",
                        "category_id": category_id
                    };
                    addRecipe(newRecipe)
                        .then(recipeResult => {
                        if (recipeResult.success) {
                            // Display the success message
                            const successMessage = document.getElementById("successMessage");
                            successMessage.style.display = "block";

                            // Display the refresh button
                            const refreshButton = document.getElementById("refreshButton");
                            refreshButton.style.display = "block";
                        }
                        });
                }
            })
            .catch(error => {
                console.error(error);
            });
    };

    async function addRecipe(newRecipe) {
        console.log(newRecipe);
        const jwtToken = localStorage.getItem('jwt_token');
        const init = {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Accept': 'application/json',
              'Authorization': 'Bearer ' + jwtToken
            },
            body: JSON.stringify(newRecipe)
        };
        console.log(init);

        try {
            const response = await fetch(`http://localhost:8080/api/recipes`, init);
            
            if (response.status === 201) {
                // Parse the response body as JSON.
                const rec = await response.json();
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

    const handleCatInput = (value) => {
        setCategory(value);
    }

    const handleAddInput = () => {
        setInputCount(inputCount + 1);
        setInputValues([...inputValues, ""]);
    }

    const handleRemoveInput = () => {
        if (inputCount > 1) {
            setInputCount(inputCount - 1);
            const updatedValues = [...inputValues];
            updatedValues.pop();
            setInputValues(updatedValues);
        }
    }

    const handleInputChange = (index, value) => {
        const updatedValues = [...inputValues];
        updatedValues[index] = value;
        setInputValues(updatedValues);
        console.log(inputValues);
    }

    async function main() {
        setIsLoading(true);
        const message = "make me a recipe using strictly only these ingredients and no other ingredients (format the recipe to start with the title after Title:): " + inputValues;
        try {
            const completion = await openai.chat.completions.create({
                messages: [{ role: "system", content: message }],
                model: "gpt-3.5-turbo",
            });
            const generatedRecipe = completion.choices[0].message.content;
            setResponse(generatedRecipe);

            // Format the recipe message
            const formattedRecipe = formatRecipeMessage(generatedRecipe);
            setResponse(formattedRecipe);
            const title = formatRecipeTitle(generatedRecipe);
            setRecipeTitle(title);
        } catch (error){
            console.log(error);
        } finally {
            setIsLoading(false);
        }
        
      
    }

    async function findCategory(cat) {
        const jwtToken = localStorage.getItem('jwt_token');
        const init = {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
              'Accept': 'application/json',
              'Authorization': 'Bearer ' + jwtToken
            },
        };

        try {
            const response = await fetch(`http://localhost:8080/api/category/${cat}`, init);
            
            if (response.status === 200) {
                // Parse the response body as JSON.
                const category = await response.json();
                return category;
              } else if (response.status === 404) {
                // Handle the case where the category is not found.
                return null;
              } else if (response.status === 403) {
                const failMessage = document.getElementById("failMessage");
                failMessage.style.display = "block";
                const refreshButton = document.getElementById("refreshButton");
                refreshButton.style.display = "block";
              } else {
                // Handle other error cases.
                return Promise.reject('Error');
              }
        } catch (error) {
            console.error('Error:', error);
        }

    }

    async function addCategory(cat) {
        const jwtToken = localStorage.getItem('jwt_token');
        const init = {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Accept': 'application/json',
              'Authorization': 'Bearer ' + jwtToken
            },
            body: JSON.stringify({"label": cat})
        };

        try {
            const response = await fetch(`http://localhost:8080/api/category`, init);
            
            if (response.status === 201) {
                // Parse the response body as JSON.
                const category = await response.json();
                return category;
              } else if (response.status === 403) {
                // Handle the case where the category is not found.
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

    function formatRecipeTitle(message){
        const recipeTitleRegex = /(?:Recipe|Title):\s+(.+)/;
        const match = message.match(recipeTitleRegex);

        if (match && match[1]) {
            return match[1];
        }

        return "Generated Recipe";
    }

    function formatRecipeMessage(message) {
        // Split the message into lines
        const lines = message.split('\n');
        
        // Initialize variables to store ingredients and instructions
        const ingredients = [];
        const instructions = [];
        
        // Initialize flags to determine whether we are processing ingredients or instructions
        let isIngredientsSection = false;
        let isInstructionsSection = false;

        // Process each line in the message
        for (const line of lines) {
            if (line.toLowerCase().includes('ingredients:')) {
                isIngredientsSection = true;
                isInstructionsSection = false;
            } else if (line.toLowerCase().includes('instructions:')) {
                isIngredientsSection = false;
                isInstructionsSection = true;
            } else if (line.trim() && (isIngredientsSection || isInstructionsSection)) {
                // Remove any leading numbers and '- ' characters
                const cleanedLine = line.trim().replace(/^\d+\.\s*- /, '');
                
                // Depending on the section, add the line to either ingredients or instructions
                if (isIngredientsSection) {
                    ingredients.push(cleanedLine);
                } else if (isInstructionsSection) {
                    instructions.push(cleanedLine);
                }
            }
        }

        // Join the ingredients and instructions into formatted strings with HTML tags
        const formattedIngredients = ingredients.map(ingredient => `${ingredient}`).join('<br>');
        const formattedInstructions = instructions.map(instruction => `${instruction}<br>`).join('\n');

        setIngredients(ingredients.join(', '));
        setInstructions(instructions.join(', '));

        // Construct the final formatted message with HTML structure
        return `I'd be happy to provide you with a delicious recipe using:<br>${formattedIngredients}<br>Here's a simple idea:<br><br>${formattedInstructions}<br>These are a delicious and simple way to enjoy a classic combination. Feel free to customize them with your favorite toppings to make them even more flavorful. Enjoy!`;
    }


      return (
        <div className="container-fluid">
            <div className="my-4">
                <div className="list-ingredients">List Ingredients</div>
                <div className="input-container">
                    <div>
                        {inputValues.map((value, index) => (
                        <div key={index} className="input-row">
                            <input
                                type="text"
                                value={value}
                                placeholder="Enter your Ingredient"
                                onChange={(e) => handleInputChange(index, e.target.value)}
                            />
                            {index === inputValues.length - 1 ? (
                                <button className="add-button" onClick={handleAddInput}>
                                    +
                                </button>
                            ) : null}
                            {index === inputValues.length - 1 ? (
                                <button className="remove-button" onClick={handleRemoveInput}>
                                    -
                                </button>
                            ) : null}
                        </div>
                    ))}
                    </div>
                
                <div className="button-container">
                    <button className="submit-button" onClick={main}>
                        <img className="init-img" src="https://thenounproject.com/api/private/icons/5427069/edit/?backgroundShape=SQUARE&backgroundShapeColor=%23000000&backgroundShapeOpacity=0&exportSize=752&flipX=false&flipY=false&foregroundColor=%23000000&foregroundOpacity=1&imageFormat=png&rotation=0"
                        width="200" height="200" alt=""/>
                        <img className="hover-img" src="https://thenounproject.com/api/private/icons/5427070/edit/?backgroundShape=SQUARE&backgroundShapeColor=%23000000&backgroundShapeOpacity=0&exportSize=752&flipX=false&flipY=false&foregroundColor=%23000000&foregroundOpacity=1&imageFormat=png&rotation=0"
                        width="200" height="200" alt=""/>
                    </button>
                </div>
                {isLoading && (
                        <div className="loading">
                            <span className="loading-text">
                                Generating<span className="dots"></span>
                            </span>
                        </div>
                    )}
                    
            </div>
            {!isLoading && response && (
                <div className="recipe">
                    <h2>{recipeTitle}</h2>
                    <div dangerouslySetInnerHTML={{ __html: response }}></div>
                    <input className="category-input" type="text" placeholder="Enter a Category" onChange={(e) => handleCatInput(e.target.value)}/>
                    <button className="save-button" onClick={handleSubmit}>Save</button>
                    <div id="successMessage" className="success-message" style={{ display: "none" }}> Recipe saved successfully! </div>
                    <div id="failMessage" className="fail-message" style={{ display: "none" }}> Must Sign In to Save! </div>
                    <button id="refreshButton" className="generate-another" style={{ display: "none" }} onClick={() => window.location.reload()}> Make Another Recipe </button>
                </div>
            )}
            </div>
            
        </div>
    );
}