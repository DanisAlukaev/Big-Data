# Lab Block 9. GraphX
Author: Danis Alukaev \
Email: d.alukaev@innopolis.university \
Group: B19-DS-01 



### 1. Pregel function parameters 
The pregel is a data flow paradigm for large-scale graph processing. Essentially, this is the message-passing interface constrained to the edges of a graph. The computation of state for a given node depends only on the states of its neighbours.

A Pregel computation takes a graph and a corresponding set of vertex states as its inputs. At each iteration, referred to as a superstep, each vertex can send a message to its neighbors, process messages it received in a previous superstep, and update its state. Thus, each superstep consists of a round of messages being passed between neighbors and an update of the global vertex state.

Consider the parameters of [Scala implementation](https://github.com/apache/spark/blob/master/graphx/src/main/scala/org/apache/spark/graphx/Pregel.scala) of the Pregel function:
- `graph` - the input graph to be processed
- `initialMsg` - the message passed to each vertex at the first iteration
- `MaxIterations` - the maximal number of iterations (defaut: max integer)
- `activeDirection` - the direction [values: Out, Either, Both] of incident to the node edges where `sendMsg` should be applied
  - Out - apply to the out-edges of vertices recieved message at the previous iteration
  - Either - apply to edges, where one of the vertices recieved message at the previous iteration
  - Both - apply to edges, where both vertices recieved message at the previous iteration
- `vprog` - the user-defined program executed on each vertex, it computes the value of vertex after the message arrived
- `sendMsg` - the user-defined function that can be applied to incident edges
- `mergeMsg` - the user-defined function merging two messages of the same type into one 
  - Must be commulative and associative

### 2. GraphX usage cases 
The first example, where graph databases can be used is **fraudulent transactions detection**. The graph might include entities like users, products, transactions, and events. The objective here is the pattern finding (not path following).

The second example is **recommendation and personalization systems**, where the graph structure promotes search for insights allowing enterprises to influence the cutomers. The graph learning (GL) recommender systems is the [popular research topic](https://arxiv.org/pdf/2105.06339.pdf) nowadays.
### 3. Changing the graph structure.
The notebook with Scala script [is located](./GraphX.ipynb) in this repository.

Start the spykernel using:
```
docker run --dns=8.8.8.8 --rm -p 0.0.0.0:8888:8888 -p 0.0.0.0:4040:4040 -e JUPYTER_ENABLE_LAB=yes -v /home/danis:/home/jovyan/work jupyter/all-spark-notebook start-notebook.sh
```

In order make the vertex 1 most popular in the graph (by PageRank metric), there were
- changed direction of the first edge `(1, Ann) -is-friends-with-> (2, Bill)` to `(2, Bill) -is-friends-with-> (1, Ann)`
- removed edge `(4, Diane) -Likes-status-> (5, Went to the gym this morning)`
- added edge `(4, Diane) -Wrote-status-> (1, Ann)`

#### Before the change
![before](./documents/Current%20topology.png)

#### After the change
![before](./documents/New%20topology.png)
