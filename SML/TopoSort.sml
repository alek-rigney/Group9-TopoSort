(*I did this in sosml then pasted here, I didnt have a sml interpreter downloaded*)
(*FORMAT: val g = [(1,[2,3]),(2,[5]),(3,[4]),(4,[5]),(5,[])];*)
(*        topoSort g;                                        *)
(*g is written as 1 is pointing to 2,3 - 2 pointint to 5, etc*)
(*not implemented is eaasier way to enter values like java*)
datatype state = UNVISITED | VISITING | VISITED

type graph = ((int * int list) list)
(*list of int*int == this.node * neighbor.node *)

datatype result = Order of int list
                | Cycle of int list
(* works like topoSortResult variables*)

fun neighborsOf graph node = case List.find (fn (n,_) => n = node) graph of
                                 SOME (_,neighbors) => neighbors
                               | NONE => [];

fun getState stateList node = case List.find (fn (n,_) => n = node) stateList of
                                 SOME (_,state) => state
                               | NONE => UNVISITED;

fun setState stateList node newState = (node,newState) :: 
                                       List.filter (fn(n,_) => n <> node) stateList;

fun dfs graph currentNode stateList resultStack currentPath =
    let
        val updatedStates = setState stateList currentNode VISITING
        val updatedPath = currentNode :: currentPath

        fun visitNeighbors [] (states , stack) = 
        (*matches pretty closely to java dfs for this internal function*)
                (NONE, states, stack)
            | visitNeighbors (neighbor::remaniningNeighbors) (states, stack) =  
                case getState states neighbor of
                    UNVISITED => 
                        let
                            val (result, newStates, newStack) = 
                                dfs graph neighbor states stack updatedPath
                        in
                            case result of
                                SOME cycle => (SOME cycle, newStates, newStack)
                                (* cycle != null return cycle to cycleopt*)
                                | NONE => visitNeighbors remaniningNeighbors (newStates, newStack)
                                (*search deeper*)
                        end
                    | VISITING => 
                        (SOME (neighbor :: updatedPath), states, stack)
                        (*cycle found return SOME*)
                    | VISITED =>
                        visitNeighbors remaniningNeighbors (states, stack)
                        (*this one is visited, check other neighbors*)
        val (cycleOption, finalStates, finalStack) = 
            visitNeighbors (neighborsOf graph currentNode) (updatedStates, resultStack)
    in
        case cycleOption of
            SOME cycle => (SOME cycle, finalStates, finalStack)
            | NONE =>
                let
                    val finishedStates = setState finalStates currentNode VISITED
                in
                    (NONE, finishedStates, currentNode::finalStack)
                    (*no cycle, topoSort sees NONE and processes rest*)
                end
    end;

fun topoSort graph nodeList = 
    let
        fun process [] stateList stack = Order (stack)
            | process (node::rem) stateList stack = 
                if getState stateList node = UNVISITED then
                    let
                        val (cycleOpt, newStates, newStack) =
                            dfs graph node stateList stack []
                    in
                        case cycleOpt of
                            SOME cycle => Cycle cycle
                          | NONE => process rest newStates newStack
                    end
                else
                    process rest stateList stack
    in
        process nodeList [] []
    end;
