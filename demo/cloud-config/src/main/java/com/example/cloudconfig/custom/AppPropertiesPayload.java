package com.example.cloudconfig.custom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class AppPropertiesPayload {
    private String application;
    private String profile;
    private String label;
    private Collection<AppProperty> properties;
}
