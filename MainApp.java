import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CaesarCipher cipher = new CaesarCipher();
        FileManager fileManager = new FileManager();
        Validator validator = new Validator();

        while (true) {
            showMenu();
            System.out.print("Выберите режим: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Программа завершена.");
                break;
            }

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Путь к файлу с текстом: ");
                        String inputFile = scanner.nextLine();
                        System.out.print("Путь к файлу для результата: ");
                        String outputFile = scanner.nextLine();
                        System.out.print("Ключ (сдвиг): ");
                        int key = scanner.nextInt();

                        if (!validator.isFileExists(inputFile)) {
                            System.out.println("Ошибка: файл не найден!");
                            break;
                        }

                        String text = fileManager.readFile(inputFile);
                        String encrypted = cipher.encrypt(text, key);
                        fileManager.writeFile(encrypted, outputFile);
                        System.out.println("Готово! Зашифрованный текст сохранён в " + outputFile);
                        break;

                    case 2:
                        System.out.print("Путь к зашифрованному файлу: ");
                        inputFile = scanner.nextLine();
                        System.out.print("Путь для сохранения: ");
                        outputFile = scanner.nextLine();
                        System.out.print("Ключ (сдвиг): ");
                        key = scanner.nextInt();

                        if (!validator.isFileExists(inputFile)) {
                            System.out.println("Ошибка: файл не найден!");
                            break;
                        }

                        text = fileManager.readFile(inputFile);
                        String decrypted = cipher.decrypt(text, key);
                        fileManager.writeFile(decrypted, outputFile);
                        System.out.println("Готово! Расшифрованный текст сохранён в " + outputFile);
                        break;

                    case 3:
                        System.out.print("Путь к зашифрованному файлу: ");
                        inputFile = scanner.nextLine();
                        System.out.print("Путь для сохранения: ");
                        outputFile = scanner.nextLine();
                        System.out.print("Путь к файлу-образцу (Enter если нет): ");
                        String sampleFile = scanner.nextLine();

                        if (!validator.isFileExists(inputFile)) {
                            System.out.println("Ошибка: файл не найден!");
                            break;
                        }

                        BruteForce bruteForce = new BruteForce();
                        String result = bruteForce.decryptByBruteForce(inputFile, sampleFile);
                        fileManager.writeFile(result, outputFile);
                        System.out.println("Готово! Результат сохранён в " + outputFile);
                        break;

                    case 4:
                        System.out.print("Путь к зашифрованному файлу: ");
                        inputFile = scanner.nextLine();
                        System.out.print("Путь для сохранения: ");
                        outputFile = scanner.nextLine();
                        System.out.print("Путь к файлу-образцу (Enter если нет): ");
                        sampleFile = scanner.nextLine();

                        if (!validator.isFileExists(inputFile)) {
                            System.out.println("Ошибка: файл не найден!");
                            break;
                        }

                        StatisticalAnalyzer analyzer = new StatisticalAnalyzer();
                        result = analyzer.decryptByStatisticalAnalysis(inputFile, sampleFile);
                        fileManager.writeFile(result, outputFile);
                        System.out.println("Готово! Результат сохранён в " + outputFile);
                        break;

                    default:
                        System.out.println("Неверный выбор!");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("+---------------- ШИФР ЦЕЗАРЯ ----------------+");
        System.out.println("|  1  - Зашифровать текст                       |");
        System.out.println("|  2  - Расшифровать текст с ключом              |");
        System.out.println("|  3  - Brute force (перебор)                     |");
        System.out.println("|  4  - Статистический анализ                     |");
        System.out.println("|  0  - Выход                                     |");
        System.out.println("+-----------------------------------------------+");
    }
}
