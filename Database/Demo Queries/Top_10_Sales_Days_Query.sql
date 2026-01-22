SELECT
    TO_CHAR(time_stamp, 'FMDay, FMMonth DD, YYYY') AS sale_date,
    SUM(total_cost) AS daily_revenue
FROM Orders
GROUP BY TO_CHAR(time_stamp, 'FMDay, FMMonth DD, YYYY')
ORDER BY daily_revenue DESC
LIMIT 10;
