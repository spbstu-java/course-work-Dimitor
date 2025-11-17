package lab03;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SortedDictionary implements Dictionary {
    private final Map<String, String> entries = new LinkedHashMap<>();

    @Override
    public void load(String filePath) throws FileReadException, InvalidFileFormatException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Путь к файлу не может быть пустым");
        }

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileReadException("Файл не найден: " + filePath);
        }
        if (!file.canRead()) {
            throw new FileReadException("Нет доступа: " + filePath);
        }

        List<Map.Entry<String, String>> tempEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                if (!line.contains(" | ")) {
                    throw new InvalidFileFormatException("Некорректный формат в строке " + lineNumber);
                }

                String[] parts = line.split(" \\| ", 2);
                if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                    throw new InvalidFileFormatException("Пустая часть в строке " + lineNumber);
                }

                tempEntries.add(new AbstractMap.SimpleEntry<>(
                    parts[0].trim().toLowerCase(),
                    parts[1].trim()
                ));
            }
        } catch (IOException e) {
            throw new FileReadException("Ошибка чтения: " + e.getMessage());
        }

        if (tempEntries.isEmpty()) {
            throw new InvalidFileFormatException("Словарь пуст");
        }

        tempEntries.sort((a, b) -> Integer.compare(b.getKey().length(), a.getKey().length()));

        entries.clear();
        tempEntries.forEach(entry -> entries.put(entry.getKey(), entry.getValue()));
    }

    @Override
    public String translate(String text) {
        if (text == null) return null;
        if (text.isEmpty()) return text;

        StringBuilder result = new StringBuilder();

        for (int pos = 0; pos < text.length(); ) {
            char ch = text.charAt(pos);

            if (!Character.isLetterOrDigit(ch)) {
                result.append(ch);
                pos++;
                continue;
            }

            String remainder = text.substring(pos).toLowerCase();
            String bestKey = findLongestMatch(remainder, pos, text.length());

            if (bestKey != null) {
                result.append(entries.get(bestKey));
                pos += bestKey.length();
            } else {
                int wordStart = pos;
                while (pos < text.length() && Character.isLetterOrDigit(text.charAt(pos))) {
                    pos++;
                }
                result.append(text, wordStart, pos);
            }
        }

        return result.toString();
    }

    private String findLongestMatch(String remainder, int pos, int textLength) {
        for (String key : entries.keySet()) {
            if (remainder.startsWith(key)) {
                int endPos = pos + key.length();
                if (endPos == textLength || !Character.isLetterOrDigit(remainder.charAt(key.length()))) {
                    return key;
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return entries.size();
    }
}