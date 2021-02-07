package composite.example;

public class App {

    public static void main(String[] args) {
        AbctractFile f2,f3,f4,f5,f6;
        Folder f1 = new Folder("下载");
        f2 = new ImageFile("abc.jpg");
        f3 = new TxtFile("bvc.txt");
        f4 = new VoideFile("cjk.mp4");
        f1.add(f2);
        f1.add(f3);
        f1.add(f4);
        f5 = new ImageFile("999.jpg");
        f6 = new TxtFile("qwe.txt");
        Folder f7 = new Folder("360download");
        f1.add(f7);
        f7.add(f5);
        f7.add(f6);

        f1.killVirus();
    }
}
