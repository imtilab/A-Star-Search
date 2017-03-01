
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
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
public class Map {

    Queue<Integer> openList_queue = new LinkedList<>();
    int closedList[];
    int closed_index = 1;

    double[] huristics;//Straight-Line-Distance (huristics)
    double gn[];//known path cost so far from start to n.
    double fn[];//Heuristic, estimate cost from n to the destination.

    int cities;//number of cities
    int roads;//number of roads
    String start;//start city name
    String end;//destination city name
    String city_list[];//city name list

    double[][] graph_matrix;
    int parent[];
    int source, destination;

    int cityValue_toAssign_index;//city value assign index

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
        graph_matrix = new double[cities + 1][cities + 1];
        city_list = new String[cities + 1];
        closedList = new int[cities + 1];
        huristics = new double[cities + 1];
        gn = new double[cities + 1];
        fn = new double[cities + 1];

        parent = new int[cities + 1];

        //input all pairs of city names and assign weights
        cityValue_toAssign_index = 1;
        for (int i = 0; i < roads; i++) {
            stringLine = bufferedReader.readLine();
            lineArray = stringLine.split(" ");
            //System.out.println(lineArray[0]+" "+lineArray[1]+" "+lineArray[2]);

            int row = cityValue_toAssign_index;
            int cityValue = checkCityValueAssigned(lineArray[0]);

            if (cityValue < 0) {//cityValue not assigned yet
                city_list[cityValue_toAssign_index] = lineArray[0];
                cityValue_toAssign_index++;
            } else {//assigned
                row = cityValue;
            }

            int col = cityValue_toAssign_index;
            cityValue = checkCityValueAssigned(lineArray[1]);

            if (cityValue < 0) {
                city_list[cityValue_toAssign_index] = lineArray[1];
                cityValue_toAssign_index++;
            } else {
                col = cityValue;
            }
            //put weight (lineArray[2]) in matrix
            graph_matrix[row][col] = Double.parseDouble(lineArray[2]);
            graph_matrix[col][row] = Double.parseDouble(lineArray[2]);
        }

        //input huristics for the cities
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

    //A start algo

    public void A_star() {
        openList_queue.add(source);
        fn[source] = 0;//not important as initially all 0

        A_star_visit();

        printResult();
    }

    public void A_star_visit() {

        while (!openList_queue.isEmpty()) {
            Priority.setPriority(openList_queue, fn);//sort queue based on fn value
            int parentVer = (int) openList_queue.poll();

            if (parentVer == destination) {//reached to destination
                break;
            }

            //check for all adjacency vertices of the parent vertex
            for (int adjacentVer = 1; adjacentVer < graph_matrix[0].length; adjacentVer++) {

                if (graph_matrix[parentVer][adjacentVer] > 0 && !isClosed(adjacentVer)) {//weight exists and not closed

                    gn[adjacentVer] = gn[parentVer] + graph_matrix[parentVer][adjacentVer];
                    fn[adjacentVer] = huristics[adjacentVer] + gn[adjacentVer];

                    parent[adjacentVer] = parentVer;//keeps parent ver
                    openList_queue.add(adjacentVer);//add to open list
                }
            }
            closedList[closed_index++] = parentVer;//add to closed list
        }
    }

    //take a node value, 
    //if it is in closed list return true otherwise return false

    public boolean isClosed(int adjacent) {
        for (int i = 1; i < closed_index; i++) {
            if (closedList[i] == adjacent) {
                return true;
            }
        }
        return false;
    }

    //print distance and path
    public void printResult() {
        //cost from source to each node
//        for (int c = 2; c < gn.length; c++) {
//            System.out.println("distance from " + start + " to " + city_list[c] + ": " + gn[c]);
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
        System.out.println("Shortest path: " + path);

        System.out.println("Cost from " + start + " to " + end + ": " + gn[destination]);
    }

    //take a city name and check if it is already assigned with a value
    //if assigned, return the assigned number, otherwise return -1
    public int checkCityValueAssigned(String city_name) {
        for (int i = 1; i < cityValue_toAssign_index; i++) {
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
