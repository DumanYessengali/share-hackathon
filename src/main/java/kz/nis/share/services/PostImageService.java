package kz.nis.share.services;

import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.dtos.PostImageResponse;
import kz.nis.share.entities.Image;
import kz.nis.share.entities.PostImage;
import kz.nis.share.repositories.PostImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImagesRepository postImagesRepository;


    public PostImage save(PostImage image) throws NullPointerException {
        if (image == null)
            throw new NullPointerException("Image Data NULL");
        return postImagesRepository.save(image);
    }


    public PostImage findByFileName(String fileName) {
        return this.postImagesRepository.findByFileName(fileName);
    }


    public PostImage findByUuid(String uuid) {
        return this.postImagesRepository.findByUuid(uuid);
    }


    public List<PostImageResponse> findAllImageResponse() {
        return this.postImagesRepository.findAllImageResponse();
    }



}
