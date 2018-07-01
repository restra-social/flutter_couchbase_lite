package com.restra.fluttercouchbaselite;

import android.content.Context;
import android.util.Log;

import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterCouchbaseLitePlugin
 */
public class FlutterCouchbaseLitePlugin implements MethodCallHandler {
    /**
     * Plugin registration.
     */

    private static Registrar mRegistrar;

    public FlutterCouchbaseLitePlugin(Registrar mRegistrar) {
        this.mRegistrar = mRegistrar;
    }


    // Couchbase Lite Instance
    CouchbaseDatabaseManager cbManager = CouchbaseDatabaseManager.getInstance();

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_couchbase_lite");
        channel.setMethodCallHandler(new FlutterCouchbaseLitePlugin(registrar));
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        switch (call.method) {
            case ("initDatabaseWithName"):
                final String _name = call.argument("name");
                Log.i("PARAM", _name);
                try {
                    cbManager.initDatabaseWithName(_name);
                } catch (Exception e){
                    //e.printStackTrace();
                    result.error("Plugin :: DB Init :: ", e.getMessage(), null);
                }
            case ("saveDocument"):
                Map<String, Object> _doc = call.arguments();
                try {
                    String _id = cbManager.saveDocument(_doc);
                    result.success(_id);
                }catch (Exception e ){
                    //e.printStackTrace();
                    result.error("Plugin :: Save Document :: ", e.getMessage(), null);
                }
            case ("startReplicator"):
                final String _url = call.argument("url");
                final String _type = call.argument("type");
                final Map<String, String> _credentials = call.argument("credentials");
                try {
                    // Set the Replicator URI
                    cbManager.setReplicatorEndpoint(_url);
                    // Set Replicator Type
                    cbManager.setReplicatorType(_type);
                    // Apply Basic Authentication If Available
                    if (_credentials.containsKey("username") && _credentials.containsKey("password")) {
                        cbManager.setReplicatorBasicAuthentication(_credentials);
                    }
                    // Start The Replicator
                    cbManager.startReplicator();
                } catch (Exception e){
                    e.printStackTrace();
                    result.error("Plugin :: Replicator Init ::", e.getMessage(), null);
                }
                break;
            case ("stopReplicator"):
                cbManager.stopReplicator();
                break;
            default:
                result.notImplemented();
        }
    }

    public static Context getActiveContext() throws Exception {
        return (mRegistrar.activity() != null) ? mRegistrar.activity() : mRegistrar.context();
    }
}
