package com.weets.filesystem.service;

import com.weets.filesystem.model.FileSystemTreeNode;

import java.util.List;

public interface FileSystemService {
    FileSystemTreeNode createNode(FileSystemTreeNode node);
    FileSystemTreeNode removeNode(long nodeId);
    List<FileSystemTreeNode> findContentOf(String path);

}
