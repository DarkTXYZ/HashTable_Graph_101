import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class Graph {

    Vertex[] vertexList; // Vertex List
    LinkedList<Integer>[] adjacencyList; // Adjacency List

    int cap; // capacity
    int size; // size
    int cc; // component count

    public Graph(int cap) {
        this.cap = cap;
        this.size = 0;
        this.cc = 0;

        vertexList = new Vertex[cap];
        adjacencyList = new LinkedList[cap];
    }

    public void addVertex(int key) {
        /*
         * หลักการ
         *  - ถ้า graph เต็ม (size == cap) ก็ return ทันที
         *  - สร้าง Vertex ใส่ vertexList[key]
         *  - สร้าง AdjencyList ของ Vertex นั้น ใส่ adjacencyList[key]
         *  - เพิ่ม size
         */
        if (size == cap) {
            System.out.println("Vertex list is full. You need to recreate the Graph");
            return;
        }
        Vertex newVertex = new Vertex(key);
        LinkedList<Integer> newList = new LinkedList<Integer>();
        vertexList[key] = newVertex;
        adjacencyList[key] = newList;
        size++;
    }

    public void addEdge(int u, int v) {
        /*
         * หลักการ
         *  - ถ้าไม่มี Vertex u หรือ v ก็ไม่สามารถเชื่อมได้
         *  - ถ้ามี ให้ check ว่ามันถูกเชื่อมกันหรือไม่ (isConnected(u,v))
         *  - ถ้าไม่ ก็ทำการเชื่อมกันด้วยเพิ่ม Vertex(Integer) เข้าไปใน adjacencyList ของแต่ละ Vertex
         *      > adjacencyList[u].add(v)
         *      > adjacencyList[v].add(u)
         */
        if (vertexList[u] == null) {
            System.out.println("Source node does not exist");
            return;
        }
        if (vertexList[v] == null) {
            System.out.println("Destination node does not exist");
            return;
        }
        if (!isConnected(u, v)) {
            adjacencyList[u].add(v);
            adjacencyList[v].add(u);
        } else {
            System.out.println("There is already an edge connecting vertex " + u + " and vertex " + v);
        }
    }

    public boolean isConnected(int u, int v) {
        /*
         * หลักการ 
         *  - check ว่า มี v ใน adjacencyList[u] อยู่หรือไม่ หรือ มี u ใน adjacencyList[v] อยู่หรือไม่
         *  - เนื่องจากเราเชื่อมทั้ง 2 ทางของ Vertex เราจึง check แค่ทางเดียวก็เพียงพอแล้ว
         */
        if (adjacencyList[u].contains(v) /* or adjacencyList[v].contains(u) */)
            return true;
        return false;
    }

    public void showAdjacentVertices(int u) {
        /*
         * หลักการ 
         *  - ไล่สมาชิกทุกตัว ใน adjacencyList[u]
         */
        Vertex v = vertexList[u];
        System.out.print("Vertex " + v.strKey + " connected to the following vertices: ");

        LinkedList<Integer> list = adjacencyList[u];
        for (int vertex_index : list) {
            System.out.print(vertexList[vertex_index].strKey + ", ");
        }
        System.out.println();
    }

    public void Explore(Vertex u) {
        /*
         * หลักการ
         *  - ตาม pseudocode ในสไลด์
         *  - ให้ Vertex u เป็น Vertex ที่กำลังอยู่
         *  - set ค่า ccNum ของ Vertex u = cc
         *  - ไล่ Vertex ทุกตัวที่ไปได้จาก Vertex u (adjacencyList[u.intKey])
         *  - หาก Vertex แต่ละตัวนั้น(vv) ยังไม่ถูก visited ก็ให้ Explore(vv)
         *  - recusrsive ไปเรื่อยๆ จนกว่า ทุก Vertex ใน component เดียวกันถูก visited จนหมด
         */
        u.visited = true;
        u.ccNum = cc;
        System.out.print(u.strKey + "/" + u.ccNum + " -> ");
        // Vertex ทุกตัวที่ไปได้จาก Vertex u
        LinkedList<Integer> v = adjacencyList[u.intKey];

        for (int i = 0; i < v.size(); ++i) {
            // check Vertex แต่ละตัว
            Vertex vv = vertexList[v.get(i)];
            if (vv != null && !vv.visited)
                Explore(vv);
        }
    }

    public void DFS() {
        /*
         * หลักการ
         *  - ตาม pseudocode ในสไลด์
         *  - set cc = 1 เพื่อเริ่มนับจำนวน component
         *  - ไล่ Vertex ทุกตัวใน graph
         *      > ถ้า Vertex นั้นไม่ถูก visited ก็ Explore(Vertex) นั้นเลย
         *      > cc เพิ่มขึ้น 1 (component ของ graph เพิ่ม)
         */
        cc = 1;
        for (int i = 0; i < vertexList.length; ++i) {
            if (vertexList[i] == null)
                continue;
            if (!vertexList[i].visited) {
                Explore(vertexList[i]);
                cc++;
            }
        }
        System.out.println();
    }

    public static Graph constructGraph1() {
        Graph graph = new Graph(32);
        for (int i = 0; i < 16; i++)
            graph.addVertex(i);
        graph.addEdge(0, 1);
        graph.addEdge(0, 5);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 5);
        graph.addEdge(2, 3);
        graph.addEdge(3, 6);
        graph.addEdge(4, 8);
        graph.addEdge(5, 9);
        graph.addEdge(6, 7);
        graph.addEdge(6, 10);
        graph.addEdge(6, 9);
        graph.addEdge(7, 14);
        graph.addEdge(8, 9);
        graph.addEdge(8, 13);
        graph.addEdge(8, 12);
        graph.addEdge(10, 14);
        graph.addEdge(11, 15);
        graph.addEdge(13, 14);
        graph.addEdge(14, 15);

        return graph;
    }

    public static Graph constructGraph2() {
        Graph graph = new Graph(32);
        for (int i = 0; i < 16; i++)
            graph.addVertex(i);
        graph.addEdge(0, 1);
        graph.addEdge(0, 5);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(2, 5);
        graph.addEdge(2, 3);
        graph.addEdge(3, 6);
        graph.addEdge(5, 9);
        graph.addEdge(6, 9);
        graph.addEdge(7, 14);
        graph.addEdge(8, 13);
        graph.addEdge(8, 12);
        graph.addEdge(10, 14);
        graph.addEdge(11, 15);
        graph.addEdge(14, 15);
        return graph;
    }
}
