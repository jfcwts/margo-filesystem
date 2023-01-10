package com.weets.filesystem.model;



import jakarta.persistence.*;

import java.io.File;

@Entity
public class FileSystemTreeNode {

    private static final String ROOT_FILE_SYSTEM = "src\\main\\resources\\jfs";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;

    /**
     * must always begin by /
     */
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private Boolean folder;


    protected FileSystemTreeNode(){}

    public FileSystemTreeNode(String name, String path, boolean folder){
        this.path = path;
        this.folder = folder;
        this.name = name;
    }

    public String getFormattedFullFilename(){
        return ROOT_FILE_SYSTEM + getPath().replace("/","\\") + this.name;
    }

    public File getFile(){
        return new File(getFormattedFullFilename());
    }

    public String getFilename(){
        return "%s/%s".formatted(path, name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
