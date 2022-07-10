package kz.nis.share.dtos;

import kz.nis.share.entities.Image;
import kz.nis.share.entities.PostImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImageResponse {

    private String uuid;
    private String fileName;
    private String fileType;
    private long size;

    public PostImageResponse(PostImage image) {
        setUuid(image.getUuid());
        setFileName(image.getFileName());
        setFileType(image.getFileType());
        setSize(image.getSize());
    }
}
