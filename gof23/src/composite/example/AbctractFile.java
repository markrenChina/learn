package composite.example;

import composite.template.Component;

import java.util.ArrayList;
import java.util.List;

public interface AbctractFile {

    void killVirus(); //杀毒
}

//leaf
class ImageFile implements AbctractFile {
    private final String name;

    public ImageFile(String name) {
        super();
        this.name = name;
    }

    @Override
    public void killVirus() {
        System.out.println("图像文件：" + name + "，查毒中...");
    }
}

//leaf
class TxtFile implements AbctractFile {
    private final String name;

    public TxtFile(String name) {
        super();
        this.name = name;
    }

    @Override
    public void killVirus() {
        System.out.println("文本文件：" + name + "，查毒中...");
    }
}

//leaf
class VoideFile implements AbctractFile {
    private final String name;

    public VoideFile(String name) {
        super();
        this.name = name;
    }

    @Override
    public void killVirus() {
        System.out.println("视频文件：" + name + "，查毒中...");
    }
}

//composite
class Folder implements AbctractFile {
    private final String name;
    //用来存放child node
    private final List<AbctractFile> list = new ArrayList<>();

    public Folder(String name) {
        super();
        this.name = name;
    }

    public void add(AbctractFile file) {
        list.add(file);
    }

    public void remove(AbctractFile file) {
        list.remove(file);
    }

    public AbctractFile getChild(int index) {
        return list.get(index);
    }

    @Override
    public void killVirus() {
        System.out.println("文件夹：" + name + "，查毒中...");
        //递归
        list.forEach(AbctractFile::killVirus);
    }
}