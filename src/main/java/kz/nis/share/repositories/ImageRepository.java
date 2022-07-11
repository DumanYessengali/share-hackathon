package kz.nis.share.repositories;


import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByFileName(String fileName);

	Image findByUuid(String uuid);

	@Query(value = "select * from images i join users u on i.user_id = u.id where u.id = :id limit 1", nativeQuery = true)
	Image findByUserId(Long id);

	@Query(value = "select new kz.nis.share.dtos.ImageResponse(im.uuid, im.fileName, im.fileType, im.size) from Image im")
	List<ImageResponse> findAllImageResponse();

}