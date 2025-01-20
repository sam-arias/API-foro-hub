package com.challenge.forohub.controller;

import com.challenge.forohub.domain.Topico.*;
import com.challenge.forohub.domain.usuarios.Usuario;
import com.challenge.forohub.domain.usuarios.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Mostrar una lista de los Topicos publicados
    @GetMapping("/listado")
    public List<Topico> mostrarTopicos() {
        return topicoRepository.findAll();
    }

    @GetMapping()
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).map(DatosListadoTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> mostrarUnTopicoPorId(@PathVariable Long id) {
        Optional<Topico> topicoTemporal = topicoRepository.findById(id);
        if (topicoTemporal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el tópico con el ID especificado.");
        }
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getAutor().getNombre(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getCurso(),
                topico.getFechaDeCreacion()
        );

        return ResponseEntity.ok(datosTopico);
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> crearNuevoTopico(
            @RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {
        // Busca al autor por su id
        Usuario usuario = usuarioRepository.findById(datosRegistroTopico.idAutor())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe un autor con el id especificado"));

        // Crea el tópico con el autor encontrado
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, usuario));

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getAutor().getNombre(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getCurso(),
                topico.getFechaDeCreacion()
        );

        URI url = uriComponentsBuilder.path("topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        try {
            // Verifica si el tópico existe y está activo
            Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
            if (!topico.getStatus()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tópico no está activo y no se puede actualizar.");
            }

            // Actualiza el tópico
            topico.actualizarTopico(datosActualizarTopico);

            // Devuelve una respuesta exitosa
            DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                    topico.getId(),
                    topico.getAutor().getNombre(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getCurso(),
                    topico.getFechaDeCreacion()
            );
            return ResponseEntity.ok(datosRespuestaTopico);
        } catch (EntityNotFoundException e) {
            // Lanza una excepción clara si el tópico no existe
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el tópico con el ID especificado.");
        }
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopicoLogicamente(@PathVariable Long id) {
        // Verificar si el tópico existe
        Optional<Topico> topicoTemporal = topicoRepository.findById(id);
        if (topicoTemporal.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el tópico con el ID especificado.");
        }

        // Cambiar el estado a eliminado
        Topico topico = topicoTemporal.get();
        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el tópico con el ID especificado.");
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
