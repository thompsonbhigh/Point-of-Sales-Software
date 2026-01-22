SELECT
    mi.MenuItem_Name,
    COUNT(r.Item_ID) AS ingredient_count
FROM
    MenuItem AS mi
JOIN
    Recipes AS r ON mi.MenuItem_ID = r.MenuItem_ID
GROUP BY
    mi.MenuItem_Name
ORDER BY
    ingredient_count DESC;