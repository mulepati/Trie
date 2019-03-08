import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Trie {
    private Tree trie = new Tree();

    public void printTrie() {
        trie.print();
    }

    public void addWords(List<String> words) {
        for (String word: words) {
            insert(word);
        }
    }

    public void insert(String word) {
        EntryNode current = trie.getRoot();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            EntryNode child = current.getChild(character);
            if (child == null) {
                child = new EntryNode(character, i == word.length() - 1);
                current.addChild(child);
            } else if(i == (word.length() - 1)) {
                child.setTerminal(true);
            }

            current = child;
        }
    }

    public void remove(String word) {
        EntryNode current = trie.getRoot();
        if(contains(word)) {
            for (int i = 0; i <= word.length() - 1; i++) {
                char character = word.charAt(i);
                current = current.getChild(character);
            }
            current.remove();
            current = trie.getRoot();
        }
        for (int x = 0; x <= word.length(); x++) {
            if (contains(word)) {
                for (int i = 0; i < x; i++) {
                    char character = word.charAt(i);
                    EntryNode child = current.getChild(character);
                    if (child != null) {
                        current = current.getChild(character);
                    }
                }
                if (current.childrenSize() < 2 && !current.isTerminal()) {
                    current.remove();
                }
            }
        }


    }


    public boolean contains(String potentialWord) {
        EntryNode current = trie.getRoot();
        for (int i = 0; i < potentialWord.length(); i++) {
            char character = potentialWord.charAt(i);

            EntryNode child = current.getChild(character);
            if (child == null) {
                return false;
            }
            current = child;
        }

        return current.isTerminal();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File("src/main/resources/dictionary.txt"));
        List<String> words = new ArrayList<>();

        while (scan.hasNext()) {
            words.addAll(Arrays.asList(scan.nextLine().split(" ")));
        }

        Trie trie = new Trie();
        trie.addWords(words);
        trie.printTrie();
        trie.remove("shorebird");
        trie.printTrie();
        System.out.println();
        System.out.println("This test should report false:");
        System.out.println("Contains 's': " + trie.contains("s"));
        System.out.println("Contains 'bye': " + trie.contains("bye"));
        System.out.println("Contains 'bird': " + trie.contains("bird"));
        System.out.println("Removes 'shorebird' : " + trie.contains("shorebird"));

        System.out.println();
        System.out.println("These tests should report true:");
        System.out.println("Contains 'she': " + trie.contains("she"));
        System.out.println("Contains 'sells': " + trie.contains("sells"));
        System.out.println("Contains 'sea': " + trie.contains("sea"));
        System.out.println("Contains 'shells': " + trie.contains("shells"));
        System.out.println("Contains 'by': " + trie.contains("by"));
        System.out.println("Contains 'the': " + trie.contains("the"));
        System.out.println("Contains 'shore': " + trie.contains("shore"));
        System.out.println("Contains 'shorebird': " + trie.contains("shorebird"));
    }
}
