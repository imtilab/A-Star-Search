
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mti
 */
public class Map {
    Queue<Integer> queue = new LinkedList<>();

    double[][] graph_matrix;
    double distance[];
    int parent[];
    int source, destination;
//------------------------------------------
    int cities;//number of cities
    int roads;//number of roads
    String city_list[];//city name list
    String start;//start city name
    String end;//destination city name

    double[] huristics;//Straight-Line-Distance (huristics)
    int openList[];
    int closedList[];
    int cityValueToAssigned;//city value assign counter

    //set and initialize the full graph_matrix
    public void setGraph() throws Exception {
        FileReader fileReader = new FileReader("sample2.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String stringLine = bufferedReader.readLine();

        String lineArray[] = stringLine.split(" ");
        cities = Integer.parseInt(lineArray[0]);
        roads = Integer.parseInt(lineArray[1]);

        start = bufferedReader.readLine();
        end = bufferedReader.readLine();

        //initializations
        graph_matrix = new double[cities+1][cities+1];
        city_list = new String[cities+1];
        openList = new int[cities+1];
        closedList = new int[cities+1];
        huristics = new double[cities+1];
        
        distance = new double[cities + 1];
        parent = new int[cities + 1];

        //input all pairs of city names and assign weights
        lineArray = new String[3];
        cityValueToAssigned = 1;
        for (int i = 0; i < roads; i++) {
            stringLine = bufferedReader.readLine();
            lineArray = stringLine.split(" ");
            //System.out.println(lineArray[0]+" "+lineArray[1]+" "+lineArray[2]);

            int row = cityValueToAssigned;
            int cityValue = checkCityValueAssigned(lineArray[0]);

            if (cityValue < 0) {//cityValue not assigned yet
                city_list[cityValueToAssigned] = lineArray[0];
                cityValueToAssigned++;
            } else {//assigned
                row = cityValue;
            }

            int col = cityValueToAssigned;
            cityValue = checkCityValueAssigned(lineArray[1]);

            if (cityValue < 0) {
                city_list[cityValueToAssigned] = lineArray[1];
                cityValueToAssigned++;
            } else {
                col = cityValue;
            }
            //put weight (lineArray[2]) in matrix
            graph_matrix[row][col] = Double.parseDouble(lineArray[2]);
            graph_matrix[col][row] = Double.parseDouble(lineArray[2]);
        }

        //input huristics for the cities
        //  lineArray = new String[2];
        for (int i = 0; i < cities; i++) {
            stringLine = bufferedReader.readLine();
            lineArray = stringLine.split(" ");
            //System.out.println(lineArray[0]+" "+lineArray[1]); 

            int cityValue = checkCityValueAssigned(lineArray[0]);
            if (cityValue >= 0) {//city found, assigned value is index for huristics
                huristics[cityValue] = Double.parseDouble(lineArray[1]);
            } else {
                System.out.println("city not found, check input");
            }
        }

        source = checkCityValueAssigned(start);//assign value of start city
        destination = checkCityValueAssigned(end);//assign value of end city
    }

    public void Dijkstra() {
        for (int i = 1; i < distance.length; i++) {//initial distance infinity
            distance[i] = 99999;
        }

        distance[source] = 0;
        for (int v = 1; v <= cities; v++) {
            queue.add(v);
        }

        Dijkstra_visit();

        printResult();
    }

    public void Dijkstra_visit() {

        while (!queue.isEmpty()) {
            Priority.setPriority(queue, distance);
            int ParentVer = (int) queue.poll();

            //check for all adjacency vertices of the visisted vertex
            for (int adjacent = 1; adjacent < graph_matrix[0].length; adjacent++) {
                if (graph_matrix[ParentVer][adjacent] > 0) {//weight exists
                    if (distance[adjacent] > distance[ParentVer] + graph_matrix[ParentVer][adjacent]) {
                        distance[adjacent] = distance[ParentVer] + graph_matrix[ParentVer][adjacent];
                        parent[adjacent] = ParentVer;//keeps parent ver
                    }
                }
            }
        }
    }

    //print distance and path
    public void printResult() {
        //cost from source to each node
//        for (int c = 2; c < distance.length; c++) {
//            System.out.println("distance from " + source + " to " + c + ": " + distance[c]);
//        }
        
        //path from source to destination
        int dest = destination;
        String path = "" + city_list[dest];
        while (true) {
            path = city_list[parent[dest]] + " --> " + path;
            dest = parent[dest];
            if (dest == source) {
                break;
            }
        }
        System.out.println("Shortest path: "+path);
        
        System.out.println("Cost from "+start+" to "+end+": "+distance[destination]);
    }
    
    //take a city name and check if it is already assigned with a value
    //if assigned, return the assigned number, otherwise return -1
    public int checkCityValueAssigned(String city_name) {
        for (int i = 1; i < cityValueToAssigned; i++) {
            if (city_list[i] != null) {
                if (city_list[i].equals(city_name)) {
                    return i;
                }
            }
        }
        return -1;
    }

    //print graphMatrix    
    public void showGraph() {
        for (int i = 0; i < graph_matrix.length; i++) {
            for (int j = 0; j < graph_matrix[0].length; j++) {
                System.out.print(graph_matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
