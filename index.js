import { NativeModules, NativeEventEmitter, Platform } from 'react-native';
import MessageQueue from 'react-native/Libraries/BatchedBridge/MessageQueue';
const { RNPushdy } = NativeModules;

 const eventEmitter = new NativeEventEmitter(RNPushdy);

export function addEventListener(event, listener) {
  console.warn('listener')
  return eventEmitter.addListener(event, listener);
}

export function removeEventListener() {
  console.warn(2222)

  eventEmitter.remove();
}

export default RNPushdy
// export default new RNPushdyWrapper();
