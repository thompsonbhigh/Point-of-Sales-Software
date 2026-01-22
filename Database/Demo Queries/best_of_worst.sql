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
SELECT MenuItem.MenuItem_Name
FROM (
    SELECT 
        mode() WITHIN GROUP (ORDER BY OrderEntry.MenuItem_ID) AS popular_id
    FROM OrderEntry
    JOIN Orders ON OrderEntry.order_id = Orders.order_id
    JOIN WorstSalesDay ON CAST(Orders.time_stamp AS DATE) = WorstSalesDay.sale_day
) t
JOIN MenuItem ON t.popular_id = MenuItem.MenuItem_ID;