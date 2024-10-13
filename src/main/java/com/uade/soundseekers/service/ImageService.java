package com.uade.soundseekers.service;
import com.uade.soundseekers.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.soundseekers.entity.Image;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    // Obtener todas las imágenes
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    // Obtener imágenes por nombre
    public List<Image> getImagesByName(String imageName) {
        return imageRepository.findByImageName(imageName);
    }

    // Obtener imágenes por evento
    public List<Image> getImagesByEvent(Long event_id) {
        return imageRepository.findByEventId(event_id);
    }

    // Guardar una nueva imagen
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    // Eliminar una imagen por ID
    public void deleteImage(int id) {
        imageRepository.deleteById(id);
    }
}
