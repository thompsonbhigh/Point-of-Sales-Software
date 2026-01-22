--Find ingredients that are low (<10)
SELECT 
    Item_Name, 
    Quantity, 
    Unit
FROM 
    Inventory
WHERE 
    Quantity < 10;