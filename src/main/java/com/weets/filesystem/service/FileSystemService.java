package com.weets.filesystem.service;

import com.weets.filesystem.dto.FileDto;
import com.weets.filesystem.model.FileSystemTreeNode;

import java.io.IOException;
import java.util.List;

public interface FileSystemService {
    FileSystemTreeNode createNode(FileDto fileDto) throws Exception;

    void removeNode(String filePath);

    List<FileSystemTreeNode> findContentOf(String path);

    void appendFile(FileDto fileDto) throws Exception;

    void concatanateFiles(FileDto  fileDto) throws Exception;

}
