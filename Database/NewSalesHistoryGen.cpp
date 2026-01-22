#include <iostream>
#include <fstream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <iomanip>
using namespace std;

// List of exact peak dates
vector<string> peakDates = {
    "2024-11-30",
    "2025-08-25" 
};

string randomTimestamp(const vector<string>& peakDates) {
    time_t now = time(0);

    // Decide if this order should fall on a peak day (e.g. 10% chance)
    bool usePeak = (rand() % 100 < 10);

    char buffer[20];

    if (usePeak) {
        // Pick one of the exact peak dates
        string chosenDate = peakDates[rand() % peakDates.size()];

        // Random time of day (10:00–21:59)
        int hour = 10 + rand() % 12;
        int minute = rand() % 60;
        int second = rand() % 60;

        // Build final timestamp: "YYYY-MM-DD HH:MM:SS"
        snprintf(buffer, sizeof(buffer), "%s %02d:%02d:%02d",
                 chosenDate.c_str(), hour, minute, second);

        return string(buffer);
    } else {
        // Normal random day in the past year
        int randomDays = rand() % 365;
        time_t randomTime = now - (randomDays * 24 * 3600);
        tm* t = localtime(&randomTime);
        int randomHour = 10 + rand() % 12;
        int randomMinute = rand() % 60;
        int randomSecond = rand() % 60;
        t->tm_hour = randomHour;
        t->tm_min = randomMinute;
        t->tm_sec = randomSecond;
        strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", t);
        return string(buffer);
    }
}


int main() {
    srand(time(0));
    ofstream sqlFile("orderHistory.sql");
    if (!sqlFile.is_open()) {
        cerr << "Error opening output file\n";
        return 1;
    }

    // Example employee IDs
    vector<int> employees = {72, 21, 32, 64, 89, 12, 44, 29, 55};

    // Example MenuItem_IDs (these must match your DB inserts!)
    vector<int> entrees = {7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};     // Regular entrées
    vector<int> premiumEntrees = {5, 6};                 // Premium entrées
    vector<int> sides = {1, 2, 3, 4};                    // Sides
    vector<int> appetizers = {18, 19, 20};               // Appetizers
    vector<int> drinks = {21, 22, 23, 24, 25};           // Drinks

    // Sizes
    vector<string> mealSizes = {"Bowl", "Plate", "Bigger Plate"};
    vector<string> alaCarteSizes = {"Small", "Medium", "Large"};

    int numOrders = 50;
    int orderId = 1;

    for (int i = 0; i < numOrders; i++) {
        int employeeId = employees[rand() % employees.size()];
        vector<string> peakDates = {"2024-11-30", "2025-08-25"};
        string timestamp = randomTimestamp(peakDates);


        sqlFile << "INSERT INTO Orders (order_id, employee_id, time_stamp) VALUES ("
                << orderId << ", " << employeeId << ", '" << timestamp << "');\n";

        // Randomly pick order type
        int orderType = rand() % 5; // 0=meal, 1=ala carte, 2=kids, 3=family, 4=drink/app

        if (orderType == 0) {
            // ---- Meal ----
            string size = mealSizes[rand() % mealSizes.size()];
            int entreeCount = (size == "Bowl") ? 1 : (size == "Plate") ? 2 : 3;

            for (int j = 0; j < entreeCount; j++) {
                int menuId = (rand() % 10 < 2)
                             ? premiumEntrees[rand() % premiumEntrees.size()]
                             : entrees[rand() % entrees.size()];
                sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                        << orderId << ", " << menuId << ", '" << size << "');\n";
            }

            // Always one side
            int sideId = sides[rand() % sides.size()];
            sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                    << orderId << ", " << sideId << ", 'Side');\n";

        } else if (orderType == 1) {
            // ---- A La Carte ----
            bool premium = (rand() % 10 < 3);
            int menuId = premium ? premiumEntrees[rand() % premiumEntrees.size()]
                                 : entrees[rand() % entrees.size()];
            string size = alaCarteSizes[rand() % alaCarteSizes.size()];
            sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                    << orderId << ", " << menuId << ", '" << size << "');\n";

        } else if (orderType == 2) {
            // ---- Kids Meal ----
            int menuId = (rand() % 10 < 2)
                         ? premiumEntrees[rand() % premiumEntrees.size()]
                         : entrees[rand() % entrees.size()];
            sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                    << orderId << ", " << menuId << ", 'Kids');\n";

            int sideId = sides[rand() % sides.size()];
            sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                    << orderId << ", " << sideId << ", 'Side');\n";

        } else if (orderType == 3) {
            // ---- Family Meal ---- (3 entrées + 2 sides)
            for (int j = 0; j < 3; j++) {
                int menuId = (rand() % 10 < 3)
                             ? premiumEntrees[rand() % premiumEntrees.size()]
                             : entrees[rand() % entrees.size()];
                sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                        << orderId << ", " << menuId << ", 'Family');\n";
            }

            for (int j = 0; j < 2; j++) {
                int sideId = sides[rand() % sides.size()];
                sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                        << orderId << ", " << sideId << ", 'Side');\n";
            }

        } else {
            // ---- Drink or Appetizer ----
            if (rand() % 2 == 0) {
                int drinkId = drinks[rand() % drinks.size()];
                sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                        << orderId << ", " << drinkId << ", 'Drink');\n";
            } else {
                int appId = appetizers[rand() % appetizers.size()];
                sqlFile << "INSERT INTO OrderEntry (order_id, MenuItem_ID, Size) VALUES ("
                        << orderId << ", " << appId << ", 'Appetizer');\n";
            }
        }

        sqlFile << "\n";
        orderId++;
    }

    sqlFile.close();
    cout << "Generated orderHistory.sql with " << numOrders << " orders.\n";
    return 0;
}
