package com.weets.filesystem.service;

import com.weets.filesystem.model.FileSystemTreeNode;

import java.io.IOException;
import java.util.List;

public interface FileSystemService {
    FileSystemTreeNode createNode(FileSystemTreeNode node) throws IOException;

    void removeNode(String filename);
    List<FileSystemTreeNode> findContentOf(String path);

}
