import java.util.Scanner;

class DictionaryBST {

    // Node of the Binary Search Tree
    static class Node {
        String word;
        String meaning;
        Node left;
        Node right;

        Node(String word, String meaning) {
            this.word = word;
            this.meaning = meaning;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    // Insert a new word
    // If word already exists, update its meaning
    public boolean insert(String word, String meaning) {
        if (root == null) {
            root = new Node(word, meaning);
            return true;
        }

        Node current = root;

        while (true) {
            int comparison = word.compareToIgnoreCase(current.word);

            if (comparison == 0) {
                current.meaning = meaning;
                return false; // Word already existed
            }

            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new Node(word, meaning);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node(word, meaning);
                    return true;
                }
                current = current.right;
            }
        }
    }

    // Search for a word
    public Node search(String word) {
        Node current = root;

        while (current != null) {
            int comparison = word.compareToIgnoreCase(current.word);

            if (comparison == 0) {
                return current;
            }

            if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    // Update meaning of an existing word
    public boolean updateMeaning(String word, String newMeaning) {
        Node node = search(word);

        if (node == null) {
            return false;
        }

        node.meaning = newMeaning;
        return true;
    }

    // Delete a word
    public boolean delete(String word) {
        if (search(word) == null) {
            return false;
        }

        root = deleteNode(root, word);
        return true;
    }

    private Node deleteNode(Node node, String word) {

        if (node == null) {
            return null;
        }

        int comparison = word.compareToIgnoreCase(node.word);

        if (comparison < 0) {
            node.left = deleteNode(node.left, word);
        } 
        else if (comparison > 0) {
            node.right = deleteNode(node.right, word);
        } 
        else {
            // Case 1: No child
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: Only right child
            if (node.left == null) {
                return node.right;
            }

            // Case 3: Only left child
            if (node.right == null) {
                return node.left;
            }

            // Case 4: Two children
            Node successor = findMinimum(node.right);

            node.word = successor.word;
            node.meaning = successor.meaning;

            node.right = deleteNode(node.right, successor.word);
        }

        return node;
    }

    // Find minimum node
    private Node findMinimum(Node node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    // Display all words in alphabetical order
    public void displayAllWords() {
        if (root == null) {
            System.out.println("Dictionary is empty.");
            return;
        }

        System.out.println("\n--- Words in Alphabetical Order ---");
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.println(node.word + " : " + node.meaning);
        inOrder(node.right);
    }

    // Display tree structure
    public void displayTree() {
        if (root == null) {
            System.out.println("Dictionary is empty.");
            return;
        }

        System.out.println("\n--- Tree Structure ---");
        printTree(root, "", true);
    }

    private void printTree(Node node, String prefix, boolean isLast) {

        if (node == null) {
            return;
        }

        System.out.println(prefix + (isLast ? "- " : "|- ") + node.word);

        if (node.left != null) {
            printTree(
                node.left,
                prefix + (isLast ? "    " : "|   "),
                node.right == null
            );
        }

        if (node.right != null) {
            printTree(
                node.right,
                prefix + (isLast ? "    " : "|   "),
                true
            );
        }
    }

    // Count total words
    public int countWords() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + countNodes(node.left) + countNodes(node.right);
    }
}


public class DictionaryBSTMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DictionaryBST dictionary = new DictionaryBST();

        // Initial dictionary data
        dictionary.insert(
            "Array",
            "A collection of elements identified by index."
        );

        dictionary.insert(
            "Tree",
            "A hierarchical data structure with nodes."
        );

        int choice;

        do {

            System.out.println("\n========= DICTIONARY BST =========");
            System.out.println("1. Insert Word");
            System.out.println("2. Search Word");
            System.out.println("3. Update Meaning");
            System.out.println("4. Delete Word");
            System.out.println("5. Display All Words");
            System.out.println("6. Display Tree Structure");
            System.out.println("7. Total Word Count");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number from 1 to 8.");
                scanner.next();
                System.out.print("Enter your choice: ");
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter word: ");
                    String word = scanner.nextLine().trim();

                    System.out.print("Enter meaning: ");
                    String meaning = scanner.nextLine().trim();

                    if (word.isEmpty() || meaning.isEmpty()) {
                        System.out.println("Word and meaning cannot be empty.");
                        break;
                    }

                    boolean inserted = dictionary.insert(word, meaning);

                    if (inserted) {
                        System.out.println("Word inserted successfully.");
                    } else {
                        System.out.println(
                            "Word already exists. Meaning updated."
                        );
                    }
                    break;

                case 2:
                    System.out.print("Enter word to search: ");
                    String searchWord = scanner.nextLine().trim();

                    DictionaryBST.Node result =
                        dictionary.search(searchWord);

                    if (result != null) {
                        System.out.println("Meaning: " + result.meaning);
                    } else {
                        System.out.println("Word not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter word to update: ");
                    String updateWord = scanner.nextLine().trim();

                    System.out.print("Enter new meaning: ");
                    String newMeaning = scanner.nextLine().trim();

                    if (dictionary.updateMeaning(updateWord, newMeaning)) {
                        System.out.println("Meaning updated successfully.");
                    } else {
                        System.out.println("Word not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter word to delete: ");
                    String deleteWord = scanner.nextLine().trim();

                    if (dictionary.delete(deleteWord)) {
                        System.out.println("Word deleted successfully.");
                    } else {
                        System.out.println("Word not found.");
                    }
                    break;

                case 5:
                    dictionary.displayAllWords();
                    break;

                case 6:
                    dictionary.displayTree();
                    break;

                case 7:
                    System.out.println(
                        "Total words: " + dictionary.countWords()
                    );
                    break;

                case 8:
                    System.out.println("Exiting... Thank you!");
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Please enter 1 to 8."
                    );
            }

        } while (choice != 8);

        scanner.close();
    }
}