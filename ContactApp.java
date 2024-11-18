import java.util.Scanner;

class TreeNode {
    String contactName;
    String phoneNumber; 
    TreeNode left, right;

    public TreeNode(String contactName, String phoneNumber) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.left = this.right = null;
    }
}

class BinaryTree {
    TreeNode root;

    public BinaryTree() {
        root = null;
    }

    public void insert(String contactName, String phoneNumber) {
        root = insertRecursive(root, contactName, phoneNumber);
    }

    private TreeNode insertRecursive(TreeNode current, String contactName, String phoneNumber) {
        if (current == null) {
            return new TreeNode(contactName, phoneNumber);
        }

        if (contactName.compareTo(current.contactName) < 0) {
            current.left = insertRecursive(current.left, contactName, phoneNumber);
        } else if (contactName.compareTo(current.contactName) > 0) {
            current.right = insertRecursive(current.right, contactName, phoneNumber);
        }

        return current;
    }

    public boolean search(String contactName) {
        return searchRecursive(root, contactName);
    }

    private boolean searchRecursive(TreeNode current, String contactName) {
        if (current == null) {
            return false;
        }

        if (contactName.equals(current.contactName)) {
            return true;
        }

        if (contactName.compareTo(current.contactName) < 0) {
            return searchRecursive(current.left, contactName);
        } else {
            return searchRecursive(current.right, contactName);
        }
    }

    public void delete(String contactName) {
        root = deleteRecursive(root, contactName);
    }

    private TreeNode deleteRecursive(TreeNode current, String contactName) {
        if (current == null) {
            return null;
        }

        if (contactName.equals(current.contactName)) {
            if (current.left == null && current.right == null) {
                return null;
            }

            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            String smallestValue = findSmallestValue(current.right);
            current.contactName = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;
        }

        if (contactName.compareTo(current.contactName) < 0) {
            current.left = deleteRecursive(current.left, contactName);
        } else {
            current.right = deleteRecursive(current.right, contactName);
        }

        return current;
    }

    private String findSmallestValue(TreeNode root) {
        return root.left == null ? root.contactName : findSmallestValue(root.left);
    }

    public void display(TreeNode node) {
        if (node != null) {
            display(node.left);
            System.out.println("Name: " + node.contactName + ", Phone: " + node.phoneNumber);
            display(node.right);
        }
    }
}

public class ContactApp {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Contact Manager Program!");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("\nInvalid input. The input is not a number.");
                scanner.next();
                continue;
            }

            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("\nEnter contact name: ");
                    String contactName = scanner.nextLine();
                    System.out.print("Enter contact phone number: ");
                    String phoneNumber = scanner.nextLine();
                    tree.insert(contactName, phoneNumber);
                    System.out.println("\nContact added!");
                    break;

                case 2:
                    System.out.print("\nEnter contact name to search: ");
                    String searchKey = scanner.nextLine();
                    boolean found = tree.search(searchKey);
                    if (found) {
                        System.out.println("\nContact found!");
                    } else {
                        System.out.println("\nContact not found.");
                    }
                    break;

                case 3:
                    System.out.print("\nEnter contact name to delete: ");
                    String deleteKey = scanner.nextLine();
                    tree.delete(deleteKey);
                    System.out.println("\nContact deleted (if it existed).");
                    break;

                case 4:
                    System.out.println("\nDisplaying All Contacts (Sorted by Name):");
                    tree.display(tree.root);
                    break;

                case 5:
                    System.out.println("\nExiting. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
}
