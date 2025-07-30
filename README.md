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

2. Set up Android SDK path:
   - If using Android Studio: Open the project and let Android Studio configure it automatically
   - If building from command line: Copy `local.properties.example` to `local.properties` and set your Android SDK path

3. Build the project:
```bash
# Using Gradle Wrapper (recommended)
./gradlew build

# Or on Windows
gradlew.bat build
```

4. Install on device/emulator:
```bash
./gradlew installDebug
```

**Note**: The Gradle Wrapper is included in the project, so you don't need to install Gradle separately. The wrapper will automatically download the correct version of Gradle.

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

We welcome contributions! Here's how to get started:

### Development Setup

1. Fork the repository
2. Clone your fork: `git clone https://github.com/yourusername/ScriptableDroid.git`
3. Open in Android Studio
4. Create a feature branch: `git checkout -b feature/amazing-feature`
5. Make your changes
6. Test thoroughly
7. Commit your changes: `git commit -m 'Add amazing feature'`
8. Push to your branch: `git push origin feature/amazing-feature`
9. Open a Pull Request

### Code Style

- Follow Kotlin coding conventions
- Use meaningful variable and function names  
- Add comments for complex logic
- Maintain consistent formatting (use Android Studio's auto-formatter)

### Adding New JavaScript APIs

To add new JavaScript APIs:

1. Modify `ScriptEngine.kt` to add your API methods
2. Test the API with example scripts
3. Update the documentation in README.md
4. Add example scripts demonstrating the new API

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

