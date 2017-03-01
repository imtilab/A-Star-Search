
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mti
 */
public class Priority {
    //take queue<Integer> and array<Double>
    //sort the queue based on distance and return sorted sorted queue
    public static Queue setPriority(Queue<Integer> queue, double[] distance) {
        
        int queueSize = queue.size();
        //put vertices and respective distance in nodeArray
        Node[] nodeArray = new Node[queueSize];
        for (int i = 0; i < queueSize; i++) {
            int vertex = queue.poll();
            nodeArray[i] = new Node(vertex, distance[vertex]);
        }
        queue.clear();

        //sorting nodes based on distance of vertices
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = nodeArray.length - 1; j > i; j--) {
                if (nodeArray[j].verDist < nodeArray[j - 1].verDist) {
                    Node temp = nodeArray[j];
                    nodeArray[j] = nodeArray[j - 1];
                    nodeArray[j - 1] = temp;
                }
            }
        }
        
        //add sorted vertices back to the queue
        for (int i = 0; i < nodeArray.length; i++) {
            queue.add(nodeArray[i].ver);
        }

        return queue;
    }
}
//Node class
class Node {

    int ver;//vertices
    double verDist;//Distace of the verices

    public Node(int ver, double verDist) {
        this.ver = ver;
        this.verDist = verDist;
    }
}
