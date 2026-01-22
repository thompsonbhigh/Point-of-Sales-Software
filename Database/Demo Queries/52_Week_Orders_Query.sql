SELECT
    'Week ' || EXTRACT(WEEK FROM time_stamp) ||
    ' of ' || EXTRACT(ISOYEAR FROM time_stamp) ||
    ' has ' || COUNT(order_id) || ' orders' AS WeeklySalesSummary
FROM Orders
WHERE time_stamp >= (SELECT MAX(time_stamp) - INTERVAL '52 weeks' FROM Orders)
GROUP BY EXTRACT(ISOYEAR FROM time_stamp), EXTRACT(WEEK FROM time_stamp)
ORDER BY EXTRACT(ISOYEAR FROM time_stamp), EXTRACT(WEEK FROM time_stamp);
