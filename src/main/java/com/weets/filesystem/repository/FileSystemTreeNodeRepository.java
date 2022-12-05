package com.weets.filesystem.repository;

import com.weets.filesystem.model.FileSystemTreeNode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileSystemTreeNodeRepository extends JpaRepository<FileSystemTreeNode, Long> {

}
