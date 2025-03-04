import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создание структуры файловой системы
        Directory root = new Directory("Корневая папка");

        // Папка "Документы"
        Directory documents = new Directory("Документы");
        documents.add(new File("lab2.txt", 100));
        documents.add(new File("presentation.pdf", 200));

        // Папка "Работа" внутри "Документы"
        Directory work = new Directory("Работа");
        work.add(new File("report.docx", 150));
        work.add(new File("presentation.pptx", 300));
        documents.add(work);

        root.add(documents);

        // Папка "Музыка"
        Directory music = new Directory("Музыка");
        music.add(new File("song1.mp3", 5000));

        root.add(music);

        // Вывод структуры
        System.out.println(root.toString());
        System.out.println("Общий размер корневой папки: " + root.getSize() + " КБ");

        // Добавление нового файла
        documents.add(new File("note.txt", 50));
        System.out.println("\nПосле добавления note.txt:");
        System.out.println(root.toString());
        System.out.println("Общий размер корневой папки: " + root.getSize() + " КБ");

        // Удаление файла
        documents.remove(documents.getChild(0)); // Удаляем document1.txt
        System.out.println("\nПосле удаления document1.txt:");
        System.out.println(root.toString());
        System.out.println("Общий размер корневой папки: " + root.getSize() + " КБ");
    }
}

// FILE SYSTEM INTERFACE
interface FileSystemComponent {
    String getName();
    long getSize();
    void add(FileSystemComponent component);
    void remove(FileSystemComponent component);
    FileSystemComponent getChild(int index);
}

// LEAF COMPONENT
class File implements FileSystemComponent {
    private String name;
    private long size;

    public File(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public void add(FileSystemComponent component) {
        throw new UnsupportedOperationException("Файл не может содержать другие элементы");
    }

    @Override
    public void remove(FileSystemComponent component) {
        throw new UnsupportedOperationException("Файл не может содержать другие элементы");
    }

    @Override
    public FileSystemComponent getChild(int index) {
        throw new UnsupportedOperationException("Файл не имеет дочерних элементов");
    }

    @Override
    public String toString() {
        return "Файл: " + name + " (размер: " + size + " КБ)";
    }
}

// COMPOSITE COMPONENT
class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        long totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }

    @Override
    public void add(FileSystemComponent component) {
        components.add(component);
    }

    @Override
    public void remove(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public FileSystemComponent getChild(int index) {
        return components.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Папка: " + name + " (общий размер: " + getSize() + " КБ)\n");
        for (FileSystemComponent component : components) {
            sb.append("  ").append(component.toString()).append("\n");
        }
        return sb.toString();
    }
}