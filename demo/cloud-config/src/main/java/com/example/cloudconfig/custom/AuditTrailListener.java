package com.example.cloudconfig.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class AuditTrailListener {
    private static H2Service h2Service;

    @Autowired
    public void setH2Service(final H2Service h2Service) {
        AuditTrailListener.h2Service = h2Service;
    }

    @PostPersist
    public void onPostPersist(Object o) {
        AuditTrailListener.h2Service.onRowChanged(o);
    }

    @PostRemove
    public void onPostRemove(Object o) {
        AuditTrailListener.h2Service.onRowChanged(o);
    }

    @PostUpdate
    public void onPostUpdate(Object o) {
        AuditTrailListener.h2Service.onRowChanged(o);
    }

}
