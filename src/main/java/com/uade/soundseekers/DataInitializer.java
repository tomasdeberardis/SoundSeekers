package com.uade.soundseekers;

import com.uade.soundseekers.dto.EventDTO;
import com.uade.soundseekers.entity.Localidad;
import com.uade.soundseekers.entity.MusicGenre;
import com.uade.soundseekers.entity.Role;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.repository.UserRepository;
import com.uade.soundseekers.service.EventService;
import com.uade.soundseekers.service.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EventService eventService;

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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

        List<User> users = List.of(
                createUser("juan.perez@gmail.com", "Juan", "Pérez", "juanp", 25, Role.ARTIST, localidades.get(0), List.of(MusicGenre.CUARTETO, MusicGenre.POP)),
                createUser("maria.gomez@gmail.com", "Maria", "Gomez", "mariag", 30, Role.ARTIST, localidades.get(1), List.of(MusicGenre.ROCK, MusicGenre.FOLKLORE)),
                createUser("carlos.lopez@gmail.com", "Carlos", "Lopez", "carlosl", 28, Role.CLIENT, localidades.get(2), List.of(MusicGenre.RUMBA, MusicGenre.INDIE)),
                createUser("ana.martinez@gmail.com", "Ana", "Martinez", "anam", 22, Role.CLIENT, localidades.get(3), List.of(MusicGenre.SALSA, MusicGenre.HIP_HOP)),
                createUser("pedro.fernandez@gmail.com", "Pedro", "Fernandez", "pedrof", 35, Role.ARTIST, localidades.get(4), List.of(MusicGenre.ELECTRONICA, MusicGenre.REGGAETON)),
                createUser("laura.rodriguez@gmail.com", "Laura", "Rodriguez", "laurar", 26, Role.CLIENT, localidades.get(5), List.of(MusicGenre.BLUES, MusicGenre.PUNK)),
                createUser("martin.sanchez@gmail.com", "Martin", "Sanchez", "martins", 31, Role.ARTIST, localidades.get(6), List.of(MusicGenre.TANGO, MusicGenre.JAZZ)),
                createUser("sofia.diaz@gmail.com", "Sofia", "Diaz", "sofiad", 27, Role.CLIENT, localidades.get(7), List.of(MusicGenre.CLASICO, MusicGenre.ALTERNATIVE)),
                createUser("lucas.perez@gmail.com", "Lucas", "Perez", "lucasp", 29, Role.ARTIST, localidades.get(8), List.of(MusicGenre.PUNK_ROCK, MusicGenre.GRUNGE)),
                createUser("elena.moreno@gmail.com", "Elena", "Moreno", "elenam", 24, Role.CLIENT, localidades.get(9), List.of(MusicGenre.BOSSA_NOVA, MusicGenre.FUSION))
        );

        userRepository.saveAll(users);

        List<EventDTO> events = List.of(
                createEventDTO("Concierto de Rock en Buenos Aires", "Una noche increíble de rock en vivo.", -34.603722, -58.381592, LocalDateTime.of(2024, 12, 10, 21, 0), 1500.0, List.of("ROCK"), users.get(0).getId(), localidades.get(0).getId()));

        events.forEach(eventService::createEvent);
    }

    private User createUser(String email, String name, String lastName, String username, int age, Role role, Localidad localidad, List<MusicGenre> genres) {
        return User.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode("Password123"))
                .lastName(lastName)
                .username(username)
                .edad(age)
                .isEmailVerified(true)
                .role(role)
                .localidad(localidad)
                .generosMusicalesPreferidos(genres)
                .build();
    }

    private EventDTO createEventDTO(String name, String description, double latitude, double longitude, LocalDateTime dateTime, double price, List<String> genres, Long organizerId, Long localidadId) {
        return EventDTO.builder()
                .name(name)
                .description(description)
                .latitude(latitude)
                .longitude(longitude)
                .dateTime(dateTime)
                .price(price)
                .genres(genres)
                .organizerId(organizerId)
                .localidadId(localidadId)
                .build();
    }
}