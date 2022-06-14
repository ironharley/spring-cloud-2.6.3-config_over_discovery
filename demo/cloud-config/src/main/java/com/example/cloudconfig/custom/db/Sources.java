package com.example.cloudconfig.custom.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Sources {

    @Column(name = "APPLICATION")
    private String application;

    @Column(name = "PROFILE")
    private String profile;

    @Column(name = "LABEL")
    private String label;

    @Id
    @Column(name = "FILENAME")
    private String filename;


}
