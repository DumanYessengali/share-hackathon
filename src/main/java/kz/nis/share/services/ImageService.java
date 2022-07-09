package kz.nis.share.services;

import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.entities.Image;
import kz.nis.share.entities.User;
import kz.nis.share.repositories.ImageRepository;
import kz.nis.share.utils.FileNameHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.Transient;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;


    public Image save(Image image) throws NullPointerException {
        if (image == null)
            throw new NullPointerException("Image Data NULL");
        return imageRepository.save(image);
    }


    public Image findByFileName(String fileName) {
        return this.imageRepository.findByFileName(fileName);
    }


    public Image findByUuid(String uuid) {
        return this.imageRepository.findByUuid(uuid);
    }


    public List<ImageResponse> findAllImageResponse() {
        return this.imageRepository.findAllImageResponse();
    }







}
