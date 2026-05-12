# EcoTrackPort - Environmental Monitoring Mobile App

**Version:** 1.0.0  
**Platform:** Android (Java)  
**Target:** Sousse Port Environmental Monitoring

## 📋 Project Overview

EcoTrackPort is a comprehensive environmental monitoring application designed for tracking and reporting pollution in the Sousse port area. The app leverages AI analysis to identify pollution types, assess severity, and provide recommended solutions.

## ✨ Key Features

- **Dashboard** - Real-time environmental statistics and alerts
- **Interactive Map** - GPS-based location tracking and pollution visualization
- **Report Pollution** - Easy-to-use form with photo upload and AI analysis
- **History Tracking** - Complete record of all pollution reports with filtering
- **AI Analysis** - Automated pollution type detection and solution recommendations
- **Location Services** - Background GPS tracking and zone-based filtering
- **User Profiles** - Multi-user support with professional roles
- **Settings** - Customizable notifications, refresh intervals, and themes

## 🏗️ Architecture

### Database Layer
- **Room Database** - Local SQLite database for offline access
- **Entities**: Pollution, UserProfile
- **DAOs**: PollutionDao, UserProfileDao

### Repository Pattern
- **PollutionRepository** - Manages pollution data operations
- **UserProfileRepository** - Manages user profile operations

### ViewModels
- **PollutionViewModel** - Handles pollution-related UI logic
- **UserProfileViewModel** - Handles user profile UI logic

### Services
- **LocationTrackingService** - Background location updates
- **AIAnalysisSimulator** - Simulates AI pollution analysis

## 📦 Project Structure

```
app/src/main/java/com/ecotrackport/
├── activities/
│   ├── SplashActivity.java
│   ├── LoginActivity.java
│   ├── MainActivity.java
│   ├── ReportPollutionActivity.java
│   ├── MapActivity.java
│   ├── HistoryActivity.java
│   └── SettingsActivity.java
├── database/
│   ├── AppDatabase.java
│   ├── PollutionDao.java
│   ├── UserProfileDao.java
│   └── DateConverter.java
├── models/
│   ├── Pollution.java
│   ├── UserProfile.java
│   └── AIAnalysisResult.java
├── repository/
│   ├── PollutionRepository.java
│   └── UserProfileRepository.java
├── viewmodels/
│   ├── PollutionViewModel.java
│   └── UserProfileViewModel.java
├── services/
│   └── LocationTrackingService.java
├── adapters/
│   ├── PollutionAdapter.java
│   └── PollutionHistoryAdapter.java
├── utils/
│   ├── AIAnalysisSimulator.java
│   ├── LocationUtils.java
│   ├── NotificationManager.java
│   └── SharedPreferencesHelper.java
├── receivers/
│   └── NotificationReceiver.java
and resources files (layouts, strings, colors, etc.)
```

## 🔧 Dependencies

- AndroidX AppCompat 1.6.1
- Google Material Design 3
- Room Database 2.5.2
- Google Play Services (Maps & Location) 18.1.0 / 21.0.1
- Retrofit 2 & OkHttp 3
- Glide 4.15.1
- Gson 2.10.1

## 🚀 Getting Started

### Prerequisites
- Android Studio 2022.3+
- JDK 11+
- Android SDK 24+ (API level 24)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/ou125ssa/ecotrackport.git
   cd ecotrackport
   ```

2. **Configure Google Maps API**
   - Get API key from [Google Cloud Console](https://console.cloud.google.com/)
   - Add to `AndroidManifest.xml`:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_API_KEY" />
   ```

3. **Build & Run**
   ```bash
   ./gradlew build
   ./gradlew installDebug
   ```

## 📱 Usage

### Report Pollution
1. Navigate to "Signaler Pollution"
2. Select pollution type and severity
3. Add photo (recommended)
4. Submit - AI analysis will be performed automatically

### View on Map
- All reported pollutions are displayed on the interactive map
- Color-coded markers indicate severity (Red=Critical, Yellow=Medium, Green=Low)

### Check History
- Filter by type, severity, or date
- View detailed information and solutions

## 🎨 UI/UX Features

- Material Design 3 components
- Gradient backgrounds and smooth animations
- Responsive layouts for all screen sizes
- Dark theme support
- Accessibility-friendly design

## 🔐 Permissions

- `ACCESS_FINE_LOCATION` - GPS location tracking
- `CAMERA` - Photo capture for pollution reports
- `READ_EXTERNAL_STORAGE` - Media access
- `INTERNET` - API communication
- `POST_NOTIFICATIONS` - Push notifications

## 📊 Database Schema

### Pollution Table
```sql
CREATE TABLE pollutions (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    type TEXT,
    gravity TEXT,
    description TEXT,
    latitude REAL,
    longitude REAL,
    zone TEXT,
    date TEXT,
    imagePath TEXT,
    status TEXT,
    reporterProfession TEXT,
    reporterZone TEXT,
    aiConfidence TEXT,
    aiEvolutionType TEXT,
    aiInterpretation TEXT,
    recommendedSolution TEXT,
    estimatedCost INTEGER,
    timeToResolve INTEGER,
    priority TEXT
);
```

### UserProfile Table
```sql
CREATE TABLE user_profiles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    professionName TEXT,
    zone TEXT,
    email TEXT,
    phone TEXT,
    currentLatitude REAL,
    currentLongitude REAL,
    lastLocationUpdate TEXT,
    notificationsEnabled INTEGER,
    refreshInterval INTEGER,
    theme TEXT,
    profileCreatedAt TEXT,
    lastLoginAt TEXT
);
```

## 🐛 Known Issues & TODO

- [ ] Complete UI Activities implementation
- [ ] Implement real API integration
- [ ] Add camera and gallery image picker
- [ ] Implement push notifications
- [ ] Add offline mode support
- [ ] Implement user authentication
- [ ] Add data synchronization

## 👥 Contributors

- **Developer**: ou125ssa
- **Designer**: hadil jamaoui

## 📄 License

This project is private and confidential.

## 📞 Support

For issues and questions, please create a GitHub issue.

---

**Last Updated**: May 12, 2026
