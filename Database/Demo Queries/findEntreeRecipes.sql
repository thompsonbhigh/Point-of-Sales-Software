SELECT i.item_name, m.menuitem_name
FROM recipes r
JOIN inventory i ON r.item_id = i.item_id
JOIN menuitem m ON r.menuitem_id = m.menuitem_id
WHERE m.category = 'Entree'
ORDER BY m.menuitem_name, i.item_name;
