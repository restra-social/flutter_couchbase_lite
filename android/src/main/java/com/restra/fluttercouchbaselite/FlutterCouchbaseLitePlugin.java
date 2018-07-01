package com.restra.fluttercouchbaselite;

import android.content.Context;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterCouchbaseLitePlugin */
public class FlutterCouchbaseLitePlugin implements MethodCallHandler {
  /** Plugin registration. */

  private static Registrar mRegistrar;

  public FlutterCouchbaseLitePlugin(Registrar mRegistrar) {
    this.mRegistrar = mRegistrar;
  }

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_couchbase_lite");
    channel.setMethodCallHandler(new FlutterCouchbaseLitePlugin(registrar));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      result.notImplemented();
    }
  }

  public static Context getActiveContext()  throws  Exception {
    return (mRegistrar.activity() != null) ? mRegistrar.activity() : mRegistrar.context();
  }
}
