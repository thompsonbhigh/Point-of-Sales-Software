-- Create the Size table to store different size options and their prices.
CREATE TABLE Size (
    size VARCHAR(50) PRIMARY KEY,
    price FLOAT
);

-- Insert the 'bowl' size and its corresponding price into the Size table.
INSERT INTO Size (size, price) VALUES ('bowl', 8.50);
INSERT INTO Size (size, price) VALUES ('plate', 10.10);
INSERT INTO Size (size, price) VALUES ('drink', 2.50);
INSERT INTO Size (size, price) VALUES ('family', 44.00);
INSERT INTO Size (size, price) VALUES ('bigger plate', 11.70);
INSERT INTO Size (size, price) VALUES ('Entree S a la carte', 5.40);
INSERT INTO Size (size, price) VALUES ('Entree M a la carte', 8.70);
INSERT INTO Size (size, price) VALUES ('Entree L a la carte', 11.40);
INSERT INTO Size (size, price) VALUES ('Side S a la carte', 4.60);
INSERT INTO Size (size, price) VALUES ('Side M a la carte', 5.60);