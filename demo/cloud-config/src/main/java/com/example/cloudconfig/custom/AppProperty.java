package com.example.cloudconfig.custom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppProperty {
    private String key;
    private String value;
}

/*
curl -X PUT -H 'Content-Type: application/json' \
-d '{"key":"app.test.value","value":"updated_now222"}' \
http://10.12.47.130:18888/config/up/38

 */