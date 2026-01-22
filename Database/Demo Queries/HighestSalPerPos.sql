--shows the highest salary for every position 
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
