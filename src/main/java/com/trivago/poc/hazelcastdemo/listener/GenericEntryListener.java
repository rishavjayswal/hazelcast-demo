package com.trivago.poc.hazelcastdemo.listener;

import com.hazelcast.core.EntryListener;

public interface GenericEntryListener<K, V> extends EntryListener<K, V> {

  String mapName();

}
