package com.store.apicontents.service;

import com.store.apicontents.exceptions.CryptoStoreException;
import com.store.apicontents.model.ContentEntity;
import com.store.apicontents.model.dto.ContentGetDto;
import com.store.apicontents.model.dto.ContentPostDto;
import com.store.apicontents.repository.ContentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    private static final ModelMapper modelMapper = new ModelMapper();
    private ContentRepository contentRepository;

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public Page<ContentGetDto> findPage(PageRequest pageRequest) {
        Page<ContentEntity> contentPage = contentRepository.findAll(pageRequest);
        return contentPage.map(c -> modelMapper.map(c, ContentGetDto.class));
    }

    public ContentEntity create(ContentPostDto newContent) {
        ContentEntity content = modelMapper.map(newContent, ContentEntity.class);
        return contentRepository.save(content);
    }
}
