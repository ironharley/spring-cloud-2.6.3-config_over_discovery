package com.example.cloudconfig.custom;

import com.example.cloudconfig.custom.db.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config")
public class CustomController {

    private final H2Service h2Service;

    @Autowired public CustomController(H2Service h2Service) {
        this.h2Service = h2Service;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@Operation(description = "Добавить настройки, активировать каждую после записи (полезно для 1-2 разных сервисов)")
    public @ResponseBody
    ResponseEntity<String> add(@RequestBody Collection<AppPropertiesPayload> payload) {
        return new ResponseEntity<>(String.valueOf(this.h2Service.create(payload, false)),
                HttpStatus.OK);
    }

    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@Operation(description = "Добавить настройки, активировать все оптом в конце (полезно для 10-20 записей одного-двух сервисов)")
    public @ResponseBody
    ResponseEntity<String> batch(@RequestBody Collection<AppPropertiesPayload> payload) {
        return new ResponseEntity<>(String.valueOf(this.h2Service.create(payload, true)),
                HttpStatus.OK);
    }

    @PutMapping(value = "/up/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@Operation(description = "Изменить настройку, активировать после записи")
    public @ResponseBody
    ResponseEntity<Properties> up(@PathVariable("id") int id, @RequestBody AppProperty payload) {
        return new ResponseEntity<>(this.h2Service.update( id, payload), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@Operation(description = "Получить настройку")
    public @ResponseBody
    ResponseEntity<Properties> get(@PathVariable("id") int id) {
        return new ResponseEntity<>(this.h2Service.get( id), HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@Operation(description = "Получить все настройки")
    public @ResponseBody
    ResponseEntity<Collection<Properties>> get() {
        return new ResponseEntity<>(this.h2Service.getAll(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@Operation(description = "Удалить настройку, активировать после удаления")
    public @ResponseBody
    ResponseEntity<Properties> delete(@PathVariable("id") int id) {
        return new ResponseEntity<>(this.h2Service.delete( id), HttpStatus.OK);
    }

    @GetMapping("apply/{application}")
    //@Operation(description = "Активировать изменения настроек сервиса")
    public @ResponseBody
    ResponseEntity<Boolean> get(@PathVariable("application") String application) {
        this.h2Service.publishEventOf( application);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = {"/fillAll"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@Operation(description = "Заменить все настройки приложений настройками из Git (батч, блокирует базу)")
    public String fillAll() {
        h2Service.cleanProperties();
        h2Service.fillProperties();
        return "";
    }

}
