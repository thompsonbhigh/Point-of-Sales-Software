--Comparison of the avg salary of an employee vs the avg salary of the manager
SELECT 
    AVG(CASE WHEN Is_Manager = FALSE THEN Salary END) AS avgEmploySal, -- if IsManager is false then its an employee and include the salary and then at the very end avg them 
    AVG(CASE WHEN Is_Manager = TRUE THEN Salary END) AS avgManSal -- if IsManager is true then its a manager and include the salary and then at the very end avg them 
FROM Employees;