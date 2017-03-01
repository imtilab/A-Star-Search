# A* Search (A Star Search)

![alt tag](https://2.bp.blogspot.com/-JBy0uOfnD3c/WLccSY11QBI/AAAAAAAAAdY/RANjsVPQN38KzSPHARFul39jiDyKR8SCwCLcB/s1600/astar.jpg)
<p>
You are given a graph where the nodes denote cities and each edge denotes the distance of two connecting cities. Each node has a heuristic associated with it. The heuristic is the actual geological distance between a city and the destination city (Blue node). Now find the shortest path from the starting city (yellow node) to the destination city.
</p>
Recall that in A* search, the cost of a node can be denoted by a function f(n) where:
```
f(n) = g(n) + h(n)
```
Here, 
  - g(n) = Known cost of getting from the initial city to n. (path cost)
  - h(n)= Heuristic estimate of the cost to get from n to the destination city.

<p>
Note that, you can use Dijkstra to find g(n). The heuristic value for each node n, h(n)  with respect to a goal node will be provided in the input set.
</p>

Input:
```
7 7 (Graph Dimension)
s (Starting Location)
f (Destination Location)
s d 2
s a 1.5
a b 2
b c 3
c f 4
e f 2
d e 3
s d 2
s 10
a 4
b 2
c 4
e 2
d 4.5
f 0
```

Output:
```
1. Shortest Path from source s to destination f. 
2. Cost from source to destination.
```

A* Search Pseudo code:
```
initialize the open list
initialize the closed list
put the starting node on the open list (you can leave its f at zero)
 
while the open list is not empty
    find the node with the least f on the open list, call it "q"
    pop q off the open list
    generate q's 8 successors and set their parents to q
    for each successor
    	if successor is the goal, stop the search
        successor.g = q.g + distance between successor and q
        successor.h = distance from goal to successor
        successor.f = successor.g + successor.h
 
        if a node with the same position as successor is in the OPEN list \
            which has a lower f than successor, skip this successor
        if a node with the same position as successor is in the CLOSED list \ 
            which has a lower f than successor, skip this successor
        otherwise, add the node to the open list
    end
    push q on the closed list
end

```
