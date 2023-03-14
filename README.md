# react-native-package-installer

Package installer

## Installation

```sh
npm install react-native-package-installer
```

## Usage

### Android

To install silently the app, you need admin priviledges

To enable admin you need to run:

```bash
adb shell dpm set-device-owner com.packageinstallerexample/com.packageinstaller.MyDeviceAdminReceiver
```

To remove admin instead do:

```bash
adb shell dpm remove-active-admin com.packageinstallerexample/com.packageinstaller.MyDeviceAdminReceiver
```

Change `com.packageinstallerexample` to your bundle id

You can also make your own admin receiver, you can check the code for Android for this package as reference

The APK you want to install needs to be in the cache folder, check the example project for an implementation using the Expo SDK

```js
import { install } from 'react-native-package-installer';

// ...

await install('/path/to/apk');
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
