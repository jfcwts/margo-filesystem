package com.weets.filesystem.repository;

import com.weets.filesystem.model.FileSystemTreeNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileSystemTreeNodeRepository extends JpaRepository<FileSystemTreeNode, Long> {
    List<FileSystemTreeNode> findByNameAndPath(String name, String path);
    List<FileSystemTreeNode> findByPath(String path);
}
