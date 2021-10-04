import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class JavaCollectionTest {

    public static void linkedlistTest(){
        /*
         * หลักการ
         *  - สร้าง LinkedList
         *              LinkedList<Type> varName = new LinkedList<Type>();
         *    โดย   - varName คือ ชื่อตัวแปร
         *          - Type คือ ชนิดของ object เช่น Integer , String , Stack , ...
         *  - เพิ่ม object : add()
         *  - check ว่ามี object อยู่ใน LinkedList หรือไม่ : contains()
         *  - เข้าถึง object ตัวที่ i ใน LinkedList : get(i)
         */
        
        // สร้าง LinkedList
        LinkedList<Integer> list = new LinkedList<Integer>();
        
        int x;
        for (int i=1; i<10; i+= 2){
            x = i;
            // เพิ่ม object เข้า LinkedList
            list.add(x);
        }
            
        // check ว่ามี object ที่เราต้องการหาใน LinkedList หรือไม่ 
        boolean check = list.contains(5); 
        System.out.println("5 is in the list = "+check);
        
        check = list.contains(6); 
        System.out.println("6 is in the list = "+check);
        
        for (int i = 0; i < list.size(); i++) {
            // เข้าถึง object ตัวที่ i ใน LinkedList
            System.out.print(list.get(i) + ", ");
        }
        System.out.println();
    }
        
    public static void queueTest(){
        /*
         * หลักการ
         *  - สร้าง Queue
         *              Queue<Type> varName = new LinkedList<Type>();
         *    โดย   - varName คือ ชื่อตัวแปร
         *          - Type คือ ชนิดของ object เช่น Integer , String , Stack , ...
         *  - เพิ่ม object : add()
         *  - ดูตัวหน้าสุดของ Queue (Q.front()) : peek()
         *  - เอาตัวหน้าสุดออก Queue (Q.pop()) : remove()
         */

        // สร้าง Queue
        Queue<String> q = new LinkedList<String>();
        String[] names = {"Abraham","Andrew","Benjamin","Claudia","Gabriel"};
        for (int i=0; i<names.length; i++){
            // เพิ่ม object เข้า Queue
            q.add(names[i]);
        }

        System.out.println("Top = "+ q.peek());        // Q.front()
        System.out.println("1st Pop = " + q.remove());   // Q.pop()
        System.out.println("2nd Pop = " + q.remove());   // Q.pop()
        System.out.println("3rd Pop = " + q.remove());   // Q.pop()
    }
        
    public static void stackTest(){
        /*
         * หลักการ
         *  - สร้าง Queue
         *              Stack<Type> varName = new Stack<Type>();
         *    โดย   - varName คือ ชื่อตัวแปร
         *          - Type คือ ชนิดของ object เช่น Integer , String , Stack , ...
         *  - เพิ่ม object : push()
         *  - ดูตัวบนสุดของ Stack (S.top()) : peek()
         *  - เอาตัวบนสุดออก Stack (S.pop()) : pop()
         */
        
        Stack<String> s = new Stack<String>();
        String[] names = {"Abraham","Andrew","Benjamin","Claudia","Gabriel"};
        
        for (int i=0; i<names.length; i++){
            // เพิ่ม object เข้า Stack
            s.push(names[i]);
        }
            
        System.out.println("Top = "+ s.peek());        // S.top()
        System.out.println("1st Pop = " + s.pop());   // S.pop()
        System.out.println("2nd Pop = " + s.pop());   // S.pop()
        System.out.println("3rd Pop = " + s.pop());   // S.pop()
    }
        
        
    public static void arrayOfListTest(){
        /*
         * หลักการ
         *  - การสร้าง LinkedList array
         *      > สร้าง array ของ LinkedList ขึ้นมาก่อน แล้วไปไล่สร้าง LinkedList ในละช่องของ array
         *  - แสดงทุกสมาชิกใน LinkedList array
         *      > ไล่ทุกช่องของ array ในแต่ละช่อง จะแสดงสมาชิกแต่ละตัวใน LinkedList
         *  - ตรวจสอบว่า มีสมาชิกใน LinkedList array มั้ย
         *      > ไล่ทุกช่องของ array ในแต่ละช่อง ใช้ contains()
         */

        // สร้าง LinkedList array
        LinkedList<String>[] arr = new LinkedList[5];
        String[] names = {"Abraham","Andrew","Benjamin","Claudia","Gabriel"};
        for (int i=0; i<5; i++){
            // สร้าง LinkedList ในแต่ละช่อง
            LinkedList<String> list = new LinkedList<String>();
            // เพิ่ม object
            for (int j=0; j<=i; j++){
                list.add(names[j]);
            }
            // เพิ่ม LinkedList เข้าไปในช่อง
            arr[i] = list;
        }
        
        // ไล่ทุกช่องของ array
        for (int i=0; i<arr.length; i++){
            LinkedList<String> list = arr[i];
            System.out.print("arr["+i+"] = ");
            // ไล่ทุกสมาชิกของ LinkedList ตัวที่ i
            for(int j=0 ; j < list.size() ; ++j){
                System.out.print(list.get(j) + ", ");
            }
            System.out.println("");
        }
        
        // ไล่ทุกช่องของ array
        for (int i=0; i<arr.length; i++){
            LinkedList<String> list = arr[i];
            // check ว่ามีสมาชิกใน list มั้ย
            boolean check = list.contains("Benjamin");
            System.out.println("Benjamin is contained in arr["+i+"]? = " + check);
        }
    }
}