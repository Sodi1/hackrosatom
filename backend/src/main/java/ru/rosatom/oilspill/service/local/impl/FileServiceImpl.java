package ru.rosatom.oilspill.service.local.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rosatom.oilspill.repository.FileRepository;
import ru.rosatom.oilspill.service.local.FileService;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;


}
