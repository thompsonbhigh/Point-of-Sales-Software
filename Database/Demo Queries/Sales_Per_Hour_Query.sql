SELECT
    TO_CHAR(time_stamp, 'HH24') || ':00 - ' || TO_CHAR(Orders.time_stamp, 'HH24') || ':59' AS hour_range,
    COUNT(Order_ID) AS number_of_orders,
    SUM(total_cost) AS total_sales
FROM Orders 
GROUP BY TO_CHAR(Orders.time_stamp, 'HH24')
ORDER BY TO_CHAR(Orders.time_stamp, 'HH24');
