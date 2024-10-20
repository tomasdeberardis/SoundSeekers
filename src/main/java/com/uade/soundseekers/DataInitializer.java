package com.uade.soundseekers;

import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.Localidad;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.entity.Role;
import com.uade.soundseekers.entity.musicGenre;
import com.uade.soundseekers.service.EventService;
import com.uade.soundseekers.service.LocalidadService;
import com.uade.soundseekers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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

        // Create Users
        User user1 = User.builder()
            .name("Juan")
            .lastName("Pérez")
            .username("juanp")
            .email("juan.perez@gmail.com")
            .password("password123")
            .edad(25)
            .role(Role.ARTIST)
            .build();

        User user2 = User.builder()
            .name("Maria")
            .lastName("Gomez")
            .username("mariag")
            .email("maria.gomez@gmail.com")
            .password("password123")
            .edad(30)
            .role(Role.CLIENT)
            .build();

        User user3 = User.builder()
            .name("Carlos")
            .lastName("Lopez")
            .username("carlosl")
            .email("carlos.lopez@gmail.com")
            .password("password123")
            .edad(28)
            .role(Role.CLIENT)
            .build();

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);

        // Create Events
        Event event1 = new Event();
        event1.setName("Concierto de Rock en Buenos Aires");
        event1.setDescription("Una noche increíble de rock en vivo.");
        event1.setLatitude(-34.603722);
        event1.setLongitude(-58.381592);
        event1.setDateTime(LocalDateTime.of(2024, 10, 10, 21, 0));
        event1.setPrice(1500.0);
        event1.setGenres(List.of(musicGenre.ROCK));
        event1.setOrganizer(user1);
        event1.setAttendees(List.of(user1, user2));

        Event event2 = new Event();
        event2.setName("Festival de Jazz en Palermo");
        event2.setDescription("Sonidos vibrantes de jazz en Palermo.");
        event2.setLatitude(-34.571527);
        event2.setLongitude(-58.423469);
        event2.setDateTime(LocalDateTime.of(2024, 11, 5, 19, 0));
        event2.setPrice(2000.0);
        event2.setGenres(List.of(musicGenre.JAZZ));
        event2.setOrganizer(user2);
        event2.setAttendees(List.of(user2, user3));

        Event event3 = new Event();
        event3.setName("Fiesta de Cumbia en La Plata");
        event3.setDescription("Baila toda la noche con los mejores hits de cumbia.");
        event3.setLatitude(-34.921450);
        event3.setLongitude(-57.954530);
        event3.setDateTime(LocalDateTime.of(2024, 12, 20, 23, 0));
        event3.setPrice(1000.0);
        event3.setGenres(List.of(musicGenre.CUMBIA));
        event3.setOrganizer(user3);
        event3.setAttendees(List.of(user1, user3));

        Event event4 = new Event();
        event4.setName("Fiesta Electrónica en Rosario");
        event4.setDescription("Disfruta la mejor música electrónica en Rosario.");
        event4.setLatitude(-32.944244);
        event4.setLongitude(-60.650539);
        event4.setDateTime(LocalDateTime.of(2024, 10, 31, 22, 0));
        event4.setPrice(3000.0);
        event4.setGenres(List.of(musicGenre.ELECTRONICA));
        event4.setOrganizer(user1);
        event4.setAttendees(List.of(user2));

        Event event5 = new Event();
        event5.setName("Festival de Reggae en Córdoba");
        event5.setDescription("Vibra con los sonidos del reggae en Córdoba.");
        event5.setLatitude(-31.416668);
        event5.setLongitude(-64.183334);
        event5.setDateTime(LocalDateTime.of(2024, 10, 25, 20, 0));
        event5.setPrice(1800.0);
        event5.setGenres(List.of(musicGenre.REGGAE));
        event5.setOrganizer(user3);
        event5.setAttendees(List.of(user2, user3));

        Event event6 = new Event();
        event6.setName("Concierto de Música Clásica en Mendoza");
        event6.setDescription("Disfruta de una noche de música clásica en el corazón de Mendoza.");
        event6.setLatitude(-32.889458);
        event6.setLongitude(-68.845840);
        event6.setDateTime(LocalDateTime.of(2024, 11, 15, 19, 30));
        event6.setPrice(2200.0);
        event6.setGenres(List.of(musicGenre.CLASICO));
        event6.setOrganizer(user2);
        event6.setAttendees(List.of(user1));

        Event event7 = new Event();
        event7.setName("Fiesta de Reggaetón en Mar del Plata");
        event7.setDescription("Ven a disfrutar del mejor reggaetón en la playa.");
        event7.setLatitude(-38.005477);
        event7.setLongitude(-57.542611);
        event7.setDateTime(LocalDateTime.of(2024, 12, 5, 23, 0));
        event7.setPrice(2500.0);
        event7.setGenres(List.of(musicGenre.REGGAETON));
        event7.setOrganizer(user3);
        event7.setAttendees(List.of(user1, user2, user3));

        // Save Events
        eventService.createEvent(event1);
        eventService.createEvent(event2);
        eventService.createEvent(event3);
        eventService.createEvent(event4);
        eventService.createEvent(event5);
        eventService.createEvent(event6);
        eventService.createEvent(event7);
    }
}