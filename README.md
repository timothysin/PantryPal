# PantryPal

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#proposal">Proposal</a>
      <ul>
        <li><a href="#Problem Statement">Problem Statement</a></li>
        <li><a href="#Technical Solution">Technical Solution</a></li>
        <li><a href="#Glossary">Glossary</a></li>
        <li><a href="#High Level Requirement">High Level Requirement</a></li>
        <li><a href="#User Stories / Scenarios">User Stories / Scenarios</a></li>
      </ul>
    </li>
    <li>
      <a href="#Checklist">Checklist</a>
    </li>
  </ol>
</details>


<!-- Proposal -->
## Proposal

### Problem Statement

Have you ever found yourself standing in front of your refrigerator, staring at a bunch of leftover ingredients and wondering what to make for your next meal? You're not alone. It's a common challenge many people face, leading to food waste and missed opportunities to create delicious, cost-effective meals at home.

Our lives are busy, and we don't always have time to plan elaborate recipes or make frequent trips to the grocery store. As a result, perfectly good ingredients often go to waste, and we end up ordering takeout instead.

But what if there was a solution that could transform your leftover ingredients into mouthwatering recipes with just a few clicks? That's where PantryPal comes in.

### Technical Solution

Create an application for generating recipes based off of the ingredients you have in your pantry/kitchen. Make it easy to input available ingredients as well as provide detailed recipes, including step-by-step instructions and possibly nutritional information. Make it possible to post/save recipes for each user. Additionally, the app could incorporate user-generated content and reviews, allowing for community engagement.

**Scenario 1: Busy Weeknight Dinner**

Lisa is a working mother with a busy schedule. After a long day at the office, she opens the PantryPal and enters the ingredients she has at home, which include chicken, broccoli, and some basic pantry staples. The app suggests quick and easy recipes, like a stir-fry with a delicious sauce. Lisa selects the recipe, and the app generates a step-by-step cooking guide, helping her prepare a tasty, home-cooked meal in under 30 minutes.

**Scenario 2: Dietary Restrictions**

John is a college student who has recently adopted a vegetarian diet. He's still learning to cook without meat and often struggles to come up with diverse vegetarian meals. Using PantryPal, John lists the vegetables, grains, and legumes he has. The app recommends various vegetarian recipes, taking his dietary restrictions into account. John selects a recipe for a chickpea curry, and the app provides a detailed ingredient list and cooking instructions, making it easier for him to explore new vegetarian dishes.

**Scenario 3: Reducing Food Waste**

Sarah wants to be more eco-conscious and reduce food waste. She uses PantryPal to check what ingredients are about to expire in her fridge, including some leftover vegetables, cheese, and half a carton of eggs. The app suggests a frittata recipe that uses all these ingredients. Sarah is thrilled because she's not only creating a delicious meal but also minimizing food waste.

### Glossary

**Recipe App**

The software or platform designed to help users discover, plan, and create meals based on available ingredients, dietary preferences, and other user-specific criteria.

**User**

Individuals who access and interact with the Recipe App. Users may access recipes posted by the community. Users may include home cooks, chefs, or anyone seeking culinary inspiration. All members are users, but not all users are members.

**Member**

Individuals who have signed up on the app. Members may post/edit/delete their own recipes for the community and save other existing recipes for their personal use.

**Admin**

Individuals who oversee the community page and are able to delete other recipes posted by members. All admins are members, but not all members are admins.

**Ingredients**

The individual food items, spices, and pantry staples that users have on hand and wish to incorporate into their meal planning.

**Cooking Guide / Recipe**

Step-by-step instructions and tips provided by the Recipe App to assist users in preparing the selected recipes.

**Shopping List (Stretch)**

A feature of the Recipe App that compiles the additional ingredients required for selected recipes, making it easier for users to shop for what they need.


### High Level Requirement

* Create a recipe (anyone)
* Save a recipe (MEMBER, ADMIN)
* Edit own recipe (MEMBER, ADMIN)
* Delete own recipe (MEMBER, ADMIN)
* Post recipes on community (MEMBER, ADMIN)
* Edit own recipes on community (MEMBER, ADMIN)
* Delete own recipes on community (MEMBER, ADMIN)
* Delete others recipes community (ADMIN)
* Browse community recipes (anyone)
* Comment on recipes (MEMBER, ADMIN)
* Apply to be a member (Authenticated)

### User Stories / Scenarios

**Create A Recipe**

Suggested Data: 
* Ingredients (e.g. eggs, potatoes)

Description: Users can create new recipes and add them to the Recipe App's database if they are a member or admin. This includes entering the recipe's name, a list of ingredients, step-by-step instructions, and other relevant details.

Precondition: Any user can create a recipe.

Post-condition: If the user is a member or admin, the newly created recipe may be saved in the Recipe App's database.

**Save A Recipe**

Description: Members and admins can save recipes they find interesting to their personal collections for future reference.

Precondition: The user must be logged in as a MEMBER or ADMIN.

Post-condition: The saved recipe is added to the user's personal collection.

**Edit Own Recipe**

Description: Members and admins can edit recipes they have created or saved. This includes making changes to the ingredients, instructions, and other details.

Precondition: The user must be logged in as a MEMBER or ADMIN, and the recipe must be one they've created or saved.

Post-condition: The edited recipe is updated in the Recipe App's database.

**Delete Own Recipe**

Description: Members and admins have the option to delete recipes they've created or saved in their personal collections.

Precondition: The user must be logged in as a MEMBER or ADMIN, and the recipe must be one they've created or saved.

Post-condition: The deleted recipe is removed from the Recipe App's database.

**Post Recipes On Community**

Description: Members and admins can choose to share their recipes with the Recipe App's community. These shared recipes are accessible to all users.

Precondition: The user must be logged in as a MEMBER or ADMIN.

Post-condition: The shared recipe is made visible in the Recipe App's community.

**Edit Own Recipes on Community**

Description: Members and admins can edit recipes they've posted in the community. This includes updating ingredients, instructions, and other recipe details.

Precondition: The user must be logged in as a MEMBER or ADMIN, and the recipe must be one they've posted in the community.

Post-condition: The edited recipe is updated in the community recipe database.

**Delete Own Recipes on Community**

Description: Members and admins can remove recipes they've posted in the community.

Precondition: The user must be logged in as a MEMBER or ADMIN, and the recipe must be one they've posted in the community.

Post-condition: The deleted recipe is removed from the community recipe database.

**Delete Others' Recipes on Community**

Description: Admins have the authority to delete recipes posted by other users in the community.

Precondition: The user must be logged in with ADMIN privileges.

Post-condition: The deleted recipe is removed from the community recipe database.

**Browse Community Recipes**

Description: Users can access and view recipes shared by members and admins in the Recipe App's community. They can filter and search for recipes based on criteria like date, ingredients, or dietary preferences.

Precondition: None.

Post-condition: Users can view and interact with recipes in the community.

**Comment on Recipes**

Description: Members and admins can leave comments on recipes in the community, sharing feedback, tips, or additional information.

Precondition: The user must be logged in as a MEMBER or ADMIN, and the recipe must be one posted in the community.

Post-condition: The comment is added to the recipe's comments section.