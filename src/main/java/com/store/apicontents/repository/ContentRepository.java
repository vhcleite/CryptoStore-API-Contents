package com.store.apicontents.repository;

import com.store.apicontents.model.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {
}
