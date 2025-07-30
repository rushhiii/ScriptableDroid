# 🐍 ScriptableDroid

> **ScriptableDroid** is an open-source Android app that brings Scriptable-like JavaScript automation to Android.
> Users can write and run scripts directly on their device, access system features like battery info and notifications, and eventually create home screen widgets powered by custom code.
>
> 🔧 Built with:
>
> * Kotlin
> * [Duktape](https://github.com/square/duktape-android) JS engine
> * Android App Widgets (coming soon)
>
> 🌟 Ideal for:
>
> * Developers who want to automate Android
> * Power users looking for customizable widget logic
> * Anyone who wishes Scriptable existed on Android

## Features

- **JavaScript Script Editor**: Write and edit JavaScript code with syntax highlighting
- **Script Execution**: Run JavaScript scripts with access to Android APIs
- **Battery Information**: Access battery level and charging state through JavaScript
- **Script Management**: Save, edit, and organize your scripts
- **System Integration**: Built-in APIs for device information and notifications

## Getting Started

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or later
- Android SDK API level 21 or higher
- Java 8 or higher

### Building the Project

1. Clone the repository:
```bash
git clone https://github.com/rushhiii/ScriptableDroid.git
cd ScriptableDroid
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Build and run the app on your device or emulator

### Project Structure

```
ScriptableDroid/
├── app/
│   ├── src/main/
│   │   ├── java/com/scriptabledroid/app/
│   │   │   ├── MainActivity.kt           # Main activity with script list
│   │   │   ├── ScriptEditorActivity.kt   # Script editor with code highlighting
│   │   │   ├── ScriptEngine.kt           # JavaScript execution engine
│   │   │   ├── ScriptStorage.kt          # Script persistence
│   │   │   ├── ScriptAdapter.kt          # RecyclerView adapter for scripts
│   │   │   └── Script.kt                 # Script data model
│   │   ├── res/                          # Android resources
│   │   └── AndroidManifest.xml
│   └── build.gradle                      # App-level Gradle config
├── build.gradle                          # Project-level Gradle config
└── settings.gradle                       # Gradle settings
```

## JavaScript API

ScriptableDroid provides several built-in JavaScript APIs:

### Device API
```javascript
// Get battery level (0-100)
const batteryLevel = Device.batteryLevel();

// Get battery state ("charging", "discharging", "full", "not_charging", "unknown")
const batteryState = Device.batteryState();
```

### Console API
```javascript
// Log messages
console.log("Hello from ScriptableDroid!");
```

### Notification API (Coming Soon)
```javascript
// Create notifications
Notification.create("Title", "Message body");
```

## Example Scripts

### Battery Monitor
```javascript
// Get battery information
const batteryLevel = Device.batteryLevel();
const batteryState = Device.batteryState();

console.log("Battery Level: " + batteryLevel + "%");
console.log("Battery State: " + batteryState);

"Battery: " + batteryLevel + "% (" + batteryState + ")";
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Roadmap

- [x] Basic JavaScript execution environment
- [x] Script editor with syntax highlighting
- [x] Device battery information API
- [ ] File system access
- [ ] Network requests support
- [ ] Home screen widgets
- [ ] More system APIs (contacts, calendar, etc.)
- [ ] Script sharing and import/export

