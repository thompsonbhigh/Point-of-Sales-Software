CREATE TABLE Schedule (
    ScheduleDate DATE NOT NULL,
    Shift VARCHAR(50) NOT NULL,
    Position VARCHAR(100),
    EmployeeID INT NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(Employee_ID)
);