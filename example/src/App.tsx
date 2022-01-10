import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { verifySignature } from 'react-native-vc';
import samplejwt from './samplejwt';

export default function App() {
  const [result, setResult] = React.useState<Boolean>(false);

  React.useEffect(() => {
    setResult(verifySignature(samplejwt.valid));
  }, []);

  return (
    <View style={styles.container}>
      <Text>Result: {result.toString()}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
