--most frequent ingredient in the recipes 
SELECT 
    i.Item_Name, 
    COUNT(DISTINCT r.MenuItem_ID) AS numMenuItems
FROM 
    Recipes r
JOIN 
    Inventory i ON r.Item_ID = i.Item_ID
GROUP BY 
    i.Item_Name
ORDER BY 
    numMenuItems DESC;