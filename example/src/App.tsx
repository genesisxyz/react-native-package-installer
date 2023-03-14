import * as React from 'react';

import { StyleSheet, View, Button, Alert } from 'react-native';
import { install } from 'react-native-package-installer';
import { cacheDirectory, createDownloadResumable } from 'expo-file-system';

export default function App() {
  const [loading, setLoading] = React.useState(false);

  const onPress = () => {
    setLoading(true);
    downloadApk()
      .then(async (uri) => {
        if (uri) {
          const installed = await install(
            uri.replace('file:/', ''),
            'com.trianguloy.urlchecker'
          );
          if (installed) {
            Alert.alert('Package installed');
          }
        }
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <View style={styles.container}>
      <Button title="Install URLChecker" onPress={onPress} disabled={loading} />
    </View>
  );
}

const downloadApk = async () => {
  const downloadResumable = createDownloadResumable(
    'https://f-droid.org/repo/com.trianguloy.urlchecker_22.apk',
    cacheDirectory + 'package.apk'
  );

  try {
    const download = await downloadResumable.downloadAsync();
    return download?.uri || null;
  } catch (e) {
    console.error(e);
  }
  return null;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
