package skip_list;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Node {

    int amount;
    int data;
    Node next, previous;

    public Node() {
        amount = 1;
    }

    public Node(int a) {
        amount = a;
    }
}

class SkipList {

    Node head, tail, current;

    //HELPER METHOD
    public int coinFlipAmount() {
        int i = 1;
        while (Math.random() > .5) {
            i++;
        }
        return i;
    }

    //HELPER METHOD
    public Node searchNode(int i) {
        Node n = head;
        while (n != null) {
            if (n.data != i) {
                n = n.next;
            } else {
                return n;
            }
        }
        return null;
    }

    //HELPER METHOD
    public void order() {
        Node x = head;
        ArrayList<Integer> store = new ArrayList();
        while (x != null) {
            store.add(x.data);
            x = x.next;
        }
        x = head;
        Node temp = new Node();
        int i = 0;
        while (x != null && i < store.size()) {
            if (searchNode(store.get(i)).next != null) {
                if (searchNode(store.get(i)).data > searchNode(store.get(i)).next.data) {
                    temp.data = searchNode(store.get(i)).next.data;
                    searchNode(store.get(i)).next.data = searchNode(store.get(i)).data;
                    searchNode(store.get(i)).data = temp.data;
                }
            }
            i++;
            x = x.next;
        }
    }

    public void insert(int d) {
        if (search(d) == false) {
            Node newNode = new Node(coinFlipAmount());
            newNode.data = d;
            if (head == null) {
                head = newNode;
                tail = newNode;
                current = newNode;
            } else {
                current.next = newNode;
                newNode.previous = current;
                current = newNode;
                tail = newNode;
            }
        } else {
            searchNode(d).amount += 1;
        }
        order();
    }

    public void delete(int i) {
        searchNode(i).amount = 0;
        if (searchNode(i).previous != null) {
            searchNode(i).previous.next = searchNode(i).next;
        }
        if (searchNode(i).next != null) {
            searchNode(i).next.previous = searchNode(i).previous;
        }
    }

    public boolean search(int i) {
        Node n = head;
        boolean found = false;
        while (n != null) {
            if (n.data != i) {
                n = n.next;
            } else {
                found = true;
                break;
            }
        }
        return found;
    }

    public void print() {
        int max = 0;
        Node n = head;
        Node x = head;
        while (n != null) {
            if (n.amount > max) {
                max = n.amount;
            }
            n = n.next;
        }
        for (int i = max; i > 0; i--) {
            while (x != null) {
                if (x.amount >= i) {
                    System.out.print(x.data + " ");

                }
                x = x.next;
            }
            x = head;
            System.out.println("");
        }
    }
}

public class SkpLst {

    public static ArrayList<Integer> readFile(String fileName, String directory) {
        System.out.println("Reading file...\n");
        File file = new File(directory + "\\" + fileName + ".txt");
        ArrayList<Integer> intList = new ArrayList();
        try {
            System.out.println("Reading integers from file...\n");
            Scanner scan = new Scanner(file);
            while (scan.hasNextInt()) {
                intList.add(scan.nextInt());
            }
            return intList;
        } catch (FileNotFoundException ex) {
            System.out.println("READ FILE ERROR");
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanMain = new Scanner(System.in);
        System.out.println("Input File Name (without .txt extension):");
        String name = scanMain.nextLine();
        System.out.println("Input File Directory:\nWARNING: The output files will be printed to this directory.\nEXAMPLE: C:\\Users\\YourName\\TargetFolder");
        String dir = scanMain.nextLine();
        System.out.println("Beginning analysis.\n");
        ArrayList<Integer> list = readFile(name, dir);
        SkipList skip = new SkipList();
        System.out.println("Creating Skip List...");
        for (int i = 0; i < list.size(); i++) {
            skip.insert(list.get(i));
        }
        System.out.println("Printing list...\n");
        skip.print();
        System.out.println("\nDone.");
    }

}
