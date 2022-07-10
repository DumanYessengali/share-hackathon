package kz.nis.share.repositories;

import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.dtos.PostImageResponse;
import kz.nis.share.entities.Image;

import kz.nis.share.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostImagesRepository extends JpaRepository<PostImage, Long> {

    PostImage findByFileName(String fileName);

    PostImage findByUuid(String uuid);

    @Query(value = "select new kz.nis.share.dtos.PostImageResponse(im.uuid, im.fileName, im.fileType, im.size) from PostImage im")
    List<PostImageResponse> findAllImageResponse();
}
