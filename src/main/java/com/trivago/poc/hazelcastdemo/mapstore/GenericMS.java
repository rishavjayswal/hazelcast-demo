package com.trivago.poc.hazelcastdemo.mapstore;

import com.hazelcast.map.MapStore;

public interface GenericMS<K, V> extends MapStore<K, V> {

  String mapName();

}
