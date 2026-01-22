SELECT e.Employee_Name, SUM(total_cost) AS total_sales
    FROM Orders o
    JOIN Employees e ON o.Employee_ID = e.Employee_ID
    GROUP BY e.Employee_ID, e.Employee_Name
ORDER BY total_sales DESC;
