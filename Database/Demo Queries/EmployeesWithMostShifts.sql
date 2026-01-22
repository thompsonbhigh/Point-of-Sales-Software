--Knowing which Employees have the most shifts
SELECT 
    e.Employee_Name, 
    COUNT(s.ScheduleDate) AS numShifts 
FROM 
    Schedule s
JOIN 
    Employees e ON s.EmployeeID = e.Employee_ID
GROUP BY 
    e.Employee_Name
ORDER BY 
    numShifts DESC;
