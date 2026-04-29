datatype state = UNVISITED | VISITING | VISITED

type graph = ((int * int list) list)
(*list of int*int == this.node * neighbor.node *)

datatype result = Order of int list
                | Cycle of int list
(* works like topoSortResult variables*)

fun neigborsOf graph node = case List.find (fn (n,_) => n = node) graph of
                                 SOME (_,neighbors) => neighbors
                               | NONE => []

fun getState stateList node = case List.find (fn (n,_) => n = node) stateList of
                                 SOME (_,state) => state
                               | NONE => []

fun setState stateList node newState = (node,newState) :: 
                                       List.filter (fn(n,_) => n <> node) stateList

fun dfs graph currentNode stateList resultStack currentPath =
    let
        val updatedStates = setState stateList currentNode VISITING
        val updatedPath = currentNode :: currentPath

        fun visitNeighbors [] (states , currentStack) = 
                (NONE, states, currentStack)
            | visitNeighbors (neighbor::remaniningNeihbors) (states, currentStack) =  
                case getState states neighbor of
                    UNVISITED => 
                        let
                            val (resule, newStates, newStack) = 
                                dfs graph neighbor states currentStack updatedPath
                        in
                            case result of
                                SOME cycle => (SOME cycle, newStates, newStack)
                                | NONE => visitNeighbors remaniningNeihbors (newStates, newStack)
                        end
                    | VISITING => 
                        (SOME (neighbor :: updatedPath), states, currentStack)
                    | VISITED =>
                        visitNeighbors remaniningNeihbors (states, currentStack)
        val (cycleOption, finalStates, finalStack) = 
            visitNeighbors (neigborsOf graph currentNode) (updatedStates, resultStack)
    in
        case cycleOption of
            SOME cycle => (SOME cycle, finalStates, finalStack)
            | NONE =>
                let
                    val finishedStates = setState finalStates currentNode VISITED
                in
                    (NONE, finishedStates, currentNode::finalStack)
                end
    end