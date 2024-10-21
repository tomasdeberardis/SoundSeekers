package com.uade.soundseekers;

import com.uade.soundseekers.dto.EventDTO;
import com.uade.soundseekers.dto.UserDTO;
import com.uade.soundseekers.entity.Localidad;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.service.EventService;
import com.uade.soundseekers.service.LocalidadService;
import com.uade.soundseekers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocalidadService localidadService;

    @Override
    public void run(String... args) throws Exception {
        List<Localidad> localidades = List.of(
            Localidad.builder().nombre("Almagro").latitud(-34.6056).longitud(-58.4192).build(),
            Localidad.builder().nombre("Balvanera").latitud(-34.6099).longitud(-58.4053).build(),
            Localidad.builder().nombre("Barracas").latitud(-34.6372).longitud(-58.3831).build(),
            Localidad.builder().nombre("Belgrano").latitud(-34.5612).longitud(-58.4585).build(),
            Localidad.builder().nombre("Boedo").latitud(-34.6261).longitud(-58.4174).build(),
            Localidad.builder().nombre("Caballito").latitud(-34.6197).longitud(-58.4306).build(),
            Localidad.builder().nombre("Chacarita").latitud(-34.5871).longitud(-58.4582).build(),
            Localidad.builder().nombre("Coghlan").latitud(-34.5617).longitud(-58.4684).build(),
            Localidad.builder().nombre("Colegiales").latitud(-34.5743).longitud(-58.4497).build(),
            Localidad.builder().nombre("Constitución").latitud(-34.6284).longitud(-58.3859).build(),
            Localidad.builder().nombre("Flores").latitud(-34.6345).longitud(-58.4675).build(),
            Localidad.builder().nombre("Floresta").latitud(-34.6297).longitud(-58.4912).build(),
            Localidad.builder().nombre("La Boca").latitud(-34.6348).longitud(-58.3633).build(),
            Localidad.builder().nombre("La Paternal").latitud(-34.5969).longitud(-58.4642).build(),
            Localidad.builder().nombre("Liniers").latitud(-34.6425).longitud(-58.5201).build(),
            Localidad.builder().nombre("Mataderos").latitud(-34.6629).longitud(-58.5088).build(),
            Localidad.builder().nombre("Monte Castro").latitud(-34.6129).longitud(-58.4965).build(),
            Localidad.builder().nombre("Monserrat").latitud(-34.6113).longitud(-58.3807).build(),
            Localidad.builder().nombre("Nueva Pompeya").latitud(-34.6458).longitud(-58.4249).build(),
            Localidad.builder().nombre("Nuñez").latitud(-34.5463).longitud(-58.4544).build(),
            Localidad.builder().nombre("Palermo").latitud(-34.5826).longitud(-58.4329).build(),
            Localidad.builder().nombre("Parque Avellaneda").latitud(-34.6424).longitud(-58.4805).build(),
            Localidad.builder().nombre("Parque Chacabuco").latitud(-34.6355).longitud(-58.4409).build(),
            Localidad.builder().nombre("Parque Chas").latitud(-34.5891).longitud(-58.4827).build(),
            Localidad.builder().nombre("Parque Patricios").latitud(-34.6352).longitud(-58.4024).build(),
            Localidad.builder().nombre("Puerto Madero").latitud(-34.6091).longitud(-58.3628).build(),
            Localidad.builder().nombre("Recoleta").latitud(-34.5889).longitud(-58.3938).build(),
            Localidad.builder().nombre("Retiro").latitud(-34.5929).longitud(-58.3733).build(),
            Localidad.builder().nombre("Saavedra").latitud(-34.5639).longitud(-58.4895).build(),
            Localidad.builder().nombre("San Cristóbal").latitud(-34.6224).longitud(-58.4007).build(),
            Localidad.builder().nombre("San Nicolás").latitud(-34.6083).longitud(-58.3772).build(),
            Localidad.builder().nombre("San Telmo").latitud(-34.6215).longitud(-58.3736).build(),
            Localidad.builder().nombre("Vélez Sarsfield").latitud(-34.6412).longitud(-58.4939).build(),
            Localidad.builder().nombre("Versalles").latitud(-34.6292).longitud(-58.5104).build(),
            Localidad.builder().nombre("Villa Crespo").latitud(-34.5991).longitud(-58.4374).build(),
            Localidad.builder().nombre("Villa del Parque").latitud(-34.6089).longitud(-58.4868).build(),
            Localidad.builder().nombre("Villa Devoto").latitud(-34.5975).longitud(-58.5136).build(),
            Localidad.builder().nombre("Villa Gral. Mitre").latitud(-34.6101).longitud(-58.4697).build(),
            Localidad.builder().nombre("Villa Lugano").latitud(-34.6736).longitud(-58.4769).build(),
            Localidad.builder().nombre("Villa Luro").latitud(-34.6375).longitud(-58.5039).build(),
            Localidad.builder().nombre("Villa Ortúzar").latitud(-34.5859).longitud(-58.4717).build(),
            Localidad.builder().nombre("Villa Pueyrredón").latitud(-34.5779).longitud(-58.4998).build(),
            Localidad.builder().nombre("Villa Riachuelo").latitud(-34.6789).longitud(-58.4893).build(),
            Localidad.builder().nombre("Villa Santa Rita").latitud(-34.6151).longitud(-58.4852).build(),
            Localidad.builder().nombre("Villa Soldati").latitud(-34.6694).longitud(-58.4425).build(),
            Localidad.builder().nombre("Villa Urquiza").latitud(-34.5739).longitud(-58.4914).build()
        );

        localidadService.saveAll(localidades);

        UserDTO user1DTO = UserDTO.builder()
            .name("Juan")
            .lastName("Pérez")
            .username("juanp")
            .email("juan.perez@gmail.com")
            .password("password123")
            .edad(25)
            .role("ARTIST")
            .localidadId(1L)
            .genres(List.of("CUARTETO", "POP"))
            .build();

        UserDTO user2DTO = UserDTO.builder()
            .name("Maria")
            .lastName("Gomez")
            .username("mariag")
            .email("maria.gomez@gmail.com")
            .password("password123")
            .edad(30)
            .role("ARTIST")
            .localidadId(3L)
            .genres(List.of("ROCK", "FUSION"))
            .build();

        UserDTO user3DTO = UserDTO.builder()
            .name("Carlos")
            .lastName("Lopez")
            .username("carlosl")
            .email("carlos.lopez@gmail.com")
            .password("password123")
            .edad(28)
            .role("CLIENT")
            .localidadId(8L)
            .genres(List.of("REGGAE", "POP"))
            .build();

        userService.createUser(user1DTO);
        userService.createUser(user2DTO);
        userService.createUser(user3DTO);

        Optional<User> user1 = userService.getUserByUsername("juanp");
        Optional<User> user2 = userService.getUserByUsername("mariag");
        Optional<User> user3 = userService.getUserByUsername("carlosl");

        // Create Events
        EventDTO event1DTO = EventDTO.builder()
            .name("Concierto de Rock en Buenos Aires")
            .description("Una noche increíble de rock en vivo.")
            .latitude(-34.603722)
            .longitude(-58.381592)
            .dateTime(LocalDateTime.of(2024, 10, 10, 21, 0))
            .price(1500.0)
            .genres(List.of("ROCK"))
            .organizerId(user1.get().getId())
            .imageIds(List.of())
            .localidadId(1L)
            .build();

        EventDTO event2DTO = EventDTO.builder()
            .name("Festival de Jazz en Palermo")
            .description("Sonidos vibrantes de jazz en Palermo.")
            .latitude(-34.571527)
            .longitude(-58.423469)
            .dateTime(LocalDateTime.of(2024, 11, 5, 19, 0))
            .price(2000.0)
            .genres(List.of("JAZZ"))
            .organizerId(user1.get().getId())
            .localidadId(12L)
            .build();

        EventDTO event3DTO = EventDTO.builder()
            .name("Fiesta de Cumbia en La Plata")
            .description("Baila toda la noche con los mejores hits de cumbia.")
            .latitude(-34.921450)
            .longitude(-57.954530)
            .dateTime(LocalDateTime.of(2024, 12, 20, 23, 0))
            .price(1000.0)
            .genres(List.of("CUMBIA"))
            .organizerId(user1.get().getId())
            .localidadId(8L)
            .build();

        EventDTO event4DTO = EventDTO.builder()
            .name("Fiesta Electrónica en Rosario")
            .description("Disfruta la mejor música electrónica en Rosario.")
            .latitude(-32.944244)
            .longitude(-60.650539)
            .dateTime(LocalDateTime.of(2024, 10, 31, 22, 0))
            .price(3000.0)
            .genres(List.of("ELECTRONICA"))
            .organizerId(user1.get().getId())
            .localidadId(9L)
            .build();

        EventDTO event5DTO = EventDTO.builder()
            .name("Festival de Reggae en Córdoba")
            .description("Vibra con los sonidos del reggae en Córdoba.")
            .latitude(-31.416668)
            .longitude(-64.183334)
            .dateTime(LocalDateTime.of(2024, 10, 25, 20, 0))
            .price(1800.0)
            .genres(List.of("REGGAE"))
            .organizerId(user2.get().getId())
            .localidadId(13L)
            .build();

        EventDTO event6DTO = EventDTO.builder()
            .name("Concierto de Música Clásica en Mendoza")
            .description("Disfruta de una noche de música clásica en el corazón de Mendoza.")
            .latitude(-32.889458)
            .longitude(-68.845840)
            .dateTime(LocalDateTime.of(2024, 11, 15, 19, 30))
            .price(2200.0)
            .genres(List.of("TANGO"))
            .organizerId(user2.get().getId())
            .localidadId(10L)
            .build();

        EventDTO event7DTO = EventDTO.builder()
            .name("Fiesta de Reggaetón en Mar del Plata")
            .description("Ven a disfrutar del mejor reggaetón en la playa.")
            .latitude(-38.005477)
            .longitude(-57.542611)
            .dateTime(LocalDateTime.of(2024, 12, 5, 23, 0))
            .price(2500.0)
            .genres(List.of("RUMBA"))
            .organizerId(user2.get().getId())
            .localidadId(7L)
            .build();

        // Save Events
        eventService.createEvent(event1DTO);
        eventService.createEvent(event2DTO);
        eventService.createEvent(event3DTO);
        eventService.createEvent(event4DTO);
        eventService.createEvent(event5DTO);
        eventService.createEvent(event6DTO);
        eventService.createEvent(event7DTO);
    }
}
