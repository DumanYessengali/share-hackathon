package kz.nis.share.controllers;


import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.entities.Image;
import kz.nis.share.entities.User;
import kz.nis.share.services.ImageService;
import kz.nis.share.services.UserService;
import kz.nis.share.utils.FileNameHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final UserService userService;
    private final ImageService imageService;

    private FileNameHelper fileHelper = new FileNameHelper();


    @GetMapping
    public ResponseEntity<List<ImageResponse>> getAllImageInfo() {

        List<ImageResponse> imageResponses = imageService.findAllImageResponse();
        return ResponseEntity.ok().body(imageResponses);
    }


    @PostMapping("/upload")
    public ImageResponse uploadSingleFile(@RequestParam("file") MultipartFile file, Principal principal) {
        User a = userService.findUserByLogin(principal.getName());
        Image image = Image.buildImage(file, fileHelper, a);
        imageService.save(image);
        return new ImageResponse(image);
    }


//    @PostMapping("/uploads")
//    public List<ImageResponse> uploadMultiFiles(@RequestParam("files") MultipartFile[] files, @RequestParam String login) {
//        return Arrays.asList(files).stream().map(file -> uploadSingleFile(file, login)).collect(Collectors.toList());
//    }


    @GetMapping("/show/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws Exception {
        Image image = getImageByName(fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
    }


    @GetMapping("/show")
    public ResponseEntity<byte[]> getImageWithRequestParam(@RequestParam(required = false, value = "uuid") String uuid,
                                                           @RequestParam(required = false, value = "name") String name) throws Exception {

        if (uuid != null) {
            Image image = getImageByUuid(uuid);
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
        }
        if (name != null) {
            Image image = getImageByName(name);
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
        }
        Image defaultImage = Image.defaultImage();
        return ResponseEntity.ok().contentType(MediaType.valueOf(defaultImage.getFileType()))
                .body(defaultImage.getData());

    }

    @GetMapping("/show/client/{id}")
    public ResponseEntity<byte[]> getImageWithRequestParam(@PathVariable Long id) throws Exception {
        if(id != null) {
            User user = userService.findById(id);
            Image image = getImageByUserId(user.getId());
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
        }


        Image defaultImage = Image.defaultImage();
        return ResponseEntity.ok().contentType(MediaType.valueOf(defaultImage.getFileType()))
                .body(defaultImage.getData());

    }


    @GetMapping("/show/{width}/{height}")
    public ResponseEntity<byte[]> getScaledImageWithRequestParam(@PathVariable int width, @PathVariable int height,
                                                                 @RequestParam(required = false, value = "uuid") String uuid,
                                                                 @RequestParam(required = false, value = "name") String name) throws Exception {

        if (uuid != null) {
            Image image = getImageByUuid(uuid, width, height);
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
        }
        if (name != null) {
            Image image = getImageByName(name, width, height);
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
        }
        Image defImage = Image.defaultImage(width, height);
        return ResponseEntity.ok().contentType(MediaType.valueOf(defImage.getFileType())).body(defImage.getData());
    }


//	@GetMapping("/show/{width}/{height}/{fileName:.+}")
//	public ResponseEntity<byte[]> getScaledImage(@PathVariable int width, @PathVariable int height,
//			@PathVariable String fileName) throws Exception {
//		Image image = getImageByName(fileName, width, height);
//		return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
//	}


    public Image getImageByName(String name) throws Exception {
        Image image = imageService.findByFileName(name);
        if (image == null) {
            return Image.defaultImage();
        }
        return image;
    }


    public Image getImageByName(String name, int width, int height) throws Exception {
        Image image = imageService.findByFileName(name);
        if (image == null) {
            Image defImage = Image.defaultImage();
            defImage.scale(width, height);
            return defImage;
        }
        image.scale(width, height);
        return image;
    }


    public Image getImageByUuid(String uuid) throws Exception {
        Image image = imageService.findByUuid(uuid);
        if (image == null) {
            return Image.defaultImage();
        }
        return image;
    }

    public Image getImageByUserId(Long id) throws Exception {
        Image image = imageService.findByUserId(id);
        if (image == null) {
            return Image.defaultImage();
        }
        return image;
    }



    public Image getImageByUuid(String uuid, int width, int height) throws Exception {
        Image image = imageService.findByUuid(uuid);
        if (image == null) {
            Image defImage = Image.defaultImage();
            defImage.scale(width, height);
            return defImage;
        }
        image.scale(width, height);
        return image;
    }

}
