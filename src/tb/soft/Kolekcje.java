package tb.soft;

import java.util.*;

public class Kolekcje {
    private Integer number;
    private final HashSet<Person> hashSet;
    private final TreeSet<Person> treeSet;
    private final ArrayList<Person> arrayList;
    private final LinkedList<Person> linkedList;
    private final HashMap<Integer,Person> hashMap;
    private final TreeMap<Integer,Person> treeMap;

    public Kolekcje(){
        hashSet = new HashSet<>();
        treeSet = new TreeSet<>();
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        hashMap = new HashMap<>();
        treeMap = new TreeMap<>();
        number = 0;
    }

    //Dodawanie elementów
    public void addElement(Person p){
        hashSet.add(p);
        treeSet.add(p);
        arrayList.add(p);
        linkedList.add(p);
        hashMap.put(number,p);
        treeMap.put(number,p);
        number++;
    }

    //Wypisywanie zawartości
    public String printContents(){
        StringBuilder result = new StringBuilder();
        Iterator[] iterator = new Iterator[4];
        iterator[0] = hashSet.iterator();
        iterator[1] = treeSet.iterator();
        iterator[2] = arrayList.iterator();
        iterator[3] = linkedList.iterator();
        for(int j=0; j<6; j++) {
            switch (j) {
                case 0 -> result.append("Zawartość HashSet:\n");
                case 1 -> result.append("Zawartość TreeSet:\n");
                case 2 -> result.append("Zawartość ArrayList:\n");
                case 3 -> result.append("Zawartość LinkedList:\n");
                case 4 -> result.append("Zawartość HashMap:\n");
                case 5 -> result.append("Zawartość TreeMap:\n");
            }
            for (int i = 0; i < number && j< 4; i++) {
                if(iterator[j].hasNext()) result.append(iterator[j].next().toString()).append("\n");
            }
            for (Integer i = 0; j== 4 && i < number; i++) {
                if(hashMap.get(i) != null) result.append(hashMap.get(i).toString()).append("\n");
            }
            for (Integer i = 0; j== 5 && i < number ; i++) {
                if(treeMap.get(i) != null) result.append(treeMap.get(i).toString()).append("\n");
            }
        }
        return result.toString();
    }
}
