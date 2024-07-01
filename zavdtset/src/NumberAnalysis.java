import java.io.*;
import java.util.*;

public class NumberAnalysis {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();

        // Вказуємо шлях до файлу в папці src
        String filePath = "src/10m.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    numbers.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (numbers.isEmpty()) {
            System.out.println("No numbers found in the file.");
            return;
        }

        int[] numbersArray = numbers.stream().mapToInt(Integer::intValue).toArray();

        // Сортування масиву для знаходження медіани
        quickSort(numbersArray, 0, numbersArray.length - 1);

        int max = numbersArray[numbersArray.length - 1];
        int min = numbersArray[0];
        double median;
        if (numbersArray.length % 2 == 0) {
            median = (numbersArray[numbersArray.length / 2 - 1] + numbersArray[numbersArray.length / 2]) / 2.0;
        } else {
            median = numbersArray[numbersArray.length / 2];
        }
        double average = Arrays.stream(numbersArray).average().orElse(0);

        System.out.println("Максимальне число: " + max);
        System.out.println("Мінімальне число: " + min);
        System.out.println("Медіана: " + median);
        System.out.println("Середнє арифметичне: " + average);

        // Пошук найдовших послідовностей у початковому порядку
        List<Integer> longestIncreasingSequence = findLongestSequence(numbers, true);
        List<Integer> longestDecreasingSequence = findLongestSequence(numbers, false);

        System.out.println("Найбільша послідовність, яка збільшується: " + longestIncreasingSequence);
        System.out.println("Найбільша послідовність, яка зменшується: " + longestDecreasingSequence);
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    private static List<Integer> findLongestSequence(List<Integer> numbers, boolean increasing) {
        List<Integer> longestSequence = new ArrayList<>();
        List<Integer> currentSequence = new ArrayList<>();

        for (int i = 0; i < numbers.size(); i++) {
            if (currentSequence.isEmpty() ||
                    (increasing && numbers.get(i) > currentSequence.get(currentSequence.size() - 1)) ||
                    (!increasing && numbers.get(i) < currentSequence.get(currentSequence.size() - 1))) {
                currentSequence.add(numbers.get(i));
            } else {
                if (currentSequence.size() > longestSequence.size()) {
                    longestSequence = new ArrayList<>(currentSequence);
                }
                currentSequence.clear();
                currentSequence.add(numbers.get(i));
            }
        }

        if (currentSequence.size() > longestSequence.size()) {
            longestSequence = new ArrayList<>(currentSequence);
        }

        return longestSequence;
    }
}
