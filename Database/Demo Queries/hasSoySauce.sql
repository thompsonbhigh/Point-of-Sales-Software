SELECT i.item_name, m.menuitem_id, r.quantity_required
FROM recipes r
JOIN inventory i ON r.item_id = i.item_id
JOIN menuitem m ON r.menuitem_id = m.menuitem_id
WHERE i.item_name = 'Soy Sauce';
