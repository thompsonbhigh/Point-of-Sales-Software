WITH maxOrder AS (
    SELECT order_id, total_cost
    FROM Orders
    ORDER BY total_cost DESC
    LIMIT 1
)
SELECT m.menuitem_name, oe.size, mo.total_cost
FROM OrderEntry oe
JOIN MenuItem m ON oe.menuitem_id = m.menuitem_id
JOIN maxOrder mo ON oe.order_id = mo.order_id;