package com.challenge.forohub.infra.errors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> manejarErrores(ResponseStatusException ex) {
        // Imprime el error en la consola
        System.err.println("Error: " + ex.getReason());

        // Crea una respuesta personalizada
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", ex.getStatusCode().toString());
        errorResponse.put("message", ex.getReason());

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> manejarErrorDeIntegridadTituloMensaje(DataIntegrityViolationException ex) {
        // Identificar si es un error de unicidad
        String mensajeError = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "Violación de integridad de datos";

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "409 CONFLICT");
        errorResponse.put("message", mensajeError.contains("Duplicate entry")
                ? "El título y mensaje especificados ya existen. Intenta con valores diferentes."
                : mensajeError);

        System.err.println("Error de integridad: " + mensajeError);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // Tratar error genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarError500(Exception ex) {
        // Registrar el error para seguimiento
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error interno en el servidor.");
    }


}
