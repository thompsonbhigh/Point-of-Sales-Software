DROP TABLE IF EXISTS Employees;

CREATE TABLE Employees(
    Employee_ID int PRIMARY KEY,
    Employee_Name text,
    Employement_Status BOOLEAN,
    Is_Manager BOOLEAN,
    Hours_Worked float,
    Salary float
);

INSERT INTO Employees (Employee_ID, Employee_Name, Employement_Status, Is_Manager, Hours_Worked, Salary)

VALUES

(72, 'Onson Sweeny', TRUE, TRUE, 72, 17.05),
(21, 'Rey Mcsriff', TRUE, FALSE, 63, 14.00),
(32, 'Dean Wesrey', TRUE, FALSE, 80, 14.00),
(64, 'Mike Truk', TRUE, FALSE, 79, 14.00),
(89, 'Todd Bonzalez', TRUE, FALSE, 74, 14.00),
(12, 'Kevin Nogily', TRUE, FALSE, 69, 14.00),
(44, 'Willie Dustice', TRUE, FALSE, 75, 14.00),
(29, 'Mario Mario', TRUE, FALSE, 65, 14.00),
(55, 'Dave Johnson', FALSE, FALSE, 43, 14.00);

SELECT * FROM Employee;