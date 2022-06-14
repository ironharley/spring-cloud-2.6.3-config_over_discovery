package com.example.cloudconfig.custom;

import com.example.cloudconfig.custom.db.Properties;
import com.example.cloudconfig.custom.db.PropertiesRepository;
import com.example.cloudconfig.custom.db.Sources;
import com.example.cloudconfig.custom.db.SourcesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.JdbcEnvironmentRepository;
import org.springframework.cloud.config.server.environment.SearchPathCompositeEnvironmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class H2Service {
    private final SearchPathCompositeEnvironmentRepository gitRepository;
    private final JdbcEnvironmentRepository jdbcRepository;
    private final SourcesRepository sourcesRepository;
    private final PropertiesRepository propertiesRepository;

    @Value("${debug:false}")
    private boolean debug;

    @Value("${app.cloud-config.default-label:master}")
    private String defaultLabel;

    public H2Service(@Autowired(required = false) @Qualifier("searchPathCompositeEnvironmentRepository") EnvironmentRepository gitRepository,
                     @Autowired(required = false) @Qualifier("jdbcEnvironmentRepository") EnvironmentRepository jdbcRepository,
                     // эти две кастомные
                     final SourcesRepository sourcesRepository,  // список файлов конфигураций (костыль, но пока не понял как проще и лучше)
                     final PropertiesRepository propertiesRepository) {
        this.gitRepository = (SearchPathCompositeEnvironmentRepository) gitRepository;
        this.jdbcRepository = (JdbcEnvironmentRepository) jdbcRepository;
        this.sourcesRepository = sourcesRepository;
        this.propertiesRepository = propertiesRepository;
    }

    public Collection<Sources> getAllSources() {
        return sourcesRepository.findAll();
    }

    @Transactional
    public void cleanProperties() {
        propertiesRepository.deleteAll();
    }

    @Transactional
    public void fillProperties() {
        Map<String, Map<String, Map<String, String>>> props = this.getAllKnownProperties(true);
               props .forEach((profile, appmap) ->
                        //todo add labelmap here
                        appmap.forEach((application, values) ->
                                values.forEach((key, value) -> {
                                    if (value != null) {
                                        try {
                                            Properties p = propertiesRepository.save(
                                                    Properties
                                                            .builder()
                                                            .application(application)
                                                            .profile(profile)
                                                            .key(key)
                                                            .value(value)
                                                            //.lastAccess(Instant.now())
                                                            .build()
                                            );
                                            if (debug)
                                                log.info("saved: {}", p);
                                        } catch (Throwable e) {
                                            log.error("{}", e.getMessage(), e);
                                            System.exit(1);
                                        }
                                    } else {
                                        log.warn("{}-{} key {} value is null", application, profile, key);
                                    }
                                })));
    }

    public Map<String, Map<String, Map<String, String>>> getAllKnownProperties(boolean fill) {
        final Map<String, Map<String, Map<String, String>>> rtn = new HashMap<>();
        if (jdbcRepository != null && !fill) {
            log.info("Jdbc profile use");

        } else if (gitRepository != null) {
            log.info("Git/Native profile use");

        } else {
            log.warn("Smth wrong: no jdbc and no git/native repository found");
            return rtn;
        }
        for (Sources src : this.getAllSources()) {
            Environment env;
            if (jdbcRepository != null && !fill) {
                env = jdbcRepository.findOne(src.getApplication(),
                        src.getProfile(),
                        // fuckoff: jdbc changed null to master
                        null);

            } else {
                env = gitRepository.findOne(src.getApplication(),
                        src.getProfile(),
                        src.getLabel());
                log.info("Git/Native profile use");

            }
            if (env != null) {
                env.getPropertySources().forEach(ps -> {
                    for (Map.Entry<?, ?> e : ps.getSource().entrySet()) {
                        String v = null;
                        Object ov = e.getValue();
                        if (ov instanceof String)
                            v = (String) ov;
                        else if (ov instanceof Number || ov instanceof Boolean)
                            v = String.valueOf(ov);

                        rtn.computeIfAbsent(src.getProfile(),
                                s -> new HashMap<>()).computeIfAbsent(src.getApplication(),
                                s -> new HashMap<>()).put((String) e.getKey(), v);
                    }
                });
            }
        }

        return rtn;
    }

    public void onRowChanged(Object o) {
        int a = 0;
    }

    public String create(String app, String profile, String key, String value) {
        return propertiesRepository.save(Properties
                .builder()
                        .application(app)
                        .profile(profile)
                        .key(key)
                        .value(value)
                .build()
        ).toString();
    }
}
