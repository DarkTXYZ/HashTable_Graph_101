public class HashTable {
    String[] arr;
    int size;
    int capacity;

    // Hashing parameters
    int p;
    int x;

    public HashTable(int cap, int p, int x) {
        arr = new String[cap];
        this.capacity = cap;
        this.p = p;
        this.x = x;
    }

    public void addString(String s) {
        /*
         * หลักการ 
         *  - นำ string มาเข้า getIndex() เพื่อที่จะได้รู้ว่า จะเพิ่ม string นี้ตรงช่องไหนของ array
         *  - และทำการ เพิ่ม size 
         */
        size++;
        arr[getIndex(s)] = s;

    }

    public int getIndex(String s) {
        /*
         * หลักการ 
         *  - นำ string มา hash ก่อน (polyHash())
         *  - จากนั้น นำค่า hash ที่ได้มา Cardinality Fix (hash % capacity) ได้ hashIndex
         *  - ทำการ Quadratic probing หา index 
         *      > จะวน loop จาก k = 0 ถึง k = capacity - 1
         *      > ในแต่ละ loop จะทำการ set ค่า hashIndex ให้ hashIndex = (hashIndex + k) % capacity (ตามที่เรียนในห้อง)
         *      > หากเจอช่องที่ว่างแล้ว ก็ return hashIndex ตัวนั้นเลย (หรือ return เมื่อ string s == arr[hashIndex])
         */
        int hash = polyHash(s, p, x);
        int hashIndex = hash % capacity;

        for (int i = 0; i < capacity; ++i) {
            hashIndex = (hashIndex + i) % capacity;
            if (arr[hashIndex] == null || s.compareTo(arr[hashIndex]) == 0) {
                return hashIndex;
            }
        }
        return -1;
    }

    public static int polyHash(String s, int p, int x) {
        /*
         * หลักการ 
         *  - เป็นการ String hashing รูปแบบหนึ่ง
         *  - ตาม pseudocode ในสไลด์ แต่ในที่นี้ เราจะวิ่งจาก 0 ไป |S| - 1    
         */
        int hash = 0;
        for (int i = 0; i <= s.length() - 1; ++i)
            hash = (hash * x + (int) (s.charAt(i))) % p;
        return hash;
    }

    public static void test1() {
        int p = 1100101; 
        int x = 1001; 
        HashTable table = new HashTable(16, p, x);
        String[] names = { "a", "b", "c", "A", "B", "BA", "BBA", "aaa", "aaaaa", "0", "1", "11", "111",
                "abcdABCD01234" };
        for (int i = 0; i < names.length; i++)
            System.out.println("HashCode of '" + names[i] + "' is " + HashTable.polyHash(names[i], p, x));
    }

    public static void test2() {
        int p = 1100101;
        int x = 1001;
        HashTable table = new HashTable(16, p, x);
        String[] names = { "a", "b", "c", "A", "B", "BA", "BBA", "aaa", "aaaaa", "0", "1", "11", "111",
                "abcdABCD01234" };

        for (int i = 0; i < names.length; i++) {
            table.addString(names[i]);
            System.out.println("Index of '" + names[i] + "' is " + table.getIndex(names[i]));
        }
    }

    public static void test3() {
        int p = 1100101;
        int x = 101;
        HashTable table = new HashTable(16, p, x);
        String[] names = { "Abraham", "Andrew", "Benjamin", "Claudia", "Gabriel", "Esther", "Martha", "Rebecca",
                "Moses", "Timothy" };
        for (int i = 0; i < names.length; i++)
            System.out.println("HashCode of " + names[i] + " is " + HashTable.polyHash(names[i], p, x));

        for (int i = 0; i < names.length; i++) {
            table.addString(names[i]);
            System.out.println("Index of " + names[i] + " is " + table.getIndex(names[i]));
        }
    }
}