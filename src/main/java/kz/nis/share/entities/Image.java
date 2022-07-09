package kz.nis.share.entities;

import kz.nis.share.utils.FileNameHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "images")
public class Image  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "size")
    private long size;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Lob
    @Column(name = "data")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    public static Image build() {
        String uuid = UUID.randomUUID().toString();
        Image image = new Image();
        image.setUuid(uuid);
        image.setCreatedDate(LocalDate.now());
        return image;
    }

    @Transient
    public void setFiles(MultipartFile file) {
        setFileType(file.getContentType());
        setSize(file.getSize());
    }


    @Transient
    public byte[] scale(int width, int height) throws Exception {

        if (width == 0 || height == 0)
            return data;

        ByteArrayInputStream in = new ByteArrayInputStream(data);

        try {
            BufferedImage img = ImageIO.read(in);

            java.awt.Image scaledImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            BufferedImage imgBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imgBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imgBuff, "jpg", buffer);
            setData(buffer.toByteArray());
            return buffer.toByteArray();

        } catch (Exception e) {
            throw new Exception("IOException in scale");
        }
    }


    private static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = Image.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }


    @Transient
    public static Image defaultImage() throws Exception {
        InputStream is = getResourceFileAsInputStream("notfound.jpg");
        String fileType = "image/jpeg";
        byte[] bdata = FileCopyUtils.copyToByteArray(is);
        Image image = new Image(null, fileType, 0, null, null, bdata);
        return image;
    }


    @Transient
    public static Image defaultImage(int width, int height) throws Exception {
        Image defaultImage = defaultImage();
        defaultImage.scale(width, height);
        return defaultImage;
    }


    @Transient
    public static Image buildImage(MultipartFile file, FileNameHelper helper, User user) {
        String fileName = helper.generateDisplayName(file.getOriginalFilename());

        Image image = Image.build();
        image.setFileName(fileName);
        image.setFiles(file);
        image.setUser(user);


        try {
            image.setData(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public Image(String fileName, String fileType, long size, String uuid, LocalDate createdDate, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.uuid = uuid;
        this.createdDate = createdDate;
        this.data = data;
    }

    public Image() {
    }
}