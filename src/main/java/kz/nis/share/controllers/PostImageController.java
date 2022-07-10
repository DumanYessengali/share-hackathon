package kz.nis.share.controllers;

import kz.nis.share.dtos.ImageResponse;
import kz.nis.share.dtos.PostImageResponse;
import kz.nis.share.entities.Image;
import kz.nis.share.entities.Post;
import kz.nis.share.entities.PostImage;
import kz.nis.share.entities.User;
import kz.nis.share.exceptions.PostException;
import kz.nis.share.repositories.PostRepository;
import kz.nis.share.services.ImageService;
import kz.nis.share.services.PostImageService;
import kz.nis.share.services.PostService;
import kz.nis.share.services.UserService;
import kz.nis.share.utils.FileNameHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post_image")
@RequiredArgsConstructor
public class PostImageController {
    private final PostRepository postRepository;
    private final PostImageService postImageService;

    private FileNameHelper fileHelper = new FileNameHelper();


//    @GetMapping
//    public ResponseEntity<List<ImageResponse>> getAllImageInfo() {
//
//        List<ImageResponse> imageResponses = imageService.findAllImageResponse();
//        return ResponseEntity.ok().body(imageResponses);
//    }


    @PostMapping("/upload/{post_id}")
    public PostImageResponse uploadSingleFile(@RequestParam("file") MultipartFile file,@PathVariable(name = "post_id") Long postId) {
        Post post = postRepository.findById(1L).orElseThrow(() -> new PostException("Post not found"));
        PostImage image = PostImage.buildImage(file, fileHelper, post);
        postImageService.save(image);
        return new PostImageResponse(image);
    }


//    @PostMapping("/uploads")
//    public List<ImageResponse> uploadMultiFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files).stream().map(file -> uploadSingleFile(file)).collect(Collectors.toList());
//    }


    @GetMapping("/show/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws Exception {
        PostImage image = getImageByName(fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
    }



    @GetMapping("/show/{width}/{height}")
    public ResponseEntity<byte[]> getScaledImageWithRequestParam(@PathVariable int width, @PathVariable int height,
                                                                 @RequestParam(required = false, value = "uuid") String uuid,
                                                                 @RequestParam(required = false, value = "name") String name) throws Exception {

        if (uuid != null) {
            PostImage image = getImageByUuid(uuid, width, height);
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
        }
        if (name != null) {
            PostImage image = getImageByName(name, width, height);
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
        }
        PostImage defImage = PostImage.defaultImage(width, height);
        return ResponseEntity.ok().contentType(MediaType.valueOf(defImage.getFileType())).body(defImage.getData());
    }


//	@GetMapping("/show/{width}/{height}/{fileName:.+}")
//	public ResponseEntity<byte[]> getScaledImage(@PathVariable int width, @PathVariable int height,
//			@PathVariable String fileName) throws Exception {
//		Image image = getImageByName(fileName, width, height);
//		return ResponseEntity.ok().contentType(MediaType.valueOf(image.getFileType())).body(image.getData());
//	}


    public PostImage getImageByName(String name) throws Exception {
        PostImage image = postImageService.findByFileName(name);
        if (image == null) {
            return PostImage.defaultImage();
        }
        return image;
    }


    public PostImage getImageByName(String name, int width, int height) throws Exception {
        PostImage image = postImageService.findByFileName(name);
        if (image == null) {
            PostImage defImage = PostImage.defaultImage();
            defImage.scale(width, height);
            return defImage;
        }
        image.scale(width, height);
        return image;
    }


    public PostImage getImageByUuid(String uuid) throws Exception {
        PostImage image = postImageService.findByUuid(uuid);
        if (image == null) {
            return PostImage.defaultImage();
        }
        return image;
    }


    public PostImage getImageByUuid(String uuid, int width, int height) throws Exception {
        PostImage image = postImageService.findByUuid(uuid);
        if (image == null) {
            PostImage defImage = PostImage.defaultImage();
            defImage.scale(width, height);
            return defImage;
        }
        image.scale(width, height);
        return image;
    }
}
