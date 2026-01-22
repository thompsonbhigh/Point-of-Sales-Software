CREATE TABLE MenuPrice (
    Size VARCHAR(50) PRIMARY KEY NOT NULL,
    Price DECIMAL(6,2) NOT NULL
);

INSERT INTO MenuPrice (Size, Price) VALUES 
    ('Bowl', 8.50),
    ('Plate', 10.10),
    ('Bigger Plate', 11.70),
    ('Kids',6.80),
    ('Family', 44.00),
    ('Appetizer',2.10),
    ('Small', 5.40),
    ('Medium', 8.70),
    ('Large', 11.40),
    ('Drink', 2.20),
    ('Side', 0);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    employee_id INT NOT NULL,
    total_cost DECIMAL(6,2) DEFAULT 0, 
    time_stamp DATETIME NOT NULL
);

CREATE TABLE OrderEntry (
    entry_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES Orders(order_id),
    MenuItem_ID INT NOT NULL REFERENCES MenuItem(MenuItem_ID),
    Size VARCHAR(50) NOT NULL,
    price DECIMAL(6, 2)
);

-- Function to auto-fill OrderEntry.price
CREATE OR REPLACE FUNCTION insert_entry_price()
RETURNS TRIGGER AS $$
DECLARE
    itemCategory VARCHAR(50);
    existingCount INT;
BEGIN
    -- Find the category of the item being inserted
    SELECT Category INTO itemCategory
    FROM MenuItem
    WHERE MenuItem_ID = NEW.MenuItem_ID;

    -- Case 1: Drinks & Appetizers → priced directly
    IF itemCategory IN ('Drink','Appetizer') THEN
        SELECT Price INTO NEW.price
        FROM MenuPrice
        WHERE Size = itemCategory;

    -- Case 2: Sides → always free (price = 0)
    ELSIF itemCategory = 'Side' THEN
        NEW.price := 0.00;

    -- Case 3: Combos (Bowl, Plate, Bigger Plate, Family, Kids)
    ELSIF NEW.Size IN ('Bowl','Plate','Bigger Plate','Family','Kids') THEN
        -- Check if this order already has a paid row for the combo
        SELECT COUNT(*) INTO existingCount
        FROM OrderEntry
        WHERE order_id = NEW.order_id
          AND Size = NEW.Size
          AND price > 0;  -- only count the first priced combo

        IF existingCount = 0 THEN
            -- First entrée for this combo size gets the full base price
            SELECT Price INTO NEW.price
            FROM MenuPrice
            WHERE Size = NEW.Size;
        ELSE
            -- Additional combo entrees get 0.00 base price
            NEW.price := 0.00;
        END IF;

    -- Case 4: À la carte (Small, Medium, Large)
    ELSE
        SELECT Price INTO NEW.price
        FROM MenuPrice
        WHERE Size = NEW.Size;
    END IF;

    -- Premium surcharge logic (applies even if base price = 0.00)
    IF itemCategory = 'Premium Entree' THEN
        IF NEW.Size = 'Small' THEN
            NEW.price := NEW.price + 1.50;
        ELSIF NEW.Size = 'Medium' THEN
            NEW.price := NEW.price + 3.00;
        ELSIF NEW.Size = 'Large' THEN
            NEW.price := NEW.price + 4.50;
        ELSE
            -- Default surcharge for all combo sizes (Bowl, Plate, Bigger Plate, Family, Kids)
            NEW.price := NEW.price + 1.50;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger for price insert
CREATE TRIGGER trg_insert_entry_price
BEFORE INSERT ON OrderEntry
FOR EACH ROW
EXECUTE FUNCTION insert_entry_price();



-- Function to keep Orders.total_cost updated
CREATE OR REPLACE FUNCTION update_order_total()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE Orders o
    SET total_cost = (
        SELECT COALESCE(SUM(price), 0)
        FROM OrderEntry
        WHERE order_id = NEW.order_id
    )
    WHERE o.order_id = NEW.order_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger for order totals
CREATE TRIGGER trg_update_order_total
AFTER INSERT OR UPDATE OR DELETE ON OrderEntry
FOR EACH ROW
EXECUTE FUNCTION update_order_total();
