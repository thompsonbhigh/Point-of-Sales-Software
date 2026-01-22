#include <iostream>
#include <fstream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <iomanip>
using namespace std;

std::string randomTimestamp() {
    time_t now = time(0);
    int randomDays = rand() % 365;  
    int randomHour = 10 + rand() % 12;  // 10–21 inclusive
    int randomMinute = rand() % 60;
    int randomSecond = rand() % 60;

    time_t randomTime = now - (randomDays * 24 * 3600);
    tm* t = localtime(&randomTime);
    t->tm_hour = randomHour;
    t->tm_min = randomMinute;
    t->tm_sec = randomSecond;

    char buffer[20];
    strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S", t);
    return std::string(buffer);
}

int main() {
    srand(time(0));

    std::ofstream sqlFile("orderHistory.sql");
    if (!sqlFile.is_open()) {
        std::cerr << "Error opening output file\n";
        return 1;
    }

    std::vector<int> employees = {72, 21, 32, 64, 89, 12, 44, 29, 55};

    struct Entree { std::string name; bool premium; };
    std::vector<Entree> mains = {
        {"Hot Orange Chicken", false},
        {"Honey Walnut Shrimp", true}, // premium
        {"Black Pepper Sirloin Steak", true}, // premium
        {"Mushroom Chicken", false},
        {"Kung Pao Chicken", false},
        {"String Bean Chicken Breast", false},
        {"Orange Chicken", false},
        {"Honey Sesame Chicken Breast", false},
        {"Grilled Teriyaki Chicken", false},
        {"Broccoli Beef", false},
        {"Beijing Beef", false},
        {"Black Pepper Chicken", false},
        {"Super Greens", false},
    };

    std::vector<string> sides = {"Chow Mein", "Fried Rice", "White Steamed Rice", "Super Greens"};

    struct MealType { std::string name; int entrees; double basePrice; };
    std::vector<MealType> meals = {
        {"Bowl", 1, 8.50},
        {"Plate", 2, 10.10},
        {"Bigger Plate", 3, 11.70},
        {"Panda Cub Meal", 1, 6.80},
        {"Family", 3, 44.00},
        {"Appetizers", 0, 2.10},
        {"Small", 1, 5.40},
        {"Medium", 1, 8.70},
        {"Large", 1, 11.40},
        {"Drink", 0, 2.20}
    };

    std::vector<string> appetizers = {"Veggie Spring Roll", "Chicken Egg Roll", "Cream Cheese Rangoon"};

    std::vector<string> drinks = {"Water", "Soda"};

    int numOrders = 500 * 365;
    int orderId = 1;

    for (int i = 0; i < numOrders; i++) {
        int employeeId = employees[rand() % employees.size()];
        string timestamp = randomTimestamp();

        MealType meal = meals[rand() % meals.size()];
        double total = meal.basePrice;

        sqlFile << "INSERT INTO Orders (order_id, employee_id, total_cost, time_stamp) VALUES ("
                << orderId << ", " << employeeId << ", 0, '" << timestamp << "');\n";

        if (meal.entrees > 0) {
            for (int j = 0; j < meal.entrees; j++) {
                Entree entree = mains[rand() % mains.size()];
                double price = 0.0;  // included in base, only surcharge if premium

                if (entree.premium) {
                    price += 1.50;
                    total += 1.50;
                }

                sqlFile << "INSERT INTO OrderEntry (order_id, item, size, price) VALUES ("
                        << orderId << ", '" << entree.name << "', '" << meal.name << "', " 
                        << std::fixed << std::setprecision(2) << price << ");\n";
            }

            int sideLoop = 1;
            if (meal.basePrice == 44.00) {
                sideLoop = 2;
            } else if (meal.basePrice == 5.40 || meal.basePrice == 8.70 || meal.basePrice == 11.40) {
                sideLoop = 0;
            }

            for (int i = 0; i < sideLoop; i++) {
                std::string side = sides[rand() % sides.size()];
                sqlFile << "INSERT INTO OrderEntry (order_id, item, size, price) VALUES ("
                        << orderId << ", '" << side << "', 'Side', 0.00);\n";
            }
        } else if (meal.basePrice == 2.20) {
            std::string drink = drinks[rand() % drinks.size()];
            sqlFile << "INSERT INTO OrderEntry (order_id, item, size, price) VALUES ("
                    << orderId << ", '" << drink << "', 'Drink', " 
                    << std::fixed << std::setprecision(2) << meal.basePrice << ");\n";
        } else {
            std::string appetizer = appetizers[rand() % appetizers.size()];
            sqlFile << "INSERT INTO OrderEntry (order_id, item, size, price) VALUES ("
                    << orderId << ", '" << appetizer << "', 'Appetizer', " 
                    << std::fixed << std::setprecision(2) << meal.basePrice << ");\n";
        }

            sqlFile << "UPDATE Orders SET total_cost = " << std::fixed << std::setprecision(2) << total
                    << " WHERE order_id = " << orderId << ";\n\n";

        orderId++;
    }
    sqlFile.close();
    return 0;
}