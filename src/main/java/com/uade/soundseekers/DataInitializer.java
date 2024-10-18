package com.uade.soundseekers;

import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.entity.Role;
import com.uade.soundseekers.entity.musicGenre;
import com.uade.soundseekers.service.EventService;
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

    @Override
    public void run(String... args) throws Exception {
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

        // Event in Buenos Aires (near Obelisco)
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

        // Event in Buenos Aires (near Palermo)
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

        // Event in La Plata
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

        // Event in Rosario
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

        // Event in Córdoba
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

        // Event in Mendoza
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

        // Event in Mar del Plata
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

