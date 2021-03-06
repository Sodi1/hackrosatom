package ru.rosatom.oilspill.service.local.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.rosatom.oilspill.mapper.FileMapper;
import ru.rosatom.oilspill.model.dto.FileDto;
import ru.rosatom.oilspill.model.entity.FileImage;
import ru.rosatom.oilspill.model.entity.Issue;
import ru.rosatom.oilspill.model.enums.IssueStatus;
import ru.rosatom.oilspill.repository.FileRepository;
import ru.rosatom.oilspill.service.local.IssueService;
import ru.rosatom.oilspill.service.local.UploadFileService;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UploadFileServiceImpl implements UploadFileService {

    private final FileRepository repository;
    private final FileMapper fileMapper;
    private final IssueService issueService;

    @Value("${image.upload.path}")
    private String filePath;

    @Override
    public List<FileImage> save(@NotNull Long issueId,
                                @NotNull List<MultipartFile> files) throws IOException {

        File uploadDir = new File(filePath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        List<FileImage> fileImages = new ArrayList<>();

        for (MultipartFile file : files) {

            String uuidFile = UUID.randomUUID().toString();

            String resultFileName = filePath + "/" + uuidFile + "-" + file.getOriginalFilename();

            file.transferTo(new File(resultFileName));

            FileImage fileImage = saveIntoDb(new FileDto(issueId, "https://oil.kovalev.team/back-img/" + uuidFile + "-" + file.getOriginalFilename()));

            fileImages.add(fileImage);
        }

        Optional<Issue> issueOpt = issueService.findById(issueId);

        if (issueOpt.isPresent()) {

            Issue issue = issueOpt.get();

            if (issue.getIssueStatus() != null && issue.getIssueStatus().equals(IssueStatus.WAIT_PHOTO)) {

                issue.setIssueStatus(IssueStatus.ACTIVE);

            }

            issueService.save(issue);

        }

        return fileImages;
    }

    @Override
    public FileImage saveIntoDb(@NotNull FileDto fileImage) {
        return repository.save(repository.save(fileMapper.toEntity(fileImage)));
    }

    @Override
    public List<FileImage> findByIssueId(@NotNull Long issueId) {
        return repository.findByIssueId(issueId);
    }

    @SneakyThrows
    @Override
    public FileInputStream findImageByName(String imageName) {

        File file = new File(filePath + "/" + imageName);
        return new FileInputStream(file);

    }


}
