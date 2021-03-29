package com.trivago.poc.hazelcastdemo.listener;

import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClusterMembershipListener implements MembershipListener {

  @Override
  public void memberAdded(final MembershipEvent membershipEvent) {
    log.info("Added: {}", membershipEvent);
  }

  @Override
  public void memberRemoved(final MembershipEvent membershipEvent) {
    log.info("Removed: {}", membershipEvent);
  }
}
