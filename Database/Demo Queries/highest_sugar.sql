SELECT m.MenuItem_Name, r.quantity_required AS sugar_content
    FROM Recipes r
    JOIN Inventory i ON r.Item_ID=i.Item_ID
    JOIN MenuItem m ON r.MenuItem_ID = m.MenuItem_ID
    WHERE i.Item_Name='Sugar'
    ORDER BY r.Quantity_Required DESC
    LIMIT 1;
