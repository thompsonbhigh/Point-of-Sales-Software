--
SELECT m.menuitem_name,
    COUNT(*) AS num_orders
FROM orderentry o
JOIN menuitem m on o.menuitem_id = m.menuitem_id
GROUP BY m.menuitem_name
ORDER BY num_orders DESC;