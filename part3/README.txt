Valentina Munoz 324568377
Moris Amon 318009198

Possible generate algorithms:
- Simple
- GrowingTree: needs a select cell method. 

Possible select cell methods:
- Last
- Random

Possible search algorithms:
- DFS
- BFS: needs a comparator.

Possible comparators:
- Cost

Possible commands:
- dir path
- generate_maze mazeName mazeFloors mazeRows mazeCols generateAlgorithm selectCellMethod(if needed)
- display mazeName
- display_cross_section mazeName index section(z/y/x)
- save_maze mazeName fileName
- load_maze fileName mazeName
- solve mazeName searchAlgorithm comparator(if needed)
- display_solution mazeName
- exit
