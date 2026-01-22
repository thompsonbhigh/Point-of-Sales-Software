--1. 52 weeks
SELECT
    'Week ' || EXTRACT(WEEK FROM time_Stamp) ||
    ' of ' || EXTRACT(ISOYEAR FROM time_Stamp) ||
    ' has ' || COUNT(Order_ID) || ' orders' AS WeeklySalesSummary
FROM Orders
WHERE time_stamp >= (SELECT MAX(time_stamp) - INTERVAL '52 weeks' FROM Orders)
GROUP BY EXTRACT(ISOYEAR FROM time_stamp), EXTRACT(WEEK FROM time_stamp)
ORDER BY EXTRACT(ISOYEAR FROM time_stamp), EXTRACT(WEEK FROM time_stamp);

--2. Hourly Sales History
SELECT
    TO_CHAR(time_stamp, 'HH24') || ':00 - ' || TO_CHAR(Orders.time_stamp, 'HH24') || ':59' AS hour_range,
    COUNT(Order_ID) AS number_of_orders,
    SUM(total_cost) AS total_sales
FROM Orders 
GROUP BY TO_CHAR(Orders.time_stamp, 'HH24')
ORDER BY TO_CHAR(Orders.time_stamp, 'HH24');

--3. Peak days
SELECT
    TO_CHAR(time_stamp, 'FMDay, FMMonth DD, YYYY') AS sale_date,
    SUM(total_cost) AS daily_revenue
FROM Orders
GROUP BY TO_CHAR(time_stamp, 'FMDay, FMMonth DD, YYYY')
ORDER BY daily_revenue DESC
LIMIT 10;

--4. Ingredients for 20 menu items
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

--5. Best of the worst
WITH DailySales AS (
    SELECT
        CAST(time_stamp AS DATE) AS sale_day,
        SUM(total_cost) AS daily_revenue
    FROM Orders
    GROUP BY CAST(time_stamp AS DATE)
),
WorstSalesDay AS (
    SELECT
        sale_day
    FROM DailySales
    ORDER BY daily_revenue ASC
    LIMIT 1
)
SELECT mi.MenuItem_Name
FROM (
    SELECT 
        mode() WITHIN GROUP (ORDER BY oe.MenuItem_ID) AS popular_id
    FROM OrderEntry oe
    JOIN Orders o ON oe.order_id = o.order_id
    JOIN WorstSalesDay ON CAST(o.time_stamp AS DATE) = WorstSalesDay.sale_day
) t
JOIN MenuItem mi ON t.popular_id = mi.MenuItem_ID;

--6. Comparison of the avg salary of an employee vs the avg salary of the manager
SELECT 
    AVG(CASE WHEN Is_Manager = FALSE THEN Salary END) AS avgEmploySal, -- if IsManager is false then its an employee and include the salary and then at the very end avg them 
    AVG(CASE WHEN Is_Manager = TRUE THEN Salary END) AS avgManSal -- if IsManager is true then its a manager and include the salary and then at the very end avg them 
FROM Employees;

--7. Knowing which Employees have the most shifts
SELECT 
    e.Employee_Name, 
    COUNT(s.scheduledate) AS numShifts 
FROM 
    Schedule s
JOIN 
    Employees e ON s.EmployeeID = e.Employee_ID
GROUP BY 
    e.Employee_Name
ORDER BY 
    numShifts DESC;

--8. Understocked
SELECT i.item_name, r.quantity_required, i.unit
FROM inventory i
JOIN recipes r ON r.item_id = i.item_id
GROUP BY i.item_name, i.quantity, i.unit, r.quantity_required
HAVING i.quantity > MAX(r.quantity_required)
ORDER BY i.item_name;

--9. Items with Soy Sauce
SELECT i.item_name, m.menuitem_id, r.quantity_required
FROM recipes r
JOIN inventory i ON r.item_id = i.item_id
JOIN menuitem m ON r.menuitem_id = m.menuitem_id
WHERE i.item_name = 'Soy Sauce';

--10. most frequent ingredient in the recipes 
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

--11. shows the highest salary for every position 
SELECT 
    s.Position, 
    e.Employee_Name, 
    MAX(e.Salary) AS topSalary
FROM 
    Schedule s
JOIN Employees e ON s.EmployeeID = e.Employee_ID
GROUP BY 
    s.Position, e.Employee_Name
ORDER BY 
    topSalary DESC;

--12. most sugar
SELECT m.MenuItem_Name, r.quantity_required AS sugar_content
    FROM Recipes r
    JOIN Inventory i ON r.Item_ID=i.Item_ID
    JOIN MenuItem m ON r.MenuItem_ID = m.MenuItem_ID
    WHERE i.Item_Name='Sugar'
    ORDER BY r.Quantity_Required DESC
LIMIT 1;

--13. Most Ordered Item
SELECT m.menuitem_name,
    COUNT(*) AS num_orders
FROM orderentry o
JOIN menuitem m on o.menuitem_id = m.menuitem_id
GROUP BY m.menuitem_name
ORDER BY num_orders DESC;

--14. Most Expensive order
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

--15. Rank Employees by most sales (by cost)
SELECT e.Employee_Name, SUM(total_cost) AS total_sales
    FROM Orders o
    JOIN Employees e ON o.Employee_ID = e.Employee_ID
    GROUP BY e.Employee_ID, e.Employee_Name
ORDER BY total_sales DESC