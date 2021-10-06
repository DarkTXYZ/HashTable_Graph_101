import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class HashGraph extends Graph {

    // Hashing Parameter
    int p;
    int x;

    public HashGraph(int cap, int p, int x) {
        // เนื่องจาก HashGraph สืบทอดมาจาก Graph จึงใช้ super เพื่อเรียก function ใน Graph
        super(cap);
        this.p = p;
        this.x = x;
    }

    // polyHash ตามที่ implement ไว้ใน HashTable
    public static int polyHash(String s, int p, int x) {
        int hash = 0;
        for (int i = s.length() - 1; i >= 0 ; --i)
            hash = (hash * x + (int) (s.charAt(i))) % p;
        return hash;
    }

    // getListIndex ตามที่ implement ไว้ใน HashTable
    // โดย array ของเราจะเป็น vertexList 
    public int getListIndex(String s) {
        int hash = polyHash(s, p, x);
        int hashIndex = hash % this.cap;
        for (int i = 0; i < this.cap; ++i) {
            int newInd = (hashIndex + (i + i * i) / 2) % this.cap;
            if (this.vertexList[newInd] == null || s.compareTo(vertexList[newInd].strKey) == 0)
                return newInd;
        }
        return -1;
    }

    public void addVertex(String key) {
        /*
         * หลักการ
         *  - ถ้า graph เต็ม (size == cap) ก็ return ทันที
         *  - Vertext ที่มี string key จะมี index == getListIndex(key) (hash string และ cardinality fix)
         *  - สร้าง Vertex ใส่ vertexList[index] 
         *  - สร้าง AdjencyList ของ Vertex นั้น ใส่ adjacencyList[index] 
         *  - เพิ่ม size
         */

        if (size == cap) {
            System.out.println("Vertex list is full. You need to recreate the Graph");
            return;
        }

        Vertex newVertex = new Vertex(key);
        LinkedList<Integer> newList = new LinkedList<Integer>();
        int index = getListIndex(key);
        this.vertexList[index] = newVertex;
        this.adjacencyList[index] = newList;
    }

    public void addEdge(String source, String destination) {
        /*
         * หลักการ
         *  - ทำการหา index ของ string ก่อน จึงค่อย check
         *      > u = getListIndex(source);
         *      > v = getListIndex(destination);
         *  - ถ้าไม่มี Vertex u หรือ v ก็ไม่สามารถเชื่อมได้
         *  - ถ้ามี ให้ check ว่ามันถูกเชื่อมกันหรือไม่ (isConnected(u,v))
         *  - ถ้าไม่ ก็ทำการเชื่อมกันด้วยเพิ่ม Vertex(Integer) เข้าไปใน adjacencyList ของแต่ละ Vertex
         *      > adjacencyList[u].add(v)
         *      > adjacencyList[v].add(u)
         */

        int u = getListIndex(source);
        int v = getListIndex(destination);
        // เนื่องจาก HashGraph สืบทอดมาจาก Graph จึงใช้ super เพื่อเรียก function ใน Graph
        super.addEdge(u, v);
    }

    public void BFS(Vertex s) {
        /*
         * หลักการ
         *  - ตาม pseudocode ใน slide
         *  - set dist ของ Vertex ทุกตัวเป็น INF ก่อน
         *  - ให้ Vertex เริ่มต้น(s) dist = 0 แล้ว นำใส่เข้าไปใน queue
         *  - ถ้า queue ไม่ empty 
         *      > pop Vertex ตัวหน้าสุด(u)
         *      > ดู Vertex ทุกตัวที่สามารถไปได้ จาก u (v)
         *          > ถ้า v ยังไม่ visited(v.dist == INF) 
         *              > ก็ set ค่า v.dist = u.dist + 1 
         *              > v.prev = u ไว้สำหรับการหา shortestPath
         *              > แล้วนำ v เข้า queue
         */

        // set dist เป็น INF
        for (int i = 0; i < vertexList.length; ++i)
            if (vertexList[i] != null)
                vertexList[i].dist = Integer.MAX_VALUE;
        // set dist[s] = 0 และใส่เข้า queue
        s.dist = 0;
        Queue<Vertex> q = new LinkedList<Vertex>();
        q.add(s);
        // ถ้า queue ยังมีสมาชิกอยู่
        while (!q.isEmpty()) {
            Vertex u = q.remove();
            // hash หา index
            int ui = getListIndex(u.strKey);
            for (int i = 0; i < adjacencyList[ui].size(); ++i) {
                int vi = adjacencyList[ui].get(i);
                Vertex v = vertexList[vi];
                if (v.dist == Integer.MAX_VALUE) {
                    q.add(v);
                    v.dist = u.dist + 1;
                    v.prev = u;
                }
            }
        }
    }

    public Stack<Vertex> getShortestPathList(Vertex S, Vertex U) {
        /*
         * Vertex S - Source
         * Vertex U - Destination
         * 
         * หลักการ
         *  - สร้าง Stack ไว้เก็บ Vertex
         *  - เริ่มจาก Vertex current = Vertex U 
         *  - ให้วน loop ไปเรื่อยๆ จนกว่า current == S
         *      > push Vertex เข้า stack
         *      > ไล่ Vertex กลับไปยัง Vertex ก่อนหน้า (current = current.prev)
         */
        
        Stack<Vertex> s = new Stack<Vertex>();

        Vertex current = U;

        while(true){
            s.push(current);
            // ถ้าอยูที่จุดเริ่มต้นแล้ว ก็ break
            if(current == S)
                break;
            current = current.prev;
        }

        return s; 
    }

    public void printShortestPath(String s_str, String u_str) {
        /*
         * หลักการ
         *  - hash string s_str กับ u_str ก่อน และหา Vertex เริ่มต้น กับ Vertex ปลายทาง
         *  - BFS จากจุดเริ่มต้น (BFS(u)) เพื่อสร้าง shortestPath
         *  - เรียก getShortestPathList() เพื่อเอา shortestPath ที่ได้
         *  - pop stack ที่ได้ จนกว่าจะ empty
         */

        // hash string
        int ui = getListIndex(s_str);
        int vi = getListIndex(u_str);
        // เอา Vertex
        Vertex u = vertexList[ui];
        Vertex v = vertexList[vi];
        // BFS
        BFS(u);
        // เอา shortestPath
        Stack<Vertex> shortestPathList = getShortestPathList(u, v);
        // แสดง Path
        while (!shortestPathList.isEmpty()) {
            System.out.print(shortestPathList.pop().strKey + " -> ");
        }
        System.out.println();

    }

    public void showVertexList() {
        for (int i = 0; i < vertexList.length; i++) {
            if (vertexList[i] != null)
                System.out.println("vertexList[" + i + "] contains " + vertexList[i].strKey);
            else
                System.out.println("vertexList[" + i + "] null");
        }
    }

    public static HashGraph constructGraph() {

        int p = 101111;
        int x = 101;
        HashGraph graph = new HashGraph(32, p, x);

        String[] cities = new String[] { "Dublin", "Edinburgh", "Manchester", "Copenhagen", "Lisbon", "London",
                "Berlin", "Prague", "Madrid", "Paris", "Vienna", "Budapest", "Geneva", "Milan", "Zurich", "Rome" };
        for (int i = 0; i < 16; i++) {
            graph.addVertex(cities[i]);
        }

        return graph;
    }

    public static HashGraph connectEdges(HashGraph graph) {
        graph.addEdge("Dublin", "Edinburgh");
        graph.addEdge("Dublin", "London");
        graph.addEdge("Dublin", "Lisbon");
        graph.addEdge("Edinburgh", "Manchester");
        graph.addEdge("Manchester", "London");
        graph.addEdge("Manchester", "Copenhagen");
        graph.addEdge("Copenhagen", "Berlin");
        graph.addEdge("Lisbon", "Madrid");
        graph.addEdge("London", "Paris");
        graph.addEdge("Berlin", "Prague");
        graph.addEdge("Berlin", "Vienna");
        graph.addEdge("Berlin", "Paris");
        graph.addEdge("Prague", "Zurich");
        graph.addEdge("Madrid", "Paris");
        graph.addEdge("Madrid", "Milan");
        graph.addEdge("Madrid", "Geneva");
        graph.addEdge("Vienna", "Zurich");
        graph.addEdge("Budapest", "Rome");
        graph.addEdge("Milan", "Zurich");
        graph.addEdge("Zurich", "Rome");
        return graph;
    }
}
