package com.appguard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import com.appguard.controller.NovedadController;
import com.appguard.model.EvidenciaNovedad;
import com.appguard.model.Novedad;
import com.appguard.repository.EvidenciaNovedadRepository;
import com.appguard.repository.NovedadRepository;

class NovedadControllerTest {

    @Mock
    private NovedadRepository novedadRepository;

    @Mock
    private EvidenciaNovedadRepository evidenciaRepository;

    private NovedadController novedadController;

    @BeforeEach
    void configurar() {

        MockitoAnnotations.openMocks(this);

        novedadController = new NovedadController(
                novedadRepository,
                evidenciaRepository
        );
    }

    @Test
    void registraNovedadSinArchivos() throws Exception {

        when(
                novedadRepository.save(
                        any(Novedad.class)
                )
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        Novedad resultado =
                novedadController.registrarNovedad(
                        1,
                        "Guardas",
                        "Novedad de prueba AppGuard",
                        null
                );

        assertNotNull(resultado);

        assertEquals(
                1,
                resultado.getIdUsuario()
        );

        assertEquals(
                "Guardas",
                resultado.getTipo()
        );

        assertEquals(
                "Novedad de prueba AppGuard",
                resultado.getDescripcion()
        );

        assertEquals(
                "Pendiente",
                resultado.getEstado()
        );

        assertNotNull(
                resultado.getFecha()
        );

        assertNotNull(
                resultado.getHora()
        );

        verify(
                novedadRepository
        ).save(
                any(Novedad.class)
        );
    }

    @Test
    void rechazaMasDeDiezArchivos() {

        when(
                novedadRepository.save(
                        any(Novedad.class)
                )
        ).thenAnswer(
                invocacion ->
                        invocacion.getArgument(0)
        );

        MockMultipartFile[] archivos =
                new MockMultipartFile[11];

        for (int i = 0; i < 11; i++) {

            archivos[i] =
                    new MockMultipartFile(
                            "archivo" + i,
                            "archivo" + i + ".txt",
                            "text/plain",
                            "contenido".getBytes()
                    );
        }

        IllegalArgumentException excepcion =
                assertThrows(
                        IllegalArgumentException.class,
                        () ->
                                novedadController.registrarNovedad(
                                        1,
                                        "Otra",
                                        "Prueba con demasiados archivos",
                                        archivos
                                )
                );

        assertEquals(
                "Solo se permiten máximo 10 archivos.",
                excepcion.getMessage()
        );
    }

    @Test
    void listaEvidenciasDeUnaNovedad() {

        EvidenciaNovedad evidencia =
                new EvidenciaNovedad();

        evidencia.setIdNovedad(1);
        evidencia.setNombreArchivo(
                "evidencia.jpg"
        );

        evidencia.setTipoArchivo(
                "image/jpeg"
        );

        when(
                evidenciaRepository.findByIdNovedad(1)
        ).thenReturn(
                List.of(evidencia)
        );

        List<EvidenciaNovedad> resultado =
                novedadController.listarEvidencias(1);

        assertEquals(
                1,
                resultado.size()
        );

        assertEquals(
                "evidencia.jpg",
                resultado.get(0)
                        .getNombreArchivo()
        );

        assertEquals(
                "image/jpeg",
                resultado.get(0)
                        .getTipoArchivo()
        );

        verify(
                evidenciaRepository
        ).findByIdNovedad(1);
    }
}