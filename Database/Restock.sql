CREATE TABLE Restock(
    Restock_id SERIAL PRIMARY KEY,
    Item_ID INT REFERENCES Inventory(Item_ID),
    Max INT,
    Min INT,
    Shelf_Life INT,
    last_restock Date
);

