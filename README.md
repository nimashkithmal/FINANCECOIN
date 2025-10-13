# FinanceCoin 💰

**Your Personal Finance Management Companion**

![FinanceCoin Screenshot](Screenshot%202025-10-14%20at%2003.37.20.png)

## 📱 Overview

FinanceCoin is a comprehensive personal finance management Android application built with Kotlin. It helps you track your income, manage expenses, set budgets, and gain valuable insights into your spending patterns through intuitive charts and analytics.

## ✨ Features

### 🔐 User Authentication
- Secure login and registration system
- User profile management
- Session management

### 💸 Transaction Management
- Add income and expense transactions
- Edit and delete existing transactions
- Categorize transactions for better organization
- Add detailed descriptions to transactions
- View transaction history with sorting

### 📊 Budget Tracking
- Set monthly budget limits
- Track budget usage in real-time
- Visual budget progress indicators
- Budget remaining calculations

### 📈 Financial Analysis
- Interactive pie charts showing expense categories
- Bar charts for spending trends
- Monthly spending summaries
- Income vs expense comparisons
- Savings calculations
- Smart insights and recommendations

### 🎨 Modern UI/UX
- Material Design 3 components
- Intuitive bottom navigation
- Responsive layouts
- Dark/Light theme support
- Smooth animations and transitions

## 🛠️ Technical Stack

- **Language**: Kotlin
- **Platform**: Android (API 21+)
- **Architecture**: MVVM Pattern
- **UI Framework**: Android Views with Material Design
- **Data Storage**: File-based storage with SharedPreferences
- **Charts**: MPAndroidChart library
- **Navigation**: Bottom Navigation with Fragments

## 📱 Screenshots

The app features a clean, modern interface with:
- Dashboard with budget overview
- Transaction list with quick actions
- Analysis charts and insights
- Budget management tools
- Settings and preferences

![App Screenshot](Screenshot%202025-10-14%20at%2003.37.20.png)

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 21 or higher
- Kotlin 1.8+

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd FinanceCoin
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on your device or emulator

## 📂 Project Structure

```
app/
├── src/main/
│   ├── java/com/example/coinomy/
│   │   ├── MainActivity.kt           # Main activity with navigation
│   │   ├── login.kt                  # Login activity
│   │   ├── RegisterActivity.kt       # Registration activity
│   │   ├── HomeFragment.kt           # Dashboard fragment
│   │   ├── TransactionsFragment.kt   # Transaction list fragment
│   │   ├── AnalysisFragment.kt       # Analytics fragment
│   │   ├── BudgetFragment.kt         # Budget management fragment
│   │   ├── SettingsFragment.kt       # Settings fragment
│   │   ├── TransactionData.kt        # Data model
│   │   ├── TransactionRepository.kt  # Data repository
│   │   └── utils/                    # Utility classes
│   ├── res/
│   │   ├── layout/                   # UI layouts
│   │   ├── drawable/                 # Icons and images
│   │   ├── values/                   # Strings, colors, themes
│   │   └── navigation/               # Navigation graphs
│   └── AndroidManifest.xml
```

## 🔧 Key Components

### Data Models
- `TransactionData`: Represents financial transactions
- `TransactionType`: Enum for INCOME/EXPENSE types

### Repository Pattern
- `TransactionRepository`: Handles all data operations
- `FileStorageManager`: Manages file-based storage
- `PreferenceManager`: Handles user preferences

### UI Components
- Fragment-based architecture
- Material Design components
- Custom adapters for RecyclerViews
- Dialog fragments for data entry

## 📊 Features in Detail

### Dashboard
- Personalized greeting based on time of day
- Budget summary with progress indicators
- Recent transactions preview
- Quick action buttons for adding transactions

### Transaction Management
- Add new income/expense transactions
- Edit existing transactions
- Delete transactions with confirmation
- Filter and search capabilities
- Category-based organization

### Analytics & Insights
- Monthly spending breakdown
- Category-wise expense analysis
- Income vs expense comparison
- Budget utilization tracking
- Visual charts for better understanding

### Budget Management
- Set monthly budget limits
- Track spending against budget
- Visual progress indicators
- Budget alerts and notifications

## 🎯 Usage

1. **First Launch**: Register a new account or login
2. **Set Budget**: Configure your monthly budget in settings
3. **Add Transactions**: Record your income and expenses
4. **Monitor Progress**: Check dashboard for budget status
5. **Analyze Spending**: Use analytics to understand patterns
6. **Manage Budget**: Adjust limits and track progress

## 🔮 Future Enhancements

- [ ] Export data to CSV/PDF
- [ ] Recurring transaction support
- [ ] Multiple currency support
- [ ] Goal setting and tracking
- [ ] Bill reminders
- [ ] Cloud backup and sync
- [ ] Widget support
- [ ] Voice input for transactions

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is developed as part of academic coursework (MAD - Mobile Application Development).

## 👨‍💻 Developer

**IT23247086** - Made with ❤️ using Kotlin

---

*FinanceCoin v1.0.0 - Your journey to better financial management starts here!*