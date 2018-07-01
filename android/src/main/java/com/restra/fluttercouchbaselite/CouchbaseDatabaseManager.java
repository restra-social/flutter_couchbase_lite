package com.restra.fluttercouchbaselite;

import com.couchbase.lite.BasicAuthenticator;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Endpoint;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Replicator;
import com.couchbase.lite.ReplicatorConfiguration;
import com.couchbase.lite.URLEndpoint;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class CouchbaseDatabaseManager {
    private static final CouchbaseDatabaseManager mInstance = new CouchbaseDatabaseManager();
    private Database mDatabase;
    private ReplicatorConfiguration mReplConfig;
    private Replicator mReplicator;

    private CouchbaseDatabaseManager() {
    }

    public static CouchbaseDatabaseManager getInstance() {
        return mInstance;
    }

    public String setReplicatorEndpoint(String _endpoint) throws URISyntaxException {
        Endpoint targetEndpoint = new URLEndpoint(new URI(_endpoint));
        mReplConfig = new ReplicatorConfiguration(mDatabase, targetEndpoint);
        return mReplConfig.getTarget().toString();
    }

    public String setReplicatorBasicAuthentication(Map<String, String> _auth) throws Exception {
        if (_auth.containsKey("username") && _auth.containsKey("password")) {
            mReplConfig.setAuthenticator(new BasicAuthenticator(_auth.get("username"), _auth.get("password")));
        } else {
            throw new Exception();
        }
        return mReplConfig.getAuthenticator().toString();
    }

    public String saveDocument(Map<String, Object> _map) throws CouchbaseLiteException {
        MutableDocument mutableDoc = new MutableDocument(_map);
        mDatabase.save(mutableDoc);
        String returnedId = mutableDoc.getId();
        return returnedId;
    }

    public String saveDocumentWithId(String _id, Map<String, Object> _map) throws CouchbaseLiteException {
        MutableDocument mutableDoc = new MutableDocument(_id, _map);
        mDatabase.save(mutableDoc);
        String returnedId = mutableDoc.getId();
        return returnedId;
    }

    public Map<String, Object> getDocumentWithId(String _id) throws CouchbaseLiteException {
        return mDatabase.getDocument(_id).toMap();
    }

    public void initDatabaseWithName(String _name) throws Exception, CouchbaseLiteException {
        DatabaseConfiguration config = new DatabaseConfiguration(FlutterCouchbaseLitePlugin.getActiveContext());
        mDatabase = new Database(_name, config);
    }

    public String setReplicatorType (String _type) throws CouchbaseLiteException {
        ReplicatorConfiguration.ReplicatorType settedType = ReplicatorConfiguration.ReplicatorType.PULL;
        if (_type.equals("PUSH")) {
            settedType = ReplicatorConfiguration.ReplicatorType.PUSH;
        } else if (_type.equals("PULL")) {
            settedType = ReplicatorConfiguration.ReplicatorType.PULL;
        } else if (_type.equals("PUSH_AND_PULL")) {
            settedType = ReplicatorConfiguration.ReplicatorType.PUSH_AND_PULL;
        }
        mReplConfig.setReplicatorType(settedType);
        return settedType.toString();
    }

    public void startReplicator() {
        mReplicator = new Replicator(mReplConfig);
        mReplicator.start();
    }

    public void stopReplicator() {
        mReplicator.stop();
        mReplicator = null;
    }
}
