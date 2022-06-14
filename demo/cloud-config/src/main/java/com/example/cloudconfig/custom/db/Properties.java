package com.example.cloudconfig.custom.db;

import com.example.cloudconfig.custom.AuditTrailListener;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
@EntityListeners(AuditTrailListener.class)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Properties {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION")
    private String application;

    @Column(name = "PROFILE")
    private String profile;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

/*
    @Column(name = "LAST_ACCESSED")
    private Instant lastAccess;
*/


}
