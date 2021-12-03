package ru.rosatom.oilspill.service.local;

import org.springframework.web.multipart.MultipartFile;
import ru.rosatom.oilspill.model.dto.FileDto;
import ru.rosatom.oilspill.model.entity.FileImage;

import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public interface UploadFileService {

    List<FileImage> save(@NotNull Long issueId,
                         @NotNull List<MultipartFile> files) throws IOException;

    FileImage saveIntoDb(@NotNull FileDto fileImage);

    List<FileImage> findByIssueId(@NotNull Long issueId);

    FileInputStream findImageByName(String imageName) throws IOException;

}
