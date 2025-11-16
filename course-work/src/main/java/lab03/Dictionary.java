package lab03;

public interface Dictionary {
    /**
     * Загружает словарь из файла.
     * @param filePath путь к файлу словаря
     * @throws FileReadException если файл не найден или недоступен для чтения
     * @throws InvalidFileFormatException если формат файла некорректен
     */
    void load(String filePath) throws FileReadException, InvalidFileFormatException;

    /**
     * Переводит переданный текст, используя загруженный словарь.
     * @param text текст для перевода
     * @return переведённый текст
     */
    String translate(String text);

    /**
     * Возвращает количество записей в словаре.
     * @return размер словаря
     */
    int size();
}