package com.lianaa98.roomy.common;

import org.springframework.http.ResponseEntity;

public class Status {

    public static ResponseEntity<?> created() {
        return ResponseEntity.status(201).build();
    }

    public static ResponseEntity<?> badRequest() {
        return ResponseEntity.badRequest().build();
    }

    public static ResponseEntity<?> unauthorized() {
        return ResponseEntity.status(401).build();
    }

    public static ResponseEntity<?> forbidden(String message) {
        return ResponseEntity.status(403).body(message);
    }

    public static ResponseEntity<?> notFound() {
        return ResponseEntity.status(404).build();
    }

    public static ResponseEntity<?> conflict(String message) {
        return ResponseEntity.status(409).body(message);
    }

}
