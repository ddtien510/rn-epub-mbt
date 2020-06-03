package com.reactNativeEpub;

import android.util.Log;
import android.view.View;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableNativeArray;

import com.folioreader.FolioReader;
import com.folioreader.Config;
import com.folioreader.util.AppUtil;
import com.folioreader.model.locators.ReadLocator;
import com.folioreader.util.ReadLocatorListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Expose api to Android
 *
 * NOTE: There must be no different between iOS & android api
 */
public class PushdyModule extends ReactContextBaseJavaModule implements LifecycleEventListener, ReadLocatorListener {

    private final ReactApplicationContext reactContext;
    // private final PushdySdk pushdySdk;
    private FolioReader folioReader;

    public PushdyModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addLifecycleEventListener(this);
        // this.pushdySdk = PushdySdk.getInstance().setReactContext(reactContext);
    }

    private void sendEvent(String eventName, String position) {
        Log.d("eventName", eventName);

        reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, position);
    }

    @ReactMethod
    public void show(String path, String id, String savedPosition) {
        
        // Config config = AppUtil.getSavedConfig(reactContext);
        //                 if (config == null)
        //                 config = new Config();

        FolioReader folioReader = FolioReader.get();
        // .setConfig(config, true);
        Log.d("Pushdy", path);
        Config config = AppUtil.getSavedConfig(reactContext);
                if (config == null)
                    config = new Config();
                config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);

                
        
        folioReader.setConfig(config, true).setReadLocatorListener(this);

        folioReader.openBook(path);
    }

    @Override
    public void saveReadLocator(ReadLocator readLocator) {
        Log.d("info:::---here---", "Asdasd");
        // Log.d("readLocator", readLocator);

        sendEvent("readposition", readLocator.toJson());
    }

    @Override
    public void onHostDestroy() {
        FolioReader.clear();
    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

     }

    @Override
    public String getName() {
        return "RNPushdy";
    }

}
