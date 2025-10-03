# 🍳 Recipe App

A simple and elegant Recipe App built with Jetpack Compose.  
To run the project on your device, you’ll need to add your **API Key**.

📹 **App Demo Video** → [Watch here](https://github.com/rahul3310/RecipeApp/issues/1#issue-3481580908)

---

## 📥 How to Clone the Project
```
# Clone the repository
git clone https://github.com/rahul3310/RecipeApp.git

# Move into the project directory
cd RecipeApp

# Open in Android Studio and let it sync the Gradle dependencies
```

## 🔑 Setup Instructions

1. Generate an API key from [Spoonacular Food API](https://spoonacular.com/food-api).
2. Open your **`gradle.properties`** file.
3. Add the following line (replace `****************` with your actual key):

   ```properties
   RECIPE_API_KEY = ****************
   ```
## ⚙️ Tech Stack

- Jetpack Compose – Modern UI toolkit

- Hilt – Dependency Injection

- Hilt Worker – For background work injection

- Room Database – Local storage

- MVVM Architecture – Clean architecture pattern
  

## Features
- View Recipe list (All & Popular)
- Mark recipes as Favorite
- Get notified for favorite recipes if reminder is set
- Delete favorites by swiping right to left
- Search recipes
