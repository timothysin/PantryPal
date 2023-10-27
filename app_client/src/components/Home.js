import { useContext, useState } from "react";
import { Link } from "react-router-dom";
import "./Home.css";
import { SVG } from '@svgdotjs/svg.js';

export default function Home() {
    
    return (
        <div id="home-page" className="home-page">
            <img className="init-img" src="https://thenounproject.com/api/private/icons/489212/edit/?backgroundShape=SQUARE&backgroundShapeColor=%23000000&backgroundShapeOpacity=0&exportSize=752&flipX=false&flipY=false&foregroundColor=%23000000&foregroundOpacity=1&imageFormat=png&rotation=0"
                        width="1000" height="1000" alt=""/>
            <div>
                <p>Have Ingredients In Your Fridge and Pantry Going Bad?</p>
                <p>Can't Decide What to Cook?</p>
                <p>Use PantryPal!</p>
                <p>A web application utilizing AI to generate recipes based off of your available ingredients!</p>
            </div>
            
        </div>
    )
}