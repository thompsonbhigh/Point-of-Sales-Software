CREATE TABLE Inventory (
    Item_ID SERIAL PRIMARY KEY,
    Item_Name VARCHAR(100) NOT NULL,
    Quantity INT DEFAULT 0,
    Unit VARCHAR(20) NOT NULL
);

CREATE TABLE MenuItem (
    MenuItem_ID SERIAL PRIMARY KEY,
    MenuItem_Name VARCHAR(100) NOT NULL,
    Category VARCHAR(50) CHECK (Category IN ('Entree', 'Premium Entree', 'Side', 'Drink', 'Appetizer')) NOT NULL
);

CREATE TABLE Recipes (
    recipe_ID SERIAL PRIMARY KEY,
    MenuItem_ID INT REFERENCES MenuItem(MenuItem_ID),
    Item_ID INT REFERENCES Inventory(Item_ID),
    Quantity_Required DECIMAL(10, 2) NOT NULL
);

INSERT INTO MenuItem (MenuItem_Name, Category) VALUES
    --Sides
    ('White Rice', 'Side'),
    ('Fried Rice', 'Side'),
    ('Chow Mein', 'Side'), 
    ('Super Greens', 'Side'),

    --Entrees
    ('Honey Walnut Shrimp', 'Premium Entree'),
    ('Black Pepper Angus Steak', 'Premium Entree'),
    ('Mushroom Chicken', 'Entree'), 
    ('Kung Pao Chicken', 'Entree'),
    ('String Bean Chicken', 'Entree'),
    ('Orange Chicken', 'Entree'),
    ('Sweet Fire Chicken Breast', 'Entree'),
    ('Honey Sesame Chicken Breast', 'Entree'),
    ('Grilled Teriyaki Chicken', 'Entree'),
    ('Broccoli Beef', 'Entree'),
    ('Beijing Beef', 'Entree'), 
    ('Black Pepper Chicken', 'Entree'),
    ('Eggplant Tofu', 'Entree'),

    --Appetizers
    ('Veggie Spring Roll', 'Appetizer'),
    ('Chicken Egg Roll', 'Appetizer'),
    ('Cream Cheese Rangoon', 'Appetizer'),

    --Drinks
    ('Coke', 'Drink'),
    ('Pepsi', 'Drink'),
    ('Lemonade', 'Drink'),
    ('Iced Tea', 'Drink'),
    ('Water', 'Drink');


INSERT INTO Inventory (Item_Name, Quantity, Unit) VALUES
    --Proteins
    ('Chicken Thigh Meat', 200, 'lbs'),
    ('Beef slices', 150, 'lbs'),
    ('Shrimp', 100, 'lbs'),
    ('Tofu', 50, 'lbs'),

    --Vegetables
    ('Broccoli', 100, 'lbs'),
    ('String Beans', 100, 'lbs'),
    ('Mushrooms', 100, 'lbs'),
    ('Eggplant', 100, 'lbs'),
    ('Carrots', 100, 'lbs'),
    ('Cabbage', 100, 'lbs'),
    ('Green Onions', 50, 'lbs'),
    ('Snow Peas', 50, 'lbs'),
    ('Zucchini', 50, 'lbs'),
    ('Red Bell Peppers', 50, 'lbs'),
    ('Yellow Bell Peppers', 50, 'lbs'),
    ('Onions', 100, 'lbs'),
    ('Garlic', 20, 'lbs'),
    ('Ginger', 20, 'lbs'),

    -- Staples and sauces
    ('White Rice', 200, 'lbs'),
    ('Noodles', 200, 'lbs'),
    ('Soy Sauce', 20, 'gallons'),
    ('Oyster Sauce', 10, 'gallons'),
    ('Hoisin Sauce', 10, 'gallons'),
    ('Sesame Oil', 5, 'gallons'),
    ('Vegetable Oil', 20, 'gallons'),
    ('Cornstarch', 50, 'lbs'),
    ('Sugar', 50, 'lbs'),
    ('Salt', 50, 'lbs'),
    ('Black Pepper', 20, 'lbs'),
    ('Chili Flakes', 10, 'lbs'),
    ('Rice Vinegar', 10, 'gallons'),
    ('Chicken Broth', 20, 'gallons'),
    ('Eggs', 60, 'lbs'),
    ('Flour', 100, 'lbs'),
    ('Bread Crumbs', 50, 'lbs'),
    ('Sesame Seeds', 10, 'lbs'),
    ('Mayonnaise', 20, 'lbs'),
    ('Honey', 20, 'lbs'),
    ('Walnuts', 20, 'lbs'),
    ('Beef Broth', 20, 'gallons'),
    ('Cream Cheese', 30, 'lbs'),
    

    -- Drinks
    ('Coke', 50, 'bottles'),
    ('Pepsi', 50, 'bottles'),
    ('Lemonade', 50, 'bottles'),
    ('Iced Tea', 50, 'bottles'),
    ('Water', 100, 'bottles'),

    -- Other
    ('Bowls', 10, 'cases'),
    ('Plates', 10, 'cases'),
    ('Cups', 10, 'cases'),
    ('Bags', 4, 'cases'),
    ('Napkins', 10, 'packs'),
    ('Straws', 10, 'packs');

Insert INTO Recipes (MenuItem_ID, Item_ID, Quantity_Required) VALUES
    --Sides
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'White Rice'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'White Rice'), 25.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Fried Rice'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'White Rice'), 20.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Fried Rice'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Carrots'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Fried Rice'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cabbage'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Fried Rice'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Green Onions'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Fried Rice'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Eggs'), 1.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Fried Rice'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Fried Rice'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chow Mein'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Noodles'), 20.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chow Mein'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cabbage'), 10.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chow Mein'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Carrots'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chow Mein'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Onions'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chow Mein'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Green Onions'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chow Mein'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 0.5),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chow Mein'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Super Greens'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Broccoli'), 15.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Super Greens'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'String Beans'), 10.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Super Greens'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Snow Peas'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Super Greens'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Carrots'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Super Greens'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Super Greens'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    --Entrees
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Walnut Shrimp'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Shrimp'), 10.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Walnut Shrimp'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Walnut Shrimp'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Eggs'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Walnut Shrimp'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Walnut Shrimp'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sugar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Walnut Shrimp'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Mayonnaise'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Walnut Shrimp'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Honey'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Walnut Shrimp'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Walnuts'), 1.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Angus Steak'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Beef slices'), 10.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Angus Steak'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Onions'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Angus Steak'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Green Onions'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Angus Steak'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Angus Steak'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Oyster Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Angus Steak'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Angus Steak'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Black Pepper'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Angus Steak'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Mushrooms'), 10.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Onions'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Oyster Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Broth'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Mushroom Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Carrots'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Zucchini'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Red Bell Peppers'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Green Onions'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Rice Vinegar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sugar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Kung Pao Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chili Flakes'), 0.50),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'String Beans'), 10.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Oyster Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Broth'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'String Bean Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Eggs'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sugar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Rice Vinegar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Orange Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Broth'), 0.50),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Eggs'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sugar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Rice Vinegar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chili Flakes'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Sweet Fire Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Broth'), 0.50),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Eggs'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sugar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Rice Vinegar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Broth'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sesame Oil'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Honey Sesame Chicken Breast'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sesame Seeds'), 0.10),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Grilled Teriyaki Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Grilled Teriyaki Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Grilled Teriyaki Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sugar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Grilled Teriyaki Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Rice Vinegar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Grilled Teriyaki Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Grilled Teriyaki Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Grilled Teriyaki Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sesame Oil'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Grilled Teriyaki Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Beef slices'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Broccoli'), 10.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Oyster Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Beef Broth'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Broccoli Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Beef slices'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Eggs'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cornstarch'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sugar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Rice Vinegar'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Beijing Beef'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Beef Broth'), 0.50),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Onions'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Green Onions'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Oyster Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Black Pepper'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Black Pepper Chicken'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Eggplant Tofu'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Tofu'), 8.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Eggplant Tofu'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Eggplant'), 10.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Eggplant Tofu'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Eggplant Tofu'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Ginger'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Eggplant Tofu'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Eggplant Tofu'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Oyster Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Eggplant Tofu'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    --Appetizers
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Veggie Spring Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cabbage'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Veggie Spring Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Carrots'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Veggie Spring Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Green Onions'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Veggie Spring Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Mushrooms'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Veggie Spring Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Noodles'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Veggie Spring Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Veggie Spring Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Veggie Spring Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Flour'), 2.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cabbage'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Carrots'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Green Onions'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Mushrooms'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Noodles'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Chicken Thigh Meat'), 5.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Soy Sauce'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Chicken Egg Roll'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Flour'), 2.00),

    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Cream Cheese Rangoon'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Cream Cheese'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Cream Cheese Rangoon'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Green Onions'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Cream Cheese Rangoon'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Garlic'), 0.50),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Cream Cheese Rangoon'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Vegetable Oil'), 1.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Cream Cheese Rangoon'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Flour'), 2.00),
    ((SELECT MenuItem_ID FROM MenuItem WHERE MenuItem_Name = 'Cream Cheese Rangoon'), (SELECT Item_ID FROM Inventory WHERE Item_Name = 'Sugar'), 0.10);


