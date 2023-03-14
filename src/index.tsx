import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-package-installer' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const PackageInstaller = NativeModules.PackageInstaller
  ? NativeModules.PackageInstaller
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function install(
  path: string,
  packageName: string | null = null
): Promise<boolean> {
  return PackageInstaller.install(path, packageName);
}
