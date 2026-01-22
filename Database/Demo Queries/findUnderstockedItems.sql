SELECT i.item_name, r.quantity_required, i.unit
FROM inventory i
JOIN recipes r ON r.item_id = i.item_id
GROUP BY i.item_name, i.quantity, i.unit, r.quantity_required
HAVING i.quantity > MAX(r.quantity_required)
ORDER BY i.item_name;
