package ru.rosatom.oilspill.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.rosatom.oilspill.model.entity.FileImage;
import ru.rosatom.oilspill.service.local.UploadFileService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/upload")
public class UploadFileController {

    private final UploadFileService uploadFileService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileImage>> save(
            @RequestParam("issueId") Long issueId,
            @RequestPart List<MultipartFile> files) throws IOException {
        return ResponseEntity.ok(uploadFileService.save(issueId, files));
    }

    @GetMapping(value = "/{fileName}")
    public  ResponseEntity<InputStreamResource> findByName(@PathVariable("fileName") String name) throws IOException {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION)
                .contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(uploadFileService.findImageByName(name)));
    }


}
